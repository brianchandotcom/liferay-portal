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

package com.liferay.segments.asah.connector.internal.context.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.context.Context;
import com.liferay.segments.context.contributor.RequestContextContributor;

import java.util.Objects;
import java.util.stream.Stream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"request.context.contributor.key=" + SegmentsAsahRequestContextContributor.KEY_SEGMENTS_ANONYMOUS_USER_ID,
		"request.context.contributor.type=id"
	},
	service = RequestContextContributor.class
)
public class SegmentsAsahRequestContextContributor
	implements RequestContextContributor {

	public static final String KEY_SEGMENTS_ANONYMOUS_USER_ID =
		"segmentsAnonymousUserId";

	@Override
	public void contribute(
		Context context, HttpServletRequest httpServletRequest) {

		String segmentsAnonymousUserId = _getSegmentsAnonymousUserId(
			httpServletRequest);

		httpServletRequest.setAttribute(
			SegmentsWebKeys.SEGMENTS_ANONYMOUS_USER_ID,
			segmentsAnonymousUserId);

		context.put(KEY_SEGMENTS_ANONYMOUS_USER_ID, segmentsAnonymousUserId);
	}

	private String _getSegmentsAnonymousUserId(
		HttpServletRequest httpServletRequest) {

		Cookie[] cookies = httpServletRequest.getCookies();

		if (ArrayUtil.isEmpty(cookies)) {
			return StringPool.BLANK;
		}

		return Stream.of(
			cookies
		).filter(
			cookie -> Objects.equals(
				cookie.getName(), _AC_CLIENT_USER_ID_COOKIE_NAME)
		).map(
			Cookie::getValue
		).findFirst(
		).orElse(
			StringPool.BLANK
		);
	}

	private static final String _AC_CLIENT_USER_ID_COOKIE_NAME =
		"ac_client_user_id";

}