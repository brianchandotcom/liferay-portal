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
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceServiceUtil;
import com.liferay.segments.service.SegmentsExperimentLocalServiceUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 * @author David Arques
 */
public class SegmentsExperienceUtil {

	public static void copySegmentsExperienceData(
			long plid, CommentManager commentManager, long groupId,
			PortletRegistry portletRegistry, long sourceSegmentsExperienceId,
			long targetSegmentsExperienceId,
			Function<String, ServiceContext> serviceContextFunction,
			long userId)
		throws PortalException {

		_copyLayoutData(
			plid, commentManager, groupId, portletRegistry,
			sourceSegmentsExperienceId, targetSegmentsExperienceId,
			serviceContextFunction, userId);
	}

	public static Map<String, Object> getAvailableSegmentsExperiences(
			HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Map<String, Object> availableSegmentsExperiences = new HashMap<>();

		Layout draftLayout = themeDisplay.getLayout();

		Layout layout = LayoutLocalServiceUtil.getLayout(
			draftLayout.getClassPK());

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			layout, themeDisplay);

		List<SegmentsExperience> segmentsExperiences =
			SegmentsExperienceServiceUtil.getSegmentsExperiences(
				themeDisplay.getScopeGroupId(),
				PortalUtil.getClassNameId(Layout.class.getName()),
				themeDisplay.getPlid(), true);

		boolean addedDefault = false;

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			if ((segmentsExperience.getPriority() <
					SegmentsExperienceConstants.PRIORITY_DEFAULT) &&
				!addedDefault) {

				availableSegmentsExperiences.put(
					String.valueOf(SegmentsExperienceConstants.ID_DEFAULT),
					_getDefaultExperience(themeDisplay, layoutFullURL));

				addedDefault = true;
			}

