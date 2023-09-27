/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.service.impl;

import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.service.base.LayoutClassedModelUsageLocalServiceBaseImpl;
import com.liferay.layout.util.constants.LayoutClassedModelUsageConstants;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.layout.model.LayoutClassedModelUsage",
	service = AopService.class
)
public class LayoutClassedModelUsageLocalServiceImpl
	extends LayoutClassedModelUsageLocalServiceBaseImpl {

	@Override
	public LayoutClassedModelUsage addLayoutClassedModelUsage(
		long groupId, long classNameId, long classPK,
		String classedModelExternalReferenceCode, String containerKey,
		long containerType, long plid, ServiceContext serviceContext) {

		long layoutClassedModelUsageId = counterLocalService.increment();

		LayoutClassedModelUsage layoutClassedModelUsage =
			layoutClassedModelUsagePersistence.create(
				layoutClassedModelUsageId);

		layoutClassedModelUsage.setUuid(serviceContext.getUuid());
		layoutClassedModelUsage.setGroupId(groupId);

		long companyId = serviceContext.getCompanyId();

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group != null) {
			companyId = group.getCompanyId();
		}

		layoutClassedModelUsage.setCompanyId(companyId);

		layoutClassedModelUsage.setClassNameId(classNameId);
		layoutClassedModelUsage.setClassPK(classPK);
		layoutClassedModelUsage.setClassedModelExternalReferenceCode(
			classedModelExternalReferenceCode);
		layoutClassedModelUsage.setContainerKey(containerKey);
		layoutClassedModelUsage.setContainerType(containerType);
		layoutClassedModelUsage.setPlid(plid);
		layoutClassedModelUsage.setType(_getType(plid));

		return layoutClassedModelUsagePersistence.update(
			layoutClassedModelUsage);
	}

	@Override
	public void deleteLayoutClassedModelUsages(long classNameId, long classPK) {
		layoutClassedModelUsagePersistence.removeByCN_CPK(classNameId, classPK);
	}

	@Override
	public void deleteLayoutClassedModelUsages(
		String containerKey, long containerType, long plid) {

		layoutClassedModelUsagePersistence.removeByCK_CT_P(
			containerKey, containerType, plid);
	}

	@Override
	public void deleteLayoutClassedModelUsagesByPlid(long plid) {
		layoutClassedModelUsagePersistence.removeByPlid(plid);
	}

	@Override
	public LayoutClassedModelUsage fetchLayoutClassedModelUsage(
		long classNameId, long classPK,
		String classedModelExternalReferenceCode, String containerKey,
		long containerType, long plid) {

		return layoutClassedModelUsagePersistence.fetchByCN_CPK_CMERC_CK_CT_P(
			classNameId, classPK, classedModelExternalReferenceCode,
			containerKey, containerType, plid);
	}

	@Override
	public List<LayoutClassedModelUsage> getLayoutClassedModelUsages(
		long classNameId, long classPK) {

		return layoutClassedModelUsagePersistence.findByCN_CPK(
			classNameId, classPK);
	}

	@Override
	public List<LayoutClassedModelUsage> getLayoutClassedModelUsages(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		return layoutClassedModelUsagePersistence.findByCN_CPK_T(
			classNameId, classPK, type, start, end, orderByComparator);
	}

	@Override
	public List<LayoutClassedModelUsage> getLayoutClassedModelUsages(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<LayoutClassedModelUsage> orderByComparator) {

		return layoutClassedModelUsagePersistence.findByCN_CPK(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public List<LayoutClassedModelUsage> getLayoutClassedModelUsages(
		long companyId, long classNameId, long containerType) {

		return layoutClassedModelUsagePersistence.findByC_CN_CT(
			companyId, classNameId, containerType);
	}

	@Override
	public List<LayoutClassedModelUsage> getLayoutClassedModelUsagesByPlid(
		long plid) {

		return layoutClassedModelUsagePersistence.findByPlid(plid);
	}

	@Override
	public int getLayoutClassedModelUsagesCount(
		long classNameId, long classPK) {

		return layoutClassedModelUsagePersistence.countByCN_CPK(
			classNameId, classPK);
	}

	@Override
	public int getLayoutClassedModelUsagesCount(
		long classNameId, long classPK, int type) {

		return layoutClassedModelUsagePersistence.countByCN_CPK_T(
			classNameId, classPK, type);
	}

	private int _getType(long plid) {
		if (plid <= 0) {
			return LayoutClassedModelUsageConstants.TYPE_DEFAULT;
		}

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return LayoutClassedModelUsageConstants.TYPE_DEFAULT;
		}

		if (layout.isDraftLayout()) {
			plid = layout.getClassPK();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(plid);

		if (layoutPageTemplateEntry == null) {
			return LayoutClassedModelUsageConstants.TYPE_LAYOUT;
		}

		if (layoutPageTemplateEntry.getType() ==
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) {

			return LayoutClassedModelUsageConstants.TYPE_DISPLAY_PAGE_TEMPLATE;
		}

		return LayoutClassedModelUsageConstants.TYPE_PAGE_TEMPLATE;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}