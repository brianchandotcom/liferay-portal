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

import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsConstants;
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

	public String getMode() {
		return _mode;
	}

	public long getPlid() {
		return _plid;
	}

	public long getPreviewClassPK() {
		return _previewClassPK;
	}

	public int getPreviewType() {
		return _previewType;
	}

	public void setFieldValues(Map<String, Object> fieldValues) {
		_fieldValues = fieldValues;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setMode(String mode) {
		_mode = mode;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public void setPreviewClassPK(long previewClassPK) {
		_previewClassPK = previewClassPK;
	}

	public void setPreviewType(int previewType) {
		_previewType = previewType;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_fieldValues = null;
		_groupId = 0;
		_mode = null;
		_plid = 0;
		_previewClassPK = 0;
		_previewType = 0;
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
			"liferay-layout:render-fragment-layout:mode", _mode);
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:previewClassPK",
			_previewClassPK);
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:previewType", _previewType);
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:segmentsExperienceIds",
			_getSegmentsExperienceIds());
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:structureJSONArray",
			_getStructureJSONArray());
	}

	private long[] _getSegmentsExperienceIds() {
		return GetterUtil.getLongValues(
			request.getAttribute(SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
			new long[] {SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT});
	}

	private JSONArray _getStructureJSONArray() {
		try {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				LayoutPageTemplateStructureLocalServiceUtil.
					fetchLayoutPageTemplateStructure(
						_groupId,
						PortalUtil.getClassNameId(Layout.class.getName()),
						_plid, true);

			long[] segmentsExperienceIds = GetterUtil.getLongValues(
				request.getAttribute(SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
				new long[] {SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT});

			String data = layoutPageTemplateStructure.getData(
				segmentsExperienceIds);

			if (Validator.isNull(data)) {
				return null;
			}

			JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

			return dataJSONObject.getJSONArray("structure");
		}
		catch (Exception e) {
		}

		return null;
	}

	private static final String _PAGE = "/render_fragment_layout/page.jsp";

	private Map<String, Object> _fieldValues;
	private long _groupId;
	private String _mode;
	private long _plid;
	private long _previewClassPK;
	private int _previewType;

}