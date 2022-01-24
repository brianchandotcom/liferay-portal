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

package com.liferay.fragment.renderer;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Jorge Ferrer
 */
public class DefaultFragmentRendererContext implements FragmentRendererContext {

	public DefaultFragmentRendererContext(FragmentEntryLink fragmentEntryLink) {
		_fragmentEntryLink = fragmentEntryLink;
	}

	@Override
	public Optional<Object> getDisplayObjectOptional() {
		return Optional.ofNullable(_displayObject);
	}

	@Override
	public String getFragmentElementId() {
		StringBundler sb = new StringBundler(8);

		sb.append("fragment-");
		sb.append(_fragmentEntryLink.getFragmentEntryId());
		sb.append("-");
		sb.append(_fragmentEntryLink.getNamespace());

		if (!ListUtil.isEmpty(_collectionStyledLayoutStructureItemIds)) {
			sb.append("-");
			sb.append(
				ListUtil.toString(
					_collectionStyledLayoutStructureItemIds, StringPool.BLANK,
					StringPool.DASH));
		}

		if (_collectionElementIndex > -1) {
			sb.append("-");
			sb.append(_collectionElementIndex);
		}

		return sb.toString();
	}

	@Override
	public FragmentEntryLink getFragmentEntryLink() {
		return _fragmentEntryLink;
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
		return _segmentsSegmentsEntryIds;
	}

	@Override
	public boolean isUseCachedContent() {
		return _useCachedContent;
	}

	public void setCollectionElementIndex(int collectionElementIndex) {
		_collectionElementIndex = collectionElementIndex;
	}

	public void setCollectionStyledLayoutStructureItemIds(
		List<String> collectionStyledLayoutStructureItemIds) {

		_collectionStyledLayoutStructureItemIds =
			collectionStyledLayoutStructureItemIds;
	}

	public void setDisplayObject(Object object) {
		_displayObject = object;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setMode(String mode) {
		_mode = mode;
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

	public void setSegmentsEntryIds(long[] segmentsSegmentsEntryIds) {
		_segmentsSegmentsEntryIds = segmentsSegmentsEntryIds;
	}

	public void setUseCachedContent(boolean useCachedContent) {
		_useCachedContent = useCachedContent;
	}

	private int _collectionElementIndex = -1;
	private List<String> _collectionStyledLayoutStructureItemIds;
	private Object _displayObject;
	private final FragmentEntryLink _fragmentEntryLink;
	private Locale _locale = LocaleUtil.getMostRelevantLocale();
	private String _mode = FragmentEntryLinkConstants.VIEW;
	private long _previewClassNameId;
	private long _previewClassPK;
	private int _previewType;
	private String _previewVersion;
	private long[] _segmentsSegmentsEntryIds = new long[0];
	private boolean _useCachedContent = true;

}