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

package com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Nilton Vieira
 */
public class SiteInitializerTestrayAutoFillUtil {

	public static void addTestrayCaseResultIssue(
			long companyId,
			DefaultDTOConverterContext defaultDTOConverterContext,
			ObjectEntryManager objectEntryManager, long testrayCaseResultId,
			String testrayIssueName)
		throws Exception {

		if (Validator.isNull(testrayIssueName)) {
			return;
		}

		SiteInitializerTestrayObjectUtil.addObjectEntry(
			defaultDTOConverterContext, "CaseResultsIssues", objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"r_caseResultToCaseResultsIssues_c_caseResultId",
				testrayCaseResultId
			).put(
				"r_issueToCaseResultsIssues_c_issueId",
				() -> {
					Page<ObjectEntry> objectEntriesPage =
						SiteInitializerTestrayObjectUtil.getObjectEntriesPage(
							null, companyId, defaultDTOConverterContext,
							"name eq '" + testrayIssueName + "'", "Issue",
							objectEntryManager, null);

					ObjectEntry objectEntry =
						objectEntriesPage.fetchFirstItem();

					if (objectEntry.getId() > 0) {
						return objectEntry.getId();
					}

					objectEntry =
						SiteInitializerTestrayObjectUtil.addObjectEntry(
							defaultDTOConverterContext, "Issue",
							objectEntryManager,
							HashMapBuilder.<String, Object>put(
								"name", testrayIssueName
							).build());

					return objectEntry.getId();
				}
			).build());
	}

	public static void testrayAutoFillBuilds(
			long companyId,
			DefaultDTOConverterContext defaultDTOConverterContext,
			ObjectEntryManager objectEntryManager,
			ObjectEntry testrayBuildObjectEntry1,
			ObjectEntry testrayBuildObjectEntry2)
		throws Exception {

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries1 =
			_getTestrayCaseResultObjectEntriesByBuild(
				companyId, defaultDTOConverterContext, objectEntryManager,
				testrayBuildObjectEntry1);

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries2 =
			_getTestrayCaseResultObjectEntriesByBuild(
				companyId, defaultDTOConverterContext, objectEntryManager,
				testrayBuildObjectEntry2);

		for (Map.Entry<Long, List<ObjectEntry>> entry :
				testrayCaseResultObjectEntries1.entrySet()) {

			List<ObjectEntry> testrayCaseResultCompositesB =
				testrayCaseResultObjectEntries2.get(entry.getKey());

			if (testrayCaseResultCompositesB == null) {
				continue;
			}

			List<ObjectEntry> testrayCaseResultCompositesA = entry.getValue();

			for (ObjectEntry testrayCaseResultCompositeA :
					testrayCaseResultCompositesA) {

				String testrayCaseResultErrors1 =
					(String)SiteInitializerTestrayObjectUtil.getProperty(
						"errors", testrayCaseResultCompositeA);

				if (Validator.isNull(testrayCaseResultErrors1)) {
					continue;
				}

				for (ObjectEntry testrayCaseResultCompositeB :
						testrayCaseResultCompositesB) {

					String testrayCaseResultErrors2 =
						(String)SiteInitializerTestrayObjectUtil.getProperty(
							"errors", testrayCaseResultCompositeB);

					if (Validator.isNull(testrayCaseResultErrors2) ||
						!Objects.equals(
							testrayCaseResultErrors1,
							testrayCaseResultErrors2)) {

						continue;
					}

					_testrayAutoFillCaseResults(
						companyId, defaultDTOConverterContext,
						objectEntryManager, testrayCaseResultCompositeA,
						testrayCaseResultCompositeB);
				}
			}
		}
	}

	public static void testrayAutoFillRuns(
			long companyId,
			DefaultDTOConverterContext defaultDTOConverterContext,
			ObjectEntryManager objectEntryManager,
			ObjectEntry testrayRunObjectEntry1,
			ObjectEntry testrayRunObjectEntry2)
		throws Exception {

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries1 =
			_getTestrayCaseResultObjectEntriesByRun(
				companyId, defaultDTOConverterContext, objectEntryManager,
				testrayRunObjectEntry1);

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries2 =
			_getTestrayCaseResultObjectEntriesByRun(
				companyId, defaultDTOConverterContext, objectEntryManager,
				testrayRunObjectEntry2);

		for (Map.Entry<Long, ObjectEntry> entry :
				testrayCaseResultObjectEntries1.entrySet()) {

			ObjectEntry testrayCaseResultObjectEntry2 =
				testrayCaseResultObjectEntries2.get(entry.getKey());

			if (testrayCaseResultObjectEntry2 == null) {
				continue;
			}

			ObjectEntry testrayCaseResultObjectEntry1 = entry.getValue();

			String testrayCaseResultErrors1 =
				(String)SiteInitializerTestrayObjectUtil.getProperty(
					"errors", testrayCaseResultObjectEntry1);

			String testrayCaseResultErrors2 =
				(String)SiteInitializerTestrayObjectUtil.getProperty(
					"errors", testrayCaseResultObjectEntry2);

			if (Validator.isNull(testrayCaseResultErrors1) ||
				Validator.isNull(testrayCaseResultErrors2) ||
				!Objects.equals(
					testrayCaseResultErrors1, testrayCaseResultErrors2)) {

				continue;
			}

			_testrayAutoFillCaseResults(
				companyId, defaultDTOConverterContext, objectEntryManager,
				testrayCaseResultObjectEntry1, testrayCaseResultObjectEntry2);
		}
	}

	private static Map<Long, List<ObjectEntry>>
			_getTestrayCaseResultObjectEntriesByBuild(
				long companyId,
				DefaultDTOConverterContext defaultDTOConverterContext,
				ObjectEntryManager objectEntryManager,
				ObjectEntry testrayBuildObjectEntry)
		throws Exception {

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries =
			new HashMap<>();

		List<ObjectEntry> objectEntries =
			SiteInitializerTestrayObjectUtil.getObjectEntries(
				null, companyId, defaultDTOConverterContext,
				"buildId eq '" + testrayBuildObjectEntry.getId() + "'",
				"CaseResult", objectEntryManager, null);

		for (ObjectEntry objectEntry : objectEntries) {
			long testrayCaseId =
				(Long)SiteInitializerTestrayObjectUtil.getProperty(
					"r_caseToCaseResult_c_caseId", objectEntry);

			List<ObjectEntry> matchingTestrayCaseResults =
				testrayCaseResultObjectEntries.get(testrayCaseId);

			if (matchingTestrayCaseResults == null) {
				matchingTestrayCaseResults = new ArrayList<>();

				testrayCaseResultObjectEntries.put(
					testrayCaseId, matchingTestrayCaseResults);
			}

			matchingTestrayCaseResults.add(objectEntry);
		}

		return testrayCaseResultObjectEntries;
	}

	private static Map<Long, ObjectEntry>
			_getTestrayCaseResultObjectEntriesByRun(
				long companyId,
				DefaultDTOConverterContext defaultDTOConverterContext,
				ObjectEntryManager objectEntryManager,
				ObjectEntry testrayRunObjectEntry)
		throws Exception {

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries = new HashMap<>();

		for (ObjectEntry objectEntry :
				SiteInitializerTestrayObjectUtil.getObjectEntries(
					null, companyId, defaultDTOConverterContext,
					"runId eq '" + testrayRunObjectEntry.getId() + "'",
					"CaseResult", objectEntryManager, null)) {

			testrayCaseResultObjectEntries.put(
				(Long)SiteInitializerTestrayObjectUtil.getProperty(
					"r_caseToCaseResult_c_caseId", objectEntry),
				objectEntry);
		}

		return testrayCaseResultObjectEntries;
	}

	private static void _testrayAutoFillCaseResults(
			long companyId,
			DefaultDTOConverterContext defaultDTOConverterContext,
			ObjectEntryManager objectEntryManager,
			ObjectEntry testrayCaseResultObjectEntry1,
			ObjectEntry testrayCaseResultObjectEntry2)
		throws Exception {

		ObjectEntry destinationTestrayCaseResultObjectEntry = null;
		ObjectEntry sourceTestrayCaseResultObjectEntry = null;
		List<ObjectEntry> sourceTestrayCaseResultsIssuesObjectEntries = null;

		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries1 =
			SiteInitializerTestrayObjectUtil.getObjectEntries(
				null, companyId, defaultDTOConverterContext,
				"caseResultId eq '" + testrayCaseResultObjectEntry1.getId() +
					"'",
				"CaseResultsIssues", objectEntryManager, null);

		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries2 =
			SiteInitializerTestrayObjectUtil.getObjectEntries(
				null, companyId, defaultDTOConverterContext,
				"caseResultId eq '" + testrayCaseResultObjectEntry2.getId() +
					"'",
				"CaseResultsIssues", objectEntryManager, null);

		if (((Long)SiteInitializerTestrayObjectUtil.getProperty(
				"r_userToCaseResults_userId", testrayCaseResultObjectEntry1) >
					0) &&
			!testrayCaseResultsIssuesObjectEntries1.isEmpty() &&
			((Long)SiteInitializerTestrayObjectUtil.getProperty(
				"r_userToCaseResults_userId", testrayCaseResultObjectEntry2) <=
					0) &&
			testrayCaseResultsIssuesObjectEntries2.isEmpty()) {

			destinationTestrayCaseResultObjectEntry =
				testrayCaseResultObjectEntry2;
			sourceTestrayCaseResultObjectEntry = testrayCaseResultObjectEntry1;
			sourceTestrayCaseResultsIssuesObjectEntries =
				testrayCaseResultsIssuesObjectEntries1;
		}
		else if (((Long)SiteInitializerTestrayObjectUtil.getProperty(
					"r_userToCaseResults_userId",
					testrayCaseResultObjectEntry1) <= 0) &&
				 testrayCaseResultsIssuesObjectEntries1.isEmpty() &&
				 ((Long)SiteInitializerTestrayObjectUtil.getProperty(
					 "r_userToCaseResults_userId",
					 testrayCaseResultObjectEntry2) > 0) &&
				 !testrayCaseResultsIssuesObjectEntries2.isEmpty()) {

			destinationTestrayCaseResultObjectEntry =
				testrayCaseResultObjectEntry1;
			sourceTestrayCaseResultObjectEntry = testrayCaseResultObjectEntry2;
			sourceTestrayCaseResultsIssuesObjectEntries =
				testrayCaseResultsIssuesObjectEntries2;
		}

		if ((destinationTestrayCaseResultObjectEntry == null) ||
			(sourceTestrayCaseResultObjectEntry == null)) {

			return;
		}

		Map<String, Object> properties =
			destinationTestrayCaseResultObjectEntry.getProperties();

		properties.put(
			"dueStatus",
			SiteInitializerTestrayObjectUtil.getProperty(
				"dueStatus", sourceTestrayCaseResultObjectEntry));
		properties.put(
			"r_userToCaseResults_userId",
			SiteInitializerTestrayObjectUtil.getProperty(
				"r_userToCaseResults_userId",
				sourceTestrayCaseResultObjectEntry));

		SiteInitializerTestrayObjectUtil.updateObjectEntry(
			defaultDTOConverterContext, "CaseResult",
			destinationTestrayCaseResultObjectEntry,
			destinationTestrayCaseResultObjectEntry.getId(),
			objectEntryManager);

		for (ObjectEntry sourceTestrayCaseResultsIssuesObjectEntry :
				sourceTestrayCaseResultsIssuesObjectEntries) {

			long testrayIssueId =
				(long)SiteInitializerTestrayObjectUtil.getProperty(
					"r_issueToCaseResultsIssues_c_issueId",
					sourceTestrayCaseResultsIssuesObjectEntry);

			ObjectEntry testrayIssueObjectEntry =
				SiteInitializerTestrayObjectUtil.getObjectEntry(
					defaultDTOConverterContext, "Issue", testrayIssueId,
					objectEntryManager);

			if (testrayIssueObjectEntry == null) {
				continue;
			}

			addTestrayCaseResultIssue(
				companyId, defaultDTOConverterContext, objectEntryManager,
				destinationTestrayCaseResultObjectEntry.getId(),
				(String)SiteInitializerTestrayObjectUtil.getProperty(
					"name", testrayIssueObjectEntry));
		}
	}

}