/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

/**
 * @author Marcos Martins
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ProjectUsageDisplay {

	public ProjectUsageDisplay() {
	}

	public ProjectUsageDisplay(FaroProject faroProject) throws Exception {
		_corpProjectName = faroProject.getCorpProjectName();
		_corpProjectUuid = faroProject.getCorpProjectUuid();

		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		_lastAccessDateString = dateFormat.format(
			new Date(faroProject.getLastAccessTime()));

		if (!StringUtil.equals(
				faroProject.getState(), FaroProjectConstants.STATE_READY)) {

			_offline = true;
		}

		JSONObject subscriptionJSONObject = JSONFactoryUtil.createJSONObject(
			faroProject.getSubscription());

		_individualsCountSinceLastAnniversary = subscriptionJSONObject.getLong(
			"individualsCountSinceLastAnniversary");

		JSONObject individualsCountsJSONObject =
			JSONFactoryUtil.createJSONObject(
				subscriptionJSONObject.getString("individualsCounts"));

		_individualsCounts = individualsCountsJSONObject.toMap();

		_lastAnniversaryDateString = dateFormat.format(
			new Date(subscriptionJSONObject.getLong("lastAnniversaryDate")));

		_pageViewsCountSinceLastAnniversary = subscriptionJSONObject.getLong(
			"pageViewsCountSinceLastAnniversary");

		JSONObject pageViewsCountsJSONObject = JSONFactoryUtil.createJSONObject(
			subscriptionJSONObject.getString("pageViewsCounts"));

		_pageViewsCounts = pageViewsCountsJSONObject.toMap();

		_weDeployKey = faroProject.getWeDeployKey();
	}

	public String getCorpProjectName() {
		return _corpProjectName;
	}

	public String getCorpProjectUuid() {
		return _corpProjectUuid;
	}

	public Map<String, Object> getIndividualsCounts() {
		return _individualsCounts;
	}

	public long getIndividualsCountSinceLastAnniversary() {
		return _individualsCountSinceLastAnniversary;
	}

	public String getLastAccessDateString() {
		return _lastAccessDateString;
	}

	public String getLastAnniversaryDateString() {
		return _lastAnniversaryDateString;
	}

	public boolean getOffline() {
		return isOffline();
	}

	public Map<String, Object> getPageViewsCounts() {
		return _pageViewsCounts;
	}

	public long getPageViewsCountSinceLastAnniversary() {
		return _pageViewsCountSinceLastAnniversary;
	}

	public String getWeDeployKey() {
		return _weDeployKey;
	}

	public boolean isOffline() {
		return _offline;
	}

	private String _corpProjectName;
	private String _corpProjectUuid;
	private Map<String, Object> _individualsCounts;
	private long _individualsCountSinceLastAnniversary;
	private String _lastAccessDateString;
	private String _lastAnniversaryDateString;
	private boolean _offline;
	private Map<String, Object> _pageViewsCounts;
	private long _pageViewsCountSinceLastAnniversary;
	private String _weDeployKey;

}