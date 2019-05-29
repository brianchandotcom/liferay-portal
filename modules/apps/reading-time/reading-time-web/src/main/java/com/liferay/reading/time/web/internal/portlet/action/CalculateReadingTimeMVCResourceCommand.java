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

package com.liferay.reading.time.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.reading.time.calculator.ReadingTimeCalculator;
import com.liferay.reading.time.message.ReadingTimeMessageProvider;
import com.liferay.reading.time.web.internal.constants.ReadingTimePortletKeys;

import java.time.Duration;

import java.util.Optional;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ReadingTimePortletKeys.READING_TIME,
		"mvc.command.name=/reading_time/calculate"
	},
	service = MVCResourceCommand.class
)
public class CalculateReadingTimeMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String content = ParamUtil.getString(resourceRequest, "content");
		String contentType = ParamUtil.getString(
			resourceRequest, "contentType");

		Optional<Duration> readingTimeDurationOptional =
			_readingTimeCalculator.calculate(
				content, contentType, resourceRequest.getLocale());

		readingTimeDurationOptional.ifPresent(
			readingTimeDuration -> jsonObject.put(
				"readingTimeInSeconds", (float)readingTimeDuration.getSeconds()
			).put(
				"readingTimeMessage",
				_readingTimeMessageProvider.provide(
					readingTimeDuration, resourceRequest.getLocale())
			));

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	@Reference
	private ReadingTimeCalculator _readingTimeCalculator;

	@Reference
	private ReadingTimeMessageProvider _readingTimeMessageProvider;

}