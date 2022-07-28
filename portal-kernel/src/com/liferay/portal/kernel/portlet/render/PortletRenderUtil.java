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

package com.liferay.portal.kernel.portlet.render;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletNameComparator;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera Avellón
 */
public class PortletRenderUtil {

	public static PortletRenderParts getPortletRenderParts(
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

		return _getPortletRenderParts(
			httpServletRequest, portletHTML, portlet, portletOnLayout);
	}

	public static void writeFooterPaths(
			HttpServletResponse httpServletResponse,
			PortletRenderParts portletRenderParts)
		throws IOException {

		_writePaths(
			httpServletResponse, portletRenderParts.getFooterCssPaths(),
			portletRenderParts.getFooterJavaScriptPaths());
	}

	public static void writeHeaderPaths(
			HttpServletResponse httpServletResponse,
			PortletRenderParts portletRenderParts)
		throws IOException {

		_writePaths(
			httpServletResponse, portletRenderParts.getHeaderCssPaths(),
			portletRenderParts.getHeaderJavaScriptPaths());
	}

	public static void writeJavaScriptPath(
			Writer writer, String javaScriptPath,
			Map<String, String> attributes)
		throws IOException {

		String type = "text/javascript";

		if (javaScriptPath.startsWith("module:")) {
			javaScriptPath = javaScriptPath.substring(7);

			type = "module";
		}

		PrintWriter printWriter = new PrintWriter(writer, true);

		printWriter.print("<script src=\"");
		printWriter.print(HtmlUtil.escapeAttribute(javaScriptPath));
		printWriter.print("\" type=\"");
		printWriter.print(type);
		printWriter.print(StringPool.QUOTE);

		if (attributes != null) {
			for (Map.Entry<String, String> entry : attributes.entrySet()) {
				printWriter.print(StringPool.SPACE);
				printWriter.print(entry.getKey());
				printWriter.print("=\"");
				printWriter.print(HtmlUtil.escapeAttribute(entry.getValue()));
				printWriter.print(StringPool.QUOTE);
			}
		}

		printWriter.println("></script>");
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

	private static PortletRenderParts _getPortletRenderParts(
		HttpServletRequest httpServletRequest, String portletHTML,
		Portlet portlet, boolean portletOnLayout) {

		Collection<String> footerCssPaths = Collections.emptyList();
		Collection<String> footerJavaScriptPaths = Collections.emptyList();
		Collection<String> headerCssPaths = Collections.emptyList();
		Collection<String> headerJavaScriptPaths = Collections.emptyList();

		if (!portletOnLayout && portlet.isAjaxable()) {
			Set<String> visitedURLs =
				(Set<String>)httpServletRequest.getAttribute(
					WebKeys.PORTLET_RESOURCE_STATIC_URLS);

			if (visitedURLs == null) {
				visitedURLs = new LinkedHashSet<>();

				httpServletRequest.setAttribute(
					WebKeys.PORTLET_RESOURCE_STATIC_URLS, visitedURLs);
			}

			footerCssPaths = _getStaticURLs(
				httpServletRequest, portlet,
				Arrays.asList(
					new PortletResourceAccessor(false) {

						@Override
						public Collection<String> get(Portlet portlet) {
							return portlet.getFooterPortletCss();
						}

					},
					new PortletResourceAccessor(true) {

						@Override
						public Collection<String> get(Portlet portlet) {
							return portlet.getFooterPortalCss();
						}

					}),
				visitedURLs);

			footerJavaScriptPaths = _getStaticURLs(
				httpServletRequest, portlet,
				Arrays.asList(
					new PortletResourceAccessor(false) {

						@Override
						public Collection<String> get(Portlet portlet) {
							return portlet.getFooterPortletJavaScript();
						}

					},
					new PortletResourceAccessor(true) {

						@Override
						public Collection<String> get(Portlet portlet) {
							return portlet.getFooterPortalJavaScript();
						}

					}),
				visitedURLs);

			headerCssPaths = _getStaticURLs(
				httpServletRequest, portlet,
				Arrays.asList(
					new PortletResourceAccessor(false) {

						@Override
						public Collection<String> get(Portlet portlet) {
							return portlet.getHeaderPortletCss();
						}

					},
					new PortletResourceAccessor(true) {

						@Override
						public Collection<String> get(Portlet portlet) {
							return portlet.getHeaderPortalCss();
						}

					}),
				visitedURLs);

			headerJavaScriptPaths = _getStaticURLs(
				httpServletRequest, portlet,
				Arrays.asList(
					new PortletResourceAccessor(false) {

						@Override
						public Collection<String> get(Portlet portlet) {
							return portlet.getHeaderPortletJavaScript();
						}

					},
					new PortletResourceAccessor(true) {

						@Override
						public Collection<String> get(Portlet portlet) {
							return portlet.getHeaderPortalJavaScript();
						}

					}),
				visitedURLs);
		}

		return new PortletRenderParts(
			footerCssPaths, footerJavaScriptPaths, headerCssPaths,
			headerJavaScriptPaths, portletHTML, !portlet.isAjaxable());
	}

	private static String _getRootPortletId(Portlet portlet) {

		// Workaround for portlet#getRootPortletId because that does not return
		// the proper root portlet ID for OpenSocial and WSRP portlets

		Portlet rootPortlet = portlet.getRootPortlet();

		return rootPortlet.getPortletId();
	}

	private static List<String> _getComboServletURLs(
		Collection<PortletResourceAccessor> portletResourceAccessors,
		List<Portlet> portlets, Predicate<String> predicate, long timestamp,
		String urlPrefix, Set<String> visitedURLs) {

		if (predicate == null) {
			predicate = s -> true;
		}

		List<String> urls = new ArrayList<>();

		StringBundler comboServletSB = new StringBundler();

		portlets = ListUtil.copy(portlets);

		portlets = ListUtil.sort(portlets, _portletNameComparator);

		for (Portlet portlet : portlets) {
			for (PortletResourceAccessor portletResourceAccessor :
				portletResourceAccessors) {

				String contextPath = null;

				if (portletResourceAccessor.isPortalResource()) {
					contextPath = PortalUtil.getPathContext();
				}
				else {
					contextPath =
						PortalUtil.getPathProxy() + portlet.getContextPath();
				}

				Collection<String> portletResources =
					portletResourceAccessor.get(portlet);

				for (String portletResource : portletResources) {
					if (!predicate.test(portletResource)) {
						continue;
					}

					boolean module = false;

					if (portletResource.startsWith("module:")) {
						module = true;

						portletResource = portletResource.substring(7);
					}

					boolean absolute = HttpComponentsUtil.hasProtocol(
						portletResource);

					if (!absolute) {
						portletResource = contextPath + portletResource;
					}

					if (module) {
						portletResource = "module:" + portletResource;
					}

					if (visitedURLs.contains(portletResource)) {
						continue;
					}

					visitedURLs.add(portletResource);

					if (absolute || module) {
						urls.add(portletResource);
					}
					else {
						comboServletSB.append(StringPool.AMPERSAND);

						if (!portletResourceAccessor.isPortalResource()) {
							comboServletSB.append(portlet.getPortletId());
							comboServletSB.append(StringPool.COLON);
						}

						comboServletSB.append(
							HtmlUtil.escapeURL(portletResource));

						timestamp = Math.max(timestamp, portlet.getTimestamp());
					}
				}
			}
		}

		if (comboServletSB.length() > 0) {
			String url = urlPrefix + comboServletSB;

			url = HttpComponentsUtil.addParameter(url, "t", timestamp);

			urls.add(url);
		}

		return urls;
	}

	private static List<String> _getStaticURLs(
		HttpServletRequest httpServletRequest, Portlet portlet,
		Collection<PortletResourceAccessor> portletResourceAccessors,
		Set<String> visitedURLs) {

		Portlet rootPortlet = portlet.getRootPortlet();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<String> urls = new ArrayList<>();

		for (PortletResourceAccessor portletResourceAccessor :
				portletResourceAccessors) {

			String contextPath = null;

			if (portletResourceAccessor.isPortalResource()) {
				contextPath = PortalUtil.getPathContext();
			}
			else {
				contextPath =
					PortalUtil.getPathProxy() + portlet.getContextPath();
			}

			Collection<String> portletResources = portletResourceAccessor.get(
				portlet);

			for (String portletResource : portletResources) {
				boolean module = false;

				if (portletResource.startsWith("module:")) {
					module = true;

					portletResource = portletResource.substring(7);
				}

				if (!HttpComponentsUtil.hasProtocol(portletResource)) {
					portletResource = PortalUtil.getStaticResourceURL(
						httpServletRequest, contextPath + portletResource,
						rootPortlet.getTimestamp());
				}

				if (!portletResource.contains(Http.PROTOCOL_DELIMITER)) {
					String cdnBaseURL = themeDisplay.getCDNBaseURL();

					portletResource = cdnBaseURL.concat(portletResource);
				}

				if (module) {
					portletResource = "module:" + portletResource;
				}

				if (visitedURLs.contains(portletResource)) {
					continue;
				}

				visitedURLs.add(portletResource);

				urls.add(portletResource);
			}
		}

		return urls;
	}

	private static void _writePaths(
			HttpServletResponse httpServletResponse,
			Collection<String> cssPaths, Collection<String> javaScriptPaths)
		throws IOException {

		if ((cssPaths == null) || (javaScriptPaths == null) ||
			(cssPaths.isEmpty() && javaScriptPaths.isEmpty())) {

			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		for (String cssPath : cssPaths) {
			printWriter.print("<link href=\"");
			printWriter.print(HtmlUtil.escape(cssPath));
			printWriter.println("\" rel=\"stylesheet\" type=\"text/css\" />");
		}

		for (String javaScriptPath : javaScriptPaths) {
			writeJavaScriptPath(printWriter, javaScriptPath, null);
		}
	}

	private static final PortletNameComparator _portletNameComparator =
		new PortletNameComparator();

}