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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.MimeResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public class PortletJSONUtil {

	public static void populatePortletJSONObject(
			HttpServletRequest httpServletRequest, String portletHTML,
			Portlet portlet, JSONObject jsonObject)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Set<String> footerCssSet = new LinkedHashSet<>();
		Set<String> footerJavaScriptSet = new LinkedHashSet<>();
		Set<String> headerCssSet = new LinkedHashSet<>();
		Set<String> headerJavaScriptSet = new LinkedHashSet<>();

		boolean portletOnLayout = false;

		String rootPortletId = _getRootPortletId(portlet);
		String portletId = portlet.getPortletId();

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		for (Portlet layoutPortlet : layoutTypePortlet.getAllPortlets()) {

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

		String footerCssPaths = JSONFactoryUtil.serialize(
			footerCssSet.toArray(new String[0]));

		jsonObject.put(
			"footerCssPaths", JSONFactoryUtil.createJSONArray(footerCssPaths));

		String footerJavaScriptPaths = JSONFactoryUtil.serialize(
			footerJavaScriptSet.toArray(new String[0]));

		jsonObject.put(
			"footerJavaScriptPaths",
			JSONFactoryUtil.createJSONArray(footerJavaScriptPaths));

		String headerCssPaths = JSONFactoryUtil.serialize(
			headerCssSet.toArray(new String[0]));

		jsonObject.put(
			"headerCssPaths", JSONFactoryUtil.createJSONArray(headerCssPaths));

		String headerJavaScriptPaths = JSONFactoryUtil.serialize(
			headerJavaScriptSet.toArray(new String[0]));

		jsonObject.put(
			"headerJavaScriptPaths",
			JSONFactoryUtil.createJSONArray(headerJavaScriptPaths));

		List<String> markupHeadElements =
			(List<String>)httpServletRequest.getAttribute(
				MimeResponse.MARKUP_HEAD_ELEMENT);

		if (markupHeadElements != null) {
			jsonObject.put(
				"markupHeadElements",
				StringUtil.merge(markupHeadElements, StringPool.BLANK));
		}

		jsonObject.put(
			"portletHTML", portletHTML
		).put(
			"refresh", !portlet.isAjaxable()
		);
	}

	public static void writeFooterPaths(
			HttpServletResponse httpServletResponse, JSONObject jsonObject)
		throws IOException {

		_writePaths(
			httpServletResponse, jsonObject.getJSONArray("footerCssPaths"),
			jsonObject.getJSONArray("footerJavaScriptPaths"));
	}

	public static void writeHeaderPaths(
			HttpServletResponse httpServletResponse, JSONObject jsonObject)
		throws IOException {

		_writePaths(
			httpServletResponse, jsonObject.getJSONArray("headerCssPaths"),
			jsonObject.getJSONArray("headerJavaScriptPaths"));
	}

	private static String _getRootPortletId(Portlet portlet) {

		// Workaround for portlet#getRootPortletId because that does not return
		// the proper root portlet ID for OpenSocial and WSRP portlets

		Portlet rootPortlet = portlet.getRootPortlet();

		return rootPortlet.getPortletId();
	}

	private static void _writePaths(
			HttpServletResponse httpServletResponse,
			JSONArray cssPathsJSONArray, JSONArray javaScriptPathsJSONArray)
		throws IOException {

		if ((cssPathsJSONArray.length() == 0) &&
			(javaScriptPathsJSONArray.length() == 0)) {

			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		for (int i = 0; i < cssPathsJSONArray.length(); i++) {
			String value = cssPathsJSONArray.getString(i);

			printWriter.print("<link href=\"");
			printWriter.print(HtmlUtil.escape(value));
			printWriter.println("\" rel=\"stylesheet\" type=\"text/css\" />");
		}

		for (int i = 0; i < javaScriptPathsJSONArray.length(); i++) {
			String value = javaScriptPathsJSONArray.getString(i);

			printWriter.print("<script src=\"");
			printWriter.print(HtmlUtil.escape(value));
			printWriter.println("\" type=\"text/javascript\"></script>");
		}
	}

}