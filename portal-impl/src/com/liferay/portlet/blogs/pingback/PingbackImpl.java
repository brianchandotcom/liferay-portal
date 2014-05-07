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

package com.liferay.portlet.blogs.pingback;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Chow
 * @author André de Oliveira
 */
public class PingbackImpl implements Pingback {

	public PingbackImpl() {
		_pingbackExcerptExtractor = new PingbackExcerptExtractorImpl(
			PropsValues.BLOGS_LINKBACK_EXCERPT_LENGTH);

		_pingbackComments = new PingbackCommentsImpl();
	}

	@Override
	public void addPingback(long companyId) throws Exception {
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
		String body = buildBody();
		String urlTitle = entry.getUrlTitle();

		addComment(
			userId, groupId, className, classPK, body, companyId, urlTitle);
	}

	@Override
	public void setSourceUri(String sourceUri) {
		_sourceUri = sourceUri;
		_pingbackExcerptExtractor.setSourceUri(sourceUri);
	}

	@Override
	public void setTargetUri(String targetUri) {
		_targetUri = targetUri;
		_pingbackExcerptExtractor.setTargetUri(targetUri);
	}

	protected PingbackImpl(
		PingbackComments pingbackComments,
		PingbackExcerptExtractor pingbackExcerptExtractor) {

		_pingbackComments = pingbackComments;
		_pingbackExcerptExtractor = pingbackExcerptExtractor;
	}

	protected void addComment(
			long userId, long groupId, String className, long classPK,
			String body, long companyId, String urlTitle)
		throws PortalException, SystemException {

		_pingbackComments.addComment(
			userId, groupId, className, classPK, body,
			new PingbackServiceContextFunction(companyId, groupId, urlTitle));
	}

	protected String buildBody() {
		StringBundler sb = new StringBundler(7);

		sb.append("[...] ");
		sb.append(_pingbackExcerptExtractor.getExcerpt());
		sb.append(" [...] [url=");
		sb.append(_sourceUri);
		sb.append("]");
		sb.append(LanguageUtil.get(LocaleUtil.getSiteDefault(), "read-more"));
		sb.append("[/url]");

		return sb.toString();
	}

	protected BlogsEntry getBlogsEntry(long companyId) throws Exception {
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

		BlogsEntry entry;

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

	private PingbackComments _pingbackComments;
	private PingbackExcerptExtractor _pingbackExcerptExtractor;
	private String _sourceUri;
	private String _targetUri;

}