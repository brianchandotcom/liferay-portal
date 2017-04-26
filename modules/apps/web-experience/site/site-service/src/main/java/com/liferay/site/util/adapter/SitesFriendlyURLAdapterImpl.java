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

package com.liferay.site.util.adapter;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.service.SiteFriendlyURLLocalService;
import com.liferay.sites.kernel.util.SitesFriendlyURLAdapter;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = SitesFriendlyURLAdapter.class)
public class SitesFriendlyURLAdapterImpl implements SitesFriendlyURLAdapter {

	@Override
	public Group getGroupBySiteFriendlyURL(long companyId, String friendlyURL) {
		SiteFriendlyURL siteFriendlyURL =
			_siteFriendlyURLLocalService.fetchSiteFriendlyURLByFriendlyURL(
				companyId, friendlyURL);

		if (siteFriendlyURL != null) {
			return _groupLocalService.fetchGroup(siteFriendlyURL.getGroupId());
		}

		return null;
	}

	@Override
	public String getSiteFriendlyURL(long groupId, Locale locale) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return StringPool.BLANK;
		}

		SiteFriendlyURL siteFriendlyURL =
			_siteFriendlyURLLocalService.fetchSiteFriendlyURL(
				group.getCompanyId(), group.getGroupId(),
				LocaleUtil.toLanguageId(locale));

		if ((siteFriendlyURL != null) &&
			Validator.isNotNull(siteFriendlyURL.getFriendlyURL())) {

			return siteFriendlyURL.getFriendlyURL();
		}

		return group.getFriendlyURL();
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SiteFriendlyURLLocalService _siteFriendlyURLLocalService;

}