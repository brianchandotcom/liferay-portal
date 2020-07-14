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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Iván Zaera
 */
@Component(service = TokenDefinitionRegistry.class)
public class TokenDefinitionRegistryImpl implements TokenDefinitionRegistry {

	@Override
	public Collection<TokenDefinition> getTokenDefinitions() {
		Map<Bundle, AtomicReference<TokenDefinition>> map =
			bundleTracker.getTracked();

		Collection<AtomicReference<TokenDefinition>> atomicReferences =
			map.values();

		Stream<AtomicReference<TokenDefinition>> stream =
			atomicReferences.stream();

		return stream.reduce(
			new ArrayList<>(),
			(tokenDefinitions, atomicReference) -> {
				TokenDefinition tokenDefinition = atomicReference.get();

				if (tokenDefinition != null) {
					tokenDefinitions.add(tokenDefinition);
				}

				return tokenDefinitions;
			},
			(tokenDefinitions, tokenDefinitions2) -> {
				ArrayList<TokenDefinition> combinedTokenDefinitions =
					new ArrayList<>();

				combinedTokenDefinitions.addAll(tokenDefinitions);

				combinedTokenDefinitions.addAll(tokenDefinitions2);

				return combinedTokenDefinitions;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE,
			new BundleTrackerCustomizer<AtomicReference<TokenDefinition>>() {

				@Override
				public AtomicReference<TokenDefinition> addingBundle(
					Bundle bundle, BundleEvent bundleEvent) {

					return new AtomicReference<>(getTokenDefinition(bundle));
				}

				@Override
				public void modifiedBundle(
					Bundle bundle, BundleEvent bundleEvent,
					AtomicReference<TokenDefinition> atomicReference) {

					atomicReference.set(getTokenDefinition(bundle));
				}

				@Override
				public void removedBundle(
					Bundle bundle, BundleEvent bundleEvent,
					AtomicReference<TokenDefinition> atomicReference) {

					atomicReference.set(null);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		bundleTracker.close();
	}

	protected TokenDefinition getTokenDefinition(Bundle bundle) {
		String tokenDefinitionPath = getTokenDefinitionPath(bundle);

		URL url = bundle.getEntry(tokenDefinitionPath);

		if (url == null) {
			return null;
		}

		try (InputStream is = url.openStream()) {
			return new TokenDefinitionImpl(StringUtil.read(is));
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to read: " + tokenDefinitionPath, ioException);
		}
	}

	protected String getTokenDefinitionPath(Bundle bundle) {
		Locale defaultLocale = LocaleUtil.getDefault();

		Dictionary<String, String> headers = bundle.getHeaders(
			defaultLocale.toString());

		String tokenDefinitionPath = headers.get("Token-Definition-Path");

		if (Validator.isNull(tokenDefinitionPath)) {
			tokenDefinitionPath = "META-INF/token-definition.json";
		}

		if (tokenDefinitionPath.charAt(0) == '/') {
			tokenDefinitionPath = tokenDefinitionPath.substring(1);
		}

		return tokenDefinitionPath;
	}

	protected BundleTracker<AtomicReference<TokenDefinition>> bundleTracker;

}