/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Dictionary;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Anderson Luiz
 * @author Thiago Buarque
 */
public class ThemeBundleTrackerCustomizer
	implements BundleTrackerCustomizer<FrontendTokenDefinitionImpl> {

	public ThemeBundleTrackerCustomizer(
		FrontendTokenDefinitionManager manager, Portal portal) {

		_manager = manager;
		_portal = portal;
	}

	@Override
	public FrontendTokenDefinitionImpl addingBundle(
		Bundle bundle, BundleEvent event) {

		String themeId = null;
		try {
			String frontendTokenDefinitionAsJsonString =
				_getFrontendTokenDefinitionJSONFromBundle(bundle);

			if (Objects.nonNull(frontendTokenDefinitionAsJsonString)) {
				themeId = _getThemeId(bundle, _portal);
				return _manager.addFrontendTokenDefinition(
						themeId, _getResourceBundleLoader(bundle.getSymbolicName()),
						frontendTokenDefinitionAsJsonString);
			}
		}
		catch (RuntimeException runtimeException) {
			_log.error(
				"Unable to parse frontend token definitions for theme " +
					themeId,
				runtimeException);
		}

		return null;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent event,
		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl) {

		_manager.removeFrontendTokenDefinition(
			frontendTokenDefinitionImpl.getThemeId());
	}

	private String _getFrontendTokenDefinitionJSONFromBundle(Bundle bundle) {
		URL url = bundle.getEntry("WEB-INF/frontend-token-definition.json");

		if (url == null) {
			return null;
		}

		try {
			return URLUtil.toString(url);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to read WEB-INF/frontend-token-definition.json",
				ioException);
		}
	}

	private ResourceBundleLoader _getResourceBundleLoader(
		String bundleSymbolicName) {

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(bundleSymbolicName);

		if (resourceBundleLoader != null) {
			return resourceBundleLoader;
		}

		return ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
	}

	private String _getServletContextName(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String webContextPath = headers.get("Web-ContextPath");

		if (webContextPath == null) {
			return null;
		}

		if (webContextPath.startsWith(StringPool.SLASH)) {
			webContextPath = webContextPath.substring(1);
		}

		return webContextPath;
	}

	private String _getThemeId(Bundle bundle, Portal portal) {
		URL url = bundle.getEntry("WEB-INF/liferay-look-and-feel.xml");

		if (url == null) {
			return null;
		}

		try {
			String xml = URLUtil.toString(url);

			xml = xml.replaceAll(StringPool.NEW_LINE, StringPool.SPACE);

			Matcher matcher = _themeIdPattern.matcher(xml);

			if (!matcher.matches()) {
				return null;
			}

			String themeId = matcher.group(1);

			String servletContextName = _getServletContextName(bundle);

			if (servletContextName != null) {
				themeId =
					themeId + PortletConstants.WAR_SEPARATOR +
						servletContextName;
			}

			return portal.getJsSafePortletId(themeId);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to read WEB-INF/liferay-look-and-feel.xml",
				ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeBundleTrackerCustomizer.class);

	private static final Pattern _themeIdPattern = Pattern.compile(
		".*<theme id=\"([^\"]*)\"[^>]*>.*");

	private final FrontendTokenDefinitionManager _manager;
	private final Portal _portal;

}