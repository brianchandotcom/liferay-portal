/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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