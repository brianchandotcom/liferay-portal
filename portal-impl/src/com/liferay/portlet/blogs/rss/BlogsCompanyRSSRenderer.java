/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.rss;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portlet.blogs.model.BlogsEntry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
public class BlogsCompanyRSSRenderer extends BlogsBaseRSSRenderer {

	public BlogsCompanyRSSRenderer(
		Company company, List<BlogsEntry> blogsEntries,
		HttpServletRequest request) {

		super(blogsEntries, request);
		this._company = company;
	}

	@Override
	public String getFeedURL() {
		return StringPool.BLANK;
	}

	@Override
	public String getRSSName() {

		try {
			return _company.getName();
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	@Override
	protected String getEntryURL() throws PortalException, SystemException {
		return super.getFeedURL();
	}

	private Company _company;

}