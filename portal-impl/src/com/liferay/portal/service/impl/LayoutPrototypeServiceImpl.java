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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutPrototypeServiceBaseImpl;
import com.liferay.portal.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class LayoutPrototypeServiceImpl extends LayoutPrototypeServiceBaseImpl {

	@Override
	public LayoutPrototype addLayoutPrototype(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.ADD_LAYOUT_PROTOTYPE);

		User user = getUser();

		return layoutPrototypeLocalService.addLayoutPrototype(
			user.getUserId(), user.getCompanyId(), nameMap, descriptionMap,
			active, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addLayoutPrototype(Map, Map,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public LayoutPrototype addLayoutPrototype(
			Map<Locale, String> nameMap, String description, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.ADD_LAYOUT_PROTOTYPE);

		User user = getUser();

		return layoutPrototypeLocalService.addLayoutPrototype(
			user.getUserId(), user.getCompanyId(), nameMap, description, active,
			serviceContext);
	}

	@Override
	public void deleteLayoutPrototype(long layoutPrototypeId)
		throws PortalException {

		LayoutPrototypePermissionUtil.check(
			getPermissionChecker(), layoutPrototypeId, ActionKeys.DELETE);

		layoutPrototypeLocalService.deleteLayoutPrototype(layoutPrototypeId);
	}

	@Override
	public LayoutPrototype fetchLayoutPrototype(long layoutPrototypeId)
		throws PortalException {

		LayoutPrototypePermissionUtil.check(
			getPermissionChecker(), layoutPrototypeId, ActionKeys.VIEW);

		return layoutPrototypeLocalService.fetchLayoutPrototype(
			layoutPrototypeId);
	}

	@Override
	public LayoutPrototype getLayoutPrototype(long layoutPrototypeId)
		throws PortalException {

		LayoutPrototypePermissionUtil.check(
			getPermissionChecker(), layoutPrototypeId, ActionKeys.VIEW);

		return layoutPrototypeLocalService.getLayoutPrototype(
			layoutPrototypeId);
	}

	@Override
	public List<LayoutPrototype> search(
			long companyId, Boolean active,
			OrderByComparator<LayoutPrototype> obc)
		throws PortalException {

		List<LayoutPrototype> filteredLayoutPrototypes = new ArrayList<>();

		List<LayoutPrototype> layoutPrototypes =
			layoutPrototypeLocalService.search(
				companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		for (LayoutPrototype layoutPrototype : layoutPrototypes) {
			if (LayoutPrototypePermissionUtil.contains(
					getPermissionChecker(),
					layoutPrototype.getLayoutPrototypeId(), ActionKeys.VIEW)) {

				filteredLayoutPrototypes.add(layoutPrototype);
			}
		}

		return filteredLayoutPrototypes;
	}

	@Override
	public LayoutPrototype updateLayoutPrototype(
			long layoutPrototypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPrototypePermissionUtil.check(
			getPermissionChecker(), layoutPrototypeId, ActionKeys.UPDATE);

		return layoutPrototypeLocalService.updateLayoutPrototype(
			layoutPrototypeId, nameMap, descriptionMap, active, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateLayoutPrototype(long,
	 *             Map, Map, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public LayoutPrototype updateLayoutPrototype(
			long layoutPrototypeId, Map<Locale, String> nameMap,
			String description, boolean active, ServiceContext serviceContext)
		throws PortalException {

		LayoutPrototypePermissionUtil.check(
			getPermissionChecker(), layoutPrototypeId, ActionKeys.UPDATE);

		return layoutPrototypeLocalService.updateLayoutPrototype(
			layoutPrototypeId, nameMap, description, active, serviceContext);
	}

}