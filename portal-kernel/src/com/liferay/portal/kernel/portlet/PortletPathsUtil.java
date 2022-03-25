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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
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

	public static void populatePortletPaths(
		HttpServletRequest httpServletRequest, String portletHTML,
		Portlet portlet, Map<String, Object> paths) {

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

		_populatePortletPaths(
			httpServletRequest, portletHTML, portlet, portletOnLayout, paths);
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

	private static String _getRootPortletId(Portlet portlet) {

		// Workaround for portlet#getRootPortletId because that does not return
		// the proper root portlet ID for OpenSocial and WSRP portlets

		Portlet rootPortlet = portlet.getRootPortlet();

		return rootPortlet.getPortletId();
	}

	private static void _populatePortletPaths(
		HttpServletRequest httpServletRequest, String portletHTML,
		Portlet portlet, boolean portletOnLayout, Map<String, Object> paths) {

		Set<String> footerCssSet = new LinkedHashSet<>();
		Set<String> footerJavaScriptSet = new LinkedHashSet<>();
		Set<String> headerCssSet = new LinkedHashSet<>();
		Set<String> headerJavaScriptSet = new LinkedHashSet<>();

		if (!portletOnLayout && portlet.isAjaxable()) {
			Portlet rootPortlet = portlet.getRootPortlet();

			for (String footerPortalCss : portlet.getFooterPortalCss()) {
				if (!HttpUtil.hasProtocol(footerPortalCss)) {
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

				if (!HttpUtil.hasProtocol(footerPortalJavaScript)) {
					footerPortalJavaScript =
						PortalUtil.getPathContext() + footerPortalJavaScript;

					footerPortalJavaScript = PortalUtil.getStaticResourceURL(
						httpServletRequest, footerPortalJavaScript,
						rootPortlet.getTimestamp());
				}

				footerJavaScriptSet.add(footerPortalJavaScript);
			}

			for (String footerPortletCss : portlet.getFooterPortletCss()) {
				if (!HttpUtil.hasProtocol(footerPortletCss)) {
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

				if (!HttpUtil.hasProtocol(footerPortletJavaScript)) {
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
				if (!HttpUtil.hasProtocol(headerPortalCss)) {
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

				if (!HttpUtil.hasProtocol(headerPortalJavaScript)) {
					headerPortalJavaScript =
						PortalUtil.getPathContext() + headerPortalJavaScript;

					headerPortalJavaScript = PortalUtil.getStaticResourceURL(
						httpServletRequest, headerPortalJavaScript,
						rootPortlet.getTimestamp());
				}

				headerJavaScriptSet.add(headerPortalJavaScript);
			}

			for (String headerPortletCss : portlet.getHeaderPortletCss()) {
				if (!HttpUtil.hasProtocol(headerPortletCss)) {
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

				if (!HttpUtil.hasProtocol(headerPortletJavaScript)) {
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

		paths.put("footerCssPaths", footerCssSet);
		paths.put("footerJavaScriptPaths", footerJavaScriptSet);
		paths.put("headerCssPaths", headerCssSet);
		paths.put("headerJavaScriptPaths", headerJavaScriptSet);
		paths.put("portletHTML", portletHTML);
		paths.put("refresh", !portlet.isAjaxable());

		List<String> markupHeadElements =
			(List<String>)httpServletRequest.getAttribute(
				MimeResponse.MARKUP_HEAD_ELEMENT);

		if (markupHeadElements != null) {
			paths.put(
				"markupHeadElements",
				StringUtil.merge(markupHeadElements, StringPool.BLANK));
		}
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