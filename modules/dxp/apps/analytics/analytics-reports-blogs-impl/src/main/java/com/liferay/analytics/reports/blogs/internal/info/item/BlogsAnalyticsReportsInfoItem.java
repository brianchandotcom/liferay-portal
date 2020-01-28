/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.blogs.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AnalyticsReportsInfoItem.class)
public class BlogsAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<BlogsEntry> {

	@Override
	public String getAuthorName(BlogsEntry blogsEntry) {
		User user = _userLocalService.fetchUser(blogsEntry.getUserId());

		if (user != null) {
			return user.getFullName();
		}

		return StringPool.BLANK;
	}

	@Override
	public Date getPublishDate(BlogsEntry blogsEntry) {
		return blogsEntry.getDisplayDate();
	}

	@Override
	public String getTitle(BlogsEntry blogsEntry, Locale locale) {
		return blogsEntry.getTitle();
	}

	@Reference
	private UserLocalService _userLocalService;

}