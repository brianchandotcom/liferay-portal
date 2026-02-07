/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.memory;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
public class ChatMemoryProviderUtil {

	public static MessageWindowChatMemory provide(Object id) {
		return MessageWindowChatMemory.builder(
		).chatMemoryStore(
			_inMemoryChatMemoryStore
		).id(
			id
		).maxMessages(
			30
		).build();
	}

	private static final InMemoryChatMemoryStore _inMemoryChatMemoryStore =
		new InMemoryChatMemoryStore();

}