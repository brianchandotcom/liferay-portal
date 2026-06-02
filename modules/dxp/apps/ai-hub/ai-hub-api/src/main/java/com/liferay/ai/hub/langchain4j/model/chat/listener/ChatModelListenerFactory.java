/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.langchain4j.model.chat.listener;

import com.liferay.portal.kernel.service.ServiceContext;

import dev.langchain4j.model.chat.listener.ChatModelListener;

/**
 * @author Carolina Barbosa
 */
public interface ChatModelListenerFactory {

	public ChatModelListener create(ServiceContext serviceContext);

}