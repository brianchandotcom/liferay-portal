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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.info.item.NoSuchInfoItemException;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemObjectProvider.class)
public class JournalArticleInfoItemProvider
	implements InfoItemObjectProvider<JournalArticle> {

	@Override
	public JournalArticle getInfoItem(long classPK)
		throws NoSuchInfoItemException {

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			classPK);

		if ((article == null) || article.isInTrash()) {
			throw new NoSuchInfoItemException(
				"Unable to get journal article with resource prim key: " +
					classPK);
		}

		return article;
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}