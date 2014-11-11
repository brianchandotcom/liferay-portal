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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class LayoutFinderUtil {
	public static java.util.List<com.liferay.portal.model.Layout> findByNoPermissions(
		long roleId) {
		return getFinder().findByNoPermissions(roleId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByNullFriendlyURL() {
		return getFinder().findByNullFriendlyURL();
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByScopeGroup(
		long groupId, boolean privateLayout) {
		return getFinder().findByScopeGroup(groupId, privateLayout);
	}

	public static java.util.List<com.liferay.portal.model.LayoutReference> findByC_P_P(
		long companyId, java.lang.String portletId,
		java.lang.String preferencesKey, java.lang.String preferencesValue) {
		return getFinder()
				   .findByC_P_P(companyId, portletId, preferencesKey,
			preferencesValue);
	}

	public static LayoutFinder getFinder() {
		if (_finder == null) {
			_finder = (LayoutFinder)PortalBeanLocatorUtil.locate(LayoutFinder.class.getName());

			ReferenceRegistry.registerReference(LayoutFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(LayoutFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(LayoutFinderUtil.class, "_finder");
	}

	private static LayoutFinder _finder;
}