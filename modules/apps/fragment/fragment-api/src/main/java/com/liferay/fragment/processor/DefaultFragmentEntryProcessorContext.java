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
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;

import java.util.Locale;
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

	@Override
	public Optional<InfoItemReference> getContextInfoItemReferenceOptional() {
		return Optional.ofNullable(_infoItemReference);
	}

	@Override
	public String getFragmentElementId() {
		return _fragmentElementId;
	}

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	@Override
	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	@Override
	public Optional<InfoForm> getInfoFormOptional() {
		return Optional.ofNullable(_infoForm);
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
	public String getPreviewVersion() {
		return _previewVersion;
	}

	@Override
	public long[] getSegmentsEntryIds() {
		return _segmentsEntryIds;
	}

	public void setContextInfoItemReference(
		InfoItemReference infoItemReference) {

		_infoItemReference = infoItemReference;
	}

	public void setFragmentElementId(String fragmentElementId) {
		_fragmentElementId = fragmentElementId;
	}

	public void setInfoForm(InfoForm infoForm) {
		_infoForm = infoForm;
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

	public void setPreviewVersion(String previewVersion) {
		_previewVersion = previewVersion;
	}

	public void setSegmentsEntryIds(long[] segmentsEntryIds) {
		_segmentsEntryIds = segmentsEntryIds;
	}

	private String _fragmentElementId;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private InfoForm _infoForm;
	private InfoItemReference _infoItemReference;
	private final Locale _locale;
	private final String _mode;
	private long _previewClassNameId;
	private long _previewClassPK;
	private int _previewType = AssetRendererFactory.TYPE_LATEST_APPROVED;
	private String _previewVersion = InfoItemIdentifier.VERSION_LATEST_APPROVED;
	private long[] _segmentsEntryIds = new long[0];

}