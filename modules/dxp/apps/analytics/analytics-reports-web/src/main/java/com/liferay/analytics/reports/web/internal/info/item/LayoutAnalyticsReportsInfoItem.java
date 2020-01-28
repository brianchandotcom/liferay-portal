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

package com.liferay.analytics.reports.web.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
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
public class LayoutAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<Layout> {

	@Override
	public String getAuthorName(Layout layout) {
		User user = _userLocalService.fetchUser(layout.getUserId());

		if (user != null) {
			return user.getFullName();
		}

		return StringPool.BLANK;
	}

	@Override
	public Date getPublishDate(Layout layout) {
		return layout.getPublishDate();
	}

	@Override
	public String getTitle(Layout layout, Locale locale) {
		return layout.getTitle(locale);
	}

	@Reference
	private UserLocalService _userLocalService;

}