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

package com.liferay.layout.page.template.service.impl;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateStructureLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateStructureLocalServiceImpl
	extends LayoutPageTemplateStructureLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplateStructure addLayoutPageTemplateStructure(
			long userId, long groupId, long classNameId, long classPK,
			String data, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long layoutPageTemplateStructureId = counterLocalService.increment();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructurePersistence.create(
				layoutPageTemplateStructureId);

		layoutPageTemplateStructure.setUuid(serviceContext.getUuid());
		layoutPageTemplateStructure.setGroupId(groupId);
		layoutPageTemplateStructure.setCompanyId(user.getCompanyId());
		layoutPageTemplateStructure.setUserId(user.getUserId());
		layoutPageTemplateStructure.setUserName(user.getFullName());
		layoutPageTemplateStructure.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplateStructure.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplateStructure.setClassNameId(classNameId);
		layoutPageTemplateStructure.setClassPK(classPK);
		layoutPageTemplateStructure.setData(data);

		layoutPageTemplateStructurePersistence.update(
			layoutPageTemplateStructure);

		_fragmentEntryLinkLocalService.updateClassModel(classNameId, classPK);

		return layoutPageTemplateStructure;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public LayoutPageTemplateStructure deleteLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructurePersistence.findByG_C_C(
				groupId, classNameId, classPK);

		layoutPageTemplateStructurePersistence.remove(
			layoutPageTemplateStructure);

		return layoutPageTemplateStructure;
	}

	@Override
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
		long groupId, long classNameId, long classPK) {

		return layoutPageTemplateStructurePersistence.fetchByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK,
			boolean rebuildStructure)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			fetchLayoutPageTemplateStructure(groupId, classNameId, classPK);

		if ((layoutPageTemplateStructure != null) || !rebuildStructure) {
			return layoutPageTemplateStructure;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				groupId, classNameId, classPK);

		jsonObject.put("nextColumnId", fragmentEntryLinks.size());
		jsonObject.put("nextRowId", fragmentEntryLinks.size());

		JSONArray structureJSONArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < fragmentEntryLinks.size(); i++) {
			FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(i);

			JSONObject columnsJSONObject = JSONFactoryUtil.createJSONObject();

			JSONArray columnsJSONArray = JSONFactoryUtil.createJSONArray();

			JSONObject fragmentEntryLinkIdsJSONObject =
				JSONFactoryUtil.createJSONObject();

			fragmentEntryLinkIdsJSONObject.put("columnId", String.valueOf(i));

			JSONArray fragmentEntryLinkIdsJSONArray =
				JSONFactoryUtil.createJSONArray();

			fragmentEntryLinkIdsJSONArray.put(
				fragmentEntryLink.getFragmentEntryLinkId());

			fragmentEntryLinkIdsJSONObject.put(
				"fragmentEntryLinkIds", fragmentEntryLinkIdsJSONArray);

			fragmentEntryLinkIdsJSONObject.put("size", "12");

			columnsJSONArray.put(fragmentEntryLinkIdsJSONObject);

			columnsJSONObject.put("columns", columnsJSONArray);

			columnsJSONObject.put("rowId", String.valueOf(i));

			structureJSONArray.put(columnsJSONObject);
		}

		jsonObject.put("structure", structureJSONArray);

		return addLayoutPageTemplateStructure(
			PrincipalThreadLocal.getUserId(), groupId, classNameId, classPK,
			jsonObject.toString(),
			ServiceContextThreadLocal.getServiceContext());
	}

	@Override
	public LayoutPageTemplateStructure updateLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK, String data)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructurePersistence.findByG_C_C(
				groupId, classNameId, classPK);

		layoutPageTemplateStructure.setModifiedDate(new Date());
		layoutPageTemplateStructure.setData(data);

		layoutPageTemplateStructurePersistence.update(
			layoutPageTemplateStructure);

		_fragmentEntryLinkLocalService.updateClassModel(classNameId, classPK);

		return layoutPageTemplateStructure;
	}

	@ServiceReference(type = FragmentEntryLinkLocalService.class)
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@ServiceReference(type = LayoutLocalService.class)
	private LayoutLocalService _layoutLocalService;

	@ServiceReference(type = Portal.class)
	private Portal _portal;

}