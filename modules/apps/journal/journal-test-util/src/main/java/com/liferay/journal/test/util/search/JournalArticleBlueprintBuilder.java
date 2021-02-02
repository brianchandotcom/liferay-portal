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

package com.liferay.journal.test.util.search;

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Map;

/**
 * @author André de Oliveira
 */
public class JournalArticleBlueprintBuilder {

	public static JournalArticleBlueprintBuilder builder() {
		return new JournalArticleBlueprintBuilder();
	}

	public JournalArticleBlueprint build() {
		return new JournalArticleBlueprint(_journalArticleBlueprint);
	}

	public JournalArticleBlueprintBuilder expandoBridgeAttributes(
		Map<String, Serializable> expandoBridgeAttributes) {

		_journalArticleBlueprint.setExpandoBridgeAttributes(
			expandoBridgeAttributes);

		return this;
	}

	public JournalArticleBlueprintBuilder groupId(long groupId) {
		_journalArticleBlueprint.setGroupId(groupId);

		return this;
	}

	public JournalArticleBlueprintBuilder journalArticleContent(
		JournalArticleContent journalArticleContent) {

		_journalArticleBlueprint.setJournalArticleContent(
			journalArticleContent);

		return this;
	}

	public JournalArticleBlueprintBuilder journalArticleTitle(
		JournalArticleTitle journalArticleTitle) {

		_journalArticleBlueprint.setJournalArticleTitle(journalArticleTitle);

		return this;
	}

	public JournalArticleBlueprintBuilder serviceContext(
		ServiceContext serviceContext) {

		_journalArticleBlueprint.setServiceContext(serviceContext);

		return this;
	}

	public JournalArticleBlueprintBuilder userId(long userId) {
		_journalArticleBlueprint.setUserId(userId);

		return this;
	}

	private final JournalArticleBlueprint _journalArticleBlueprint =
		new JournalArticleBlueprint();

}