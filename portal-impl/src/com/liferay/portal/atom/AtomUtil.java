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

package com.liferay.portal.atom;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.abdera.protocol.server.RequestContext;

/**
 * @author Igor Spasic
 */
public class AtomUtil {

	public static String createCollectionLink(
		AtomRequestContext atomRequestContext, String collectionName) {

		return createEntryLink(atomRequestContext, collectionName, null);
	}

	public static String createEntryLink(
		AtomRequestContext atomRequestContext, String collectionName,
		String entryName) {

		StringBundler sb = new StringBundler(5);

		String targetBasePath = atomRequestContext.getTargetBasePath();

		sb.append(targetBasePath);

		sb.append(CharPool.SLASH);
		sb.append(collectionName);

		if (entryName != null) {
			sb.append(CharPool.SLASH);
			sb.append(entryName);
		}

		String entryLink = sb.toString();

		String resolvedUri = atomRequestContext.getResolvedUri();

		int pos = resolvedUri.indexOf(targetBasePath);

		if (pos != -1) {
			entryLink = resolvedUri.substring(0, pos) + entryLink;
		}

		return entryLink;
	}

	public static String createFeedTitleFromPortletName(
		AtomRequestContext atomRequestContext, String portletId) {

		String portletTitle = null;

		try {
			Company company = getCompany();

			portletTitle = company.getName();
		}
		catch (Exception e) {
			return null;
		}

		User user = getUser(atomRequestContext);

		portletTitle = portletTitle.concat(StringPool.SPACE);

		portletTitle = portletTitle.concat(
			PortalUtil.getPortletTitle(portletId, user));

		portletTitle = portletTitle.trim();

		return portletTitle;
	}

	public static String createIdTagPrefix(String title) {
		Company company = null;

		try {
			company = getCompany();
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("tag:");
		sb.append(company.getWebId());
		sb.append(StringPool.COLON);
		sb.append(title);
		sb.append(StringPool.COLON);

		String idTagPrefix = sb.toString();

		return StringUtil.toLowerCase(idTagPrefix);
	}

	public static Company getCompany() throws PortalException {
		return CompanyLocalServiceUtil.getCompanyById(
			CompanyThreadLocal.getCompanyId());
	}

	public static AtomPager getPager(RequestContext requestContext) {
		return (AtomPager)requestContext.getAttribute(
			RequestContext.Scope.REQUEST, _PAGER);
	}

	public static User getUser(AtomRequestContext atomRequestContext) {
		return (User)atomRequestContext.getRequestAttribute(_USER);
	}

	public static String resolveCollectionUrl(
		String url, String collectionName) {

		String collection = CharPool.SLASH + collectionName + CharPool.SLASH;

		int collectionIndex = url.indexOf(collection);

		if (collectionIndex == -1) {
			return url;
		}

		collectionIndex += collectionName.length() + 1;

		int questionIndex = url.indexOf(CharPool.QUESTION, collectionIndex);

		if (questionIndex != -1) {
			url =
				url.substring(0, collectionIndex) +
					url.substring(questionIndex);
		}
		else {
			url = url.substring(0, collectionIndex);
		}

		return url;
	}

	public static void saveAtomPagerInRequest(
		AtomRequestContext atomRequestContext, AtomPager atomPager) {

		atomRequestContext.setRequestAttribute(_PAGER, atomPager);
	}

	public static void saveUserInRequest(
		HttpServletRequest httpServletRequest, User user) {

		httpServletRequest.setAttribute(_USER, user);
	}

	public static String setPageInUrl(String url, int page) {
		int pageIndex = url.indexOf("page=");

		if (pageIndex == -1) {
			int questionIndex = url.indexOf(CharPool.QUESTION);

			if (questionIndex == -1) {
				url += CharPool.QUESTION;
			}
			else {
				url += CharPool.AMPERSAND;
			}

			return url + "page=" + page;
		}

		int endIndex = url.indexOf(CharPool.AMPERSAND, pageIndex);

		if (endIndex == -1) {
			url = url.substring(0, pageIndex);
		}
		else {
			url = url.substring(0, pageIndex) + url.substring(endIndex + 1);

			url += CharPool.AMPERSAND;
		}

		url += "page=" + page;

		return url;
	}

	private static final String _PAGER = AtomUtil.class.getName() + ".pager";

	private static final String _USER = AtomUtil.class.getName() + ".user";

}