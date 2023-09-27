/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.blogs.internal.item.type;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Locale;

/**
 * @author Cristina González
 */
public class BlogsEntryContentDashboardItemSubtype
	implements ContentDashboardItemSubtype<BlogsEntry> {

	@Override
	public boolean equals(Object object) {
		if ((this == object) ||
			(object instanceof BlogsEntryContentDashboardItemSubtype)) {

			return true;
		}

		return false;
	}

	@Override
	public String getFullLabel(Locale locale) {
		return getLabel(locale);
	}

	@Override
	public InfoItemReference getInfoItemReference() {
		return new InfoItemReference(BlogsEntry.class.getName(), 0);
	}

	@Override
	public String getLabel(Locale locale) {
		InfoItemClassDetails infoItemClassDetails = new InfoItemClassDetails(
			BlogsEntry.class.getName());

		return infoItemClassDetails.getLabel(locale);
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, BlogsEntry.class.getName());
	}

	@Override
	public String toJSONString(Locale locale) {
		return JSONUtil.put(
			"entryClassName", BlogsEntry.class.getName()
		).put(
			"title", getFullLabel(locale)
		).toString();
	}

}