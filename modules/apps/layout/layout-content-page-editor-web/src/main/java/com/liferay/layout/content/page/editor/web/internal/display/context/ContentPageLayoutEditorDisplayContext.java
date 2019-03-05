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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.template.soy.util.SoyContext;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryServiceUtil;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ContentPageLayoutEditorDisplayContext
	extends ContentPageEditorDisplayContext {

	public ContentPageLayoutEditorDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse,
		String className, long classPK) {

		super(request, renderResponse, className, classPK);
	}

	@Override
	public SoyContext getEditorContext() throws Exception {
		if (_editorSoyContext != null) {
			return _editorSoyContext;
		}

		SoyContext soyContext = super.getEditorContext();

		soyContext.put(
			"availableExperiences",
			_getSoyContextAvailableSegmentsExperiences());
		soyContext.put(
			"availableSegments", _getSoyContextAvailableSegmentsEntries());
		soyContext.put("sidebarPanels", getSidebarPanelSoyContexts(false));

		_editorSoyContext = soyContext;

		return _editorSoyContext;
	}

	@Override
	public SoyContext getFragmentsEditorToolbarContext()
		throws PortalException {

		if (_fragmentsEditorToolbarSoyContext != null) {
			return _fragmentsEditorToolbarSoyContext;
		}

		SoyContext soyContext = super.getFragmentsEditorToolbarContext();

		soyContext.put(
			"availableSegments", _getSoyContextAvailableSegmentsEntries());
		soyContext.put(
			"availableExperiences",
			_getSoyContextAvailableSegmentsExperiences());

		_fragmentsEditorToolbarSoyContext = soyContext;

		return _fragmentsEditorToolbarSoyContext;
	}

	private SoyContext _getSoyContextAvailableSegmentsEntries() {
		SoyContext availableSegmentsEntriesSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		List<SegmentsEntry> segmentsEntries =
			SegmentsEntryServiceUtil.getSegmentsEntries(getGroupId(), true);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			SoyContext segmentsSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			segmentsSoyContext.put(
				"segmentId",
				String.valueOf(segmentsEntry.getSegmentsEntryId()));
			segmentsSoyContext.put(
				"segmentLabel",
				segmentsEntry.getName(themeDisplay.getLocale()));

			availableSegmentsEntriesSoyContext.put(
				String.valueOf(segmentsEntry.getSegmentsEntryId()),
				segmentsSoyContext);
		}

		return availableSegmentsEntriesSoyContext;
	}

	private SoyContext _getSoyContextAvailableSegmentsExperiences()
		throws PortalException {

		SoyContext availableSegmentsEntriesSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		List<SegmentsExperience> segmentsExperiences =
			SegmentsExperienceLocalServiceUtil.getSegmentsExperiences(
				getGroupId(), classNameId, classPK, true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			SoyContext experienceSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			experienceSoyContext.put(
				"experienceId",
				String.valueOf(segmentsExperience.getSegmentsExperienceId()));
			experienceSoyContext.put(
				"experienceLabel",
				segmentsExperience.getName(themeDisplay.getLocale()));
			experienceSoyContext.put(
				"segmentId",
				String.valueOf(segmentsExperience.getSegmentsEntryId()));

			availableSegmentsEntriesSoyContext.put(
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				experienceSoyContext);
		}

		return availableSegmentsEntriesSoyContext;
	}

	private SoyContext _editorSoyContext;
	private SoyContext _fragmentsEditorToolbarSoyContext;

}