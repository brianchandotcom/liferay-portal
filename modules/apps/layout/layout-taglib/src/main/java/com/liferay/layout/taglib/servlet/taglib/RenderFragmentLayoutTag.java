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

import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.json.JSONArray;
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

	public String getMode() {
		return _mode;
	}

	public long getPreviewClassPK() {
		return _previewClassPK;
	}

	public int getPreviewType() {
		return _previewType;
	}

	public long[] getSegmentsExperienceIds() {
		return _segmentsExperienceIds;
	}

	public JSONArray getStructureJSONArray() {
		return _structureJSONArray;
	}

	public void setFieldValues(Map<String, Object> fieldValues) {
		_fieldValues = fieldValues;
	}

	public void setMode(String mode) {
		_mode = mode;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPreviewClassPK(long previewClassPK) {
		_previewClassPK = previewClassPK;
	}

	public void setPreviewType(int previewType) {
		_previewType = previewType;
	}

	public void setSegmentsExperienceIds(long[] segmentsExperienceIds) {
		_segmentsExperienceIds = segmentsExperienceIds;
	}

	public void setStructureJSONArray(JSONArray structureJSONArray) {
		_structureJSONArray = structureJSONArray;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_fieldValues = null;
		_mode = null;
		_previewClassPK = 0;
		_previewType = 0;
		_segmentsExperienceIds = null;
		_structureJSONArray = null;
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
			_segmentsExperienceIds);
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:structureJSONArray",
			_structureJSONArray);
	}

	private static final String _PAGE = "/render_fragment_layout/page.jsp";

	private Map<String, Object> _fieldValues;
	private String _mode;
	private long _previewClassPK;
	private int _previewType;
	private long[] _segmentsExperienceIds;
	private JSONArray _structureJSONArray;

}