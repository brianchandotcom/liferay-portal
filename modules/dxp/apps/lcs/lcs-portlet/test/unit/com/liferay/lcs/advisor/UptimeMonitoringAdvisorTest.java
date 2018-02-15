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

import com.liferay.lcs.util.KeyGeneratorImpl;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;

import java.lang.management.ManagementFactory;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Ivica Cardic
 */
@PrepareForTest(
	{
		JSONFactoryUtil.class, LCSUtil.class, ManagementFactory.class,
		PropsUtil.class
	}
)
@RunWith(PowerMockRunner.class)
public class UptimeMonitoringAdvisorTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		mockStatic(
			JSONFactoryUtil.class, LCSUtil.class, ManagementFactory.class,
			PropsUtil.class);

		KeyGeneratorImpl keyGenerator = spy(new KeyGeneratorImpl());

		doReturn(
			"lcsServerId"
		).when(
			keyGenerator
		).getKey(
			Boolean.FALSE
		);

		_uptimeMonitoringAdvisor.setKeyGenerator(keyGenerator);

		_uptimeMonitoringAdvisor.init();

		when(
			JSONFactoryUtil.createJSONObject()
		).thenReturn(
			new JSONObjectImpl()
		);

		_uptimesJSONArray = new JSONArrayImpl();

		JSONObject uptimeJSONObject = new JSONObjectImpl();

		uptimeJSONObject.put("endTime", System.currentTimeMillis() + 1000);
		uptimeJSONObject.put("startTime", System.currentTimeMillis());

		_uptimesJSONArray.put(uptimeJSONObject);

		uptimeJSONObject = new JSONObjectImpl();

		uptimeJSONObject.put("endTime", System.currentTimeMillis() + 3000);
		uptimeJSONObject.put("startTime", System.currentTimeMillis() + 2000);

		_uptimesJSONArray.put(uptimeJSONObject);

		when(
			JSONFactoryUtil.createJSONArray()
		).thenReturn(
			new JSONArrayImpl()
		);
	}

	@Test
	public void testGetUptimes() throws Exception {
		List<Map<String, Long>> uptimes = _uptimeMonitoringAdvisor.getUptimes();

		Map<String, Long> uptime = uptimes.get(uptimes.size() - 1);

		Assert.assertEquals(null, uptime.get("endTime"));
	}

	@Test
	public void testUpdateCurrentUptime() throws Exception {
		JSONObject uptimeJSONObject = _uptimesJSONArray.getJSONObject(
			_uptimesJSONArray.length() - 1);

		long endTime = uptimeJSONObject.getLong("endTime");

		_uptimeMonitoringAdvisor.updateCurrentUptime();

		uptimeJSONObject = _uptimesJSONArray.getJSONObject(
			_uptimesJSONArray.length() - 1);

		long newEndTime = uptimeJSONObject.getLong("endTime");

		Assert.assertNotEquals(endTime, newEndTime);
	}

	private final UptimeMonitoringAdvisor _uptimeMonitoringAdvisor = spy(
		new UptimeMonitoringAdvisor());
	private JSONArray _uptimesJSONArray;

}