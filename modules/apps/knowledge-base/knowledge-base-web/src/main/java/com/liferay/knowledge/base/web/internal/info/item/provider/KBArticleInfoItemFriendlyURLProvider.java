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

package com.liferay.knowledge.base.web.internal.info.item.provider;

import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.petra.string.StringPool;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "item.class.name=com.liferay.knowledge.base.model.KBArticle",
	service = InfoItemFriendlyURLProvider.class
)
public class KBArticleInfoItemFriendlyURLProvider
	implements InfoItemFriendlyURLProvider<KBArticle> {

	@Override
	public String getFriendlyURL(KBArticle kbArticle, String languageId) {
		KBFolder kbFolder = _kbFolderLocalService.fetchKBFolder(
			kbArticle.getKbFolderId());

		if (kbFolder == null) {
			return kbArticle.getUrlTitle();
		}

		return kbFolder.getUrlTitle() + StringPool.SLASH +
			kbArticle.getUrlTitle();
	}

	@Override
	public List<FriendlyURLEntryLocalization> getFriendlyURLEntryLocalizations(
		KBArticle kbArticle, String languageId) {

		return Collections.emptyList();
	}

	@Reference
	private KBFolderLocalService _kbFolderLocalService;

}