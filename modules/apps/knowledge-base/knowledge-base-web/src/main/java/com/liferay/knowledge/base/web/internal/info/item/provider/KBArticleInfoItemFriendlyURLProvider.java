/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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