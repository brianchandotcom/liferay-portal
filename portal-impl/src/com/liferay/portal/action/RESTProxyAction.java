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

package com.liferay.portal.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author David Truong
 * @author Gavin Wan
 * @author Samuel Kong
 */
public class RESTProxyAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String url = ParamUtil.getString(request, "url");

		if (!validate(url)) {
			return null;
		}

		Http.Options options = new Http.Options();

		int pos = url.indexOf(CharPool.QUESTION);

		if (pos != -1) {
			options.setBody(
				url.substring(pos + 1),
				ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED,
				StringPool.UTF8);
			options.setLocation(url.substring(0, pos));
		}
		else {
			options.setLocation(url);
		}

		options.setPost(true);

		String content = HttpUtil.URLtoString(options);

		ServletResponseUtil.write(response, content);

		return null;
	}

	protected boolean validate(String url) {
		if (Validator.isNull(url) || !HttpUtil.hasDomain(url)) {
			return false;
		}

		if (PropsValues.REST_PROXY_URL_PREFIXES_ALLOWED.length == 0) {
			return true;
		}

		for (String urlPrefix : PropsValues.REST_PROXY_URL_PREFIXES_ALLOWED) {
			if (StringUtil.startsWith(url, urlPrefix)) {
				return true;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("URL " + url + " is not allowed");
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RESTProxyAction.class);

}