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

package com.liferay.fragment.processor;

import com.liferay.asset.kernel.model.AssetRendererFactory;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Savinov
 */
public class DefaultFragmentEntryProcessorContext
	implements FragmentEntryProcessorContext {

	public DefaultFragmentEntryProcessorContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String mode, Locale locale) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_mode = mode;
		_locale = locale;
	}

	public Optional<Map<String, Object>> getFieldValuesOptional() {
		return Optional.ofNullable(_fieldValues);
	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public String getMode() {
		return _mode;
	}

	@Override
	public long getPreviewClassNameId() {
		return _previewClassNameId;
	}

	@Override
	public long getPreviewClassPK() {
		return _previewClassPK;
	}

	@Override
	public int getPreviewType() {
		return _previewType;
	}

	@Override
	public long[] getSegmentsExperienceIds() {
		return _segmentsExperienceIds;
	}

	public void setFieldValues(Map<String, Object> fieldValues) {
		_fieldValues = fieldValues;
	}

	public void setPreviewClassNameId(long previewClassNameId) {
		_previewClassNameId = previewClassNameId;
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

	private Map<String, Object> _fieldValues;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final Locale _locale;
	private final String _mode;
	private long _previewClassNameId;
	private long _previewClassPK;
	private int _previewType = AssetRendererFactory.TYPE_LATEST_APPROVED;
	private long[] _segmentsExperienceIds = new long[0];

}