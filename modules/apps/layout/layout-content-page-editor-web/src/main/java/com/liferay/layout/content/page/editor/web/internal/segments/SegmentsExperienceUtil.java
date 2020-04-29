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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Eduardo García
 * @author David Arques
 */
public class SegmentsExperienceUtil {

	public static void copySegmentsExperienceData(
			long classNameId, long classPK, CommentManager commentManager,
			long groupId, long sourceSegmentsExperienceId,
			long targetSegmentsExperienceId,
			Function<String, ServiceContext> serviceContextFunction,
			long userId)
		throws PortalException {

		_copyLayoutData(
			classNameId, classPK, commentManager, groupId,
			sourceSegmentsExperienceId, targetSegmentsExperienceId,
			serviceContextFunction, userId);

		_copyPortletPreferences(classPK);
	}

	private static void _copyLayoutData(
			long classNameId, long classPK, CommentManager commentManager,
			long groupId, long sourceSegmentsExperienceId,
			long targetSegmentsExperienceId,
			Function<String, ServiceContext> serviceContextFunction,
			long userId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					groupId, classNameId, classPK, true);

		JSONObject dataJSONObject = _updateLayoutDataJSONObject(
			classNameId, classPK, commentManager,
			layoutPageTemplateStructure.getData(sourceSegmentsExperienceId),
			groupId, sourceSegmentsExperienceId, serviceContextFunction,
			targetSegmentsExperienceId, userId);

		LayoutPageTemplateStructureLocalServiceUtil.
			updateLayoutPageTemplateStructure(
				groupId, classNameId, classPK, targetSegmentsExperienceId,
				dataJSONObject.toString());
	}

	private static void _copyPortletPreferences(long plid) {
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

			//TODO check
			String newPortletId = PortletIdCodec.generateInstanceId();

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

	private static JSONObject _updateLayoutDataJSONObject(
			long classNameId, long classPK, CommentManager commentManager,
			String data, long groupId, long sourceSegmentsExperienceId,
			Function<String, ServiceContext> serviceContextFunction,
			long targetSegmentsExperienceId, long userId)
		throws PortalException {

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.
				getFragmentEntryLinksBySegmentsExperienceId(
					groupId, sourceSegmentsExperienceId, classNameId, classPK);

		Stream<FragmentEntryLink> stream = fragmentEntryLinks.stream();

		Map<Long, FragmentEntryLink> fragmentEntryLinkMap = stream.collect(
			Collectors.toMap(
				FragmentEntryLink::getFragmentEntryLinkId,
				fragmentEntryLink -> fragmentEntryLink));

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			if (!(layoutStructureItem instanceof FragmentLayoutStructureItem)) {
				continue;
			}

			FragmentLayoutStructureItem fragmentLayoutStructureItem =
				(FragmentLayoutStructureItem)layoutStructureItem;

			FragmentEntryLink fragmentEntryLink = fragmentEntryLinkMap.get(
				fragmentLayoutStructureItem.getFragmentEntryLinkId());

			if (fragmentEntryLink == null) {
				continue;
			}

			FragmentEntryLink newFragmentEntryLink =
				(FragmentEntryLink)fragmentEntryLink.clone();

			newFragmentEntryLink.setUuid(PortalUUIDUtil.generate());
			newFragmentEntryLink.setFragmentEntryLinkId(
				CounterLocalServiceUtil.increment());
			newFragmentEntryLink.setCreateDate(new Date());
			newFragmentEntryLink.setModifiedDate(new Date());
			newFragmentEntryLink.setOriginalFragmentEntryLinkId(0);
			newFragmentEntryLink.setSegmentsExperienceId(
				targetSegmentsExperienceId);
			newFragmentEntryLink.setLastPropagationDate(new Date());

			newFragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
					newFragmentEntryLink);

			fragmentLayoutStructureItem.setFragmentEntryLinkId(
				newFragmentEntryLink.getFragmentEntryLinkId());

			commentManager.copyDiscussion(
				userId, groupId, FragmentEntryLink.class.getName(),
				fragmentEntryLink.getFragmentEntryLinkId(),
				newFragmentEntryLink.getFragmentEntryLinkId(),
				serviceContextFunction);
		}

		return layoutStructure.toJSONObject();
	}

}