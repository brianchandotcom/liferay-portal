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

package com.liferay.fragment.entry.processor.drop.zone.listener;

import com.liferay.fragment.entry.processor.drop.zone.DropZoneFragmentEntryProcessor;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListener;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ContentPageEditorListener.class)
public class DropZoneContentPageEditorListener
	implements ContentPageEditorListener {

	@Override
	public void onAddFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
		try {
			_updateLayoutPageTemplateStructure(fragmentEntryLink);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to update layout page template structure",
					exception);
			}
		}
	}

	@Override
	public void onDeleteFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
		try {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues());

			JSONObject dropZoneProcessorJSONObject =
				editableValuesJSONObject.getJSONObject(
					DropZoneFragmentEntryProcessor.class.getName());

			if ((dropZoneProcessorJSONObject == null) ||
				(dropZoneProcessorJSONObject.length() <= 0)) {

				return;
			}

			LayoutStructure layoutStructure = _getLayoutStructure(
				fragmentEntryLink);

			if (layoutStructure == null) {
				return;
			}

			Iterator<String> keys = dropZoneProcessorJSONObject.keys();

			while (keys.hasNext()) {
				String key = keys.next();

				String uuid = dropZoneProcessorJSONObject.getString(key);

				if (Validator.isNull(uuid)) {
					continue;
				}

				List<LayoutStructureItem> layoutStructureItems =
					layoutStructure.deleteLayoutStructureItem(uuid);

				for (LayoutStructureItem layoutStructureItem :
						layoutStructureItems) {

					if (!(layoutStructureItem instanceof
							FragmentLayoutStructureItem)) {

						continue;
					}

					FragmentLayoutStructureItem fragmentLayoutStructureItem =
						(FragmentLayoutStructureItem)layoutStructureItem;

					if (fragmentLayoutStructureItem.getFragmentEntryLinkId() <=
							0) {

						continue;
					}

					_deleteFragmentEntryLink(
						fragmentLayoutStructureItem.getFragmentEntryLinkId());
				}
			}

			JSONObject dataJSONObject = layoutStructure.toJSONObject();

			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructure(
					fragmentEntryLink.getGroupId(),
					fragmentEntryLink.getClassNameId(),
					fragmentEntryLink.getClassPK(),
					fragmentEntryLink.getSegmentsExperienceId(),
					dataJSONObject.toString());
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onUpdateFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
	}

	@Override
	public void onUpdateFragmentEntryLinkConfigurationValues(
		FragmentEntryLink fragmentEntryLink) {

		try {
			_updateLayoutPageTemplateStructure(fragmentEntryLink);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to update layout page template structure",
					exception);
			}
		}
	}

	private void _deleteFragmentEntryLink(long fragmentEntryLinkId)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
				fragmentEntryLinkId);

		if (fragmentEntryLink.getFragmentEntryId() == 0) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues());

			String portletId = jsonObject.getString(
				"portletId", StringPool.BLANK);

			if (Validator.isNotNull(portletId)) {
				String instanceId = jsonObject.getString(
					"instanceId", StringPool.BLANK);

				_portletLocalService.deletePortlet(
					fragmentEntryLink.getCompanyId(),
					PortletIdCodec.encode(portletId, instanceId),
					fragmentEntryLink.getClassPK());

				_layoutClassedModelUsageLocalService.
					deleteLayoutClassedModelUsages(
						PortletIdCodec.encode(portletId, instanceId),
						_portal.getClassNameId(Portlet.class),
						fragmentEntryLink.getClassPK());
			}
		}

		List<String> portletIds =
			_portletRegistry.getFragmentEntryLinkPortletIds(fragmentEntryLink);

		for (String portletId : portletIds) {
			_portletLocalService.deletePortlet(
				fragmentEntryLink.getCompanyId(), portletId,
				fragmentEntryLink.getClassPK());

			_layoutClassedModelUsageLocalService.deleteLayoutClassedModelUsages(
				portletId, _portal.getClassNameId(Portlet.class),
				fragmentEntryLink.getClassPK());
		}

		_layoutClassedModelUsageLocalService.deleteLayoutClassedModelUsages(
			String.valueOf(fragmentEntryLinkId),
			_portal.getClassNameId(FragmentEntryLink.class),
			fragmentEntryLink.getClassPK());
	}

	private LayoutStructure _getLayoutStructure(
		FragmentEntryLink fragmentEntryLink) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					fragmentEntryLink.getGroupId(),
					fragmentEntryLink.getClassNameId(),
					fragmentEntryLink.getClassPK());

		if (layoutPageTemplateStructure == null) {
			return null;
		}

		String data = layoutPageTemplateStructure.getData(
			fragmentEntryLink.getSegmentsExperienceId());

		if (Validator.isNull(data)) {
			return null;
		}

		return LayoutStructure.of(data);
	}

	private void _updateLayoutPageTemplateStructure(
			FragmentEntryLink fragmentEntryLink)
		throws PortalException {

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject dropZoneProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				DropZoneFragmentEntryProcessor.class.getName());

		if ((dropZoneProcessorJSONObject == null) ||
			(dropZoneProcessorJSONObject.length() <= 0)) {

			return;
		}

		LayoutStructure layoutStructure = _getLayoutStructure(
			fragmentEntryLink);

		if (layoutStructure == null) {
			return;
		}

		LayoutStructureItem parentLayoutStructureItem =
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLink.getFragmentEntryLinkId());

		if (parentLayoutStructureItem == null) {
			return;
		}

		Iterator<String> keys = dropZoneProcessorJSONObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			String uuid = dropZoneProcessorJSONObject.getString(key);

			if (Validator.isNotNull(uuid)) {
				continue;
			}

			LayoutStructureItem fragmentDropZoneLayoutStructureItem =
				layoutStructure.addFragmentDropZoneLayoutStructureItem(
					parentLayoutStructureItem.getItemId(), 0);

			dropZoneProcessorJSONObject.put(
				key, fragmentDropZoneLayoutStructureItem.getItemId());
		}

		fragmentEntryLink.setEditableValues(
			editableValuesJSONObject.toString());

		JSONObject dataJSONObject = layoutStructure.toJSONObject();

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(
				fragmentEntryLink.getGroupId(),
				fragmentEntryLink.getClassNameId(),
				fragmentEntryLink.getClassPK(),
				fragmentEntryLink.getSegmentsExperienceId(),
				dataJSONObject.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DropZoneContentPageEditorListener.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletRegistry _portletRegistry;

}