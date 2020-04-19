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
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.util.SegmentsExperiencePortletUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo García
 * @author David Arques
 */
public class SegmentsExperienceUtil {

	public static void copySegmentsExperienceData(
			long classNameId, long classPK, long groupId,
			long sourceSegmentsExperienceId, long targetSegmentsExperienceId)
		throws PortalException {

		_copyLayoutData(
			classNameId, classPK, groupId, sourceSegmentsExperienceId,
			targetSegmentsExperienceId);

		_copyFragmentEntryLinksEditableValues(
			classNameId, classPK, groupId, sourceSegmentsExperienceId,
			targetSegmentsExperienceId);

		_copyPortletPreferences(
			classPK, sourceSegmentsExperienceId, targetSegmentsExperienceId);
	}

	protected static JSONObject copyEditableValues(
		JSONObject editableValuesJSONObject, long sourceSegmentsExperienceId,
		long targetSegmentsExperienceId) {

		Iterator<String> keysIterator = editableValuesJSONObject.keys();

		while (keysIterator.hasNext()) {
			String editableProcessorKey = keysIterator.next();

			JSONObject editableProcessorJSONObject =
				editableValuesJSONObject.getJSONObject(editableProcessorKey);

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

				if ((editableJSONObject != null) &&
					editableJSONObject.has(
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

		return editableValuesJSONObject;
	}

	private static Map<Long, String> _copyFragmentEntryLinksEditableValues(
			List<FragmentEntryLink> fragmentEntryLinks,
			long sourceSegmentsExperienceId, long targetSegmentsExperienceId)
		throws PortalException {

		Map<Long, String> fragmentEntryLinksEditableValuesMap = new HashMap<>();

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues());

			copyEditableValues(
				editableValuesJSONObject, sourceSegmentsExperienceId,
				targetSegmentsExperienceId);

			fragmentEntryLinksEditableValuesMap.put(
				fragmentEntryLink.getFragmentEntryLinkId(),
				editableValuesJSONObject.toString());
		}

		return fragmentEntryLinksEditableValuesMap;
	}

	private static void _copyFragmentEntryLinksEditableValues(
			long classNameId, long classPK, long groupId,
			long sourceSegmentsExperienceId, long targetSegmentsExperienceId)
		throws PortalException {

		Map<Long, String> fragmentEntryLinksEditableValuesMap =
			_copyFragmentEntryLinksEditableValues(
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
					groupId, classNameId, classPK),
				sourceSegmentsExperienceId, targetSegmentsExperienceId);

		FragmentEntryLinkLocalServiceUtil.updateFragmentEntryLinks(
			fragmentEntryLinksEditableValuesMap);
	}

	private static void _copyLayoutData(
			long classNameId, long classPK, long groupId,
			long sourceSegmentsExperienceId, long targetSegmentsExperienceId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					groupId, classNameId, classPK, true);

		LayoutPageTemplateStructureLocalServiceUtil.
			updateLayoutPageTemplateStructure(
				groupId, classNameId, classPK, targetSegmentsExperienceId,
				layoutPageTemplateStructure.getData(
					sourceSegmentsExperienceId));
	}

	private static void _copyPortletPreferences(
		long plid, long sourceSegmentsExperienceId,
		long targetSegmentsExperienceId) {

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				portletPreferences.getPortletId());

			if ((portlet == null) || portlet.isUndeployedPortlet()) {
				continue;
			}

			long segmentsExperienceId =
				SegmentsExperiencePortletUtil.getSegmentsExperienceId(
					portletPreferences.getPortletId());

			if (segmentsExperienceId != sourceSegmentsExperienceId) {
				continue;
			}

			String newPortletId =
				SegmentsExperiencePortletUtil.setSegmentsExperienceId(
					portletPreferences.getPortletId(),
					targetSegmentsExperienceId);

			PortletPreferences existingPortletPreferences =
				PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
					portletPreferences.getOwnerId(),
					portletPreferences.getOwnerType(), plid, newPortletId);

			if (existingPortletPreferences == null) {
				PortletPreferencesLocalServiceUtil.addPortletPreferences(
					portletPreferences.getCompanyId(),
					portletPreferences.getOwnerId(),
					portletPreferences.getOwnerType(), plid, newPortletId,
					portlet, portletPreferences.getPreferences());
			}
			else {
				existingPortletPreferences.setPreferences(
					portletPreferences.getPreferences());

				PortletPreferencesLocalServiceUtil.updatePortletPreferences(
					existingPortletPreferences);
			}
		}
	}

}