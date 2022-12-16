/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.aggregation.Facet;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util.SiteInitializerTestrayDispatchTaskExecutorHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nilton Vieira
 */
@Component(
	property = {
		"dispatch.task.executor.cluster.mode=single-node",
		"dispatch.task.executor.feature.flag=LPS-166126",
		"dispatch.task.executor.name=testray-testflow",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testray-testflow"
	},
	service = DispatchTaskExecutor.class
)
public class SiteInitializerTestrayTestFlowDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		if (Validator.isNull(
				unicodeProperties.getProperty("testrayCaseTypeIds")) ||
			Validator.isNull(unicodeProperties.getProperty("testrayBuildId")) ||
			Validator.isNull(unicodeProperties.getProperty("testrayTaskId"))) {

			_log.error("The required properties are not set");

			return;
		}

		User user = _userLocalService.getUser(dispatchTrigger.getUserId());

		_siteInitializerTestrayDispatchTaskExecutorHelper.
			createDefaultDTOConverterContext(user);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		String originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		try {
			_siteInitializerTestrayDispatchTaskExecutorHelper.
				loadObjectDefinitions(dispatchTrigger.getCompanyId());

			_updateTestrayTaskStatus(
				unicodeProperties, _TESTRAY_TASK_STATUS_PROCESSING);

			_process(dispatchTrigger.getCompanyId(), unicodeProperties);

			_updateTestrayTaskStatus(
				unicodeProperties, _TESTRAY_TASK_STATUS_IN_ANALYSIS);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Override
	public String getName() {
		return "testray-testflow";
	}

	private String _getTestrayIssueNames(
			long companyId, ObjectEntry testrayCaseResultObjectEntry)
		throws Exception {

		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries =
			_siteInitializerTestrayDispatchTaskExecutorHelper.getObjectEntries(
				null, companyId,
				"caseResultId eq '" + testrayCaseResultObjectEntry.getId() +
					"'",
				"CaseResultsIssues", null);

		if (testrayCaseResultsIssuesObjectEntries.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		for (ObjectEntry testrayCaseResultsIssuesObjectEntry :
				testrayCaseResultsIssuesObjectEntries) {

			long issueId =
				(long)
					_siteInitializerTestrayDispatchTaskExecutorHelper.
						getProperty(
							"r_issueToCaseResultsIssues_c_issueId",
							testrayCaseResultsIssuesObjectEntry);

			ObjectEntry testrayIssueObjectEntry =
				_siteInitializerTestrayDispatchTaskExecutorHelper.
					getObjectEntry("Issue", issueId);

			sb.append(
				StringUtil.removeSubstring(
					(String)
						_siteInitializerTestrayDispatchTaskExecutorHelper.
							getProperty("name", testrayIssueObjectEntry),
					StringPool.DASH));

			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private int _getTestraySubtaskScore(List<ObjectEntry> objectEntries)
		throws Exception {

		int score = 0;

		for (ObjectEntry objectEntry : objectEntries) {
			Long testrayCaseId =
				(Long)
					_siteInitializerTestrayDispatchTaskExecutorHelper.
						getProperty("r_caseToCaseResult_c_caseId", objectEntry);

			ObjectEntry testrayCaseObjectEntry =
				_siteInitializerTestrayDispatchTaskExecutorHelper.
					getObjectEntry("Case", testrayCaseId);

			score +=
				(int)
					_siteInitializerTestrayDispatchTaskExecutorHelper.
						getProperty("priority", testrayCaseObjectEntry);
		}

		return score;
	}

	private void _process(long companyId, UnicodeProperties unicodeProperties)
		throws Exception {

		String[] testrayCaseTypeIds = StringUtil.split(
			unicodeProperties.getProperty("testrayCaseTypeIds"));

		StringBundler sb = new StringBundler();

		for (int i = 0; i <= (testrayCaseTypeIds.length - 1); i++) {
			sb.append("caseTypeId eq '");
			sb.append(testrayCaseTypeIds[i]);
			sb.append("'");
			sb.append(" or ");
		}

		sb.setIndex(sb.index() - 1);

		Aggregation aggregation = new Aggregation();

		aggregation.setAggregationTerms(
			HashMapBuilder.put(
				"errors", "errors"
			).build());

		long testrayBuildId = GetterUtil.getLong(
			unicodeProperties.getProperty("testrayBuildId"));

		Page<ObjectEntry> testrayCaseResultObjectEntriesPage1 =
			_siteInitializerTestrayDispatchTaskExecutorHelper.
				getObjectEntriesPage(
					aggregation, companyId,
					"buildId eq '" + testrayBuildId + "'", "CaseResult", null);

		List<Facet> testrayCaseResultFacets =
			(List<Facet>)testrayCaseResultObjectEntriesPage1.getFacets();

		Facet testrayCaseResultFacet = testrayCaseResultFacets.get(0);

		List<Facet.FacetValue> testrayCaseResultFacetValues =
			testrayCaseResultFacet.getFacetValues();

		List<Long> testrayCaseObjectEntriesIds = TransformUtil.transform(
			_siteInitializerTestrayDispatchTaskExecutorHelper.getObjectEntries(
				null, companyId, sb.toString(), "Case", null),
			ObjectEntry::getId);

		List<List<ObjectEntry>> testrayCaseResultGroups = new ArrayList<>();

		for (Facet.FacetValue testrayCaseResultFacetValue :
				testrayCaseResultFacetValues) {

			if (Objects.equals(testrayCaseResultFacetValue.getTerm(), "null")) {
				continue;
			}

			List<ObjectEntry> testrayCaseResultObjectEntries =
				_siteInitializerTestrayDispatchTaskExecutorHelper.
					getObjectEntries(
						null, companyId,
						StringBundler.concat(
							"buildId eq '", testrayBuildId, "' and errors eq '",
							StringUtil.removeChar(
								StringUtil.replace(
									testrayCaseResultFacetValue.getTerm(), '\'',
									"''"),
								'\\'),
							"'"),
						"CaseResult", null);

			testrayCaseResultObjectEntries.removeIf(
				objectEntry -> !testrayCaseObjectEntriesIds.contains(
					(Long)
						_siteInitializerTestrayDispatchTaskExecutorHelper.
							getProperty(
								"r_caseToCaseResult_c_caseId", objectEntry)));

			Map<String, List<ObjectEntry>> testrayCaseResultIssuesMap =
				new HashMap<>();

			for (ObjectEntry testrayCaseResultObjectEntry :
					testrayCaseResultObjectEntries) {

				String testrayIssueNames = _getTestrayIssueNames(
					companyId, testrayCaseResultObjectEntry);

				List<ObjectEntry> matchingTestrayCaseResults =
					testrayCaseResultIssuesMap.get(testrayIssueNames);

				if (matchingTestrayCaseResults == null) {
					matchingTestrayCaseResults = new ArrayList<>();

					testrayCaseResultIssuesMap.put(
						testrayIssueNames, matchingTestrayCaseResults);
				}

				matchingTestrayCaseResults.add(testrayCaseResultObjectEntry);
			}

			testrayCaseResultGroups.addAll(testrayCaseResultIssuesMap.values());
		}

		ListUtil.sort(
			testrayCaseResultGroups,
			new Comparator<List<ObjectEntry>>() {

				@Override
				public int compare(
					List<ObjectEntry> testrayCaseResultObjectEntries1,
					List<ObjectEntry> testrayCaseResultObjectEntries2) {

					int testraySubtaskScore1 = 0;
					int testraySubtaskScore2 = 0;

					try {
						testraySubtaskScore1 = _getTestraySubtaskScore(
							testrayCaseResultObjectEntries1);

						testraySubtaskScore2 = _getTestraySubtaskScore(
							testrayCaseResultObjectEntries2);
					}
					catch (Exception exception) {
						throw new RuntimeException(exception);
					}

					if (testraySubtaskScore1 > testraySubtaskScore2) {
						return -1;
					}
					else if (testraySubtaskScore1 < testraySubtaskScore2) {
						return 1;
					}

					return 0;
				}

			});

		long testrayTaskId = GetterUtil.getLong(
			unicodeProperties.getProperty("testrayTaskId"));

		for (List<ObjectEntry> testrayCaseResultObjectEntry :
				testrayCaseResultGroups) {

			long testraySubtaskNumber =
				_siteInitializerTestrayDispatchTaskExecutorHelper.
					incrementTestrayFieldValue(
						companyId, "number",
						"taskId eq '" + testrayTaskId + "'", "Subtask",
						new Sort[] {
							new Sort("nestedFieldArray.value_long#number", true)
						});

			ObjectEntry testraySubtaskObjectEntry =
				_siteInitializerTestrayDispatchTaskExecutorHelper.
					addObjectEntry(
						"Subtask",
						HashMapBuilder.<String, Object>put(
							"dueStatus", "OPEN"
						).put(
							"name", "ST-" + testraySubtaskNumber
						).put(
							"number", testraySubtaskNumber
						).put(
							"r_taskToSubtasks_c_taskId", testrayTaskId
						).put(
							"score",
							_getTestraySubtaskScore(
								testrayCaseResultObjectEntry)
						).build());

			for (ObjectEntry objectEntry : testrayCaseResultObjectEntry) {
				_siteInitializerTestrayDispatchTaskExecutorHelper.
					addObjectEntry(
						"SubtasksCasesResults",
						HashMapBuilder.<String, Object>put(
							"r_caseResultToSubtasksCasesResults_c_caseResultId",
							objectEntry.getId()
						).put(
							"r_subtaskToSubtasksCasesResults_c_subtaskId",
							String.valueOf(testraySubtaskObjectEntry.getId())
						).build());
			}
		}
	}

	private void _updateTestrayTaskStatus(
			UnicodeProperties unicodeProperties, String testrayTaskStatus)
		throws Exception {

		long testrayTaskId = GetterUtil.getLong(
			unicodeProperties.getProperty("testrayTaskId"));

		ObjectEntry objectEntry =
			_siteInitializerTestrayDispatchTaskExecutorHelper.getObjectEntry(
				"Task", testrayTaskId);

		Map<String, Object> map = objectEntry.getProperties();

		map.replace("dueStatus", testrayTaskStatus);

		objectEntry.setProperties(map);

		_siteInitializerTestrayDispatchTaskExecutorHelper.updateObjectEntry(
			"Task", objectEntry, testrayTaskId);
	}

	private static final String _TESTRAY_TASK_STATUS_IN_ANALYSIS = "INANALYSIS";

	private static final String _TESTRAY_TASK_STATUS_PROCESSING = "PROCESSING";

	private static final Log _log = LogFactoryUtil.getLog(
		SiteInitializerTestrayTestFlowDispatchTaskExecutor.class);

	@Reference
	private SiteInitializerTestrayDispatchTaskExecutorHelper
		_siteInitializerTestrayDispatchTaskExecutorHelper;

	@Reference
	private UserLocalService _userLocalService;

}