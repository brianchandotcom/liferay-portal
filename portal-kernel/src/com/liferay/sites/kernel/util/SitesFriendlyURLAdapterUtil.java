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

package com.liferay.sites.kernel.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Locale;

/**
 * @author Pavel Savinov
 */
public class SitesFriendlyURLAdapterUtil {

	public static Group getGroup(long companyId, String friendlyURL) {
		SitesFriendlyURLAdapter sitesFriendlyURLAdapter =
			getSiteFriendlyURLAdapter();

		if (sitesFriendlyURLAdapter != null) {
			return sitesFriendlyURLAdapter.getGroup(companyId, friendlyURL);
		}

		return null;
	}

	public static String getSiteFriendlyURL(long groupId, Locale locale) {
		SitesFriendlyURLAdapter sitesFriendlyURLAdapter =
			getSiteFriendlyURLAdapter();

		if (sitesFriendlyURLAdapter != null) {
			return sitesFriendlyURLAdapter.getSiteFriendlyURL(groupId, locale);
		}

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		if (group != null) {
			return group.getFriendlyURL();
		}

		return StringPool.BLANK;
	}

	public static SitesFriendlyURLAdapter getSiteFriendlyURLAdapter() {
		return _serviceTracker.getService();
	}

	private static final
		ServiceTracker<SitesFriendlyURLAdapter, SitesFriendlyURLAdapter>
			_serviceTracker;

	static {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(SitesFriendlyURLAdapter.class);

		_serviceTracker.open();
	}

}