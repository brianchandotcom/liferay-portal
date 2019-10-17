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

package com.liferay.info.service.impl;

import com.liferay.info.constants.InfoItemUsageConstants;
import com.liferay.info.model.InfoItemUsage;
import com.liferay.info.service.base.InfoItemUsageLocalServiceBaseImpl;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.info.model.InfoItemUsage",
	service = AopService.class
)
public class InfoItemUsageLocalServiceImpl
	extends InfoItemUsageLocalServiceBaseImpl {

	@Override
	public InfoItemUsage addDefaultInfoItemUsage(
		long groupId, long classNameId, long classPK,
		ServiceContext serviceContext) {

		return addInfoItemUsage(
			groupId, classNameId, classPK, 0, StringPool.BLANK, 0,
			serviceContext);
	}

	@Override
	public InfoItemUsage addInfoItemUsage(
		long groupId, long classNameId, long classPK, long containerType,
		String containerKey, long plid, ServiceContext serviceContext) {

		long infoItemUsageId = counterLocalService.increment();

		InfoItemUsage infoItemUsage = infoItemUsagePersistence.create(
			infoItemUsageId);

		infoItemUsage.setUuid(serviceContext.getUuid());
		infoItemUsage.setGroupId(groupId);
		infoItemUsage.setCreateDate(new Date());
		infoItemUsage.setModifiedDate(new Date());
		infoItemUsage.setClassNameId(classNameId);
		infoItemUsage.setClassPK(classPK);
		infoItemUsage.setContainerType(containerType);
		infoItemUsage.setContainerKey(containerKey);
		infoItemUsage.setPlid(plid);
		infoItemUsage.setType(_getType(plid));

		return infoItemUsagePersistence.update(infoItemUsage);
	}

	@Override
	public void deleteInfoItemUsages(long classNameId, long classPK) {
		infoItemUsagePersistence.removeByC_C(classNameId, classPK);
	}

	@Override
	public void deleteInfoItemUsages(
		long containerType, String containerKey, long plid) {

		infoItemUsagePersistence.removeByC_C_P(
			containerType, containerKey, plid);
	}

	@Override
	public void deleteInfoItemUsagesByPlid(long plid) {
		infoItemUsagePersistence.removeByPlid(plid);
	}

	@Override
	public InfoItemUsage fetchInfoItemUsage(
		long classNameId, long classPK, long containerType, String containerKey,
		long plid) {

		return infoItemUsagePersistence.fetchByC_C_C_C_P(
			classNameId, classPK, containerType, containerKey, plid);
	}

	@Override
	public List<InfoItemUsage> getInfoItemUsages(
		long classNameId, long classPK) {

		return infoItemUsagePersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public List<InfoItemUsage> getInfoItemUsages(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return infoItemUsagePersistence.findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator);
	}

	@Override
	public List<InfoItemUsage> getInfoItemUsages(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return infoItemUsagePersistence.findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public List<InfoItemUsage> getInfoItemUsagesByPlid(long plid) {
		return infoItemUsagePersistence.findByPlid(plid);
	}

	@Override
	public int getInfoItemUsagesCount(long classNameId, long classPK) {
		return infoItemUsagePersistence.countByC_C(classNameId, classPK);
	}

	@Override
	public int getInfoItemUsagesCount(
		long classNameId, long classPK, int type) {

		return infoItemUsagePersistence.countByC_C_T(
			classNameId, classPK, type);
	}

	@Override
	public int getUniqueInfoItemUsagesCount(long classNameId, long classPK) {
		return infoItemUsageFinder.countByC_C(classNameId, classPK);
	}

	@Override
	public boolean hasDefaultInfoItemUsage(long classNameId, long classPK) {
		InfoItemUsage infoItemUsage =
			infoItemUsageLocalService.fetchInfoItemUsage(
				classNameId, classPK, 0, StringPool.BLANK, 0);

		if (infoItemUsage != null) {
			return true;
		}

		return false;
	}

	private int _getType(long plid) {
		if (plid <= 0) {
			return InfoItemUsageConstants.TYPE_DEFAULT;
		}

		Layout layout = layoutLocalService.fetchLayout(plid);

		if ((layout.getClassNameId() > 0) && (layout.getClassPK() > 0)) {
			plid = layout.getClassPK();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(plid);

		if (layoutPageTemplateEntry == null) {
			return InfoItemUsageConstants.TYPE_LAYOUT;
		}

		if (layoutPageTemplateEntry.getType() ==
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) {

			return InfoItemUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE;
		}

		return InfoItemUsageConstants.TYPE_PAGE_TEMPLATE;
	}

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}