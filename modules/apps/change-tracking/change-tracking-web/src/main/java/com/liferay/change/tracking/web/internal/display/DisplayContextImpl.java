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

package com.liferay.change.tracking.web.internal.display;

import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Preston Crary
 */
public class DisplayContextImpl<T> implements DisplayContext<T> {

	public DisplayContextImpl(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, T model, long ctEntryId,
		String type) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_model = model;
		_ctEntryId = ctEntryId;
		_type = type;
	}

	@Override
	public String getDownloadURL(String key, long size, String title) {
		if (_ctEntryId <= 0) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(13);

		sb.append(themeDisplay.getPortalURL());
		sb.append(Portal.PATH_MODULE);
		sb.append("/change_tracking/documents/");
		sb.append(_ctEntryId);
		sb.append(StringPool.SLASH);
		sb.append(_type);

		if (Validator.isNotNull(key)) {
			sb.append(StringPool.SLASH);
			sb.append(String.valueOf(key));
		}

		if (size > 0) {
			sb.append("?size=");
			sb.append(String.valueOf(size));
		}

		if (Validator.isNotNull(title)) {
			if (size > 0) {
				sb.append(StringPool.AMPERSAND);
			}
			else {
				sb.append(StringPool.QUESTION);
			}

			sb.append("title=");
			sb.append(URLCodec.encodeURL(title));
		}

		return sb.toString();
	}

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	@Override
	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	@Override
	public T getModel() {
		return _model;
	}

	private final long _ctEntryId;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final T _model;
	private final String _type;

}