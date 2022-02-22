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

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Date;
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
	public Date getModifiedDate() {
		return new Date();
	}

	@Override
	public long getUserId() {
		return 0;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, BlogsEntry.class.getName());
	}

	@Override
	public String toJSONString(Locale locale) {
		return JSONUtil.put(
			"className", BlogsEntry.class.getName()
		).put(
			"title", getFullLabel(locale)
		).toJSONString();
	}

}