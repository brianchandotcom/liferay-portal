/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.util;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Feliphe Marinho
 */
public class SseUtil {

	public static void closeAll() {
		if (_sseEventSinks.isEmpty() || !PortalRunMode.isTestMode()) {
			return;
		}

		_sseEventSinks.forEach((__, sseEventSink) -> sseEventSink.close());

		_sseEventSinks = new ConcurrentHashMap<>();
	}

	public static SseEventSink getSSEEventSink(String sseEventSinkKey) {
		return _sseEventSinks.get(sseEventSinkKey);
	}

	public static Set<String> getSSEEventSinksKeys() {
		if (!PortalRunMode.isTestMode()) {
			return null;
		}

		return _sseEventSinks.keySet();
	}

	public static void initialize(Sse sse, SseEventSink sseEventSink) {
		String sseEventSinkKey = PortalUUIDUtil.generate();

		_sseEventSinks.put(sseEventSinkKey, sseEventSink);

		sseEventSink.send(
			sse.newEventBuilder(
			).data(
				String.class, sseEventSinkKey
			).name(
				"Subscribe"
			).build());
	}

	public static void send(String data, String name, String sseEventSinkKey) {
		if (Validator.isBlank(sseEventSinkKey)) {
			return;
		}

		SseEventSink sseEventSink = getSSEEventSink(sseEventSinkKey);

		sseEventSink.send(
			_sse.newEventBuilder(
			).data(
				String.class,
				JSONUtil.put(
					"data", data
				).toString()
			).name(
				name
			).build());

		_sse = null;
	}

	public static void setSse(Sse sse) {
		_sse = sse;
	}

	private static Sse _sse;
	private static Map<String, SseEventSink> _sseEventSinks =
		new ConcurrentHashMap<>();

}