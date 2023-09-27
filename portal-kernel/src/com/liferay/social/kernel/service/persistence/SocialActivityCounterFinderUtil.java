/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityCounterFinderUtil {

	public static int countU_ByG_N(long groupId, String[] names) {
		return getFinder().countU_ByG_N(groupId, names);
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivityCounter>
			findAC_ByG_N_S_E_1(
				long groupId, String name, int startPeriod, int endPeriod,
				int periodLength) {

		return getFinder().findAC_ByG_N_S_E_1(
			groupId, name, startPeriod, endPeriod, periodLength);
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivityCounter>
			findAC_ByG_N_S_E_2(
				long groupId, String counterName, int startPeriod,
				int endPeriod, int periodLength) {

		return getFinder().findAC_ByG_N_S_E_2(
			groupId, counterName, startPeriod, endPeriod, periodLength);
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivityCounter>
			findAC_By_G_C_C_N_S_E(
				long groupId, java.util.List<Long> userIds, String[] names,
				int start, int end) {

		return getFinder().findAC_By_G_C_C_N_S_E(
			groupId, userIds, names, start, end);
	}

	public static java.util.List<Long> findU_ByG_N(
		long groupId, String[] names, int start, int end) {

		return getFinder().findU_ByG_N(groupId, names, start, end);
	}

	public static SocialActivityCounterFinder getFinder() {
		if (_finder == null) {
			_finder = (SocialActivityCounterFinder)PortalBeanLocatorUtil.locate(
				SocialActivityCounterFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(SocialActivityCounterFinder finder) {
		_finder = finder;
	}

	private static SocialActivityCounterFinder _finder;

}