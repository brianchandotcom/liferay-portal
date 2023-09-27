/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.blogs.internal.filter;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.sharing.filter.SharedAssetsFilterItem;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "navigation.item.order:Integer=1000",
	service = SharedAssetsFilterItem.class
)
public class BlogsSharedAssetsFilterItem implements SharedAssetsFilterItem {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

}