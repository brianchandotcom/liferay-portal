/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.blogs.internal.info.item.provider;

import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = AnalyticsReportsInfoItemObjectProvider.class)
public class BlogsEntryAnalyticsReportsInfoItemObjectProvider
	implements AnalyticsReportsInfoItemObjectProvider<BlogsEntry> {

	@Override
	public BlogsEntry getAnalyticsReportsInfoItemObject(
		InfoItemReference infoItemReference) {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		return _blogsEntryLocalService.fetchBlogsEntry(
			classPKInfoItemIdentifier.getClassPK());
	}

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

}