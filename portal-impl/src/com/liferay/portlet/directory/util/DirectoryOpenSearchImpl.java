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

package com.liferay.portlet.directory.util;

import com.liferay.portal.kernel.search.HitsOpenSearchImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.util.Enumeration;
import java.util.LinkedHashMap;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Ryan Park
 */
@OSGiBeanProperties
public class DirectoryOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String TITLE = "Liferay Directory Search: ";

	@Override
	public String getClassName() {
		return User.class.getName();
	}

	@Override
	public Indexer<User> getIndexer() {
		return IndexerRegistryUtil.getIndexer(User.class);
	}

	@Override
	public String getSearchPath() {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

	@Override
	protected PortletURL getPortletURL(
			HttpServletRequest request, String portletId, long scopeGroupId)
		throws Exception {

		return super.getPortletURL(
			request, PortletKeys.DIRECTORY, scopeGroupId);
	}

	protected LinkedHashMap<String, Object> getUserParams(
		long companyId, String keywords) {

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, User.class.getName());

		Enumeration<String> enu = expandoBridge.getAttributeNames();

		while (enu.hasMoreElements()) {
			String attributeName = enu.nextElement();

			UnicodeProperties properties = expandoBridge.getAttributeProperties(
				attributeName);

			int indexType = GetterUtil.getInteger(
				properties.getProperty(ExpandoColumnConstants.INDEX_TYPE));

			if (indexType != ExpandoColumnConstants.INDEX_TYPE_NONE) {
				userParams.put(attributeName, keywords);
			}
		}

		return userParams;
	}

}