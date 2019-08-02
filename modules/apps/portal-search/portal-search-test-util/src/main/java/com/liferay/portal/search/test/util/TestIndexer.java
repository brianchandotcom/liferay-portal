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

package com.liferay.portal.search.test.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Summary;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Tina Tian
 */
public class TestIndexer extends BaseIndexer<Object> {

	public TestIndexer(String className) {
		_className = className;
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	protected void doDelete(Object object) {
	}

	@Override
	protected Document doGetDocument(Object object) {
		return null;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return null;
	}

	@Override
	protected void doReindex(Object object) {
	}

	@Override
	protected void doReindex(String className, long classPK) {
	}

	@Override
	protected void doReindex(String[] ids) {
	}

	private final String _className;

}