/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.memory.store;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
public class InMemoryChatMemoryStore {

	public static dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore
		getInstance() {

		return _inMemoryChatMemoryStore;
	}

	private InMemoryChatMemoryStore() {
	}

	private static final
		dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore
			_inMemoryChatMemoryStore =
				new dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore();

}