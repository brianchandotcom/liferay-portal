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

package com.liferay.antivirus.async.store.test;

import com.liferay.antivirus.async.store.events.AntivirusAsyncEvent;
import com.liferay.antivirus.async.store.events.AntivirusAsyncEventListener;

import java.util.EnumMap;

/**
 * @author Raymond Augé
 */
public class EventListenerBuilder {

	public AntivirusAsyncEventListener build() {
		return message -> {
			AntivirusAsyncEvent antivirusAsyncEvent =
				(AntivirusAsyncEvent)message.get("antivirusAsyncEvent");

			Runnable runnable = _eventHandlers.get(antivirusAsyncEvent);

			runnable.run();
		};
	}

	public EventListenerBuilder register(
		AntivirusAsyncEvent antivirusAsyncEvent, Runnable runnable) {

		_eventHandlers.put(antivirusAsyncEvent, runnable);

		return this;
	}

	private final EnumMap<AntivirusAsyncEvent, Runnable> _eventHandlers =
		new EnumMap<>(AntivirusAsyncEvent.class);

}