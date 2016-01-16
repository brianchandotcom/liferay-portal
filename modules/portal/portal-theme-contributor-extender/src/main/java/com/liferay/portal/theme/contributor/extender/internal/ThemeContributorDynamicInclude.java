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

package com.liferay.portal.theme.contributor.extender.internal;

import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.contributor.extender.BundleWebResources;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true)
public class ThemeContributorDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long themeLastModified = PortalWebResourcesUtil.getLastModified(
			PortalWebResourceConstants.RESOURCE_TYPE_THEME_CONTRIBUTOR);

		PortalResourceUrlRenderedFactory.PortalResourceUrlRenderer
			portalResourceUrlRenderer = PortalResourceUrlRenderedFactory.create(
				themeDisplay.isThemeCssFastLoad(), "css",
				themeLastModified);

		portalResourceUrlRenderer.render(
			request, response.getWriter(), _cssResourceUrls,
			new PortalResourceUrlRenderedFactory.LinkRenderer() {

				@Override
				public void render(PrintWriter printWriter, String href) {
					printWriter.println(
						"<link href=\"" + href + "\" rel=\"stylesheet\" " +
							"type = \"text/css\" />");
				}

			});

		portalResourceUrlRenderer = PortalResourceUrlRenderedFactory.create(
			themeDisplay.isThemeJsFastLoad(), "js", themeLastModified);

		portalResourceUrlRenderer.render(
			request, response.getWriter(), _jsResourceUrls,
			new PortalResourceUrlRenderedFactory.LinkRenderer() {

				@Override
				public void render(PrintWriter printWriter, String href) {
					printWriter.println(
						"<script src=\"" + href + "\" " +
							"\" type = \"text/javascript\"></script>");
				}

			});
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC, unbind = "removeBundleWebResources"
	)
	protected void addBundleWebResources(
		BundleWebResources bundleWebResources) {

		String servletContextPath = bundleWebResources.getServletContextPath();

		synchronized (_cssResourceUrls) {
			for (String cssResourcePath :
					bundleWebResources.getCssResourcePaths()) {

				_cssResourceUrls.add(servletContextPath + cssResourcePath);
			}
		}

		synchronized (_jsResourceUrls) {
			for (String jsResourcePath :
					bundleWebResources.getJsResourcePaths()) {

				_jsResourceUrls.add(servletContextPath + jsResourcePath);
			}
		}
	}

	protected void removeBundleWebResources(
		BundleWebResources bundleWebResources) {

		String servletContextPath = bundleWebResources.getServletContextPath();

		synchronized (_cssResourceUrls) {
			for (String cssResourcePath :
					bundleWebResources.getCssResourcePaths()) {

				_cssResourceUrls.remove(servletContextPath + cssResourcePath);
			}
		}

		synchronized (_jsResourceUrls) {
			for (String jsResourcePath :
					bundleWebResources.getJsResourcePaths()) {

				_jsResourceUrls.remove(servletContextPath + jsResourcePath);
			}
		}
	}

	private final Collection<String> _cssResourceUrls =
		new CopyOnWriteArrayList<>();
	private final Collection<String> _jsResourceUrls =
		new CopyOnWriteArrayList<>();

	private static class PortalResourceUrlRenderedFactory {

		public static PortalResourceUrlRenderer create(
			boolean combo, final String minifierType,
			final long themeLastModified) {

			if (combo) {
				return new ComboPortalResourceUrlRenderer(
					minifierType, themeLastModified);
			}
			else {
				return new SimplePortalResourceUrlRenderer(themeLastModified);
			}
		}

		public interface LinkRenderer {

			public void render(PrintWriter printWriter, String href);

		}

		public interface PortalResourceUrlRenderer {

			public void render(
				HttpServletRequest request, PrintWriter printWriter,
				Collection<String> resourceUrls, LinkRenderer linkRenderer);

		}

		private static class ComboPortalResourceUrlRenderer
			implements PortalResourceUrlRenderer {

			public ComboPortalResourceUrlRenderer(
				String minifierType, long themeLastModified) {

				_minifierType = minifierType;
				_themeLastModified = themeLastModified;
			}

			@Override
			public void render(
				HttpServletRequest request, PrintWriter printWriter,
				Collection<String> resourceUrls, LinkRenderer linkRenderer) {

				if (resourceUrls.isEmpty()) {
					return;
				}

				StringBundler sb = new StringBundler();

				sb.append(
					PortalUtil.getStaticResourceURL(
						request, "/combo", "minifierType=" + _minifierType,
						_themeLastModified));

				for (String resourceUrl : resourceUrls) {
					sb.append("&");
					sb.append(resourceUrl);
				}

				linkRenderer.render(printWriter, sb.toString());
			}

			private final String _minifierType;
			private final long _themeLastModified;

		}

		private static class SimplePortalResourceUrlRenderer
			implements PortalResourceUrlRenderer {

			public SimplePortalResourceUrlRenderer(long themeLastModified) {
				_themeLastModified = themeLastModified;
			}

			@Override
			public void render(
				HttpServletRequest request, PrintWriter printWriter,
				Collection<String> resourceUrls, LinkRenderer linkRenderer) {

				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				String basePath =
					themeDisplay.getPortalURL() + themeDisplay.getPathContext();

				for (String resourcePath : resourceUrls) {
					String resourceUrl = PortalUtil.getStaticResourceURL(
						request, basePath + resourcePath, _themeLastModified);

					linkRenderer.render(printWriter, resourceUrl);
				}
			}

			private final long _themeLastModified;

		}

	}

}