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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.aggregation.Facet;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.Collections;
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
		"dispatch.task.executor.name=testflow-testray",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testflow-testray"
	},
	service = DispatchTaskExecutor.class
)
public class TaskFlowTestrayDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		if (Validator.isNull(unicodeProperties.getProperty("testrayBuildId")) ||
			Validator.isNull(unicodeProperties.getProperty("testrayTaskId")) ||
			Validator.isNull(
				unicodeProperties.getProperty("testrayCaseTypeIds"))) {

			_log.error("The required properties are not set");

			return;
		}

		User user = _userLocalService.getUser(dispatchTrigger.getUserId());

		_defaultDTOConverterContext = new DefaultDTOConverterContext(
			false, null, null, null, null, LocaleUtil.getSiteDefault(), null,
			user);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		String originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		try {
			_loadObjectDefinitions(dispatchTrigger.getCompanyId());
			_process(dispatchTrigger.getCompanyId(), unicodeProperties);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Override
	public String getName() {
		return "testray";
	}

	private ObjectEntry _addObjectEntry(
			String objectDefinitionShortName, Map<String, Object> properties)
		throws Exception {

		ObjectDefinition objectDefinition = _objectDefinitions.get(
			objectDefinitionShortName);

		if (objectDefinition == null) {
			throw new PortalException(
				"No object definition found with short name " +
					objectDefinitionShortName);
		}

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(properties);

		return _objectEntryManager.addObjectEntry(
			_defaultDTOConverterContext, objectDefinition, objectEntry, null);
	}

	private Page<ObjectEntry> _getObjectEntries(
			long companyId, String objectDefinitionName,
			Aggregation aggregation, String filter)
		throws Exception {

		return _objectEntryManager.getObjectEntries(
			companyId, _objectDefinitions.get(objectDefinitionName), null,
			aggregation, _defaultDTOConverterContext, filter, null, null, null);
	}

	private Object _getProperty(String key, ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return properties.get(key);
	}


	private String _getTestrayIssueNames(
			long companyId, ObjectEntry testrayCaseResultObjectEntry)
		throws Exception {

		Page<ObjectEntry> testrayCaseResultsIssuesObjectEntriesPage1 =
			_getObjectEntries(
				companyId, "CaseResultsIssues", null,
				"caseResultId eq '" + testrayCaseResultObjectEntry.getId() +
					"'");

		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries =
			(List<ObjectEntry>)
				testrayCaseResultsIssuesObjectEntriesPage1.getItems();

		if (testrayCaseResultsIssuesObjectEntries.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		for (ObjectEntry testrayCaseResultsIssuesObjectEntry :
				testrayCaseResultsIssuesObjectEntries) {

			long issueId = (long)_getProperty(
				"r_issueToCaseResultsIssues_c_issueId",
				testrayCaseResultsIssuesObjectEntry);

			Page<ObjectEntry> testrayIssueObejctEntriesPage = _getObjectEntries(
				companyId, "Issue", null, "id eq '" + issueId + "'");

			ObjectEntry testrayIssueObjectEntry =
				testrayIssueObejctEntriesPage.fetchFirstItem();

			sb.append(
				StringUtil.removeSubstring(
					(String)_getProperty("name", testrayIssueObjectEntry),
					StringPool.DASH));

			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private long _increment(
			long companyId, String fieldName, String filterString,
			String objectDefinitionShortName)
		throws Exception {

		Page<ObjectEntry> objectEntriesPage =
			_objectEntryManager.getObjectEntries(
				companyId, _objectDefinitions.get(objectDefinitionShortName),
				null, null, _defaultDTOConverterContext, filterString, null,
				null, new Sort[] {new Sort("createDate" + fieldName, true)});

		ObjectEntry objectEntry = objectEntriesPage.fetchFirstItem();

		if (objectEntry == null) {
			return 1;
		}

		String fieldValue = (String)_getProperty(fieldName, objectEntry);

		if (fieldValue == null) {
			return 1;
		}

		return Long.valueOf(StringUtil.extractDigits(fieldValue)) + 1;
	}

	private void _loadObjectDefinitions(long companyId) {
		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				companyId, true, WorkflowConstants.STATUS_APPROVED);

		if (ListUtil.isEmpty(objectDefinitions)) {
			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			_objectDefinitions.put(
				objectDefinition.getShortName(), objectDefinition);
		}
	}

	private void _process(long companyId, UnicodeProperties unicodeProperties)
		throws Exception {

		long testrayBuildId = Long.valueOf(
			unicodeProperties.getProperty("testrayBuildId"));
		String[] testrayCaseTypeIds = StringUtil.split(
			unicodeProperties.getProperty("testrayCaseTypeIds"));
		long testrayTaskId = Long.valueOf(
			unicodeProperties.getProperty("testrayTaskId"));

		List<List<ObjectEntry>> testrayCaseResultGroups = new ArrayList<>();
		Map<String, List<ObjectEntry>> testrayCaseResultIssuesMap =
			new HashMap<>();

		StringBundler sb = new StringBundler();

		for (int i = 0; i <= (testrayCaseTypeIds.length - 1); i++) {
			sb.append("caseTypeId eq '");
			sb.append(testrayCaseTypeIds[i]);

			if (i != (testrayCaseTypeIds.length - 1)) {
				sb.append("' or ");
			}
			else {
				sb.append("'");
			}
		}

		String filter = sb.toString();

		Page<ObjectEntry> testrayCaseObjectEntriesPage = _getObjectEntries(
			companyId, "Case", null, filter);

		List<Long> testrayCaseObjectEntriesIds = TransformUtil.transform(
			testrayCaseObjectEntriesPage.getItems(),
			objectEntry -> objectEntry.getId());

		Map<String, String> map = HashMapBuilder.put(
			"errors", "errors"
		).build();

		Aggregation aggregation = new Aggregation();

		aggregation.setAggregationTerms(map);

		Page<ObjectEntry> testrayCaseResultObjectEntriesPage1 =
			_getObjectEntries(
				companyId, "CaseResult", aggregation,
				"buildId eq '" + testrayBuildId + "'");

		List<Facet> testrayCaseResultFacets =
			(List<Facet>)testrayCaseResultObjectEntriesPage1.getFacets();

		Facet testrayCaseResultFacet = testrayCaseResultFacets.get(0);

		List<Facet.FacetValue> testrayCaseResultFacetValues =
			testrayCaseResultFacet.getFacetValues();

		for (Facet.FacetValue testrayCaseResultFacetValue :
				testrayCaseResultFacetValues) {

			if (Objects.equals(testrayCaseResultFacetValue.getTerm(), "null")) {
				continue;
			}

			String escapeErrors = StringUtil.removeChar(
				StringUtil.replace(
					testrayCaseResultFacetValue.getTerm(), '\'', "''"),
				'\\');

			Page<ObjectEntry> testrayCaseResultObjectEntriesPage2 =
				_objectEntryManager.getObjectEntries(
					companyId, _objectDefinitions.get("CaseResult"), null, null,
					_defaultDTOConverterContext,
					StringBundler.concat(
						"buildId eq '", testrayBuildId, "' and errors eq '",
						escapeErrors, "'"),
					null, null, null);

			List<ObjectEntry> testrayCaseResultObjectEntries =
				(List<ObjectEntry>)
					testrayCaseResultObjectEntriesPage2.getItems();

			testrayCaseResultObjectEntries.removeIf(
				objectEntry -> !testrayCaseObjectEntriesIds.contains(
					(Long)_getProperty(
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

					int score1 = 0;
					int score2 = 0;

					try {
						score1 = _getScore(
							companyId, testrayCaseResultObjectEntries1);

						score2 = _getScore(
							companyId, testrayCaseResultObjectEntries2);
					}
					catch (Exception exception) {
						throw new RuntimeException(exception);
					}

					if (score1 > score2) {
						return -1;
					}
					else if (score1 < score2) {
						return 1;
					}

					return 0;
				}

			});

			for (List<ObjectEntry> testrayCaseResultObjectEntry :
					testrayCaseResultGroups) {

				int score = 0;

				for (ObjectEntry objectEntry : testrayCaseResultObjectEntry) {
					score += (int)_getProperty("priority", objectEntry);
				}

				long increment = _increment(
					companyId, "name", "taskId eq '" + testrayTaskId + "'",
					"Case");

				ObjectEntry testraySubtaskObjectEntry = _addObjectEntry(
					"Subtasks",
					HashMapBuilder.<String, Object>put(
						"name", "ST-" + increment
					).put(
						"score", score
					).put(
						"taskId", testrayTaskId
					).build());

				for (ObjectEntry objectEntry : testrayCaseResultObjectEntry) {
					_addObjectEntry(
						"SubtasksCasesResults",
						HashMapBuilder.<String, Object>put(
							"caseResultId", objectEntry.getId()
						).put(
							"subtaskId",
							String.valueOf(testraySubtaskObjectEntry.getId())
						).build());
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TaskFlowTestrayDispatchTaskExecutor.class);

	private DefaultDTOConverterContext _defaultDTOConverterContext;
	private final Map<String, ObjectDefinition> _objectDefinitions =
		new HashMap<>();

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private UserLocalService _userLocalService;

}