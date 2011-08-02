/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
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
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		String url = ParamUtil.getString(request, "url");

		if (validate(url)) {
			String content = HttpUtil.URLtoString(url, true);

			ServletResponseUtil.write(response, content);
		}

		return null;
	}

	protected boolean validate(String url) {
		if (Validator.isNull(url) || !HttpUtil.hasDomain(url)) {
			return false;
		}

		String domain = StringUtil.split(
			HttpUtil.getDomain(url), CharPool.COLON)[0];

		try {
			CompanyLocalServiceUtil.getCompanyByVirtualHost(domain);

			return true;
		}
		catch (Exception e) {
		}

		try {
			LayoutSetLocalServiceUtil.getLayoutSet(domain);

			return true;
		}
		catch (Exception e) {
		}

		String[] allowedDomains = PropsValues.REST_PROXY_DOMAINS_ALLOWED;

		if ((allowedDomains.length > 0) &&
			!ArrayUtil.contains(allowedDomains, domain)) {

			if (_log.isDebugEnabled()) {
				_log.debug("URL " + url + " is not allowed");
			}

			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(RESTProxyAction.class);

}