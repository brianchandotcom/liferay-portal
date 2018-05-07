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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.asset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Juan Fernández
 * @author Shuyang Zhou
 */
public class AssetLinksTag extends IncludeTag {

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public boolean getViewInContext() {
		return _viewInContext;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setViewInContext(boolean viewInContext) {
		_viewInContext = viewInContext;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_assetEntryId = 0;
		_className = StringPool.BLANK;
		_classPK = 0;
		_page = _PAGE;
		_portletURL = null;
		_viewInContext = true;
	}

	@Override
	protected String getPage() {
		return _page;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if (_page == null) {
			return;
		}

		if ((_assetEntryId <= 0) && (_classPK > 0)) {
			try {
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
					_className, _classPK);

				if (assetEntry != null) {
					_assetEntryId = assetEntry.getEntryId();
				}
			}
			catch (SystemException se) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(se, se);
				}
			}
		}

		if (_assetEntryId <= 0) {
			_page = null;

			return;
		}

		List<AssetLink> assetLinks = AssetLinkLocalServiceUtil.getDirectLinks(
			_assetEntryId);

		if (assetLinks.isEmpty()) {
			_page = null;

			return;
		}

		request.setAttribute(
			"liferay-asset:asset-links:assetEntryId",
			String.valueOf(_assetEntryId));

		request.setAttribute(
			"liferay-asset:asset-links:assetLinks", assetLinks);

		request.setAttribute(
			"liferay-asset:asset-links:portletURL", _portletURL);

		request.setAttribute(
			"liferay-asset:asset-links:viewInContext", _viewInContext);
	}

	private static final String _PAGE = "/asset_links/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(AssetLinksTag.class);

	private long _assetEntryId;
	private String _className = StringPool.BLANK;
	private long _classPK;
	private String _page = _PAGE;
	private PortletURL _portletURL;
	private boolean _viewInContext = true;

}