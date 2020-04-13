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

package com.liferay.layout.page.template.internal.importer.helper;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.PortletIdException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.util.SegmentsExperiencePortletUtil;

import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class WidgetLayoutStructureItemHelper
	extends BaseLayoutStructureItemHelper implements LayoutStructureItemHelper {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			FragmentEntryValidator fragmentEntryValidator, Layout layout,
			LayoutStructure layoutStructure, PageElement pageElement,
			String parentItemId, int position)
		throws Exception {

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			fragmentEntryProcessorRegistry, layout, pageElement);

		if (fragmentEntryLink == null) {
			return null;
		}

		return layoutStructure.addFragmentLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(), parentItemId, position);
	}

	private FragmentEntryLink _addFragmentEntryLink(
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			Layout layout, PageElement pageElement)
		throws Exception {

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap == null) {
			return null;
		}

		Map<String, Object> fragmentDefinitionMap =
			(Map<String, Object>)definitionMap.get("widget");

		String name = (String)fragmentDefinitionMap.get("name");

		if (Validator.isNull(name)) {
			return null;
		}

		try {
			JSONObject editableValueJSONObject =
				fragmentEntryProcessorRegistry.
					getDefaultEditableValuesJSONObject(
						StringPool.BLANK, StringPool.BLANK);

			String instanceId = _getPortletInstanceId(layout, name, 0);

			editableValueJSONObject.put(
				"instanceId", instanceId
			).put(
				"portletId", name
			);

			return FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				layout.getUserId(), layout.getGroupId(), 0, 0,
				PortalUtil.getClassNameId(Layout.class), layout.getPlid(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, editableValueJSONObject.toString(),
				StringPool.BLANK, 0, null,
				ServiceContextThreadLocal.getServiceContext());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return null;
	}

	private String _getPortletInstanceId(
			Layout layout, String portletId, long segmentsExperienceId)
		throws PortletIdException {

		Portlet portlet = PortletLocalServiceUtil.fetchPortletById(
			layout.getCompanyId(), portletId);

		if (portlet == null) {
			throw new PortletIdException();
		}

		if (portlet.isInstanceable()) {
			return SegmentsExperiencePortletUtil.setSegmentsExperienceId(
				PortletIdCodec.generateInstanceId(), segmentsExperienceId);
		}

		String instanceId =
			SegmentsExperiencePortletUtil.setSegmentsExperienceId(
				String.valueOf(CharPool.NUMBER_0), segmentsExperienceId);

		String checkPortletId =
			SegmentsExperiencePortletUtil.setSegmentsExperienceId(
				PortletIdCodec.encode(portletId, instanceId),
				segmentsExperienceId);

		long count =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				checkPortletId);

		if (count > 0) {
			throw new PortletIdException(
				"Unable to add uninstanceable portlet more than once");
		}

		return instanceId;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WidgetLayoutStructureItemHelper.class);

}