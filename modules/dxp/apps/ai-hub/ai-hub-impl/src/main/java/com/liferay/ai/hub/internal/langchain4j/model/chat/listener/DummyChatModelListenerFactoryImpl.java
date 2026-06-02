/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.langchain4j.model.chat.listener;

import com.liferay.ai.hub.langchain4j.model.chat.listener.ChatModelListenerFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import dev.langchain4j.model.chat.listener.ChatModelListener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "service.ranking:Integer=-1",
	service = ChatModelListenerFactory.class
)
public class DummyChatModelListenerFactoryImpl
	implements ChatModelListenerFactory {

	@Override
	public ChatModelListener create(ServiceContext serviceContext) {
		return new ChatModelListener() {
		};
	}

}