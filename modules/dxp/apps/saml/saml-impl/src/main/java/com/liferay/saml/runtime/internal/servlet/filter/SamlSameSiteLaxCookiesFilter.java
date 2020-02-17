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

package com.liferay.saml.runtime.internal.servlet.filter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.PrintWriter;

import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {
		"before-filter=Session Id Filter", "dispatcher=REQUEST",
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
		return true;
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return true;
	}

	@Override
	protected void doProcessFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		boolean noscript = ParamUtil.getBoolean(httpServletRequest, "noscript");

		if ((httpServletRequest.getParameter("continue") != null) ||
			(!noscript && (httpServletRequest.getSession(false) != null))) {

			filterChain.doFilter(httpServletRequest, httpServletResponse);

			return;
		}

		httpServletResponse.setContentType("text/html");

		PrintWriter writer = httpServletResponse.getWriter();

		if (noscript) {
			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(
					_portal.getLocale(httpServletRequest));

			writer.write(
				StringBundler.concat(
					"<!DOCTYPE html>\n<html><body>",
					ResourceBundleUtil.getString(
						resourceBundle,
						"your-browser-must-support-javascript-to-proceed"),
					"</body></html>"));

			writer.close();

			return;
		}

		StringBundler sb = new StringBundler(16);

		String relayState = httpServletRequest.getParameter("RelayState");
		String samlRequest = httpServletRequest.getParameter("SAMLRequest");
		String samlResponse = httpServletRequest.getParameter("SAMLResponse");

		sb.append("<!DOCTYPE html>\n");
		sb.append("<html><body onload=\"document.forms[0].submit()\">");
		sb.append("<form action=\"?continue\" method=\"post\" name=\"fm\">");

		if (relayState != null) {
			sb.append("<input type=\"hidden\" name=\"RelayState\" value=\"");
			sb.append(relayState);
			sb.append("\"/>");
		}

		if (samlRequest != null) {
			sb.append("<input type=\"hidden\" name=\"SAMLRequest\" value=\"");
			sb.append(samlRequest);
			sb.append("\"/>");
		}

		if (samlResponse != null) {
			sb.append("<input type=\"hidden\" name=\"SAMLResponse\" value=\"");
			sb.append(samlResponse);
			sb.append("\"/>");
		}

		sb.append("<noscript><iframe src=\"?noscript=true\" ");
		sb.append("style=\"width: 100%; border: 0;\">");
		sb.append("</iframe></noscript></form></body></html>");
		sb.append("</html>");

		writer.write(sb.toString());
		writer.close();
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlSameSiteLaxCookiesFilter.class);

	@Reference
	private Portal _portal;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.saml.impl)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}