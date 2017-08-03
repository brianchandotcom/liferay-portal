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
import com.liferay.lcs.util.LCSPortletPreferencesUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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
		if (!_initalized) {
			throw new UnsupportedOperationException("Bean is not initialized");
		}

		return _getUptimes();
	}

	public void init() throws Exception {
		if (_initalized) {
			return;
		}

		JSONArray jsonArray = _getUptimesJSONArray();

		_checkUptime(jsonArray);

		_initalized = true;

		if (_log.isDebugEnabled()) {
			_log.debug("Initialized");
		}
	}

	public void resetCurrentUptimeEndTime(List<Map<String, Long>> uptimes)
		throws Exception {

		long startTime = _runtimeMXBean.getStartTime();

		for (Map<String, Long> uptime : uptimes) {
			if (startTime == uptime.get("startTime")) {
				uptime.remove("endTime");

				return;
			}
		}
	}

	public synchronized void resetUptimes() throws Exception {
		if (!_initalized) {
			throw new UnsupportedOperationException("Bean is not initialized");
		}

		JSONArray jsonArray = _getUptimesJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.getLong("startTime") !=
					_runtimeMXBean.getStartTime()) {

				continue;
			}

			jsonArray = JSONFactoryUtil.createJSONArray();

			jsonArray.put(jsonObject);

			_storeUptimesJSONArray(jsonArray);

			_uptimes.clear();

			_readyForUpdates = true;

			if (_log.isDebugEnabled()) {
				_log.debug("Uptimes reset and ready for updates");
			}

			break;
		}
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public synchronized void updateCurrentUptime() throws Exception {
		if (!_initalized) {
			throw new UnsupportedOperationException("Bean is not initialized");
		}

		if (!_readyForUpdates) {
			return;
		}

		JSONArray jsonArray = _getUptimesJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.getLong("startTime") !=
					_runtimeMXBean.getStartTime()) {

				continue;
			}

			jsonObject.put(
				"endTime",
				_runtimeMXBean.getStartTime() + _runtimeMXBean.getUptime());

			_storeUptimesJSONArray(jsonArray);

			if (_log.isTraceEnabled()) {
				_log.trace("Uptimes updated to: " + jsonArray.toString());
			}

			break;
		}
	}

	private void _checkUptime(JSONArray jsonArray) throws Exception {
		long startTime = _runtimeMXBean.getStartTime();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.getLong("startTime") == startTime) {
				return;
			}
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("endTime", startTime + _runtimeMXBean.getUptime());
		jsonObject.put("startTime", startTime);

		jsonArray.put(jsonObject);

		_uptimes.add(jsonArray.toString());

		if (_log.isDebugEnabled()) {
			_log.debug("Temporary uptime created");
		}
	}

	private List<Map<String, Long>> _getUptimes() throws Exception {
		List<Map<String, Long>> uptimes = new ArrayList<>();

		JSONArray jsonArray = _getUptimesJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Map<String, Long> uptime = new HashMap<>();

			uptime.put("endTime", jsonObject.getLong("endTime"));
			uptime.put("startTime", jsonObject.getLong("startTime"));

			uptimes.add(uptime);
		}

		return uptimes;
	}

	private JSONArray _getUptimesJSONArray() throws Exception {
		JSONArray jsonArray = null;

		String key = _keyGenerator.getKey(false);

		if (key == null) {
			jsonArray = JSONFactoryUtil.createJSONArray();

			return jsonArray;
		}

		PortletPreferences portletPreferences =
			LCSPortletPreferencesUtil.fetchReadOnlyJxPortletPreferences();

		String json = portletPreferences.getValue("uptimes-" + key, null);

		if (json == null) {
			jsonArray = JSONFactoryUtil.createJSONArray();
		}
		else {
			jsonArray = JSONFactoryUtil.createJSONArray(json);
		}

		_mergeUptimesJSONArrays(jsonArray);

		return jsonArray;
	}

	private void _mergeUptimesJSONArrays(JSONArray jsonArray) throws Exception {
		for (String json : _uptimes) {
			JSONArray uptimeJSONArray = JSONFactoryUtil.createJSONArray(json);

			for (int i = 0; i < uptimeJSONArray.length(); i++) {
				JSONObject uptimeJSONObject = uptimeJSONArray.getJSONObject(i);

				boolean duplicate = false;

				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject jsonObject = jsonArray.getJSONObject(j);

					if (uptimeJSONObject.getLong("startTime") ==
							jsonObject.getLong("startTime")) {

						duplicate = true;

						break;
					}
				}

				if (duplicate) {
					continue;
				}

				jsonArray.put(uptimeJSONObject);
			}
		}
	}

	private void _storeUptimesJSONArray(JSONArray jsonArray) throws Exception {
		String key = _keyGenerator.getKey(false);

		if (key == null) {
			return;
		}

		LCSPortletPreferencesUtil.store("uptimes-" + key, jsonArray.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UptimeMonitoringAdvisor.class);

	private static final RuntimeMXBean _runtimeMXBean =
		ManagementFactory.getRuntimeMXBean();

	private boolean _initalized;
	private KeyGenerator _keyGenerator;
	private boolean _readyForUpdates;
	private final List<String> _uptimes = new ArrayList();

}