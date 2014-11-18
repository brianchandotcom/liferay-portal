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

package com.liferay.portlet.messageboards.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class MBCategoryFinderUtil {
	public static int countByS_G_U_P(long groupId, long userId,
		long[] parentCategoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBCategory> queryDefinition) {
		return getFinder()
				   .countByS_G_U_P(groupId, userId, parentCategoryIds,
			queryDefinition);
	}

	public static int filterCountByS_G_U_P(long groupId, long userId,
		long[] parentCategoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBCategory> queryDefinition) {
		return getFinder()
				   .filterCountByS_G_U_P(groupId, userId, parentCategoryIds,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByS_G_U_P(
		long groupId, long userId, long[] parentCategoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBCategory> queryDefinition) {
		return getFinder()
				   .filterFindByS_G_U_P(groupId, userId, parentCategoryIds,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByS_G_U_P(
		long groupId, long userId, long[] parentCategoryIds,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBCategory> queryDefinition) {
		return getFinder()
				   .findByS_G_U_P(groupId, userId, parentCategoryIds,
			queryDefinition);
	}

	public static MBCategoryFinder getFinder() {
		if (_finder == null) {
			_finder = (MBCategoryFinder)PortalBeanLocatorUtil.locate(MBCategoryFinder.class.getName());

			ReferenceRegistry.registerReference(MBCategoryFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(MBCategoryFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(MBCategoryFinderUtil.class,
			"_finder");
	}

	private static MBCategoryFinder _finder;
}