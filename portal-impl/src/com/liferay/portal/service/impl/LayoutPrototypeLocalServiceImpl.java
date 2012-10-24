/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.RequiredLayoutPrototypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutPrototypeLocalServiceBaseImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Vilmos Papp
 */
public class LayoutPrototypeLocalServiceImpl
	extends LayoutPrototypeLocalServiceBaseImpl {

	public LayoutPrototype addLayoutPrototype(
			long userId, long companyId, Map<Locale, String> nameMap,
			String description, boolean active)
		throws PortalException, SystemException {

		// Layout prototype

		long layoutPrototypeId = counterLocalService.increment();

		LayoutPrototype layoutPrototype = layoutPrototypePersistence.create(
			layoutPrototypeId);

		layoutPrototype.setCompanyId(companyId);
		layoutPrototype.setNameMap(nameMap);
		layoutPrototype.setDescription(description);
		layoutPrototype.setActive(active);

		layoutPrototypePersistence.update(layoutPrototype);

		// Resources

		if (userId > 0) {
			resourceLocalService.addResources(
				companyId, 0, userId, LayoutPrototype.class.getName(),
				layoutPrototype.getLayoutPrototypeId(), false, false, false);
		}

		// Group

		String friendlyURL =
			"/template-" + layoutPrototype.getLayoutPrototypeId();

		Group group = groupLocalService.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			LayoutPrototype.class.getName(),
			layoutPrototype.getLayoutPrototypeId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			layoutPrototype.getName(LocaleUtil.getDefault()), null, 0,
			friendlyURL, false, true, null);

		ServiceContext serviceContext = new ServiceContext();

		layoutLocalService.addLayout(
			userId, group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			layoutPrototype.getName(LocaleUtil.getDefault()), null, null,
			LayoutConstants.TYPE_PORTLET, false, "/layout", serviceContext);

		return layoutPrototype;
	}

	@Override
	public LayoutPrototype deleteLayoutPrototype(
			LayoutPrototype layoutPrototype)
		throws PortalException, SystemException {

		// Group

		if (layoutPersistence.countByLayoutPrototypeUuid(
				layoutPrototype.getUuid()) > 0) {

			throw new RequiredLayoutPrototypeException();
		}

		Group group = layoutPrototype.getGroup();

		groupLocalService.deleteGroup(group);

		// Resources

		resourceLocalService.deleteResource(
			layoutPrototype.getCompanyId(), LayoutPrototype.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutPrototype.getLayoutPrototypeId());

		// Layout Prototype

		layoutPrototypePersistence.remove(layoutPrototype);

		// Permission cache

		PermissionCacheUtil.clearCache();

		return layoutPrototype;
	}

	@Override
	public LayoutPrototype deleteLayoutPrototype(long layoutPrototypeId)
		throws PortalException, SystemException {

		LayoutPrototype layoutPrototype =
			layoutPrototypePersistence.findByPrimaryKey(layoutPrototypeId);

		return deleteLayoutPrototype(layoutPrototype);
	}

	/**
	 * @deprecated {@link #getLayoutPrototypeByUuidAndCompanyId(String, long)}
	 */
	public LayoutPrototype getLayoutPrototypeByUuid(String uuid)
		throws PortalException, SystemException {

		return layoutPrototypePersistence.findByUuid_First(uuid, null);
	}

	public LayoutPrototype getLayoutPrototypeByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException, SystemException {

		return layoutPrototypePersistence.findByUuid_C_First(
			uuid, companyId, null);
	}

	public List<LayoutPrototype> search(
			long companyId, Boolean active, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (active != null) {
			return layoutPrototypePersistence.findByC_A(
				companyId, active, start, end, obc);
		}
		else {
			return layoutPrototypePersistence.findByCompanyId(
				companyId, start, end, obc);
		}
	}

	public int searchCount(long companyId, Boolean active)
		throws SystemException {

		if (active != null) {
			return layoutPrototypePersistence.countByC_A(companyId, active);
		}
		else {
			return layoutPrototypePersistence.countByCompanyId(companyId);
		}
	}

	public LayoutPrototype updateLayoutPrototype(
			long layoutPrototypeId, Map<Locale, String> nameMap,
			String description, boolean active)
		throws PortalException, SystemException {

		// Layout prototype

		LayoutPrototype layoutPrototype =
			layoutPrototypePersistence.findByPrimaryKey(layoutPrototypeId);

		layoutPrototype.setNameMap(nameMap);
		layoutPrototype.setDescription(description);
		layoutPrototype.setActive(active);

		layoutPrototypePersistence.update(layoutPrototype);

		// Group

		Group group = groupLocalService.getLayoutPrototypeGroup(
			layoutPrototype.getCompanyId(), layoutPrototypeId);

		group.setName(layoutPrototype.getName(LocaleUtil.getDefault()));

		groupPersistence.update(group);

		return layoutPrototype;
	}

}