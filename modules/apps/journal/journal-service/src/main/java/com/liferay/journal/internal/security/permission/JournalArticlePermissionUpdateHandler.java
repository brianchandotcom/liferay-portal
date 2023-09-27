/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.security.permission;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = PermissionUpdateHandler.class
)
public class JournalArticlePermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			GetterUtil.getLong(primKey));

		if (article == null) {
			return;
		}

		article.setModifiedDate(new Date());

		_journalArticleLocalService.updateJournalArticle(article);
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}