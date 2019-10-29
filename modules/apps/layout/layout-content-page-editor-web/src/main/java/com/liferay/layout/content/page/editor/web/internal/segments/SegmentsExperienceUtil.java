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

package com.liferay.layout.content.page.editor.web.internal.segments;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.util.SegmentsExperiencePortletUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo García
 */
public class SegmentsExperienceUtil {

	public static Map<Long, String> copyFragmentEntryLinksEditableValues(
			long classNameId, long classPK,
			FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
			long groupId, long sourceSegmentsExperienceId,
			long targetSegmentsExperienceId)
		throws PortalException {

		Map<Long, String> fragmentEntryLinksEditableValuesMap = new HashMap<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			fragmentEntryLinkLocalService.getFragmentEntryLinks(
				groupId, classNameId, classPK);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues());

			Iterator<String> keysIterator = editableValuesJSONObject.keys();

			while (keysIterator.hasNext()) {
				String editableProcessorKey = keysIterator.next();

				JSONObject editableProcessorJSONObject =
					editableValuesJSONObject.getJSONObject(
						editableProcessorKey);

				if (editableProcessorJSONObject == null) {
					continue;
				}

				Iterator<String> editableKeysIterator =
					editableProcessorJSONObject.keys();

				while (editableKeysIterator.hasNext()) {
					String editableKey = editableKeysIterator.next();

					if (editableKey.startsWith(
							SegmentsExperienceConstants.ID_PREFIX)) {

						JSONObject baseExperienceValueJSONObject =
							editableProcessorJSONObject.getJSONObject(
								SegmentsExperienceConstants.ID_PREFIX +
									sourceSegmentsExperienceId);

						editableProcessorJSONObject.put(
							SegmentsExperienceConstants.ID_PREFIX +
								targetSegmentsExperienceId,
							baseExperienceValueJSONObject);

						editableValuesJSONObject.put(
							editableProcessorKey, editableProcessorJSONObject);

						break;
					}

					JSONObject editableJSONObject =
						editableProcessorJSONObject.getJSONObject(editableKey);

					JSONObject valueJSONObject = null;

					if (editableJSONObject.has(
							SegmentsExperienceConstants.ID_PREFIX +
								sourceSegmentsExperienceId)) {

						valueJSONObject = editableJSONObject.getJSONObject(
							SegmentsExperienceConstants.ID_PREFIX +
								sourceSegmentsExperienceId);
					}
					else {
						continue;
					}

					editableJSONObject.put(
						SegmentsExperienceConstants.ID_PREFIX +
							targetSegmentsExperienceId,
						valueJSONObject);

					editableProcessorJSONObject.put(
						editableKey, editableJSONObject);

					editableValuesJSONObject.put(
						editableProcessorKey, editableProcessorJSONObject);
				}
			}

			fragmentEntryLinksEditableValuesMap.put(
				fragmentEntryLink.getFragmentEntryLinkId(),
				editableValuesJSONObject.toString());
		}

		fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			fragmentEntryLinksEditableValuesMap);

		return fragmentEntryLinksEditableValuesMap;
	}

	public static String copyLayoutData(
			long classNameId, long classPK, long groupId,
			LayoutPageTemplateStructureLocalService
				layoutPageTemplateStructureLocalService,
			long sourceSegmentsExperienceId, long targetSegmentsExperienceId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					groupId, classNameId, classPK, true);

		String data = layoutPageTemplateStructure.getData(
			sourceSegmentsExperienceId);

		layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(
				groupId, classNameId, classPK, targetSegmentsExperienceId,
				data);

		return data;
	}

	public static void copyPortletPreferences(
		long plid, PortletLocalService portletLocalService,
		PortletPreferencesLocalService portletPreferencesLocalService,
		long sourceSegmentsExperienceId, long targetSegmentsExperienceId) {

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			Portlet portlet = portletLocalService.getPortletById(
				portletPreferences.getPortletId());

			if ((portlet == null) || portlet.isUndeployedPortlet()) {
				continue;
			}

			long segmentsExperienceId =
				SegmentsExperiencePortletUtil.getSegmentsExperienceId(
					portletPreferences.getPortletId());

			if (segmentsExperienceId == sourceSegmentsExperienceId) {
				String newPortletId =
					SegmentsExperiencePortletUtil.setSegmentsExperienceId(
						portletPreferences.getPortletId(),
						targetSegmentsExperienceId);

				PortletPreferences existingPortletPreferences =
					portletPreferencesLocalService.fetchPortletPreferences(
						portletPreferences.getOwnerId(),
						portletPreferences.getOwnerType(), plid, newPortletId);

				if (existingPortletPreferences == null) {
					portletPreferencesLocalService.addPortletPreferences(
						portletPreferences.getCompanyId(),
						portletPreferences.getOwnerId(),
						portletPreferences.getOwnerType(), plid, newPortletId,
						portlet, portletPreferences.getPreferences());
				}
				else {
					existingPortletPreferences.setPreferences(
						portletPreferences.getPreferences());

					portletPreferencesLocalService.updatePortletPreferences(
						existingPortletPreferences);
				}
			}
		}
	}

	public static void copySegmentsExperienceData(
			long classNameId, long classPK,
			FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
			long groupId,
			LayoutPageTemplateStructureLocalService
				layoutPageTemplateStructureLocalService,
			PortletLocalService portletLocalService,
			PortletPreferencesLocalService portletPreferencesLocalService,
			long sourceSegmentsExperienceId, long targetSegmentsExperienceId)
		throws PortalException {

		copyLayoutData(
			classNameId, classPK, groupId,
			layoutPageTemplateStructureLocalService, sourceSegmentsExperienceId,
			targetSegmentsExperienceId);

		copyFragmentEntryLinksEditableValues(
			classNameId, classPK, fragmentEntryLinkLocalService, groupId,
			sourceSegmentsExperienceId, targetSegmentsExperienceId);

		copyPortletPreferences(
			classPK, portletLocalService, portletPreferencesLocalService,
			sourceSegmentsExperienceId, targetSegmentsExperienceId);
	}

	private SegmentsExperienceUtil() {
	}

}