			availableSegmentsExperiences.put(
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				HashMapBuilder.<String, Object>put(
					"hasLockedSegmentsExperiment",
					segmentsExperience.hasSegmentsExperiment()
				).put(
					"name", segmentsExperience.getName(themeDisplay.getLocale())
				).put(
					"priority", segmentsExperience.getPriority()
				).put(
					"segmentsEntryId",
					String.valueOf(segmentsExperience.getSegmentsEntryId())
				).put(
					"segmentsExperienceId",
					String.valueOf(segmentsExperience.getSegmentsExperienceId())
				).put(
					"segmentsExperimentStatus",
					getSegmentsExperimentStatus(
						themeDisplay,
						segmentsExperience.getSegmentsExperienceId())
				).put(
					"segmentsExperimentURL",
					_getSegmentsExperimentURL(
						themeDisplay, layoutFullURL,
						segmentsExperience.getSegmentsExperienceId())
				).build());
		}

		if (!addedDefault) {
			availableSegmentsExperiences.put(
				String.valueOf(SegmentsExperienceConstants.ID_DEFAULT),
				_getDefaultExperience(themeDisplay, layoutFullURL));
		}

		return availableSegmentsExperiences;
	}

	public static JSONObject getSegmentsExperienceJSONObject(
		SegmentsExperience segmentsExperience) {

		return JSONUtil.put(
			"active", segmentsExperience.isActive()
		).put(
			"name", segmentsExperience.getNameCurrentValue()
		).put(
			"priority", segmentsExperience.getPriority()
		).put(
			"segmentsEntryId", segmentsExperience.getSegmentsEntryId()
		).put(
			"segmentsExperienceId", segmentsExperience.getSegmentsExperienceId()
		);
	}

	public static Map<String, Object> getSegmentsExperimentStatus(
			ThemeDisplay themeDisplay, long segmentsExperienceId)
		throws Exception {

		Optional<SegmentsExperiment> segmentsExperimentOptional =
			_getSegmentsExperimentOptional(themeDisplay, segmentsExperienceId);

		if (!segmentsExperimentOptional.isPresent()) {
			return null;
		}

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentOptional.get();

		SegmentsExperimentConstants.Status status =
			SegmentsExperimentConstants.Status.valueOf(
				segmentsExperiment.getStatus());

		return HashMapBuilder.<String, Object>put(
			"label",
			LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					themeDisplay.getLocale(), SegmentsExperienceUtil.class),
				status.getLabel())
		).put(
			"value", status.getValue()
		).build();
	}

	public static boolean hasDefaultSegmentsExperienceLockedSegmentsExperiment(
			ThemeDisplay themeDisplay)
		throws Exception {

		Optional<SegmentsExperiment> segmentsExperimentOptional =
			_getSegmentsExperimentOptional(
				themeDisplay, SegmentsExperienceConstants.ID_DEFAULT);

		if (!segmentsExperimentOptional.isPresent()) {
			return false;
		}

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentOptional.get();

		List<Integer> lockedStatusValuesList = ListUtil.fromArray(
			SegmentsExperimentConstants.Status.getLockedStatusValues());

		return lockedStatusValuesList.contains(segmentsExperiment.getStatus());
	}

	private static void _copyLayoutData(
			long plid, CommentManager commentManager, long groupId,
			PortletRegistry portletRegistry, long sourceSegmentsExperienceId,
			long targetSegmentsExperienceId,
			Function<String, ServiceContext> serviceContextFunction,
			long userId)
		throws PortalException {

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				groupId, plid, sourceSegmentsExperienceId);

		JSONObject dataJSONObject = _updateLayoutDataJSONObject(
			plid, commentManager, layoutStructure, groupId, portletRegistry,
			sourceSegmentsExperienceId, serviceContextFunction,
			targetSegmentsExperienceId, userId);

		LayoutPageTemplateStructureLocalServiceUtil.
			updateLayoutPageTemplateStructureData(
				groupId, plid, targetSegmentsExperienceId,
				dataJSONObject.toString());
	}

	private static void _copyPortletPreferences(
		FragmentEntryLink fragmentEntryLink,
		FragmentEntryLink newFragmentEntryLink, long plid,
		PortletRegistry portletRegistry) {

		for (String portletId :
				portletRegistry.getFragmentEntryLinkPortletIds(
					fragmentEntryLink)) {

			_getNewPortletPreferencesOptional(
				fragmentEntryLink.getNamespace(),
				newFragmentEntryLink.getNamespace(), plid, portletId);
		}
	}

	private static HashMap<String, Object> _getDefaultExperience(
			ThemeDisplay themeDisplay, String layoutFullURL)
		throws Exception {

		return HashMapBuilder.<String, Object>put(
			"hasLockedSegmentsExperiment",
			hasDefaultSegmentsExperienceLockedSegmentsExperiment(themeDisplay)
		).put(
			"name",
			SegmentsExperienceConstants.getDefaultSegmentsExperienceName(
				themeDisplay.getLocale())
		).put(
			"priority", SegmentsExperienceConstants.PRIORITY_DEFAULT
		).put(
			"segmentsEntryId", String.valueOf(SegmentsEntryConstants.ID_DEFAULT)
		).put(
			"segmentsExperienceId",
			String.valueOf(SegmentsExperienceConstants.ID_DEFAULT)
		).put(
			"segmentsExperimentStatus",
			getSegmentsExperimentStatus(
				themeDisplay, SegmentsExperienceConstants.ID_DEFAULT)
		).put(
			"segmentsExperimentURL",
			_getSegmentsExperimentURL(
				themeDisplay, layoutFullURL,
				SegmentsExperienceConstants.ID_DEFAULT)
		).build();
	}

	private static String _getNewEditableValues(
			String editableValues, String namespace, String newNamespace,
			long plid)
		throws JSONException {

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		String instanceId = editableValuesJSONObject.getString("instanceId");
		String portletId = editableValuesJSONObject.getString("portletId");

		if (Validator.isNull(instanceId) || Validator.isNull(portletId)) {
			return editableValues;
		}

		return _getNewPortletPreferencesOptional(
			namespace, newNamespace, plid,
			PortletIdCodec.encode(portletId, instanceId)
		).map(
			portletPreferences -> {
				JSONObject newEditableValuesJSONObject =
					editableValuesJSONObject.put(
						"instanceId",
						PortletIdCodec.decodeInstanceId(
							portletPreferences.getPortletId()));

				return newEditableValuesJSONObject.toString();
			}
		).orElse(
			editableValues
		);
	}

	private static String _getNewPortletId(
		String namespace, String newNamespace, String portletId) {

		if (!portletId.contains(namespace)) {
			return PortletIdCodec.encode(
				PortletIdCodec.decodePortletName(portletId), newNamespace);
		}

		return StringUtil.replace(portletId, namespace, newNamespace);
	}

	private static Optional<PortletPreferences>
		_getNewPortletPreferencesOptional(
			String namespace, String newNamespace, long plid,
			String portletId) {

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);

		if (portletPreferences == null) {
			return Optional.empty();
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		if ((portlet == null) || portlet.isUndeployedPortlet()) {
			return Optional.empty();
		}

		String newPortletId = _getNewPortletId(
			namespace, newNamespace, portletId);

		PortletPreferences existingPortletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences.getOwnerId(),
				portletPreferences.getOwnerType(), plid, newPortletId);

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferenceValueLocalServiceUtil.getPreferences(
				portletPreferences);

		if (existingPortletPreferences == null) {
			return Optional.of(
				PortletPreferencesLocalServiceUtil.addPortletPreferences(
					portletPreferences.getCompanyId(),
					portletPreferences.getOwnerId(),
					portletPreferences.getOwnerType(), plid, newPortletId,
					portlet,
					PortletPreferencesFactoryUtil.toXML(jxPortletPreferences)));
		}

		return Optional.of(
			PortletPreferencesLocalServiceUtil.updatePreferences(
				existingPortletPreferences.getOwnerId(),
				existingPortletPreferences.getOwnerType(),
				existingPortletPreferences.getPlid(),
				existingPortletPreferences.getPortletId(),
				jxPortletPreferences));
	}

	private static Optional<SegmentsExperiment> _getSegmentsExperimentOptional(
			ThemeDisplay themeDisplay, long segmentsExperienceId)
		throws Exception {

		Layout draftLayout = themeDisplay.getLayout();

		Layout layout = LayoutLocalServiceUtil.getLayout(
			draftLayout.getClassPK());

		return Optional.ofNullable(
			SegmentsExperimentLocalServiceUtil.fetchSegmentsExperiment(
				segmentsExperienceId, PortalUtil.getClassNameId(Layout.class),
				layout.getPlid(),
				SegmentsExperimentConstants.Status.getExclusiveStatusValues()));
	}

	private static String _getSegmentsExperimentURL(
		ThemeDisplay themeDisplay, String layoutFullURL,
		long segmentsExperienceId) {

		HttpUtil.addParameter(
			layoutFullURL, "p_l_back_url", themeDisplay.getURLCurrent());

		return HttpUtil.addParameter(
			layoutFullURL, "segmentsExperienceId", segmentsExperienceId);
	}

	private static JSONObject _updateLayoutDataJSONObject(
			long plid, CommentManager commentManager,
			LayoutStructure layoutStructure, long groupId,
			PortletRegistry portletRegistry, long sourceSegmentsExperienceId,
			Function<String, ServiceContext> serviceContextFunction,
			long targetSegmentsExperienceId, long userId)
		throws PortalException {

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.
				getFragmentEntryLinksBySegmentsExperienceId(
					groupId, sourceSegmentsExperienceId, plid);

		Stream<FragmentEntryLink> stream = fragmentEntryLinks.stream();

		Map<Long, FragmentEntryLink> fragmentEntryLinkMap = stream.collect(
			Collectors.toMap(
				FragmentEntryLink::getFragmentEntryLinkId,
				fragmentEntryLink -> fragmentEntryLink));

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			if (!(layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem)) {

				continue;
			}

			FragmentStyledLayoutStructureItem
				fragmentStyledLayoutStructureItem =
					(FragmentStyledLayoutStructureItem)layoutStructureItem;

			FragmentEntryLink fragmentEntryLink = fragmentEntryLinkMap.get(
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

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
			newFragmentEntryLink.setOriginalFragmentEntryLinkId(
				fragmentEntryLink.getFragmentEntryLinkId());
			newFragmentEntryLink.setSegmentsExperienceId(
				targetSegmentsExperienceId);

			String newNamespace = StringUtil.randomId();

			newFragmentEntryLink.setEditableValues(
				_getNewEditableValues(
					fragmentEntryLink.getEditableValues(),
					fragmentEntryLink.getNamespace(), newNamespace, plid));

			newFragmentEntryLink.setNamespace(newNamespace);

			newFragmentEntryLink.setLastPropagationDate(new Date());

			newFragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
					newFragmentEntryLink);

			fragmentStyledLayoutStructureItem.setFragmentEntryLinkId(
				newFragmentEntryLink.getFragmentEntryLinkId());

			commentManager.copyDiscussion(
				userId, groupId, FragmentEntryLink.class.getName(),
				fragmentEntryLink.getFragmentEntryLinkId(),
				newFragmentEntryLink.getFragmentEntryLinkId(),
				serviceContextFunction);

			_copyPortletPreferences(
				fragmentEntryLink, newFragmentEntryLink, plid, portletRegistry);
		}

		return layoutStructure.toJSONObject();
	}

}