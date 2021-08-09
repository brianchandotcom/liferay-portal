/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.internal.servlet.filter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.PrintWriter;

import java.util.Map;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {
		"before-filter=Session Id Filter", "dispatcher=REQUEST", "enabled=true",
		"init-param.url-regex-ignore-pattern=^/html/.+\\.(css|gif|html|ico|jpg|js|png)(\\?.*)?$",
		"servlet-context-name=",
		"servlet-filter-name=SAML SameSite Lax Support Filter",
		"url-pattern=/c/portal/saml/acs", "url-pattern=/c/portal/saml/slo",
		"url-pattern=/c/portal/saml/sso"
	},
	service = Filter.class
)
public class SamlSameSiteLaxCookiesFilter extends BaseSamlPortalFilter {

	@Override
	public boolean isFilterEnabled() {
		return _enabled;
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (Objects.equals("GET", httpServletRequest.getMethod()) ||
			ParamUtil.getBoolean(httpServletRequest, "continue") ||
			(!ParamUtil.getBoolean(httpServletRequest, "noscript") &&
			 (httpServletRequest.getSession(false) != null))) {

			return false;
		}

		return true;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_enabled = MapUtil.getBoolean(properties, "enabled");
	}

	@Override
	protected void doProcessFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		httpServletResponse.setContentType("text/html");

		PrintWriter printWriter = httpServletResponse.getWriter();

		if (ParamUtil.getBoolean(httpServletRequest, "noscript")) {
			printWriter.write(
				StringBundler.concat(
					"<!DOCTYPE html>\n\n<html><body>",
					ResourceBundleUtil.getString(
						ResourceBundleUtil.getBundle(
							_portal.getLocale(httpServletRequest), getClass()),
						"your-browser-must-support-javascript-to-proceed"),
					"</body></html>"));

			printWriter.close();

			return;
		}

		StringBundler sb = new StringBundler(7 + (5 * _PARAMS.length));

		sb.append("<!DOCTYPE html>\n\n");
		sb.append("<html><body onload=\"document.forms[0].submit()\">");
		sb.append("<form action=\"?continue=true\" method=\"post\"");
		sb.append("name=\"fm\">");

		for (String param : _PARAMS) {
			_processParameter(httpServletRequest, sb, param);
		}

		sb.append("<noscript><meta http-equiv=\"refresh\" ");
		sb.append("content=\"0;URL='?noscript=true'\"/>");
		sb.append("</noscript></form></body></html>");

		printWriter.write(sb.toString());

		printWriter.close();
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private void _processParameter(
		HttpServletRequest httpServletRequest, StringBundler sb,
		String paramName) {

		String paramValue = ParamUtil.getString(httpServletRequest, paramName);

		if (Validator.isNotNull(paramValue)) {
			sb.append("<input type=\"hidden\" name=");
			sb.append(paramName);
			sb.append(" value=\"");
			sb.append(_html.escapeAttribute(paramValue));
			sb.append("\"/>");
		}
	}

	private static final String[] _PARAMS = {
		"RelayState", "SAMLRequest", "SAMLResponse", "entityId"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		SamlSameSiteLaxCookiesFilter.class);

	private boolean _enabled = true;

	@Reference
	private Html _html;

	@Reference
	private Portal _portal;

}