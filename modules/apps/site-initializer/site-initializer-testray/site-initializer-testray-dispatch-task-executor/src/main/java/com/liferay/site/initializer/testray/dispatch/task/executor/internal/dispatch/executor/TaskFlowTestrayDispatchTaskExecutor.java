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

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
public class TaskFlowTestrayDispatchTaskExecutor extends BaseDispatchTaskExecutor{

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

		ObjectDefinition objectDefinition = _getObjectDefinition(
			objectDefinitionShortName);

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(properties);

		return _objectEntryManager.addObjectEntry(
			_defaultDTOConverterContext, objectDefinition, objectEntry, null);
	}

	private String _getFilterString(
		Collection<ObjectEntry> objectEntriesCollection, String fieldName) {

		List<ObjectEntry> objectEntries =
			(List<ObjectEntry>)objectEntriesCollection;

		StringBundler sb = new StringBundler();

		for (int i = 0; i <= (objectEntries.size() - 1); i++) {
			ObjectEntry objectEntry = objectEntries.get(i);

			sb.append(fieldName);
			sb.append(" eq '");
			sb.append(objectEntry.getId());

			if (i != (objectEntries.size() - 1)) {
				sb.append("' or ");
			}
			else {
				sb.append("'");
			}
		}

		return sb.toString();
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

	private long _increment(
			long companyId, String fieldName, String filterString,
			String objectDefinitionShortName)
		throws Exception {

		Page<ObjectEntry> objectEntriesPage =
			_objectEntryManager.getObjectEntries(
				companyId, _objectDefinitions.get(objectDefinitionShortName),
				null, null, _defaultDTOConverterContext, filterString, null,
				null,
				new Sort[] {
					new Sort("nestedFieldArray.value_long#" + fieldName, true)
				});

		ObjectEntry objectEntry = objectEntriesPage.fetchFirstItem();

		if (objectEntry == null) {
			return 1;
		}

		String fieldValue = (String)_getProperty(fieldName, objectEntry); //TODO fix get last number

		if (fieldValue == null) {
			return 1;
		}

		return fieldValue.longValue() + 1;
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
		long testrayTaskId = Long.valueOf(
			unicodeProperties.getProperty("testrayTaskId"));
		String[] testrayCaseTypeIds = StringUtil.split(
			unicodeProperties.getProperty("testrayCaseTypeIds"));

		// TODO

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

		String filterString = _getFilterString(
			testrayCaseObjectEntriesPage.getItems(), "caseId");

		Map<String, String> map = HashMapBuilder.put(
			"errors", "errors"
		).build();

		Aggregation aggregation = new Aggregation();

		aggregation.setAggregationTerms(map);

		Page<ObjectEntry> testrayCaseResultObjectEntriesPage1 =
			_getObjectEntries(companyId, "CaseResult", aggregation, null);

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

			Page<ObjectEntry> testrayCaseResultObjectEntriesPage2 =
				_getObjectEntries(
					companyId, "CaseResult", null,
					StringBundler.concat(
						"testrayBuild id eq '", testrayBuildId,
						"' and errors eq '",
						testrayCaseResultFacetValue.getTerm(), "' and ",
						filterString));

			List<ObjectEntry> testrayCaseResultObjectEntries =
				(List<ObjectEntry>)
					testrayCaseResultObjectEntriesPage2.getItems();

			for (ObjectEntry testrayCaseResultObjectEntry :
					testrayCaseResultObjectEntries) {

				Page<ObjectEntry> testrayCaseResultsIssuesObjectEntriesPage1 =
					_getObjectEntries(
						companyId, "CaseResultsIssues", null,
						"caseResultId eq '" +
							testrayCaseResultObjectEntry.getId() + "'");

				List<ObjectEntry> testrayCaseResultsIssuesObjectEntries =
					(List<ObjectEntry>)
						testrayCaseResultsIssuesObjectEntriesPage1.getItems();

				for (ObjectEntry testrayCaseResultsIssuesObjectEntry :
						testrayCaseResultsIssuesObjectEntries) {

					long issueId = (long)_getProperty(
						"issueId", testrayCaseResultsIssuesObjectEntry);

					Page<ObjectEntry> testrayIssueObejctEntriesPage =
						_getObjectEntries(
							companyId, "Issues", null,
							"id eq '" + issueId + "'");

					ObjectEntry testrayIssueObjectEntry =
						testrayIssueObejctEntriesPage.fetchFirstItem();

					if (testrayIssueObjectEntry != null) {
						String name = (String)_getProperty(
							"name", testrayIssueObjectEntry);

						List<ObjectEntry> matchingTestrayCaseResults =
							testrayCaseResultIssuesMap.get(name);

						if (matchingTestrayCaseResults == null) {
							matchingTestrayCaseResults = new ArrayList<>();

							testrayCaseResultIssuesMap.put(
								name, matchingTestrayCaseResults);
						}

						matchingTestrayCaseResults.add(
							testrayCaseResultsIssuesObjectEntry);
					}
				}

				testrayCaseResultGroups.addAll(
					testrayCaseResultIssuesMap.values());
			}

			Collections.sort(
				testrayCaseResultGroups,
				new Comparator<List<ObjectEntry>>() {

					public int compare(
						List<ObjectEntry> testrayCaseResultObjectEntries1,
						List<ObjectEntry> testrayCaseResultObjectEntries2) {

						int score1 = 0;
						int score2 = 0;

						try {
							for (ObjectEntry objectEntry :
									testrayCaseResultObjectEntries1) {

								score1 += (int)_getProperty(
									"priority", objectEntry);
							}

							for (ObjectEntry objectEntry :
									testrayCaseResultObjectEntries2) {

								score2 += (int)_getProperty(
									"priority", objectEntry);
							}
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

				_addObjectEntry(
					"Subtasks",
					HashMapBuilder.<String, Object>put(
						"name",
						"ST-" +
							_increment(
								companyId, "name",
								"taskId eq '" + testrayTaskId + "'", "Case")//TODO fix increment
					).put(
						"score", score
					).build());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TaskFlowTestrayDispatchTaskExecutor.class);

	private DefaultDTOConverterContext _defaultDTOConverterContext;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final Map<String, ObjectDefinition> _objectDefinitions =
		new HashMap<>();

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private UserLocalService _userLocalService;

}