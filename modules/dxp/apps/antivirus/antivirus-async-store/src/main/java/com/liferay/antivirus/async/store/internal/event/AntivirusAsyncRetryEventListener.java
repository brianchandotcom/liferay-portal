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

package com.liferay.antivirus.async.store.internal.event;

import com.liferay.antivirus.async.store.event.AntivirusAsyncEvent;
import com.liferay.antivirus.async.store.event.AntivirusAsyncEventListener;
import com.liferay.antivirus.async.store.retry.AntivirusAsyncRetryScheduler;
import com.liferay.portal.kernel.messaging.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = AntivirusAsyncEventListener.class
)
public class AntivirusAsyncRetryEventListener
	implements AntivirusAsyncEventListener {

	@Override
	public void receive(Message message) {
		AntivirusAsyncEvent antivirusAsyncEvent =
			(AntivirusAsyncEvent)message.get("antivirusAsyncEvent");

		if ((antivirusAsyncEvent == AntivirusAsyncEvent.MISSING) ||
			(antivirusAsyncEvent == AntivirusAsyncEvent.SIZE_EXCEEDED) ||
			(antivirusAsyncEvent == AntivirusAsyncEvent.SUCCESS) ||
			(antivirusAsyncEvent == AntivirusAsyncEvent.VIRUS_FOUND)) {

			_antivirusAsyncRetryScheduler.unschedule(message);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.PROCESSING_ERROR) {
			_antivirusAsyncRetryScheduler.schedule(message);
		}
	}

	@Reference
	private AntivirusAsyncRetryScheduler _antivirusAsyncRetryScheduler;

}