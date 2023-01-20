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
			long companyId, long testrayCaseResultId, String testrayIssueName,
			ObjectEntryManager objectEntryManager)
		throws Exception {

		if (Validator.isNull(testrayIssueName)) {
			return;
		}

		SiteInitializerTestrayObjectUtil.addObjectEntry(
			objectEntryManager, "CaseResultsIssues",
			HashMapBuilder.<String, Object>put(
				"r_caseResultToCaseResultsIssues_c_caseResultId",
				testrayCaseResultId
			).put(
				"r_issueToCaseResultsIssues_c_issueId",
				() -> {
					Page<ObjectEntry> objectEntriesPage =
						SiteInitializerTestrayObjectUtil.getObjectEntriesPage(
							null, companyId,
							"name eq '" + testrayIssueName + "'", "Issue", null,
							objectEntryManager);

					ObjectEntry objectEntry =
						objectEntriesPage.fetchFirstItem();

					if (objectEntry.getId() > 0) {
						return objectEntry.getId();
					}

					objectEntry =
						SiteInitializerTestrayObjectUtil.addObjectEntry(
							objectEntryManager, "Issue",
							HashMapBuilder.<String, Object>put(
								"name", testrayIssueName
							).build());

					return objectEntry.getId();
				}
			).build());
	}

	public static void testrayAutoFillBuilds(
			long companyId, ObjectEntry testrayBuildObjectEntry1,
			ObjectEntry testrayBuildObjectEntry2,
			ObjectEntryManager objectEntryManager)
		throws Exception {

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries1 =
			_getTestrayCaseResultObjectEntriesByBuild(
				companyId, testrayBuildObjectEntry1, objectEntryManager);

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries2 =
			_getTestrayCaseResultObjectEntriesByBuild(
				companyId, testrayBuildObjectEntry2, objectEntryManager);

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
						companyId, testrayCaseResultCompositeA,
						testrayCaseResultCompositeB, objectEntryManager);
				}
			}
		}
	}

	public static void testrayAutoFillRuns(
			long companyId, ObjectEntry testrayRunObjectEntry1,
			ObjectEntry testrayRunObjectEntry2,
			ObjectEntryManager objectEntryManager)
		throws Exception {

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries1 =
			_getTestrayCaseResultObjectEntriesByRun(
				companyId, testrayRunObjectEntry1, objectEntryManager);

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries2 =
			_getTestrayCaseResultObjectEntriesByRun(
				companyId, testrayRunObjectEntry2, objectEntryManager);

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
				companyId, testrayCaseResultObjectEntry1,
				testrayCaseResultObjectEntry2, objectEntryManager);
		}
	}

	private static Map<Long, List<ObjectEntry>>
			_getTestrayCaseResultObjectEntriesByBuild(
				long companyId, ObjectEntry testrayBuildObjectEntry,
				ObjectEntryManager objectEntryManager)
		throws Exception {

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries =
			new HashMap<>();

		List<ObjectEntry> objectEntries =
			SiteInitializerTestrayObjectUtil.getObjectEntries(
				null, companyId,
				"buildId eq '" + testrayBuildObjectEntry.getId() + "'",
				"CaseResult", null, objectEntryManager);

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
				long companyId, ObjectEntry testrayRunObjectEntry,
				ObjectEntryManager objectEntryManager)
		throws Exception {

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries = new HashMap<>();

		for (ObjectEntry objectEntry :
				SiteInitializerTestrayObjectUtil.getObjectEntries(
					null, companyId,
					"runId eq '" + testrayRunObjectEntry.getId() + "'",
					"CaseResult", null, objectEntryManager)) {

			testrayCaseResultObjectEntries.put(
				(Long)SiteInitializerTestrayObjectUtil.getProperty(
					"r_caseToCaseResult_c_caseId", objectEntry),
				objectEntry);
		}

		return testrayCaseResultObjectEntries;
	}

	private static void _testrayAutoFillCaseResults(
			long companyId, ObjectEntry testrayCaseResultObjectEntry1,
			ObjectEntry testrayCaseResultObjectEntry2,
			ObjectEntryManager objectEntryManager)
		throws Exception {

		ObjectEntry destinationTestrayCaseResultObjectEntry = null;
		ObjectEntry sourceTestrayCaseResultObjectEntry = null;
		List<ObjectEntry> sourceTestrayCaseResultsIssuesObjectEntries = null;

		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries1 =
			SiteInitializerTestrayObjectUtil.getObjectEntries(
				null, companyId,
				"caseResultId eq '" + testrayCaseResultObjectEntry1.getId() +
					"'",
				"CaseResultsIssues", null, objectEntryManager);

		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries2 =
			SiteInitializerTestrayObjectUtil.getObjectEntries(
				null, companyId,
				"caseResultId eq '" + testrayCaseResultObjectEntry2.getId() +
					"'",
				"CaseResultsIssues", null, objectEntryManager);

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
			"CaseResult", destinationTestrayCaseResultObjectEntry,
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
					"Issue", testrayIssueId, objectEntryManager);

			if (testrayIssueObjectEntry == null) {
				continue;
			}

			addTestrayCaseResultIssue(
				companyId, destinationTestrayCaseResultObjectEntry.getId(),
				(String)SiteInitializerTestrayObjectUtil.getProperty(
					"name", testrayIssueObjectEntry),
				objectEntryManager);
		}
	}

}