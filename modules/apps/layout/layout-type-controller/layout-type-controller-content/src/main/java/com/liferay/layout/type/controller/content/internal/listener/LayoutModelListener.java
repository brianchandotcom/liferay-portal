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

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.type.controller.content.internal.constants.ContentLayoutTypeControllerConstants;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) throws ModelListenerException {
		if (ExportImportThreadLocal.isImportInProcess() ||
			ExportImportThreadLocal.isStagingInProcess()) {

			return;
		}

		if (!Objects.equals(
				layout.getType(),
				ContentLayoutTypeControllerConstants.LAYOUT_TYPE_CONTENT)) {

			return;
		}

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		long layoutPageTemplateEntryId = GetterUtil.getLong(
			typeSettingsProperties.get("layoutPageTemplateEntryId"));

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				layoutPageTemplateEntryId);

		if (layoutPageTemplateEntry == null) {
			return;
		}

		try {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						layout.getGroupId(),
						_portal.getClassNameId(
							LayoutPageTemplateEntry.class.getName()),
						layoutPageTemplateEntryId, true);

			JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(
				layoutPageTemplateStructure.getData());

			JSONArray structureJSONArray = dataJSONObject.getJSONArray(
				"structure");

			if (structureJSONArray == null) {
				return;
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			for (int i = 0; i < structureJSONArray.length(); i++) {
				JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

				JSONArray columnsJSONArray = rowJSONObject.getJSONArray(
					"columns");

				for (int j = 0; j < columnsJSONArray.length(); j++) {
					JSONObject columnJSONObject =
						columnsJSONArray.getJSONObject(j);

					JSONArray fragmentEntryLinkIdsJSONArray =
						columnJSONObject.getJSONArray("fragmentEntryLinkIds");

					JSONArray newFragmentEntryLinkIdsJSONArray =
						JSONFactoryUtil.createJSONArray();

					for (int k = 0; k < fragmentEntryLinkIdsJSONArray.length();
						 k++) {

						long fragmentEntryLinkId =
							fragmentEntryLinkIdsJSONArray.getLong(k);

						if (fragmentEntryLinkId <= 0) {
							continue;
						}

						FragmentEntryLink fragmentEntryLink =
							_fragmentEntryLinkLocalService.
								fetchFragmentEntryLink(fragmentEntryLinkId);

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
								fragmentEntryLink.getHtml(),
								fragmentEntryLink.getJs(),
								fragmentEntryLink.getEditableValues(),
								fragmentEntryLink.getPosition(),
								serviceContext);

						newFragmentEntryLinkIdsJSONArray.put(
							newFragmentEntryLink.getFragmentEntryLinkId());
					}

					columnJSONObject.put(
						"fragmentEntryLinkIds",
						newFragmentEntryLinkIdsJSONArray);
				}
			}

			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					layout.getUserId(), layout.getGroupId(),
					_portal.getClassNameId(Layout.class), layout.getPlid(),
					dataJSONObject.toString(), serviceContext);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			throw new ModelListenerException(pe);
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

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutModelListener.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}