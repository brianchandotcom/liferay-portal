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

package com.liferay.portal.kernel.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpHelperUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.MimeResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public class PortletPathsUtil {

	public static Map<String, Object> getPortletPaths(
		HttpServletRequest httpServletRequest, String portletHTML,
		Portlet portlet) {

		boolean portletOnLayout = false;

		if (portlet.isInstanceable()) {
			String rootPortletId = _getRootPortletId(portlet);
			String portletId = portlet.getPortletId();

			for (Portlet layoutPortlet : _getAllPortlets(httpServletRequest)) {

				// Check to see if an instance of this portlet is already in the
				// layout, but ignore the portlet that was just added

				String layoutPortletRootPortletId = _getRootPortletId(
					layoutPortlet);

				if (rootPortletId.equals(layoutPortletRootPortletId) &&
					!portletId.equals(layoutPortlet.getPortletId())) {

					portletOnLayout = true;

					break;
				}
			}
		}

		return _getPortletPaths(
			httpServletRequest, portletHTML, portlet, portletOnLayout);
	}

	public static void writeFooterPaths(
			HttpServletResponse httpServletResponse, Map<String, Object> paths)
		throws IOException {

		_writePaths(
			httpServletResponse, (Set<String>)paths.get("footerCssPaths"),
			(Set<String>)paths.get("footerJavaScriptPaths"));
	}

	public static void writeHeaderPaths(
			HttpServletResponse httpServletResponse, Map<String, Object> paths)
		throws IOException {

		_writePaths(
			httpServletResponse, (Set<String>)paths.get("headerCssPaths"),
			(Set<String>)paths.get("headerJavaScriptPaths"));
	}

	private static List<Portlet> _getAllPortlets(
		HttpServletRequest httpServletRequest) {

		List<Portlet> allPortlets =
			(List<Portlet>)httpServletRequest.getAttribute(
				WebKeys.ALL_PORTLETS);

		if (ListUtil.isNotEmpty(allPortlets)) {
			return allPortlets;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		allPortlets = layoutTypePortlet.getAllPortlets();

		httpServletRequest.setAttribute(WebKeys.ALL_PORTLETS, allPortlets);

		return allPortlets;
	}

	private static Map<String, Object> _getPortletPaths(
		HttpServletRequest httpServletRequest, String portletHTML,
		Portlet portlet, boolean portletOnLayout) {

		Set<String> footerCssSet = new LinkedHashSet<>();
		Set<String> footerJavaScriptSet = new LinkedHashSet<>();
		Set<String> headerCssSet = new LinkedHashSet<>();
		Set<String> headerJavaScriptSet = new LinkedHashSet<>();

		if (!portletOnLayout && portlet.isAjaxable()) {
			Portlet rootPortlet = portlet.getRootPortlet();

			for (String footerPortalCss : portlet.getFooterPortalCss()) {
				if (!HttpHelperUtil.hasProtocol(footerPortalCss)) {
					footerPortalCss =
						PortalUtil.getPathContext() + footerPortalCss;

					footerPortalCss = PortalUtil.getStaticResourceURL(
						httpServletRequest, footerPortalCss,
						rootPortlet.getTimestamp());
				}

				footerCssSet.add(footerPortalCss);
			}

			for (String footerPortalJavaScript :
					portlet.getFooterPortalJavaScript()) {

				if (!HttpHelperUtil.hasProtocol(footerPortalJavaScript)) {
					footerPortalJavaScript =
						PortalUtil.getPathContext() + footerPortalJavaScript;

					footerPortalJavaScript = PortalUtil.getStaticResourceURL(
						httpServletRequest, footerPortalJavaScript,
						rootPortlet.getTimestamp());
				}

				footerJavaScriptSet.add(footerPortalJavaScript);
			}

			for (String footerPortletCss : portlet.getFooterPortletCss()) {
				if (!HttpHelperUtil.hasProtocol(footerPortletCss)) {
					footerPortletCss =
						portlet.getStaticResourcePath() + footerPortletCss;

					footerPortletCss = PortalUtil.getStaticResourceURL(
						httpServletRequest, footerPortletCss,
						rootPortlet.getTimestamp());
				}

				footerCssSet.add(footerPortletCss);
			}

			for (String footerPortletJavaScript :
					portlet.getFooterPortletJavaScript()) {

				if (!HttpHelperUtil.hasProtocol(footerPortletJavaScript)) {
					footerPortletJavaScript =
						portlet.getStaticResourcePath() +
							footerPortletJavaScript;

					footerPortletJavaScript = PortalUtil.getStaticResourceURL(
						httpServletRequest, footerPortletJavaScript,
						rootPortlet.getTimestamp());
				}

				footerJavaScriptSet.add(footerPortletJavaScript);
			}

			for (String headerPortalCss : portlet.getHeaderPortalCss()) {
				if (!HttpHelperUtil.hasProtocol(headerPortalCss)) {
					headerPortalCss =
						PortalUtil.getPathContext() + headerPortalCss;

					headerPortalCss = PortalUtil.getStaticResourceURL(
						httpServletRequest, headerPortalCss,
						rootPortlet.getTimestamp());
				}

				headerCssSet.add(headerPortalCss);
			}

			for (String headerPortalJavaScript :
					portlet.getHeaderPortalJavaScript()) {

				if (!HttpHelperUtil.hasProtocol(headerPortalJavaScript)) {
					headerPortalJavaScript =
						PortalUtil.getPathContext() + headerPortalJavaScript;

					headerPortalJavaScript = PortalUtil.getStaticResourceURL(
						httpServletRequest, headerPortalJavaScript,
						rootPortlet.getTimestamp());
				}

				headerJavaScriptSet.add(headerPortalJavaScript);
			}

			for (String headerPortletCss : portlet.getHeaderPortletCss()) {
				if (!HttpHelperUtil.hasProtocol(headerPortletCss)) {
					headerPortletCss =
						portlet.getStaticResourcePath() + headerPortletCss;

					headerPortletCss = PortalUtil.getStaticResourceURL(
						httpServletRequest, headerPortletCss,
						rootPortlet.getTimestamp());
				}

				headerCssSet.add(headerPortletCss);
			}

			for (String headerPortletJavaScript :
					portlet.getHeaderPortletJavaScript()) {

				if (!HttpHelperUtil.hasProtocol(headerPortletJavaScript)) {
					headerPortletJavaScript =
						portlet.getStaticResourcePath() +
							headerPortletJavaScript;

					headerPortletJavaScript = PortalUtil.getStaticResourceURL(
						httpServletRequest, headerPortletJavaScript,
						rootPortlet.getTimestamp());
				}

				headerJavaScriptSet.add(headerPortletJavaScript);
			}
		}

		return HashMapBuilder.<String, Object>put(
			"footerCssPaths", footerCssSet
		).put(
			"footerJavaScriptPaths", footerJavaScriptSet
		).put(
			"headerCssPaths", headerCssSet
		).put(
			"headerJavaScriptPaths", headerJavaScriptSet
		).put(
			"markupHeadElements",
			() -> {
				List<String> markupHeadElements =
					(List<String>)httpServletRequest.getAttribute(
						MimeResponse.MARKUP_HEAD_ELEMENT);

				if (markupHeadElements != null) {
					return StringUtil.merge(
						markupHeadElements, StringPool.BLANK);
				}

				return null;
			}
		).put(
			"portletHTML", portletHTML
		).put(
			"refresh", !portlet.isAjaxable()
		).build();
	}

	private static String _getRootPortletId(Portlet portlet) {

		// Workaround for portlet#getRootPortletId because that does not return
		// the proper root portlet ID for OpenSocial and WSRP portlets

		Portlet rootPortlet = portlet.getRootPortlet();

		return rootPortlet.getPortletId();
	}

	private static void _writePaths(
			HttpServletResponse httpServletResponse, Set<String> cssPathsSet,
			Set<String> javaScriptPathsSet)
		throws IOException {

		if ((cssPathsSet == null) || (javaScriptPathsSet == null) ||
			(cssPathsSet.isEmpty() && javaScriptPathsSet.isEmpty())) {

			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		for (String cssPathSet : cssPathsSet) {
			printWriter.print("<link href=\"");
			printWriter.print(HtmlUtil.escape(cssPathSet));
			printWriter.println("\" rel=\"stylesheet\" type=\"text/css\" />");
		}

		for (String javaScriptPathSet : javaScriptPathsSet) {
			printWriter.print("<script src=\"");
			printWriter.print(HtmlUtil.escape(javaScriptPathSet));
			printWriter.println("\" type=\"text/javascript\"></script>");
		}
	}

}