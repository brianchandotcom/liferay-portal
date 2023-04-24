/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.contacts.demo.internal.data.creator;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Time;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class LiferayExperimentsDataCreator extends DataCreator {

	public LiferayExperimentsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String channelId, String dataSourceId) {

		super(
			contactsEngineClient, faroProject, "osbasahfaroinfo",
			"experiments");

		_channelId = channelId;
		_dataSourceId = dataSourceId;
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		String dateString = formatDate(
			new Date(
				System.currentTimeMillis() -
					(number.numberBetween(0, 100) * Time.DAY)));

		Map<String, Object> pageContext = (Map<String, Object>)params[0];

		return HashMapBuilder.<String, Object>put(
			"channelId", _channelId
		).put(
			"confidenceLevel", 95
		).put(
			"createDate", dateString
		).put(
			"dataSourceId", _dataSourceId
		).put(
			"dxpExperienceId", "DEFAULT"
		).put(
			"dxpExperienceName", "Default"
		).put(
			"dxpGroupId", number.randomNumber(8, false)
		).put(
			"dxpLayoutId", internet.uuid()
		).put(
			"dxpSegmentId", "DEFAULT"
		).put(
			"dxpSegmentName", "Anyone"
		).put(
			"dxpVariants",
			Arrays.asList(
				HashMapBuilder.<String, Object>put(
					"changes", 0
				).put(
					"control", true
				).put(
					"dxpVariantId", "DEFAULT"
				).put(
					"dxpVariantName", "Control"
				).put(
					"trafficSplit", 34
				),
				HashMapBuilder.<String, Object>put(
					"changes", 0
				).put(
					"control", false
				).put(
					"dxpVariantId",
					String.valueOf(number.randomNumber(8, false))
				).put(
					"dxpVariantName", company.buzzword()
				).put(
					"trafficSplit", 33
				),
				HashMapBuilder.<String, Object>put(
					"changes", 0
				).put(
					"control", false
				).put(
					"dxpVariantId",
					String.valueOf(number.randomNumber(8, false))
				).put(
					"dxpVariantName", company.buzzword()
				).put(
					"trafficSplit", 33
				))
		).put(
			"goal",
			HashMapBuilder.put(
				"metric", "BOUNCE_RATE"
			).put(
				"target", ""
			).build()
		).put(
			"modifiedDate", dateString
		).put(
			"name", pageContext.get("title") + " Testing"
		).put(
			"pageURL", pageContext.get("canonicalUrl")
		).put(
			"startedDate", dateString
		).put(
			"status", "RUNNING"
		).put(
			"type", "AB"
		).build();
	}

	private final String _channelId;
	private final String _dataSourceId;

}