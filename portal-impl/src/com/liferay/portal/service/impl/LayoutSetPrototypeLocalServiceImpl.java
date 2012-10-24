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

import com.liferay.portal.RequiredLayoutSetPrototypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutSetPrototypeLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class LayoutSetPrototypeLocalServiceImpl
	extends LayoutSetPrototypeLocalServiceBaseImpl {

	public LayoutSetPrototype addLayoutSetPrototype(
			long userId, long companyId, Map<Locale, String> nameMap,
			String description, boolean active, boolean layoutsUpdateable,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout set prototype

		Date now = new Date();

		long layoutSetPrototypeId = counterLocalService.increment();

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypePersistence.create(layoutSetPrototypeId);

		layoutSetPrototype.setUuid(serviceContext.getUuid());
		layoutSetPrototype.setCompanyId(companyId);
		layoutSetPrototype.setCreateDate(serviceContext.getCreateDate(now));
		layoutSetPrototype.setModifiedDate(serviceContext.getModifiedDate(now));
		layoutSetPrototype.setNameMap(nameMap);
		layoutSetPrototype.setDescription(description);
		layoutSetPrototype.setActive(active);

		UnicodeProperties settingsProperties =
			layoutSetPrototype.getSettingsProperties();

		settingsProperties.put(
			"layoutsUpdateable", String.valueOf(layoutsUpdateable));

		layoutSetPrototype.setSettingsProperties(settingsProperties);

		layoutSetPrototypePersistence.update(layoutSetPrototype);

		// Resources

		if (userId > 0) {
			resourceLocalService.addResources(
				companyId, 0, userId, LayoutSetPrototype.class.getName(),
				layoutSetPrototype.getLayoutSetPrototypeId(), false, false,
				false);
		}

		// Group

		String friendlyURL =
			"/template-" + layoutSetPrototype.getLayoutSetPrototypeId();

		Group group = groupLocalService.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			LayoutSetPrototype.class.getName(),
			layoutSetPrototype.getLayoutSetPrototypeId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			layoutSetPrototype.getName(LocaleUtil.getDefault()), null, 0,
			friendlyURL, false, true, serviceContext);

		layoutLocalService.addLayout(
			userId, group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "home", null, null,
			LayoutConstants.TYPE_PORTLET, false, "/home", serviceContext);

		return layoutSetPrototype;
	}

	@Override
	public LayoutSetPrototype deleteLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype)
		throws PortalException, SystemException {

		// Group

		if (layoutSetPersistence.countByLayoutSetPrototypeUuid(
				layoutSetPrototype.getUuid()) > 0) {

			throw new RequiredLayoutSetPrototypeException();
		}

		Group group = layoutSetPrototype.getGroup();

		groupLocalService.deleteGroup(group);

		// Resources

		resourceLocalService.deleteResource(
			layoutSetPrototype.getCompanyId(),
			LayoutSetPrototype.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutSetPrototype.getLayoutSetPrototypeId());

		// Layout set prototype

		layoutSetPrototypePersistence.remove(layoutSetPrototype);

		// Permission cache

		PermissionCacheUtil.clearCache();

		return layoutSetPrototype;
	}

	@Override
	public LayoutSetPrototype deleteLayoutSetPrototype(
			long layoutSetPrototypeId)
		throws PortalException, SystemException {

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypePersistence.findByPrimaryKey(
				layoutSetPrototypeId);

		return deleteLayoutSetPrototype(layoutSetPrototype);
	}

	/**
	 * @deprecated {@link #getLayoutSetPrototypeByUuidAndCompanyId(String,
	 *             long)}
	 */
	public LayoutSetPrototype getLayoutSetPrototypeByUuid(String uuid)
		throws PortalException, SystemException {

		return layoutSetPrototypePersistence.findByUuid_First(uuid, null);
	}

	public LayoutSetPrototype getLayoutSetPrototypeByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException, SystemException {

		return layoutSetPrototypePersistence.findByUuid_C_First(
			uuid, companyId, null);
	}

	public List<LayoutSetPrototype> search(
			long companyId, Boolean active, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (active != null) {
			return layoutSetPrototypePersistence.findByC_A(
				companyId, active, start, end, obc);
		}
		else {
			return layoutSetPrototypePersistence.findByCompanyId(
				companyId, start, end, obc);
		}
	}

	public int searchCount(long companyId, Boolean active)
		throws SystemException {

		if (active != null) {
			return layoutSetPrototypePersistence.countByC_A(companyId, active);
		}
		else {
			return layoutSetPrototypePersistence.countByCompanyId(companyId);
		}
	}

	public LayoutSetPrototype updateLayoutSetPrototype(
			long layoutSetPrototypeId, Map<Locale, String> nameMap,
			String description, boolean active, boolean layoutsUpdateable,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout set prototype

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypePersistence.findByPrimaryKey(
				layoutSetPrototypeId);

		layoutSetPrototype.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutSetPrototype.setNameMap(nameMap);
		layoutSetPrototype.setDescription(description);
		layoutSetPrototype.setActive(active);

		UnicodeProperties settingsProperties =
			layoutSetPrototype.getSettingsProperties();

		settingsProperties.put(
			"layoutsUpdateable", String.valueOf(layoutsUpdateable));

		layoutSetPrototype.setSettingsProperties(settingsProperties);

		layoutSetPrototypePersistence.update(layoutSetPrototype);

		// Group

		Group group = groupLocalService.getLayoutSetPrototypeGroup(
			layoutSetPrototype.getCompanyId(), layoutSetPrototypeId);

		group.setName(layoutSetPrototype.getName(LocaleUtil.getDefault()));

		groupPersistence.update(group);

		return layoutSetPrototype;
	}

	public LayoutSetPrototype updateLayoutSetPrototype(
			long layoutSetPrototypeId, String settings)
		throws PortalException, SystemException {

		// Layout set prototype

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypePersistence.findByPrimaryKey(
				layoutSetPrototypeId);

		layoutSetPrototype.setModifiedDate(new Date());
		layoutSetPrototype.setSettings(settings);

		layoutSetPrototypePersistence.update(layoutSetPrototype);

		// Group

		UnicodeProperties settingsProperties =
			layoutSetPrototype.getSettingsProperties();

		if (!settingsProperties.containsKey("customJspServletContextName")) {
			return layoutSetPrototype;
		}

		Group group = groupLocalService.getLayoutSetPrototypeGroup(
			layoutSetPrototype.getCompanyId(), layoutSetPrototypeId);

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"customJspServletContextName",
			settingsProperties.getProperty("customJspServletContextName"));

		group.setTypeSettings(typeSettingsProperties.toString());

		groupPersistence.update(group);

		return layoutSetPrototype;
	}

}