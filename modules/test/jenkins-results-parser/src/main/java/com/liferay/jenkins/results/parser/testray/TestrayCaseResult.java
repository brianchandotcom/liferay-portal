/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import java.net.URL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public interface TestrayCaseResult {

	public static final String[] FIELD_NAMES = {
		"attachments", "buildToCaseResult", "caseToCaseResult",
		"componentToCaseResult", "dateCreated", "dateModified",
		"dueStatus { key name }", "errors", "id", "startDate"
	};

	public void cacheTestrayCaseResultURL();

	public TestrayAttachment getBuildResultTestrayAttachment();

	public String getCaseId();

	public String getComponentName();

	public long getDuration();

	public ErrorType getErrorType();

	public String getErrors();

	public URL getHistoryURL();

	public long getId();

	public String getIssues();

	public JSONObject getJSONObject();

	public String getName();

	public TestrayCaseResult getParentTestrayCaseResult();

	public String getPortalSHA();

	public int getPriority();

	public String getPullRequestSenderUsername();

	public Status getStatus();

	public String getSubcomponentNames();

	public String getTeamName();

	public List<TestrayAttachment> getTestrayAttachments();

	public TestrayBuild getTestrayBuild();

	public TestrayCase getTestrayCase();

	public List<TestrayCaseResult> getTestrayCaseResultHistory(
		int maxCount, int pageSize);

	public URL getTestrayCaseResultURL();

	public TestrayComponent getTestrayComponent();

	public TestrayProject getTestrayProject();

	public TestrayRun getTestrayRun();

	public TestrayServer getTestrayServer();

	public TestrayTeam getTestrayTeam();

	public String getType();

	public URL getURL();

	public String[] getWarnings();

	public void setParentTestrayCaseResult(
		TestrayCaseResult parentTestrayCaseResult);

	public void setTestrayCase(TestrayCase testrayCase);

	public void setTestrayComponent(TestrayComponent testrayComponent);

	public void setTestrayRun(TestrayRun testrayRun);

	public static enum ErrorType {

		COMMON("Common"), DID_NOT_RUN("Did not run"), UNIQUE("Unique");

		@Override
		public String toString() {
			return _name;
		}

		private ErrorType(String name) {
			_name = name;
		}

		private final String _name;

	}

	public static enum Status {

		BLOCKED(4, "blocked"), DIDNOTRUN(6, "dnr"), FAILED(3, "failed"),
		INCOMPLETE(1, "incomplete"), INPROGRESS(1, "in-progress"),
		PASSED(2, "passed"), TESTFIX(7, "test-fix"), UNTESTED(1, "untested");

		public static Status get(Integer id) {
			return _statuses.get(id);
		}

		public static List<Status> getFailedStatuses() {
			return Arrays.asList(
				BLOCKED, DIDNOTRUN, FAILED, INPROGRESS, TESTFIX, UNTESTED);
		}

		public Integer getId() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		private Status(Integer id, String name) {
			_id = id;
			_name = name;
		}

		private static Map<Integer, Status> _statuses = new HashMap<>();

		static {
			for (Status status : values()) {
				_statuses.put(status.getId(), status);
			}
		}

		private final Integer _id;
		private final String _name;

	}

}