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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.kernel.xmlrpc.Response;
import com.liferay.portal.kernel.xmlrpc.XmlRpcConstants;
import com.liferay.portal.kernel.xmlrpc.XmlRpcUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.pingback.DuplicateCommentException;
import com.liferay.portlet.blogs.pingback.InvalidSourceURIException;
import com.liferay.portlet.blogs.pingback.PingbackComments;
import com.liferay.portlet.blogs.pingback.PingbackCommentsImpl;
import com.liferay.portlet.blogs.pingback.PingbackDisabledException;
import com.liferay.portlet.blogs.pingback.PingbackExcerptExtractor;
import com.liferay.portlet.blogs.pingback.PingbackExcerptExtractorImpl;
import com.liferay.portlet.blogs.pingback.PingbackServiceContextFunction;
import com.liferay.portlet.blogs.pingback.UnavailableSourceURIException;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class PingbackMethodImpl implements Method {

	public static final int ACCESS_DENIED = 49;

	public static final int GENERIC_FAULT = 0;

	public static final int PINGBACK_ALREADY_REGISTERED = 48;

	public static final int SERVER_ERROR = 50;

	public static final int SOURCE_URI_DOES_NOT_EXIST = 16;

	public static final int SOURCE_URI_INVALID = 17;

	public static final int TARGET_URI_DOES_NOT_EXIST = 32;

	public static final int TARGET_URI_INVALID = 33;

	public PingbackMethodImpl() {
		_pingbackComments = new PingbackCommentsImpl();
		_pingbackExcerptExtractor = new PingbackExcerptExtractorImpl(
			PropsValues.BLOGS_LINKBACK_EXCERPT_LENGTH);
	}

	@Override
	public Response execute(long companyId) {
		try {
			executeAddPingback(companyId);

			return XmlRpcUtil.createSuccess("Pingback accepted");
		}
		catch (DuplicateCommentException dce) {
			return XmlRpcUtil.createFault(
				PINGBACK_ALREADY_REGISTERED, "Pingback previously registered");
		}
		catch (InvalidSourceURIException isue) {
			return XmlRpcUtil.createFault(
				SOURCE_URI_INVALID, isue.getMessage());
		}
		catch (PingbackDisabledException pde) {
			return XmlRpcUtil.createFault(
				XmlRpcConstants.REQUESTED_METHOD_NOT_FOUND, pde.getMessage());
		}
		catch (UnavailableSourceURIException usue) {
			return XmlRpcUtil.createFault(
				SOURCE_URI_DOES_NOT_EXIST, usue.getMessage());
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return XmlRpcUtil.createFault(
				TARGET_URI_INVALID, "Error parsing target URI");
		}
	}

	public void executeAddPingback(long companyId) throws Exception {
		/*
		Just to make the diff easier to review,
		in this commit this method is public and has a bad name.
		Commits afterwards will make it better.
		*/

		if (!PropsValues.BLOGS_PINGBACK_ENABLED) {
			throw new PingbackDisabledException("Pingbacks are disabled");
		}

		_pingbackExcerptExtractor.validateSource();

		BlogsEntry entry = getBlogsEntry(companyId);

		if (!entry.isAllowPingbacks()) {
			throw new PingbackDisabledException("Pingbacks are disabled");
		}

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);
		long groupId = entry.getGroupId();
		String className = BlogsEntry.class.getName();
		long classPK = entry.getEntryId();

		String urlTitle = entry.getUrlTitle();

		String body =
			"[...] " + _pingbackExcerptExtractor.getExcerpt() +
				" [...] [url=" + _sourceUri + "]" +
				LanguageUtil.get(LocaleUtil.getSiteDefault(), "read-more") +
					"[/url]";

		_pingbackComments.addComment(
			userId, groupId, className, classPK, body,
			new PingbackServiceContextFunction(companyId, groupId, urlTitle));
	}

	@Override
	public String getMethodName() {
		return "pingback.ping";
	}

	@Override
	public String getToken() {
		return "pingback";
	}

	@Override
	public boolean setArguments(Object[] arguments) {
		try {
			_pingbackExcerptExtractor.setSourceUri((String)arguments[0]);
			_pingbackExcerptExtractor.setTargetUri((String)arguments[1]);

			return true;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return false;
		}
	}

	protected BlogsEntry getBlogsEntry(long companyId) throws Exception {
		BlogsEntry entry = null;

		URL url = new URL(_targetUri);

		String friendlyURL = url.getPath();

		int end = friendlyURL.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

		if (end != -1) {
			friendlyURL = friendlyURL.substring(0, end);
		}

		long plid = PortalUtil.getPlidFromFriendlyURL(companyId, friendlyURL);
		long groupId = PortalUtil.getScopeGroupId(plid);

		Map<String, String[]> params = new HashMap<String, String[]>();

		FriendlyURLMapperThreadLocal.setPRPIdentifiers(
			new HashMap<String, String>());

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PortletKeys.BLOGS);

		FriendlyURLMapper friendlyURLMapper =
			portlet.getFriendlyURLMapperInstance();

		friendlyURL = url.getPath();

		end = friendlyURL.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

		if (end != -1) {
			friendlyURL = friendlyURL.substring(
				end + Portal.FRIENDLY_URL_SEPARATOR.length() - 1);
		}

		Map<String, Object> requestContext = new HashMap<String, Object>();

		friendlyURLMapper.populateParams(friendlyURL, params, requestContext);

		String param = getParam(params, "entryId");

		if (Validator.isNotNull(param)) {
			long entryId = GetterUtil.getLong(param);

			entry = BlogsEntryLocalServiceUtil.getEntry(entryId);
		}
		else {
			String urlTitle = getParam(params, "urlTitle");

			entry = BlogsEntryLocalServiceUtil.getEntry(groupId, urlTitle);
		}

		return entry;
	}

	protected String getParam(Map<String, String[]> params, String name) {
		String[] paramArray = params.get(name);

		if (paramArray == null) {
			String namespace = PortalUtil.getPortletNamespace(
				PortletKeys.BLOGS);

			paramArray = params.get(namespace + name);
		}

		if (ArrayUtil.isNotEmpty(paramArray)) {
			return paramArray[0];
		}
		else {
			return null;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PingbackMethodImpl.class);

	private PingbackComments _pingbackComments;
	private PingbackExcerptExtractor _pingbackExcerptExtractor;
	private String _sourceUri;
	private String _targetUri;

}