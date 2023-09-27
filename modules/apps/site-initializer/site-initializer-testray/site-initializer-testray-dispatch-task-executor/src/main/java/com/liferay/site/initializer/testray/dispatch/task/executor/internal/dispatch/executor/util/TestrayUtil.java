/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class TestrayUtil {

	public static void addTestrayCaseResultIssue(
			long companyId,
			DefaultDTOConverterContext defaultDTOConverterContext,
			ObjectEntryManager objectEntryManager, long testrayCaseResultId,
			String testrayIssueName)
		throws Exception {

		if (Validator.isNull(testrayIssueName)) {
			return;
		}

		ObjectEntryUtil.addObjectEntry(
			defaultDTOConverterContext, "CaseResultsIssues", objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"r_caseResultToCaseResultsIssues_c_caseResultId",
				testrayCaseResultId
			).put(
				"r_issueToCaseResultsIssues_c_issueId",
				() -> {
					Page<ObjectEntry> page =
						ObjectEntryUtil.getObjectEntriesPage(
							null, companyId, defaultDTOConverterContext,
							"name eq '" + testrayIssueName + "'", "Issue",
							objectEntryManager, null);

					ObjectEntry objectEntry = page.fetchFirstItem();

					if (objectEntry.getId() > 0) {
						return objectEntry.getId();
					}

					objectEntry = ObjectEntryUtil.addObjectEntry(
						defaultDTOConverterContext, "Issue", objectEntryManager,
						HashMapBuilder.<String, Object>put(
							"name", testrayIssueName
						).build());

					return objectEntry.getId();
				}
			).build());
	}

	public static void autofillTestrayBuilds(
			long companyId,
			DefaultDTOConverterContext defaultDTOConverterContext,
			ObjectEntryManager objectEntryManager,
			ObjectEntry testrayBuildObjectEntry1,
			ObjectEntry testrayBuildObjectEntry2)
		throws Exception {

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries1 =
			_getTestrayCaseResultObjectEntriesByTestrayBuild(
				companyId, defaultDTOConverterContext, objectEntryManager,
				testrayBuildObjectEntry1);
		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries2 =
			_getTestrayCaseResultObjectEntriesByTestrayBuild(
				companyId, defaultDTOConverterContext, objectEntryManager,
				testrayBuildObjectEntry2);

		for (Map.Entry<Long, List<ObjectEntry>> entry :
				testrayCaseResultObjectEntries1.entrySet()) {

			List<ObjectEntry> testrayCaseResultObjectEntry2s =
				testrayCaseResultObjectEntries2.get(entry.getKey());

			if (testrayCaseResultObjectEntry2s == null) {
				continue;
			}

			List<ObjectEntry> testrayCaseResultObjectEntry1s = entry.getValue();

			for (ObjectEntry testrayCaseResultObjectEntry1 :
					testrayCaseResultObjectEntry1s) {

				String errors1 = (String)ObjectEntryUtil.getProperty(
					"errors", testrayCaseResultObjectEntry1);

				if (Validator.isNull(errors1)) {
					continue;
				}

				for (ObjectEntry testrayCaseResultObjectEntry2 :
						testrayCaseResultObjectEntry2s) {

					String errors2 = (String)ObjectEntryUtil.getProperty(
						"errors", testrayCaseResultObjectEntry2);

					if (Validator.isNull(errors2) ||
						!Objects.equals(errors1, errors2)) {

						continue;
					}

					_autofillTestrayCaseResults(
						companyId, defaultDTOConverterContext,
						objectEntryManager, testrayCaseResultObjectEntry1,
						testrayCaseResultObjectEntry2);
				}
			}
		}
	}

	public static void autofillTestrayRuns(
			long companyId,
			DefaultDTOConverterContext defaultDTOConverterContext,
			ObjectEntryManager objectEntryManager,
			ObjectEntry testrayRunObjectEntry1,
			ObjectEntry testrayRunObjectEntry2)
		throws Exception {

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries1 =
			_getTestrayCaseResultObjectEntriesByTestrayRun(
				companyId, defaultDTOConverterContext, objectEntryManager,
				testrayRunObjectEntry1);
		Map<Long, ObjectEntry> testrayCaseResultObjectEntries2 =
			_getTestrayCaseResultObjectEntriesByTestrayRun(
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
				(String)ObjectEntryUtil.getProperty(
					"errors", testrayCaseResultObjectEntry1);

			String testrayCaseResultErrors2 =
				(String)ObjectEntryUtil.getProperty(
					"errors", testrayCaseResultObjectEntry2);

			if (Validator.isNull(testrayCaseResultErrors1) ||
				Validator.isNull(testrayCaseResultErrors2) ||
				!Objects.equals(
					testrayCaseResultErrors1, testrayCaseResultErrors2)) {

				continue;
			}

			_autofillTestrayCaseResults(
				companyId, defaultDTOConverterContext, objectEntryManager,
				testrayCaseResultObjectEntry1, testrayCaseResultObjectEntry2);
		}
	}

	private static void _autofillTestrayCaseResults(
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
			ObjectEntryUtil.getObjectEntries(
				null, companyId, defaultDTOConverterContext,
				"caseResultId eq '" + testrayCaseResultObjectEntry1.getId() +
					"'",
				"CaseResultsIssues", objectEntryManager, null);
		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries2 =
			ObjectEntryUtil.getObjectEntries(
				null, companyId, defaultDTOConverterContext,
				"caseResultId eq '" + testrayCaseResultObjectEntry2.getId() +
					"'",
				"CaseResultsIssues", objectEntryManager, null);

		if (((Long)ObjectEntryUtil.getProperty(
				"r_userToCaseResults_userId", testrayCaseResultObjectEntry1) >
					0) &&
			!testrayCaseResultsIssuesObjectEntries1.isEmpty() &&
			((Long)ObjectEntryUtil.getProperty(
				"r_userToCaseResults_userId", testrayCaseResultObjectEntry2) <=
					0) &&
			testrayCaseResultsIssuesObjectEntries2.isEmpty()) {

			destinationTestrayCaseResultObjectEntry =
				testrayCaseResultObjectEntry2;
			sourceTestrayCaseResultObjectEntry = testrayCaseResultObjectEntry1;
			sourceTestrayCaseResultsIssuesObjectEntries =
				testrayCaseResultsIssuesObjectEntries1;
		}
		else if (((Long)ObjectEntryUtil.getProperty(
					"r_userToCaseResults_userId",
					testrayCaseResultObjectEntry1) <= 0) &&
				 testrayCaseResultsIssuesObjectEntries1.isEmpty() &&
				 ((Long)ObjectEntryUtil.getProperty(
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
			ObjectEntryUtil.getProperty(
				"dueStatus", sourceTestrayCaseResultObjectEntry));
		properties.put(
			"r_userToCaseResults_userId",
			ObjectEntryUtil.getProperty(
				"r_userToCaseResults_userId",
				sourceTestrayCaseResultObjectEntry));

		ObjectEntryUtil.updateObjectEntry(
			defaultDTOConverterContext, "CaseResult",
			destinationTestrayCaseResultObjectEntry,
			destinationTestrayCaseResultObjectEntry.getId(),
			objectEntryManager);

		for (ObjectEntry sourceTestrayCaseResultsIssuesObjectEntry :
				sourceTestrayCaseResultsIssuesObjectEntries) {

			long testrayIssueId = (long)ObjectEntryUtil.getProperty(
				"r_issueToCaseResultsIssues_c_issueId",
				sourceTestrayCaseResultsIssuesObjectEntry);

			ObjectEntry testrayIssueObjectEntry =
				ObjectEntryUtil.getObjectEntry(
					defaultDTOConverterContext, "Issue", testrayIssueId,
					objectEntryManager);

			if (testrayIssueObjectEntry == null) {
				continue;
			}

			addTestrayCaseResultIssue(
				companyId, defaultDTOConverterContext, objectEntryManager,
				destinationTestrayCaseResultObjectEntry.getId(),
				(String)ObjectEntryUtil.getProperty(
					"name", testrayIssueObjectEntry));
		}
	}

	private static Map<Long, List<ObjectEntry>>
			_getTestrayCaseResultObjectEntriesByTestrayBuild(
				long companyId,
				DefaultDTOConverterContext defaultDTOConverterContext,
				ObjectEntryManager objectEntryManager,
				ObjectEntry testrayBuildObjectEntry)
		throws Exception {

		Map<Long, List<ObjectEntry>> testrayCaseResultObjectEntries =
			new HashMap<>();

		for (ObjectEntry objectEntry :
				ObjectEntryUtil.getObjectEntries(
					null, companyId, defaultDTOConverterContext,
					"buildId eq '" + testrayBuildObjectEntry.getId() + "'",
					"CaseResult", objectEntryManager, null)) {

			long testrayCaseId = (Long)ObjectEntryUtil.getProperty(
				"r_caseToCaseResult_c_caseId", objectEntry);

			List<ObjectEntry> objectEntries =
				testrayCaseResultObjectEntries.get(testrayCaseId);

			if (objectEntries == null) {
				objectEntries = new ArrayList<>();

				testrayCaseResultObjectEntries.put(
					testrayCaseId, objectEntries);
			}

			objectEntries.add(objectEntry);
		}

		return testrayCaseResultObjectEntries;
	}

	private static Map<Long, ObjectEntry>
			_getTestrayCaseResultObjectEntriesByTestrayRun(
				long companyId,
				DefaultDTOConverterContext defaultDTOConverterContext,
				ObjectEntryManager objectEntryManager,
				ObjectEntry testrayRunObjectEntry)
		throws Exception {

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries = new HashMap<>();

		for (ObjectEntry objectEntry :
				ObjectEntryUtil.getObjectEntries(
					null, companyId, defaultDTOConverterContext,
					"runId eq '" + testrayRunObjectEntry.getId() + "'",
					"CaseResult", objectEntryManager, null)) {

			testrayCaseResultObjectEntries.put(
				(Long)ObjectEntryUtil.getProperty(
					"r_caseToCaseResult_c_caseId", objectEntry),
				objectEntry);
		}

		return testrayCaseResultObjectEntries;
	}

}