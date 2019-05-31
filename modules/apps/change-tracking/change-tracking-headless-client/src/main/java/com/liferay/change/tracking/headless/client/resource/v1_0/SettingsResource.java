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

package com.liferay.change.tracking.headless.client.resource.v1_0;

import com.liferay.change.tracking.headless.client.dto.v1_0.Settings;
import com.liferay.change.tracking.headless.client.http.HttpInvoker;
import com.liferay.change.tracking.headless.client.pagination.Page;
import com.liferay.change.tracking.headless.client.serdes.v1_0.SettingsSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Mate Thurzo
 * @generated
 */
@Generated("")
public class SettingsResource {

	public static Page<Settings> getSettingsPage(Long companyId, Long userId)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = getSettingsPageHttpResponse(
			companyId, userId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, SettingsSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse getSettingsPageHttpResponse(
			Long companyId, Long userId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (companyId != null) {
			httpInvoker.parameter("companyId", String.valueOf(companyId));
		}

		if (userId != null) {
			httpInvoker.parameter("userId", String.valueOf(userId));
		}

		httpInvoker.path(
			"http://localhost:8080/o/change-tracking-headless/v1.0/settings");

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Settings putSettings(
			Long companyId, Long userId,
			com.liferay.change.tracking.headless.client.dto.v1_0.SettingsUpdate
				settingsUpdate)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = putSettingsHttpResponse(
			companyId, userId, settingsUpdate);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return SettingsSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse putSettingsHttpResponse(
			Long companyId, Long userId,
			com.liferay.change.tracking.headless.client.dto.v1_0.SettingsUpdate
				settingsUpdate)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(settingsUpdate.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		if (companyId != null) {
			httpInvoker.parameter("companyId", String.valueOf(companyId));
		}

		if (userId != null) {
			httpInvoker.parameter("userId", String.valueOf(userId));
		}

		httpInvoker.path(
			"http://localhost:8080/o/change-tracking-headless/v1.0/settings");

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	private static final Logger _logger = Logger.getLogger(
		SettingsResource.class.getName());

}