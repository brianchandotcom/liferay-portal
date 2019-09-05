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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutFinderUtil {

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
		findByNoPermissions(long roleId) {

		return getFinder().findByNoPermissions(roleId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
		findByNullFriendlyURL() {

		return getFinder().findByNullFriendlyURL();
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
		findByScopeGroup(long groupId) {

		return getFinder().findByScopeGroup(groupId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout>
		findByScopeGroup(long groupId, boolean privateLayout) {

		return getFinder().findByScopeGroup(groupId, privateLayout);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.LayoutReference> findByC_P_P(
			long companyId, String portletId, String preferencesKey,
			String preferencesValue) {

		return getFinder().findByC_P_P(
			companyId, portletId, preferencesKey, preferencesValue);
	}

	public static LayoutFinder getFinder() {
		if (_finder == null) {
			_finder = (LayoutFinder)PortalBeanLocatorUtil.locate(
				LayoutFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(LayoutFinder finder) {
		_finder = finder;
	}

	private static LayoutFinder _finder;

}