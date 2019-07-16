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

package com.liferay.asset.taglib.servlet.taglib;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class AssetMetadataTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		if (_hasMetadata) {
			return super.doEndTag();
		}

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		if (ArrayUtil.isEmpty(_metadataFields)) {
			return SKIP_BODY;
		}

		_hasMetadata = true;

		return super.doStartTag();
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String[] getMetadataFields() {
		return _metadataFields;
	}

	public boolean isFilterByMetadata() {
		return _filterByMetadata;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setFilterByMetadata(boolean filterByMetadata) {
		_filterByMetadata = filterByMetadata;
	}

	public void setMetadataFields(String[] metadataFields) {
		_metadataFields = metadataFields;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = StringPool.BLANK;
		_classPK = 0;
		_filterByMetadata = false;
		_hasMetadata = false;
		_metadataFields = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			_className, _classPK);

		httpServletRequest.setAttribute(
			"liferay-asset:asset-metadata:assetEntry", assetEntry);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_className);

		try {
			httpServletRequest.setAttribute(
				"liferay-asset:asset-metadata:assetRenderer",
				assetRendererFactory.getAssetRenderer(_classPK));
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		httpServletRequest.setAttribute(
			"liferay-asset:asset-metadata:className", _className);
		httpServletRequest.setAttribute(
			"liferay-asset:asset-metadata:classPK", _classPK);
		httpServletRequest.setAttribute(
			"liferay-asset:asset-metadata:filterByMetadata", _filterByMetadata);
		httpServletRequest.setAttribute(
			"liferay-asset:asset-metadata:metadataFields", _metadataFields);
	}

	private static final String _PAGE = "/asset_metadata/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetMetadataTag.class);

	private String _className = StringPool.BLANK;
	private long _classPK;
	private boolean _filterByMetadata;
	private boolean _hasMetadata;
	private String[] _metadataFields;

}