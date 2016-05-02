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

package com.liferay.lcs.advisor;

import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Ivica Cardic
 */
public class UptimeMonitoringAdvisor {

	public List<Map<String, Long>> getUptimes() throws Exception {
		List<Map<String, Long>> uptimes = new ArrayList<>();

		PortletPreferences portletPreferences =
			LCSUtil.fetchJxPortletPreferences();

		JSONArray jsonArray = getUptimesJSONArray(portletPreferences);

		if (jsonArray.length() == 0) {
			checkUptime(jsonArray, portletPreferences);
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Map<String, Long> uptime = new HashMap<>();

			if (i < (jsonArray.length() - 1)) {
				uptime.put("endTime", jsonObject.getLong("endTime"));
			}

			uptime.put("startTime", jsonObject.getLong("startTime"));

			uptimes.add(uptime);
		}

		return uptimes;
	}

	public void init() throws Exception {
		PortletPreferences portletPreferences =
			LCSUtil.fetchJxPortletPreferences();

		JSONArray jsonArray = getUptimesJSONArray(portletPreferences);

		checkUptime(jsonArray, portletPreferences);
	}

	public synchronized void resetUptimes() throws Exception {
		PortletPreferences portletPreferences =
			LCSUtil.fetchJxPortletPreferences();

		JSONArray jsonArray = getUptimesJSONArray(portletPreferences);

		if (jsonArray.length() == 0) {
			return;
		}

		JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);

		jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(jsonObject);

		storeUptimesJSONArray(jsonArray, portletPreferences);
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public synchronized void updateCurrentUptime() throws Exception {
		PortletPreferences portletPreferences =
			LCSUtil.fetchJxPortletPreferences();

		JSONArray jsonArray = getUptimesJSONArray(portletPreferences);

		checkUptime(jsonArray, portletPreferences);

		JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);

		jsonObject.put(
			"endTime",
			_runtimeMXBean.getStartTime() + _runtimeMXBean.getUptime());

		storeUptimesJSONArray(jsonArray, portletPreferences);
	}

	protected void checkUptime(
			JSONArray jsonArray, PortletPreferences portletPreferences)
		throws Exception {

		if (hasStartTime(jsonArray)) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"endTime",
			_runtimeMXBean.getStartTime() + _runtimeMXBean.getUptime());

		jsonObject.put("startTime", _runtimeMXBean.getStartTime());

		jsonArray.put(jsonObject);

		storeUptimesJSONArray(jsonArray, portletPreferences);
	}

	protected JSONArray getUptimesJSONArray(
			PortletPreferences portletPreferences)
		throws Exception {

		JSONArray jsonArray = null;

		String json = portletPreferences.getValue(
			"uptimes-" + _keyGenerator.getKey(), null);

		if (json == null) {
			jsonArray = JSONFactoryUtil.createJSONArray();
		}
		else {
			jsonArray = JSONFactoryUtil.createJSONArray(json);
		}

		return jsonArray;
	}

	protected boolean hasStartTime(JSONArray jsonArray) {
		long startTime = _runtimeMXBean.getStartTime();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.getLong("startTime") == startTime) {
				return true;
			}
		}

		return false;
	}

	protected void storeUptimesJSONArray(
			JSONArray jsonArray, PortletPreferences portletPreferences)
		throws Exception {

		portletPreferences.setValue(
			"uptimes-" + _keyGenerator.getKey(), jsonArray.toString());

		portletPreferences.store();
	}

	private static RuntimeMXBean _runtimeMXBean =
		ManagementFactory.getRuntimeMXBean();

	private KeyGenerator _keyGenerator;

}