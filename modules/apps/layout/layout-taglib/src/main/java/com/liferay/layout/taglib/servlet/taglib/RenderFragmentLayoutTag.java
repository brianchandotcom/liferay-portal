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

package com.liferay.layout.taglib.servlet.taglib;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Víctor Galán
 */
public class RenderFragmentLayoutTag extends IncludeTag {

	public Map<String, Object> getFieldValues() {
		return _fieldValues;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getMainItemId() {
		return _mainItemId;
	}

	public String getMode() {
		return _mode;
	}

	public long getPlid() {
		return _plid;
	}

	public boolean getShowPreview() {
		return _showPreview;
	}

	public void setFieldValues(Map<String, Object> fieldValues) {
		_fieldValues = fieldValues;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setMainItemId(String mainItemId) {
		_mainItemId = mainItemId;
	}

	public void setMode(String mode) {
		_mode = mode;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public void setShowPreview(boolean showPreview) {
		_showPreview = showPreview;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_fieldValues = null;
		_groupId = 0;
		_layoutStructure = null;
		_mainItemId = null;
		_mode = FragmentEntryLinkConstants.VIEW;
		_plid = 0;
		_showPreview = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:fieldValues", _fieldValues);
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:layoutStructure",
			_getLayoutStructure(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:mainItemId", _mainItemId);
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:mode", _mode);
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:showPreview", _showPreview);
	}

	private Layout _getLayout(HttpServletRequest httpServletRequest) {
		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			_getPlid(httpServletRequest));

		if (layout instanceof VirtualLayout) {
			VirtualLayout virtualLayout = (VirtualLayout)layout;

			layout = virtualLayout.getSourceLayout();
		}

		return layout;
	}

	private LayoutStructure _getLayoutStructure(
		HttpServletRequest httpServletRequest) {

		if (_layoutStructure != null) {
			return _layoutStructure;
		}

		try {
			Layout layout = _getLayout(httpServletRequest);

			LayoutPageTemplateStructure layoutPageTemplateStructure =
				LayoutPageTemplateStructureLocalServiceUtil.
					fetchLayoutPageTemplateStructure(
						layout.getGroupId(), layout.getPlid(), true);

			String data = layoutPageTemplateStructure.getData(
				_getSegmentsExperienceIds());

			String masterLayoutData = _getMasterLayoutData(httpServletRequest);

			if (Validator.isNull(masterLayoutData)) {
				_layoutStructure = LayoutStructure.of(data);

				return _layoutStructure;
			}

			_layoutStructure = _mergeLayoutStructure(data, masterLayoutData);

			return _layoutStructure;
		}
		catch (Exception exception) {
			_log.error("Unable to get layout structure", exception);

			return null;
		}
	}

	private String _getMasterLayoutData(HttpServletRequest httpServletRequest) {
		Layout layout = _getLayout(httpServletRequest);

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(
					layout.getMasterLayoutPlid());

		if (masterLayoutPageTemplateEntry == null) {
			return null;
		}

		LayoutPageTemplateStructure masterLayoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					masterLayoutPageTemplateEntry.getGroupId(),
					masterLayoutPageTemplateEntry.getPlid());

		if (masterLayoutPageTemplateStructure == null) {
			return null;
		}

		return masterLayoutPageTemplateStructure.getData(
			SegmentsExperienceConstants.ID_DEFAULT);
	}

	private long _getPlid(HttpServletRequest httpServletRequest) {
		long plid = getPlid();

		if (plid > 0) {
			return plid;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPlid();
	}

	private long[] _getSegmentsExperienceIds() {
		HttpServletRequest httpServletRequest = getRequest();

		return GetterUtil.getLongValues(
			httpServletRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
			new long[] {SegmentsExperienceConstants.ID_DEFAULT});
	}

	private LayoutStructure _mergeLayoutStructure(
		String data, String masterLayoutData) {

		LayoutStructure masterLayoutStructure = LayoutStructure.of(
			masterLayoutData);

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			masterLayoutStructure.addLayoutStructureItem(layoutStructureItem);
		}

		DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
			(DropZoneLayoutStructureItem)
				masterLayoutStructure.getDropZoneLayoutStructureItem();

		dropZoneLayoutStructureItem.addChildrenItem(
			layoutStructure.getMainItemId());

		LayoutStructureItem rootStructureItem =
			masterLayoutStructure.getLayoutStructureItem(
				layoutStructure.getMainItemId());

		rootStructureItem.setParentItemId(
			dropZoneLayoutStructureItem.getItemId());

		return masterLayoutStructure;
	}

	private static final String _PAGE = "/render_fragment_layout/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		RenderFragmentLayoutTag.class);

	private Map<String, Object> _fieldValues;
	private long _groupId;
	private LayoutStructure _layoutStructure;
	private String _mainItemId;
	private String _mode = FragmentEntryLinkConstants.VIEW;
	private long _plid;
	private boolean _showPreview;

}