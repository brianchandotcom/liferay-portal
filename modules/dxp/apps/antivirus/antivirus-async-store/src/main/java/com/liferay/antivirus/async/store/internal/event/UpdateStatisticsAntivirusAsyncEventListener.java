/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.internal.event;

import com.liferay.antivirus.async.store.event.AntivirusAsyncEvent;
import com.liferay.antivirus.async.store.event.AntivirusAsyncEventListener;
import com.liferay.antivirus.async.store.util.AntivirusStatisticsHolderUtil;
import com.liferay.portal.kernel.messaging.Message;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Cassio Silva do Nascimento Junior
 */
@Component(service = AntivirusAsyncEventListener.class)
public class UpdateStatisticsAntivirusAsyncEventListener
	implements AntivirusAsyncEventListener {

	@Override
	public void receive(Message message) {
		AntivirusAsyncEvent antivirusAsyncEvent =
			(AntivirusAsyncEvent)message.get("antivirusAsyncEvent");

		AntivirusStatisticsHolderUtil.onAntivirusEvent(antivirusAsyncEvent);
	}

}