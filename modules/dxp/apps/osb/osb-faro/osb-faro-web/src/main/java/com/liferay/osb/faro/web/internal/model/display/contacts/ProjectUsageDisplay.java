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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Marcos Martins
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ProjectUsageDisplay {

	public ProjectUsageDisplay() {
	}

	public ProjectUsageDisplay(
			FaroProject faroProject, boolean includeIndividualsCounts,
			boolean includeMonthlyValues, boolean includePageViewsCounts)
		throws Exception {

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

		if (includeIndividualsCounts) {
			_individualsCountsDisplay = new CountsDisplay(
				includeMonthlyValues,
				JSONFactoryUtil.createJSONObject(
					subscriptionJSONObject.getString("individualsCounts")));
		}

		_lastAnniversaryDateString = dateFormat.format(
			new Date(subscriptionJSONObject.getLong("lastAnniversaryDate")));

		_pageViewsCountSinceLastAnniversary = subscriptionJSONObject.getLong(
			"pageViewsCountSinceLastAnniversary");

		if (includePageViewsCounts) {
			_pageViewsCountsDisplay = new CountsDisplay(
				includeMonthlyValues,
				JSONFactoryUtil.createJSONObject(
					subscriptionJSONObject.getString("pageViewsCounts")));
		}

		_weDeployKey = faroProject.getWeDeployKey();
	}

	public String getCorpProjectName() {
		return _corpProjectName;
	}

	public String getCorpProjectUuid() {
		return _corpProjectUuid;
	}

	public CountsDisplay getIndividualsCountsDisplay() {
		return _individualsCountsDisplay;
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

	public CountsDisplay getPageViewsCountsDisplay() {
		return _pageViewsCountsDisplay;
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

	public static class CountsDisplay {

		public CountsDisplay() {
		}

		public CountsDisplay(
			boolean includeMonthlyValues, JSONObject jsonObject) {

			if (includeMonthlyValues) {
				JSONObject monthlyValuesJSONObject = jsonObject.getJSONObject(
					"monthlyValues");

				if (monthlyValuesJSONObject != null) {
					_monthlyValues = new LinkedHashMap<>();

					for (String key : monthlyValuesJSONObject.keySet()) {
						JSONObject monthlyValueJSONObject =
							monthlyValuesJSONObject.getJSONObject(key);

						_monthlyValues.put(
							key,
							new MonthlyValue(
								monthlyValueJSONObject.getInt("count"),
								monthlyValueJSONObject.getInt(
									"countSinceLastAnniversary")));
					}
				}
			}

			_total = jsonObject.getInt("total");
			_totalSinceLastAnniversary = jsonObject.getInt(
				"totalSinceLastAnniversary");
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof CountsDisplay)) {
				return false;
			}

			CountsDisplay countsDisplay = (CountsDisplay)object;

			if (Objects.equals(_monthlyValues, countsDisplay._monthlyValues) &&
				Objects.equals(_total, countsDisplay._total) &&
				Objects.equals(
					_totalSinceLastAnniversary,
					countsDisplay._totalSinceLastAnniversary)) {

				return true;
			}

			return false;
		}

		public Map<String, MonthlyValue> getMonthlyValues() {
			return _monthlyValues;
		}

		public int getTotal() {
			return _total;
		}

		public int getTotalSinceLastAnniversary() {
			return _totalSinceLastAnniversary;
		}

		@Override
		public int hashCode() {
			return Objects.hash(
				_monthlyValues, _total, _totalSinceLastAnniversary);
		}

		private Map<String, MonthlyValue> _monthlyValues;
		private int _total;
		private int _totalSinceLastAnniversary;

	}

	public static class MonthlyValue {

		public MonthlyValue(int count, int countSinceLastAnniversary) {
			_count = count;
			_countSinceLastAnniversary = countSinceLastAnniversary;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof MonthlyValue)) {
				return false;
			}

			MonthlyValue monthlyValue = (MonthlyValue)object;

			if (Objects.equals(_count, monthlyValue._count) &&
				Objects.equals(
					_countSinceLastAnniversary,
					monthlyValue._countSinceLastAnniversary)) {

				return true;
			}

			return false;
		}

		public int getCount() {
			return _count;
		}

		public int getCountSinceLastAnniversary() {
			return _countSinceLastAnniversary;
		}

		@Override
		public int hashCode() {
			return Objects.hash(_count, _countSinceLastAnniversary);
		}

		private final int _count;
		private final int _countSinceLastAnniversary;

	}

	private String _corpProjectName;
	private String _corpProjectUuid;
	private CountsDisplay _individualsCountsDisplay;
	private long _individualsCountSinceLastAnniversary;
	private String _lastAccessDateString;
	private String _lastAnniversaryDateString;
	private boolean _offline;
	private CountsDisplay _pageViewsCountsDisplay;
	private long _pageViewsCountSinceLastAnniversary;
	private String _weDeployKey;

}