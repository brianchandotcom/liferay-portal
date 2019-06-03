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

package com.liferay.portlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.internal.PortletSessionImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletSession;

import javax.servlet.http.HttpSession;

/**
 * @author Neil Griffin
 */
public class InvokerPortletUtil {

	public static void clearResponse(
		HttpSession session, long plid, String portletId, String languageId) {

		String sesResponseId = encodeResponseKey(plid, portletId, languageId);

		Map<String, InvokerPortletResponse> responses = getResponses(session);

		responses.remove(sesResponseId);
	}

	public static void clearResponses(PortletSession session) {
		Map<String, InvokerPortletResponse> responses = getResponses(session);

		responses.clear();
	}

	public static String encodeResponseKey(
		long plid, String portletId, String languageId) {

		StringBundler sb = new StringBundler(5);

		sb.append(StringUtil.toHexString(plid));
		sb.append(StringPool.UNDERLINE);
		sb.append(portletId);
		sb.append(StringPool.UNDERLINE);
		sb.append(languageId);

		return sb.toString();
	}

	public static Map<String, InvokerPortletResponse> getResponses(
		HttpSession session) {

		Map<String, InvokerPortletResponse> responses =
			(Map<String, InvokerPortletResponse>)session.getAttribute(
				WebKeys.CACHE_PORTLET_RESPONSES);

		if (responses == null) {
			responses = new ConcurrentHashMap<>();

			session.setAttribute(WebKeys.CACHE_PORTLET_RESPONSES, responses);
		}

		return responses;
	}

	public static Map<String, InvokerPortletResponse> getResponses(
		PortletSession portletSession) {

		PortletSessionImpl portletSessionImpl =
			(PortletSessionImpl)portletSession;

		return getResponses(portletSessionImpl.getHttpSession());
	}

}