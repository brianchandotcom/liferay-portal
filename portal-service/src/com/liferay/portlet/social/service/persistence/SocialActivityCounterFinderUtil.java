/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityCounterFinderUtil {
	public static int countU_ByG_N(long groupId, java.lang.String[] names)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countU_ByG_N(groupId, names);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findAC_ByG_N_S_E_1(
		long groupId, java.lang.String name, int startPeriod, int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findAC_ByG_N_S_E_1(groupId, name, startPeriod, endPeriod);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findAC_ByG_N_S_E_2(
		long groupId, java.lang.String counterName, int startPeriod,
		int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findAC_ByG_N_S_E_2(groupId, counterName, startPeriod,
			endPeriod);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findAC_By_G_C_C_N_S_E(
		long groupId, java.util.List<java.lang.Long> userIds,
		java.lang.String[] names, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findAC_By_G_C_C_N_S_E(groupId, userIds, names, start, end);
	}

	public static java.util.List<java.lang.Long> findU_ByG_N(long groupId,
		java.lang.String[] names, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findU_ByG_N(groupId, names, start, end);
	}

	public static SocialActivityCounterFinder getFinder() {
		if (_finder == null) {
			_finder = (SocialActivityCounterFinder)PortalBeanLocatorUtil.locate(SocialActivityCounterFinder.class.getName());

			ReferenceRegistry.registerReference(SocialActivityCounterFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(SocialActivityCounterFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(SocialActivityCounterFinderUtil.class,
			"_finder");
	}

	private static SocialActivityCounterFinder _finder;
}