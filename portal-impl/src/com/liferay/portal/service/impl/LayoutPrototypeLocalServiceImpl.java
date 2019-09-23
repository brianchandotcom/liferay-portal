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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredLayoutPrototypeException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.base.LayoutPrototypeLocalServiceBaseImpl;

import java.util.Date;
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

		layoutPrototype = layoutPrototypePersistence.update(layoutPrototype);

		// Resources

		resourceLocalService.addResources(
			companyId, 0, userId, LayoutPrototype.class.getName(),
			layoutPrototype.getLayoutPrototypeId(), false, true, false);

		// Group

		String friendlyURL =
			"/template-" + layoutPrototype.getLayoutPrototypeId();

		Group group = groupLocalService.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			LayoutPrototype.class.getName(),
			layoutPrototype.getLayoutPrototypeId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, layoutPrototype.getNameMap(),
			null, 0, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			friendlyURL, false, true, null);

		if (GetterUtil.getBoolean(
				serviceContext.getAttribute("addDefaultLayout"), true)) {

			Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "/layout"
			).build();

			layoutLocalService.addLayout(
				userId, group.getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
				layoutPrototype.getNameMap(), null, null, null, null,
				LayoutConstants.TYPE_PORTLET, StringPool.BLANK, false,
				friendlyURLMap, serviceContext);
		}

		return layoutPrototype;
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public LayoutPrototype deleteLayoutPrototype(
			LayoutPrototype layoutPrototype)
		throws PortalException {

		// Group

		if (!CompanyThreadLocal.isDeleteInProcess()) {
			int count = layoutPersistence.countByC_L(
				layoutPrototype.getCompanyId(), layoutPrototype.getUuid());

			if (count > 0) {
				StringBundler sb = new StringBundler(5);

				sb.append("Layout prototype cannot be deleted because it is ");
				sb.append("used by layout with company ID ");
				sb.append(layoutPrototype.getCompanyId());
				sb.append(" and layout prototype UUID ");
				sb.append(layoutPrototype.getUuid());

				_log.error(sb.toString());

				throw new RequiredLayoutPrototypeException();
			}
		}

		groupLocalService.deleteGroup(layoutPrototype.getGroup());

		// Resources

		resourceLocalService.deleteResource(
			layoutPrototype.getCompanyId(), LayoutPrototype.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutPrototype.getLayoutPrototypeId());

		// Layout Prototype

		layoutPrototypePersistence.remove(layoutPrototype);

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

	@Override
	public LayoutPrototype fetchLayoutPrototype(
		long companyId, String name, Locale locale) {

		List<LayoutPrototype> layoutPrototypes =
			layoutPrototypePersistence.findByCompanyId(companyId);

		for (LayoutPrototype layoutPrototype : layoutPrototypes) {
			String layoutPrototypeName = layoutPrototype.getName(locale);

			if (layoutPrototypeName.equals(name)) {
				return layoutPrototype;
			}
		}

		return null;
	}

	@Override
	public LayoutPrototype fetchLayoutProtoype(long companyId, String name) {
		return layoutPrototypeLocalService.fetchLayoutPrototype(
			companyId, name, LocaleUtil.getDefault());
	}

	@Override
	public LayoutPrototype getLayoutPrototype(long companyId, String name)
		throws PortalException {

		return layoutPrototypeLocalService.getLayoutPrototype(
			companyId, name, LocaleUtil.getDefault());
	}

	@Override
	public LayoutPrototype getLayoutPrototype(
			long companyId, String name, Locale locale)
		throws PortalException {

		LayoutPrototype layoutPrototype =
			layoutPrototypeLocalService.fetchLayoutPrototype(
				companyId, name, locale);

		if (layoutPrototype == null) {
			throw new NoSuchLayoutPrototypeException();
		}

		return layoutPrototype;
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
		OrderByComparator<LayoutPrototype> obc) {

		if (active != null) {
			return layoutPrototypePersistence.findByC_A(
				companyId, active, start, end, obc);
		}

		return layoutPrototypePersistence.findByCompanyId(
			companyId, start, end, obc);
	}

	@Override
	public int searchCount(long companyId, Boolean active) {
		if (active != null) {
			return layoutPrototypePersistence.countByC_A(companyId, active);
		}

		return layoutPrototypePersistence.countByCompanyId(companyId);
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

		layoutPrototype = layoutPrototypePersistence.update(layoutPrototype);

		// Layout

		Layout layout = layoutPrototype.getLayout();

		layout.setModifiedDate(layoutPrototype.getModifiedDate());

		layout.setNameMap(nameMap);

		layoutPersistence.update(layout);

		return layoutPrototype;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPrototypeLocalServiceImpl.class);

}