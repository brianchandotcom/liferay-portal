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

package com.liferay.twitter.util;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.twitter.configuration.TwitterGroupServiceConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Shinn Lok
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.twitter.configuration.TwitterConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = TimelineProcessorUtil.class
)
public class TimelineProcessorUtil {

	public static TimelineProcessor getInstance() {
		if (_timelineProcessor != null) {
			return _timelineProcessor;
		}

		_timelineProcessor = new HttpTimelineProcessor();

		return _timelineProcessor;
	}

	public static JSONArray getUserTimelineJSONArray(
		String twitterScreenName, long sinceId) {

		return getInstance().getUserTimelineJSONArray(
			twitterScreenName, sinceId);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_twitterGroupServiceConfiguration = ConfigurableUtil.createConfigurable(
			TwitterGroupServiceConfiguration.class, properties);

		try {
			_timelineProcessor = (TimelineProcessor)InstanceFactory.newInstance(
				_twitterGroupServiceConfiguration.usersTimelineProcessor());
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private static TimelineProcessor _timelineProcessor;
	private static volatile TwitterGroupServiceConfiguration
		_twitterGroupServiceConfiguration;

}