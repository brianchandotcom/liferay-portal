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

package com.liferay.modern.site.building.fragment.taglib.servlet.taglib;

import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.service.MSBFragmentEntryServiceUtil;
import com.liferay.modern.site.building.fragment.taglib.servlet.ServletContextUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Jürgen Kappler
 */
public class MSBFragmentEntryRendererTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			_msbFragmentEntry =
				MSBFragmentEntryServiceUtil.fetchMSBFragmentEntry(
					_msbFragmentEntryId);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get modern site building fragment entry", pe);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public void setMsbFragmentEntryId(long msbFragmentEntryId) {
		_msbFragmentEntryId = msbFragmentEntryId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		_msbFragmentEntry = null;
		_msbFragmentEntryId = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-modern-site-building-fragment:" +
				"msb-fragment-entry-renderer:msbFragmentEntry",
			_msbFragmentEntry);
	}

	private static final String _PAGE = "/msb_fragment_entry_renderer/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		MSBFragmentEntryRendererTag.class);

	private MSBFragmentEntry _msbFragmentEntry;
	private long _msbFragmentEntryId;

}