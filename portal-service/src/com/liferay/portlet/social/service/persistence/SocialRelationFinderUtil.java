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

package com.liferay.portlet.social.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class SocialRelationFinderUtil {
	public static int countSocialUsers(long companyId, long userId, int type,
		boolean equal, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countSocialUsers(companyId, userId, type, equal, status);
	}

	public static java.util.List<com.liferay.portal.model.User> findSocialUsers(
		long companyId, long userId, int type, boolean equal, int status,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findSocialUsers(companyId, userId, type, equal, status,
			start, end, obc);
	}

	public static SocialRelationFinder getFinder() {
		if (_finder == null) {
			_finder = (SocialRelationFinder)PortalBeanLocatorUtil.locate(SocialRelationFinder.class.getName());

			ReferenceRegistry.registerReference(SocialRelationFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(SocialRelationFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(SocialRelationFinderUtil.class,
			"_finder");
	}

	private static SocialRelationFinder _finder;
}