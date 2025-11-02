/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.util;

import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;

/**
 * @author Feliphe Marinho
 */
public class SseUtil {

	public static void broadcast(String data, String id, String name, Sse sse) {
		if (_sseBroadcaster == null) {
			return;
		}

		try {
			_sseBroadcaster.broadcast(
				sse.newEventBuilder(
				).name(
					name
				).data(
					String.class, data
				).id(
					id
				).build());
		}
		catch (Exception exception) {
			_sseBroadcaster.close();

			throw exception;
		}
	}

	public static void initialize(Sse sse, SseEventSink sseEventSink) {
		SseBroadcaster sseBroadcaster = _getSseBroadcaster(sse);

		sseBroadcaster.register(sseEventSink);

		sseBroadcaster.broadcast(
			sse.newEventBuilder(
			).name(
				"Open Connection"
			).data(
				String.class, "connection opened"
			).build());
	}

	private static SseBroadcaster _getSseBroadcaster(Sse sse) {
		if (_sseBroadcaster != null) {
			return _sseBroadcaster;
		}

		_sseBroadcaster = sse.newBroadcaster();

		return _sseBroadcaster;
	}

	private static SseBroadcaster _sseBroadcaster;

}