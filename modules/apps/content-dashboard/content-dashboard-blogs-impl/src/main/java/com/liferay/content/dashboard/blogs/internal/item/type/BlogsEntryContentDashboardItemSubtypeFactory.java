/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.blogs.internal.item.type;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemSubtypeFactory.class)
public class BlogsEntryContentDashboardItemSubtypeFactory
	implements ContentDashboardItemSubtypeFactory<BlogsEntry> {

	@Override
	public ContentDashboardItemSubtype<BlogsEntry> create(
			long classPK, long entryClassPK)
		throws PortalException {

		return new BlogsEntryContentDashboardItemSubtype();
	}

}