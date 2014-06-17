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

import com.liferay.portal.RequiredLayoutPrototypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutPrototypeLocalServiceBaseImpl;

import java.util.Date;
import java.util.HashMap;
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

	@Override
	public LayoutPrototype addLayoutPrototype(
			long userId, long companyId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout prototype

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long layoutPrototypeId = counterLocalService.increment();

		LayoutPrototype layoutPrototype = layoutPrototypePersistence.create(
			layoutPrototypeId);

		layoutPrototype.setUuid(serviceContext.getUuid());
		layoutPrototype.setCompanyId(companyId);
		layoutPrototype.setUserId(userId);
		layoutPrototype.setUserName(user.getFullName());
		layoutPrototype.setCreateDate(serviceContext.getCreateDate(now));
		layoutPrototype.setModifiedDate(serviceContext.getModifiedDate(now));
		layoutPrototype.setNameMap(nameMap);
		layoutPrototype.setDescriptionMap(descriptionMap);
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
			layoutPrototype.getName(LocaleUtil.getDefault()), null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL, false,
			true, null);

		if (GetterUtil.getBoolean(
				serviceContext.getAttribute("addDefaultLayout"), true)) {

			Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

			friendlyURLMap.put(LocaleUtil.getSiteDefault(), "/layout");

			layoutLocalService.addLayout(
				userId, group.getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
				layoutPrototype.getNameMap(), null, null, null, null,
				LayoutConstants.TYPE_PORTLET, StringPool.BLANK, false,
				friendlyURLMap, serviceContext);
		}

		return layoutPrototype;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addLayoutPrototype(long,
	 *             long, Map, String, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public LayoutPrototype addLayoutPrototype(
			long userId, long companyId, Map<Locale, String> nameMap,
			String description, boolean active)
		throws PortalException {

		return addLayoutPrototype(
			userId, companyId, nameMap, description, active,
			new ServiceContext());
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addLayoutPrototype(long,
	 *             long, Map, Map, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public LayoutPrototype addLayoutPrototype(
			long userId, long companyId, Map<Locale, String> nameMap,
			String description, boolean active, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		return addLayoutPrototype(
			userId, companyId, nameMap, descriptionMap, active, serviceContext);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public LayoutPrototype deleteLayoutPrototype(
			LayoutPrototype layoutPrototype)
		throws PortalException {

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
		throws PortalException {

		LayoutPrototype layoutPrototype =
			layoutPrototypePersistence.findByPrimaryKey(layoutPrototypeId);

		return layoutPrototypeLocalService.deleteLayoutPrototype(
			layoutPrototype);
	}

	@Override
	public void deleteNondefaultLayoutPrototypes(long companyId)
		throws PortalException {

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		List<LayoutPrototype> layoutPrototypes =
			layoutPrototypePersistence.findByCompanyId(companyId);

		for (LayoutPrototype layoutPrototype : layoutPrototypes) {
			if (layoutPrototype.getUserId() != defaultUserId) {
				layoutPrototypeLocalService.deleteLayoutPrototype(
					layoutPrototype);
			}
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getLayoutPrototypeByUuidAndCompanyId(String, long)}
	 */
	@Deprecated
	@Override
	public LayoutPrototype getLayoutPrototypeByUuid(String uuid)
		throws PortalException {

		return layoutPrototypePersistence.findByUuid_First(uuid, null);
	}

	@Override
	public LayoutPrototype getLayoutPrototypeByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return layoutPrototypePersistence.findByUuid_C_First(
			uuid, companyId, null);
	}

	@Override
	public List<LayoutPrototype> search(
		long companyId, Boolean active, int start, int end,
		OrderByComparator obc) {

		if (active != null) {
			return layoutPrototypePersistence.findByC_A(
				companyId, active, start, end, obc);
		}
		else {
			return layoutPrototypePersistence.findByCompanyId(
				companyId, start, end, obc);
		}
	}

	@Override
	public int searchCount(long companyId, Boolean active) {
		if (active != null) {
			return layoutPrototypePersistence.countByC_A(companyId, active);
		}
		else {
			return layoutPrototypePersistence.countByCompanyId(companyId);
		}
	}

	@Override
	public LayoutPrototype updateLayoutPrototype(
			long layoutPrototypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout prototype

		LayoutPrototype layoutPrototype =
			layoutPrototypePersistence.findByPrimaryKey(layoutPrototypeId);

		layoutPrototype.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPrototype.setNameMap(nameMap);
		layoutPrototype.setDescriptionMap(descriptionMap);
		layoutPrototype.setActive(active);

		layoutPrototypePersistence.update(layoutPrototype);

		// Layout

		Layout layout = layoutPrototype.getLayout();

		layout.setModifiedDate(layoutPrototype.getModifiedDate());
		layout.setNameMap(nameMap);

		layoutPersistence.update(layout);

		return layoutPrototype;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #updateLayoutPrototype(long,
	 *             Map, String, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public LayoutPrototype updateLayoutPrototype(
			long layoutPrototypeId, Map<Locale, String> nameMap,
			String description, boolean active)
		throws PortalException {

		return updateLayoutPrototype(
			layoutPrototypeId, nameMap, description, active, null);
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

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		return updateLayoutPrototype(
			layoutPrototypeId, nameMap, descriptionMap, active, null);
	}

}