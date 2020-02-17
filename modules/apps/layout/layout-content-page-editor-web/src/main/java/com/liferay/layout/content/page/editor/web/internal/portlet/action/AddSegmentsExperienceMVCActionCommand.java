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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.segments.SegmentsExperienceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentRelService;
import com.liferay.segments.service.SegmentsExperimentService;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/add_segments_experience"
	},
	service = MVCActionCommand.class
)
public class AddSegmentsExperienceMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	protected JSONObject addSegmentsExperience(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		SegmentsExperiment segmentsExperiment = _getSegmentsExperiment(
			actionRequest);

		SegmentsExperience segmentsExperience = _addSegmentsExperience(
			actionRequest, _portal.getClassNameId(Layout.class),
			themeDisplay.getPlid(), segmentsExperiment);

		_populateSegmentsExperienceJSONObject(jsonObject, segmentsExperience);

		long baseSegmentsExperienceId = _getBaseSegmentsExperienceId(
			segmentsExperiment);

		String layoutData = SegmentsExperienceUtil.copyLayoutData(
			_portal.getClassNameId(Layout.class), themeDisplay.getPlid(),
			themeDisplay.getScopeGroupId(),
			_layoutPageTemplateStructureLocalService, baseSegmentsExperienceId,
			segmentsExperience.getSegmentsExperienceId());

		_populateLayoutDataJSONObject(jsonObject, layoutData);

		Map<Long, String> fragmentEntryLinksEditableValuesMap =
			SegmentsExperienceUtil.copyFragmentEntryLinksEditableValues(
				_portal.getClassNameId(Layout.class), themeDisplay.getPlid(),
				_fragmentEntryLinkLocalService, themeDisplay.getScopeGroupId(),
				baseSegmentsExperienceId,
				segmentsExperience.getSegmentsExperienceId());

		_populateFragmentEntryLinksJSONObject(
			jsonObject, fragmentEntryLinksEditableValuesMap);

		if (segmentsExperiment != null) {
			SegmentsExperimentRel segmentsExperimentRel =
				_addSegmentsExperimentRel(
					actionRequest, segmentsExperiment, segmentsExperience);

			_populateSegmentsSegmentsExperimentRelJSONObject(
				jsonObject, segmentsExperimentRel, themeDisplay.getLocale());

			_initializeDraftLayout(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				segmentsExperience, baseSegmentsExperienceId);
		}

		SegmentsExperienceUtil.copyPortletPreferences(
			themeDisplay.getPlid(), _portletLocalService,
			_portletPreferencesLocalService, baseSegmentsExperienceId,
			segmentsExperience.getSegmentsExperienceId());

		return jsonObject;
	}

	@Override
	protected JSONObject executeTransactionalMVCActionCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		return addSegmentsExperience(actionRequest);
	}

	private SegmentsExperience _addSegmentsExperience(
			ActionRequest actionRequest, long classNameId, long classPK,
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		boolean active = ParamUtil.getBoolean(actionRequest, "active", true);

		long segmentsEntryId = ParamUtil.getLong(
			actionRequest, "segmentsEntryId");

		if (segmentsExperiment != null) {
			active = false;

			segmentsEntryId = SegmentsEntryConstants.ID_DEFAULT;

			if (segmentsExperiment.getSegmentsExperienceId() !=
					SegmentsExperienceConstants.ID_DEFAULT) {

				SegmentsExperience segmentsExperience =
					_segmentsExperienceService.getSegmentsExperience(
						segmentsExperiment.getSegmentsExperienceId());

				segmentsEntryId = segmentsExperience.getSegmentsEntryId();
			}
		}

		return _segmentsExperienceService.addSegmentsExperience(
			segmentsEntryId, classNameId, classPK,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(),
				ParamUtil.getString(actionRequest, "name")
			).build(),
			active, ServiceContextFactory.getInstance(actionRequest));
	}

	private SegmentsExperimentRel _addSegmentsExperimentRel(
			ActionRequest actionRequest, SegmentsExperiment segmentsExperiment,
			SegmentsExperience segmentsExperience)
		throws PortalException {

		return _segmentsExperimentRelService.addSegmentsExperimentRel(
			segmentsExperiment.getSegmentsExperimentId(),
			segmentsExperience.getSegmentsExperienceId(),
			ServiceContextFactory.getInstance(actionRequest));
	}

	private long _getBaseSegmentsExperienceId(
		SegmentsExperiment segmentsExperiment) {

		if (segmentsExperiment == null) {
			return SegmentsExperienceConstants.ID_DEFAULT;
		}

		return segmentsExperiment.getSegmentsExperienceId();
	}

	private SegmentsExperiment _getSegmentsExperiment(
			ActionRequest actionRequest)
		throws PortalException {

		SegmentsExperiment segmentsExperiment = null;

		long segmentsExperimentId = ParamUtil.getLong(
			actionRequest, "segmentsExperimentId");

		if (segmentsExperimentId != GetterUtil.DEFAULT_LONG) {
			segmentsExperiment =
				_segmentsExperimentService.getSegmentsExperiment(
					segmentsExperimentId);
		}

		return segmentsExperiment;
	}

	private void _initializeDraftLayout(
			long groupId, long classPK, SegmentsExperience segmentsExperience,
			long baseSegmentsExperienceId)
		throws PortalException {

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class.getName()), classPK);

		if (draftLayout != null) {
			SegmentsExperienceUtil.copySegmentsExperienceData(
				draftLayout.getClassNameId(), draftLayout.getPlid(),
				_fragmentEntryLinkLocalService, groupId,
				_layoutPageTemplateStructureLocalService, _portletLocalService,
				_portletPreferencesLocalService, baseSegmentsExperienceId,
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	private void _populateFragmentEntryLinksJSONObject(
			JSONObject jsonObject,
			Map<Long, String> fragmentEntryLinksEditableValuesMap)
		throws JSONException {

		JSONObject fragmentEntryLinksJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (Map.Entry<Long, String> entry :
				fragmentEntryLinksEditableValuesMap.entrySet()) {

			fragmentEntryLinksJSONObject.put(
				String.valueOf(entry.getKey()),
				JSONFactoryUtil.createJSONObject(entry.getValue()));
		}

		jsonObject.put("fragmentEntryLinks", fragmentEntryLinksJSONObject);
	}

	private void _populateLayoutDataJSONObject(
			JSONObject jsonObject, String layoutData)
		throws JSONException {

		jsonObject.put(
			"layoutData", JSONFactoryUtil.createJSONObject(layoutData));
	}

	private void _populateSegmentsExperienceJSONObject(
		JSONObject jsonObject, SegmentsExperience segmentsExperience) {

		jsonObject.put(
			"segmentsExperience",
			JSONUtil.put(
				"active", segmentsExperience.isActive()
			).put(
				"name", segmentsExperience.getNameCurrentValue()
			).put(
				"priority", segmentsExperience.getPriority()
			).put(
				"segmentsEntryId", segmentsExperience.getSegmentsEntryId()
			).put(
				"segmentsExperienceId",
				segmentsExperience.getSegmentsExperienceId()
			));
	}

	private void _populateSegmentsSegmentsExperimentRelJSONObject(
			JSONObject jsonObject, SegmentsExperimentRel segmentsExperimentRel,
			Locale locale)
		throws PortalException {

		jsonObject.put(
			"segmentsExperimentRel",
			JSONUtil.put(
				"name", segmentsExperimentRel.getName(locale)
			).put(
				"segmentsExperienceId",
				segmentsExperimentRel.getSegmentsExperienceId()
			).put(
				"segmentsExperimentId",
				segmentsExperimentRel.getSegmentsExperimentId()
			).put(
				"segmentsExperimentRelId",
				segmentsExperimentRel.getSegmentsExperimentRelId()
			).put(
				"split", segmentsExperimentRel.getSplit()
			));
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

	@Reference
	private SegmentsExperimentRelService _segmentsExperimentRelService;

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

}