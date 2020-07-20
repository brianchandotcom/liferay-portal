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

import com.liferay.frontend.token.definition.TokenDefinition;
import com.liferay.frontend.token.definition.TokenDefinitionRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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
@Component(service = TokenDefinitionRegistry.class)
public class TokenDefinitionRegistryImpl implements TokenDefinitionRegistry {

	@Override
	public TokenDefinition getThemeTokenDefinition(String themeId) {
		return themeIdTokenDefinitionImplsMap.get(themeId);
	}

	@Override
	public Collection<TokenDefinition> getTokenDefinitions() {
		return new ArrayList<>(bundleTokenDefinitionImplsMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE,
			new BundleTrackerCustomizer<Bundle>() {

				@Override
				public Bundle addingBundle(
					Bundle bundle, BundleEvent bundleEvent) {

					TokenDefinitionImpl tokenDefinitionImpl =
						getTokenDefinitionImpl(bundle);

					if (tokenDefinitionImpl == null) {
						return null;
					}

					synchronized (TokenDefinitionRegistryImpl.this) {
						bundleTokenDefinitionImplsMap.put(
							bundle, tokenDefinitionImpl);

						if (tokenDefinitionImpl.getThemeId() != null) {
							themeIdTokenDefinitionImplsMap.put(
								tokenDefinitionImpl.getThemeId(),
								tokenDefinitionImpl);
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

					synchronized (TokenDefinitionRegistryImpl.this) {
						TokenDefinitionImpl tokenDefinitionImpl =
							bundleTokenDefinitionImplsMap.remove(bundle1);

						if (tokenDefinitionImpl.getThemeId() != null) {
							themeIdTokenDefinitionImplsMap.remove(
								tokenDefinitionImpl.getThemeId());
						}
					}
				}

			});

		bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		bundleTracker.close();

		synchronized (this) {
			bundleTokenDefinitionImplsMap.clear();
			themeIdTokenDefinitionImplsMap.clear();
		}
	}

	protected String getRawTokenDefinition(Bundle bundle) {
		String tokenDefinitionPath = getTokenDefinitionPath(bundle);

		URL url = bundle.getEntry(tokenDefinitionPath);

		if (url == null) {
			return null;
		}

		try (InputStream is = url.openStream()) {
			return StringUtil.read(is);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to read: " + tokenDefinitionPath, ioException);
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

		try (InputStream is = url.openStream()) {
			String xml = StringUtil.read(is);

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
				"Unable to read: WEB-INF/liferay-look-and-feel.xml",
				ioException);
		}
	}

	protected TokenDefinitionImpl getTokenDefinitionImpl(Bundle bundle) {
		String rawTokenDefinition = getRawTokenDefinition(bundle);

		if (rawTokenDefinition == null) {
			return null;
		}

		return new TokenDefinitionImpl(rawTokenDefinition, getThemeId(bundle));
	}

	protected String getTokenDefinitionPath(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String tokenDefinitionPath = headers.get("Token-Definition-Path");

		if (Validator.isNull(tokenDefinitionPath)) {
			tokenDefinitionPath = "META-INF/token-definition.json";
		}

		if (tokenDefinitionPath.charAt(0) == '/') {
			tokenDefinitionPath = tokenDefinitionPath.substring(1);
		}

		return tokenDefinitionPath;
	}

	protected Map<Bundle, TokenDefinitionImpl> bundleTokenDefinitionImplsMap =
		new ConcurrentHashMap<>();
	protected BundleTracker<Bundle> bundleTracker;

	@Reference
	protected Portal portal;

	protected Map<String, TokenDefinitionImpl> themeIdTokenDefinitionImplsMap =
		new ConcurrentHashMap<>();

	private static final Pattern _themeIdPattern = Pattern.compile(
		".*<theme id=\"([^\"]*)\"[^>]*>.*");

}