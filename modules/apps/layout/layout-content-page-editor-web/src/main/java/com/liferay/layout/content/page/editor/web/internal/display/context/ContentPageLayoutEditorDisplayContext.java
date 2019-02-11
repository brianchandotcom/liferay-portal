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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.template.soy.util.SoyContext;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryServiceUtil;

import java.util.List;
import java.util.Objects;

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

		List<SegmentsEntry> segmentsEntries = _getSegmentsEntries();

		soyContext.put(
			"availableSegments",
			_getSoyContextAvailableSegmentsEntries(segmentsEntries));
		soyContext.put(
			"defaultSegmentId", _getDefaultSegmentId(segmentsEntries));

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

		List<SegmentsEntry> segmentsEntries = _getSegmentsEntries();

		soyContext.put(
			"availableSegments",
			_getSoyContextAvailableSegmentsEntries(segmentsEntries));
		soyContext.put(
			"defaultSegmentId", _getDefaultSegmentId(segmentsEntries));

		_fragmentsEditorToolbarSoyContext = soyContext;

		return _fragmentsEditorToolbarSoyContext;
	}

	private String _getDefaultSegmentId(List<SegmentsEntry> segmentsEntries) {
		if (ListUtil.isEmpty(segmentsEntries)) {
			return StringPool.BLANK;
		}

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			if (Objects.equals(
					SegmentsConstants.KEY_DEFAULT, segmentsEntry.getKey())) {

				return _EDITABLE_VALUES_SEGMENTS_PREFIX +
					segmentsEntry.getSegmentsEntryId();
			}
		}

		return StringPool.BLANK;
	}

	private List<SegmentsEntry> _getSegmentsEntries() {
		return SegmentsEntryServiceUtil.getSegmentsEntries(
			getGroupId(), true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	private SoyContext _getSoyContextAvailableSegmentsEntries(
		List<SegmentsEntry> segmentsEntries) {

		SoyContext availableSegmentsEntriesSoyContext =
			SoyContextFactoryUtil.createSoyContext();

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			SoyContext segmentsSoyContext =
				SoyContextFactoryUtil.createSoyContext();

			segmentsSoyContext.put(
				"segmentId",
				_EDITABLE_VALUES_SEGMENTS_PREFIX +
					segmentsEntry.getSegmentsEntryId());
			segmentsSoyContext.put("segmentKey", segmentsEntry.getKey());
			segmentsSoyContext.put(
				"segmentLabel",
				segmentsEntry.getName(themeDisplay.getLocale()));

			availableSegmentsEntriesSoyContext.put(
				segmentsEntry.getKey(), segmentsSoyContext);
		}

		return availableSegmentsEntriesSoyContext;
	}

	private static final String _EDITABLE_VALUES_SEGMENTS_PREFIX =
		"segment-id-";

	private SoyContext _editorSoyContext;
	private SoyContext _fragmentsEditorToolbarSoyContext;

}