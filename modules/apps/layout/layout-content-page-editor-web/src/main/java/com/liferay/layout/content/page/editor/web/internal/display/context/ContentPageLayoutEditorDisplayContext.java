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

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.template.soy.util.SoyContext;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryServiceUtil;
import com.liferay.segments.service.SegmentsExperienceServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ContentPageLayoutEditorDisplayContext
	extends ContentPageEditorDisplayContext {

	public ContentPageLayoutEditorDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		String className, long classPK,
		FragmentRendererController fragmentRendererController) {

		super(
			httpServletRequest, renderResponse, className, classPK,
			fragmentRendererController);
	}

	@Override
	public SoyContext getEditorSoyContext() throws Exception {
		if (_editorSoyContext != null) {
			return _editorSoyContext;
		}

		SoyContext soyContext = super.getEditorSoyContext();

		soyContext.put("sidebarPanels", getSidebarPanelSoyContexts(false));

		if (_isShowSegmentsExperiences()) {
			_populateSegmentsExperiencesSoyContext(soyContext);
		}

		_editorSoyContext = soyContext;

		return _editorSoyContext;
	}

	@Override
	public SoyContext getFragmentsEditorToolbarSoyContext()
		throws PortalException {

		if (_fragmentsEditorToolbarSoyContext != null) {
			return _fragmentsEditorToolbarSoyContext;
		}

		SoyContext soyContext = super.getFragmentsEditorToolbarSoyContext();

		if (_isShowSegmentsExperiences()) {
			_populateSegmentsExperiencesSoyContext(soyContext);
		}

		_fragmentsEditorToolbarSoyContext = soyContext;

		return _fragmentsEditorToolbarSoyContext;
	}

	private SoyContext _getAvailableSegmentsEntriesSoyContext() {
		SoyContext availableSegmentsEntriesSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		List<SegmentsEntry> segmentsEntries =
			SegmentsEntryServiceUtil.getSegmentsEntries(getGroupId(), true);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			SoyContext segmentsEntrySoyContext =
				SoyContextFactoryUtil.createSoyContext();

			segmentsEntrySoyContext.put(
				"name", segmentsEntry.getName(themeDisplay.getLocale())
			).put(
				"segmentsEntryId",
				String.valueOf(segmentsEntry.getSegmentsEntryId())
			);

			availableSegmentsEntriesSoyContext.put(
				String.valueOf(segmentsEntry.getSegmentsEntryId()),
				segmentsEntrySoyContext);
		}

		SoyContext defaultSegmentsEntrySoyContext =
			SoyContextFactoryUtil.createSoyContext();

		defaultSegmentsEntrySoyContext.put(
			"name",
			SegmentsConstants.getDefaultSegmentsEntryName(
				themeDisplay.getLocale())
		).put(
			"segmentsEntryId", SegmentsConstants.SEGMENTS_ENTRY_ID_DEFAULT
		);

		availableSegmentsEntriesSoyContext.put(
			String.valueOf(SegmentsConstants.SEGMENTS_ENTRY_ID_DEFAULT),
			defaultSegmentsEntrySoyContext);

		return availableSegmentsEntriesSoyContext;
	}

	private SoyContext _getAvailableSegmentsExperiencesSoyContext() {
		SoyContext availableSegmentsExperiencesSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		List<SegmentsExperience> segmentsExperiences =
			SegmentsExperienceServiceUtil.getSegmentsExperiences(
				getGroupId(), classNameId, classPK, true);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			SoyContext segmentsExperienceSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			segmentsExperienceSoyContext.put(
				"name", segmentsExperience.getName(themeDisplay.getLocale())
			).put(
				"priority", segmentsExperience.getPriority()
			).put(
				"segmentsEntryId",
				String.valueOf(segmentsExperience.getSegmentsEntryId())
			).put(
				"segmentsExperienceId",
				String.valueOf(segmentsExperience.getSegmentsExperienceId())
			);

			availableSegmentsExperiencesSoyContext.put(
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				segmentsExperienceSoyContext);
		}

		SoyContext defaultSegmentsExperienceSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		defaultSegmentsExperienceSoyContext.put(
			"name",
			SegmentsConstants.getDefaultSegmentsExperienceName(
				themeDisplay.getLocale())
		).put(
			"priority", SegmentsConstants.SEGMENTS_EXPERIENCE_PRIORITY_DEFAULT
		).put(
			"segmentsEntryId",
			String.valueOf(SegmentsConstants.SEGMENTS_ENTRY_ID_DEFAULT)
		).put(
			"segmentsExperienceId",
			String.valueOf(SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT)
		);

		availableSegmentsExperiencesSoyContext.put(
			String.valueOf(SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT),
			defaultSegmentsExperienceSoyContext);

		return availableSegmentsExperiencesSoyContext;
	}

	private String _getEditSegmentsEntryURL() throws PortalException {
		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			request, SegmentsEntry.class.getName(),
			PortletProvider.Action.EDIT);

		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		return portletURL.toString();
	}

	private List<SoyContext> _getLayoutDataListSoyContext()
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					themeDisplay.getScopeGroupId(), classNameId, classPK, true);

		if (layoutPageTemplateStructure == null) {
			return Collections.emptyList();
		}

		List<SoyContext> soyContexts = new ArrayList<>();

		List<LayoutPageTemplateStructureRel> layoutPageTemplateStructureRels =
			LayoutPageTemplateStructureRelLocalServiceUtil.
				getLayoutPageTemplateStructureRels(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId());

		for (LayoutPageTemplateStructureRel layoutPageTemplateStructureRel :
				layoutPageTemplateStructureRels) {

			SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

			soyContext.put(
				"layoutData",
				JSONFactoryUtil.createJSONObject(
					layoutPageTemplateStructureRel.getData())
			).put(
				"segmentsExperienceId",
				layoutPageTemplateStructureRel.getSegmentsExperienceId()
			);

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private boolean _isShowSegmentsExperiences() throws PortalException {
		if (_showSegmentsExperiences != null) {
			return _showSegmentsExperiences;
		}

		Group group = GroupLocalServiceUtil.getGroup(getGroupId());

		if (!group.isLayoutSetPrototype() && !group.isUser()) {
			_showSegmentsExperiences = true;
		}
		else {
			_showSegmentsExperiences = false;
		}

		return _showSegmentsExperiences;
	}

	private void _populateSegmentsExperiencesSoyContext(SoyContext soyContext)
		throws PortalException {

		soyContext.put(
			"availableSegmentsEntries", _getAvailableSegmentsEntriesSoyContext()
		).put(
			"availableSegmentsExperiences",
			_getAvailableSegmentsExperiencesSoyContext()
		).put(
			"defaultSegmentsEntryId",
			SegmentsConstants.SEGMENTS_ENTRY_ID_DEFAULT
		).put(
			"defaultSegmentsExperienceId",
			String.valueOf(SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT)
		).put(
			"deleteSegmentsExperienceURL",
			getFragmentEntryActionURL(
				"/content_layout/delete_segments_experience")
		).put(
			"editSegmentsEntryURL", _getEditSegmentsEntryURL()
		).put(
			"layoutDataList", _getLayoutDataListSoyContext()
		);
	}

	private SoyContext _editorSoyContext;
	private SoyContext _fragmentsEditorToolbarSoyContext;
	private Boolean _showSegmentsExperiences;

}