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

package com.liferay.frontend.token.definition.internal;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Iván Zaera
 */
@Component(service = FrontendTokenDefinitionRegistry.class)
public class FrontendTokenDefinitionRegistryImpl
	implements FrontendTokenDefinitionRegistry {

	@Override
	public FrontendTokenDefinition getFrontendTokenDefinition(String themeId) {
		return themeIdFrontendTokenDefinitionImpls.get(themeId);
	}

	@Override
	public Collection<FrontendTokenDefinition> getFrontendTokenDefinitions() {
		return new ArrayList<>(bundleFrontendTokenDefinitionImpls.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE, _bundleTrackerCustomizer);

		bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		bundleTracker.close();

		synchronized (this) {
			bundleFrontendTokenDefinitionImpls.clear();
			themeIdFrontendTokenDefinitionImpls.clear();
		}
	}

	protected FrontendTokenDefinitionImpl getFrontendTokenDefinitionImpl(
		Bundle bundle) {

		String json = getFrontendTokenDefinitionJSON(bundle);

		if (json == null) {
			return null;
		}

		String themeId = getThemeId(bundle);

		try {
			ResourceBundleLoader resourceBundleLoader =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName(
						bundle.getSymbolicName());

			if (resourceBundleLoader == null) {
				resourceBundleLoader =
					ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
			}

			return new FrontendTokenDefinitionImpl(
				jsonFactory.createJSONObject(json), jsonFactory,
				resourceBundleLoader, themeId);
		}
		catch (JSONException | RuntimeException exception) {
			_log.error(
				"Unable to parse frontend token definitions for theme " +
					themeId,
				exception);
		}

		return null;
	}

	protected String getFrontendTokenDefinitionJSON(Bundle bundle) {
		URL url = bundle.getEntry("WEB-INF/frontend-token-definition.json");

		if (url == null) {
			return null;
		}

		try (InputStream inputStream = url.openStream()) {
			return StringUtil.read(inputStream);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to read WEB-INF/frontend-token-definition.json",
				ioException);
		}
	}

	protected String getServletContextName(Bundle bundle) {
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

	protected String getThemeId(Bundle bundle) {
		URL url = bundle.getEntry("WEB-INF/liferay-look-and-feel.xml");

		if (url == null) {
			return null;
		}

		try (InputStream inputStream = url.openStream()) {
			String xml = StringUtil.read(inputStream);

			xml = xml.replaceAll(StringPool.NEW_LINE, StringPool.SPACE);

			Matcher matcher = _themeIdPattern.matcher(xml);

			if (!matcher.matches()) {
				return null;
			}

			String themeId = matcher.group(1);

			String servletContextName = getServletContextName(bundle);

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

	protected Map<Bundle, FrontendTokenDefinitionImpl>
		bundleFrontendTokenDefinitionImpls = new ConcurrentHashMap<>();
	protected BundleTracker<Bundle> bundleTracker;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Portal portal;

	protected Map<String, FrontendTokenDefinitionImpl>
		themeIdFrontendTokenDefinitionImpls = new ConcurrentHashMap<>();

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendTokenDefinitionRegistryImpl.class);

	private static final Pattern _themeIdPattern = Pattern.compile(
		".*<theme id=\"([^\"]*)\"[^>]*>.*");

	private final BundleTrackerCustomizer<Bundle> _bundleTrackerCustomizer =
		new BundleTrackerCustomizer<Bundle>() {

			@Override
			public Bundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
				FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
					getFrontendTokenDefinitionImpl(bundle);

				if (frontendTokenDefinitionImpl == null) {
					return null;
				}

				synchronized (FrontendTokenDefinitionRegistryImpl.this) {
					bundleFrontendTokenDefinitionImpls.put(
						bundle, frontendTokenDefinitionImpl);

					if (frontendTokenDefinitionImpl.getThemeId() != null) {
						themeIdFrontendTokenDefinitionImpls.put(
							frontendTokenDefinitionImpl.getThemeId(),
							frontendTokenDefinitionImpl);
					}
				}

				return bundle;
			}

			@Override
			public void modifiedBundle(
				Bundle bundle1, BundleEvent bundleEvent, Bundle bundle2) {

				removedBundle(bundle1, bundleEvent, null);

				addingBundle(bundle1, bundleEvent);
			}

			@Override
			public void removedBundle(
				Bundle bundle1, BundleEvent bundleEvent, Bundle bundle2) {

				synchronized (FrontendTokenDefinitionRegistryImpl.this) {
					FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
						bundleFrontendTokenDefinitionImpls.remove(bundle1);

					if ((frontendTokenDefinitionImpl != null) &&
						(frontendTokenDefinitionImpl.getThemeId() != null)) {

						themeIdFrontendTokenDefinitionImpls.remove(
							frontendTokenDefinitionImpl.getThemeId());
					}
				}
			}

		};

}