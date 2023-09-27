/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.journal.internal.info.item.provider;

import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = AnalyticsReportsInfoItemObjectProvider.class)
public class JournalArticleAnalyticsReportsInfoItemObjectProvider
	implements AnalyticsReportsInfoItemObjectProvider<JournalArticle> {

	@Override
	public JournalArticle getAnalyticsReportsInfoItemObject(
		InfoItemReference infoItemReference) {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		return _journalArticleLocalService.fetchLatestArticle(
			classPKInfoItemIdentifier.getClassPK());
	}

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}