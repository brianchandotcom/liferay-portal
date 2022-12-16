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

package com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util.autofill;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util.SiteInitializerTestrayDispatchTaskExecutorHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nilton Vieira
 */
@Component(service = SiteInitializerTestrayAutoFillHelper.class)
public class SiteInitializerTestrayAutoFillHelperImpl
	implements SiteInitializerTestrayAutoFillHelper {

	public void addTestrayCaseResultIssue(
			long companyId, long testrayCaseResultId, String testrayIssueName)
		throws Exception {

		if (Validator.isNull(testrayIssueName)) {
			return;
		}

		_siteInitializerTestrayDispatchTaskExecutorHelper.addObjectEntry(
			"CaseResultsIssues",
			HashMapBuilder.<String, Object>put(
				"r_caseResultToCaseResultsIssues_c_caseResultId",
				testrayCaseResultId
			).put(
				"r_issueToCaseResultsIssues_c_issueId",
				() -> {
					Page<ObjectEntry> objectEntriesPage =
						_siteInitializerTestrayDispatchTaskExecutorHelper.
							getObjectEntriesPage(
								null, companyId,
								"name eq '" + testrayIssueName + "'", "Issue",
								null);

					ObjectEntry objectEntry =
						objectEntriesPage.fetchFirstItem();

					if (objectEntry.getId() > 0) {
						return objectEntry.getId();
					}

					objectEntry =
						_siteInitializerTestrayDispatchTaskExecutorHelper.
							addObjectEntry(
								"Issue",
								HashMapBuilder.<String, Object>put(
									"name", testrayIssueName
								).build());

					return objectEntry.getId();
				}
			).build());
	}

	public void testrayAutoFillBuilds(
			long companyId, ObjectEntry testrayBuildObjectEntry1,
			ObjectEntry testrayBuildObjectEntry2)
		throws Exception {

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries1 =
			_getTestrayCaseResultObjectEntriesByBuild(
				companyId, testrayBuildObjectEntry1);

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries2 =
			_getTestrayCaseResultObjectEntriesByBuild(
				companyId, testrayBuildObjectEntry2);

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
					(String)
						_siteInitializerTestrayDispatchTaskExecutorHelper.
							getProperty("errors", testrayCaseResultCompositeA);

				if (Validator.isNull(testrayCaseResultErrors1)) {
					continue;
				}

				for (ObjectEntry testrayCaseResultCompositeB :
						testrayCaseResultCompositesB) {

					String testrayCaseResultErrors2 =
						(String)
							_siteInitializerTestrayDispatchTaskExecutorHelper.
								getProperty(
									"errors", testrayCaseResultCompositeB);

					if (Validator.isNull(testrayCaseResultErrors2) ||
						!Objects.equals(
							testrayCaseResultErrors1,
							testrayCaseResultErrors2)) {

						continue;
					}

					_testrayAutoFillCaseResults(
						companyId, testrayCaseResultCompositeA,
						testrayCaseResultCompositeB);
				}
			}
		}
	}

	public void testrayAutoFillRuns(
			long companyId, ObjectEntry testrayRunObjectEntry1,
			ObjectEntry testrayRunObjectEntry2)
		throws Exception {

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries1 =
			_getTestrayCaseResultObjectEntriesByRun(
				companyId, testrayRunObjectEntry1);

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries2 =
			_getTestrayCaseResultObjectEntriesByRun(
				companyId, testrayRunObjectEntry2);

		for (Map.Entry<Long, ObjectEntry> entry :
				testrayCaseResultObjectEntries1.entrySet()) {

			ObjectEntry testrayCaseResultObjectEntry2 =
				testrayCaseResultObjectEntries2.get(entry.getKey());

			if (testrayCaseResultObjectEntry2 == null) {
				continue;
			}

			ObjectEntry testrayCaseResultObjectEntry1 = entry.getValue();

			String testrayCaseResultErrors1 =
				(String)
					_siteInitializerTestrayDispatchTaskExecutorHelper.
						getProperty("errors", testrayCaseResultObjectEntry1);

			String testrayCaseResultErrors2 =
				(String)
					_siteInitializerTestrayDispatchTaskExecutorHelper.
						getProperty("errors", testrayCaseResultObjectEntry2);

			if (Validator.isNull(testrayCaseResultErrors1) ||
				Validator.isNull(testrayCaseResultErrors2) ||
				!Objects.equals(
					testrayCaseResultErrors1, testrayCaseResultErrors2)) {

				continue;
			}

			_testrayAutoFillCaseResults(
				companyId, testrayCaseResultObjectEntry1,
				testrayCaseResultObjectEntry2);
		}
	}

	private Map<Long, List<ObjectEntry>>
			_getTestrayCaseResultObjectEntriesByBuild(
				long companyId, ObjectEntry testrayBuildObjectEntry)
		throws Exception {

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries =
			new HashMap<>();

		List<ObjectEntry> objectEntries =
			_siteInitializerTestrayDispatchTaskExecutorHelper.getObjectEntries(
				null, companyId,
				"buildId eq '" + testrayBuildObjectEntry.getId() + "'",
				"CaseResult", null);

		for (ObjectEntry objectEntry : objectEntries) {
			long testrayCaseId =
				(Long)
					_siteInitializerTestrayDispatchTaskExecutorHelper.
						getProperty("r_caseToCaseResult_c_caseId", objectEntry);

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

	private Map<Long, ObjectEntry> _getTestrayCaseResultObjectEntriesByRun(
			long companyId, ObjectEntry testrayRunObjectEntry)
		throws Exception {

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries = new HashMap<>();

		for (ObjectEntry objectEntry :
				_siteInitializerTestrayDispatchTaskExecutorHelper.
					getObjectEntries(
						null, companyId,
						"runId eq '" + testrayRunObjectEntry.getId() + "'",
						"CaseResult", null)) {

			testrayCaseResultObjectEntries.put(
				(Long)
					_siteInitializerTestrayDispatchTaskExecutorHelper.
						getProperty("r_caseToCaseResult_c_caseId", objectEntry),
				objectEntry);
		}

		return testrayCaseResultObjectEntries;
	}

	private void _testrayAutoFillCaseResults(
			long companyId, ObjectEntry testrayCaseResultObjectEntry1,
			ObjectEntry testrayCaseResultObjectEntry2)
		throws Exception {

		ObjectEntry destinationTestrayCaseResultObjectEntry = null;
		ObjectEntry sourceTestrayCaseResultObjectEntry = null;
		List<ObjectEntry> sourceTestrayCaseResultsIssuesObjectEntries = null;

		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries1 =
			_siteInitializerTestrayDispatchTaskExecutorHelper.getObjectEntries(
				null, companyId,
				"caseResultId eq '" + testrayCaseResultObjectEntry1.getId() +
					"'",
				"CaseResultsIssues", null);

		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries2 =
			_siteInitializerTestrayDispatchTaskExecutorHelper.getObjectEntries(
				null, companyId,
				"caseResultId eq '" + testrayCaseResultObjectEntry2.getId() +
					"'",
				"CaseResultsIssues", null);

		if (((Long)
				_siteInitializerTestrayDispatchTaskExecutorHelper.getProperty(
					"r_userToCaseResults_userId",
					testrayCaseResultObjectEntry1) > 0) &&
			!testrayCaseResultsIssuesObjectEntries1.isEmpty() &&
			((Long)
				_siteInitializerTestrayDispatchTaskExecutorHelper.getProperty(
					"r_userToCaseResults_userId",
					testrayCaseResultObjectEntry2) <= 0) &&
			testrayCaseResultsIssuesObjectEntries2.isEmpty()) {

			destinationTestrayCaseResultObjectEntry =
				testrayCaseResultObjectEntry2;
			sourceTestrayCaseResultObjectEntry = testrayCaseResultObjectEntry1;
			sourceTestrayCaseResultsIssuesObjectEntries =
				testrayCaseResultsIssuesObjectEntries1;
		}
		else if (((Long)
					_siteInitializerTestrayDispatchTaskExecutorHelper.
						getProperty(
							"r_userToCaseResults_userId",
							testrayCaseResultObjectEntry1) <= 0) &&
				 testrayCaseResultsIssuesObjectEntries1.isEmpty() &&
				 ((Long)
					 _siteInitializerTestrayDispatchTaskExecutorHelper.
						 getProperty(
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
			_siteInitializerTestrayDispatchTaskExecutorHelper.getProperty(
				"dueStatus", sourceTestrayCaseResultObjectEntry));
		properties.put(
			"r_userToCaseResults_userId",
			_siteInitializerTestrayDispatchTaskExecutorHelper.getProperty(
				"r_userToCaseResults_userId",
				sourceTestrayCaseResultObjectEntry));

		_siteInitializerTestrayDispatchTaskExecutorHelper.updateObjectEntry(
			"CaseResult", destinationTestrayCaseResultObjectEntry,
			destinationTestrayCaseResultObjectEntry.getId());

		for (ObjectEntry sourceTestrayCaseResultsIssuesObjectEntry :
				sourceTestrayCaseResultsIssuesObjectEntries) {

			long testrayIssueId =
				(long)
					_siteInitializerTestrayDispatchTaskExecutorHelper.
						getProperty(
							"r_issueToCaseResultsIssues_c_issueId",
							sourceTestrayCaseResultsIssuesObjectEntry);

			ObjectEntry testrayIssueObjectEntry =
				_siteInitializerTestrayDispatchTaskExecutorHelper.
					getObjectEntry("Issue", testrayIssueId);

			if (testrayIssueObjectEntry == null) {
				continue;
			}

			addTestrayCaseResultIssue(
				companyId, destinationTestrayCaseResultObjectEntry.getId(),
				(String)
					_siteInitializerTestrayDispatchTaskExecutorHelper.
						getProperty("name", testrayIssueObjectEntry));
		}
	}

	@Reference
	private SiteInitializerTestrayDispatchTaskExecutorHelper
		_siteInitializerTestrayDispatchTaskExecutorHelper;

}