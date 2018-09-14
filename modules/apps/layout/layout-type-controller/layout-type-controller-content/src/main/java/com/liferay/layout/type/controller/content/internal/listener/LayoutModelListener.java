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

package com.liferay.layout.type.controller.content.internal.listener;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.type.controller.content.internal.constants.ContentLayoutTypeControllerConstants;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) throws ModelListenerException {
		if (!Objects.equals(
				layout.getType(),
				ContentLayoutTypeControllerConstants.LAYOUT_TYPE_CONTENT)) {

			return;
		}

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		long layoutPageTemplateEntryId = GetterUtil.getLong(
			typeSettingsProperties.get("layoutPageTemplateEntryId"));

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(),
					_portal.getClassNameId(
						LayoutPageTemplateEntry.class.getName()),
					layoutPageTemplateEntryId);

		if (layoutPageTemplateStructure == null) {
			return;
		}

		String data = layoutPageTemplateStructure.getData();

		if (Validator.isNull(data)) {
			return;
		}

		try {
			JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

			JSONArray structureJSONArray = dataJSONObject.getJSONArray(
				"structure");

			if (structureJSONArray == null) {
				return;
			}

			List<FragmentEntryLink> fragmentEntryLinks =
				_fragmentEntryLinkLocalService.getFragmentEntryLinks(
					layout.getGroupId(),
					_portal.getClassNameId(
						LayoutPageTemplateEntry.class.getName()),
					layoutPageTemplateEntryId);

			Stream<FragmentEntryLink> stream = fragmentEntryLinks.stream();

			Map<Long, FragmentEntryLink> fragmentEntryLinksMap = stream.collect(
				Collectors.toMap(
					FragmentEntryLink::getFragmentEntryLinkId,
					fragmentEntryLink -> fragmentEntryLink));

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			JSONArray newStructureJSONArray = JSONFactoryUtil.createJSONArray();

			for (int i = 0; i < structureJSONArray.length(); i++) {
				FragmentEntryLink fragmentEntryLink = fragmentEntryLinksMap.get(
					structureJSONArray.getLong(i));

				if (fragmentEntryLink == null) {
					continue;
				}

				FragmentEntryLink newFragmentEntryLink =
					_fragmentEntryLinkLocalService.addFragmentEntryLink(
						fragmentEntryLink.getUserId(),
						fragmentEntryLink.getGroupId(),
						fragmentEntryLink.getFragmentEntryLinkId(),
						fragmentEntryLink.getFragmentEntryId(),
						_portal.getClassNameId(Layout.class.getName()),
						layout.getPlid(), fragmentEntryLink.getCss(),
						fragmentEntryLink.getHtml(), fragmentEntryLink.getJs(),
						fragmentEntryLink.getEditableValues(),
						fragmentEntryLink.getPosition(), serviceContext);

				newStructureJSONArray.put(
					newFragmentEntryLink.getFragmentEntryLinkId());
			}

			JSONObject newDataJSONObject = JSONFactoryUtil.createJSONObject();

			newDataJSONObject.put("structure", newStructureJSONArray);

			LayoutPageTemplateStructure newLayoutPageTemplateSetting =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						layout.getGroupId(),
						_portal.getClassNameId(Layout.class), layout.getPlid());

			if (newLayoutPageTemplateSetting != null) {
				_layoutPageTemplateStructureLocalService.
					updateLayoutPageTemplateStructure(
						layout.getGroupId(),
						_portal.getClassNameId(Layout.class), layout.getPlid(),
						newDataJSONObject.toString());
			}
			else {
				_layoutPageTemplateStructureLocalService.
					addLayoutPageTemplateStructure(
						layout.getUserId(), layout.getGroupId(),
						_portal.getClassNameId(Layout.class), layout.getPlid(),
						newDataJSONObject.toString(), serviceContext);
			}
		}
		catch (PortalException pe) {
			pe.printStackTrace();
		}
	}

	@Override
	public void onBeforeRemove(Layout layout) throws ModelListenerException {
		_fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				layout.getGroupId(),
				_portal.getClassNameId(Layout.class.getName()),
				layout.getPlid());
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}