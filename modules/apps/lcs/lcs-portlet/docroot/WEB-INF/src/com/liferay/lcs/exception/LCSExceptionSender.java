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

package com.liferay.lcs.exception;

import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.PortletPropsValues;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceClientImpl;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mladen Cikara
 */
public class LCSExceptionSender {

	public void afterPropertiesSet() {
		_jsonWebServiceClient = new JSONWebServiceClientImpl();

		_jsonWebServiceClient.setHostName(
			PortletPropsValues.OSB_LCS_PORTLET_HOST_NAME);
		_jsonWebServiceClient.setHostPort(
			PortletPropsValues.OSB_LCS_PORTLET_HOST_PORT);
	}

	public void sendMessage(String message, Throwable t) {
		if (_key == null) {
			_key = _keyGenerator.getKey();
		}

		Map<String, String> map = new HashMap<>();

		map.put("key", _key);
		map.put("message", message);

		if (t != null) {
			StringWriter stringWriter = new StringWriter();

			t.printStackTrace(new PrintWriter(stringWriter));

			map.put("throwable", stringWriter.toString());
		}

		try {
			_jsonWebServiceClient.doPost(
				PortletPropsValues.OSB_LCS_PORTLET_UPLOAD_EXCEPTIONS_URI, map);
		}
		catch (JSONWebServiceTransportException jsonwste) {
			_log.error(jsonwste.getMessage(), jsonwste);
		}
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	private static Log _log = LogFactoryUtil.getLog(LCSExceptionSender.class);

	private JSONWebServiceClient _jsonWebServiceClient;
	private String _key;
	private KeyGenerator _keyGenerator;

}