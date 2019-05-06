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

package com.liferay.frontend.js.top.head.extender.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = {})
public class TopHeadExtender
	implements BundleTrackerCustomizer<TopHeadExtension> {

	@Override
	public TopHeadExtension addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String liferayJsResourcesTopHead = headers.get(
			"Liferay-JS-Resources-Top-Head");

		String liferayJsResourcesTopHeadAuthenticated = headers.get(
			"Liferay-JS-Resources-Top-Head-Authenticated");

		if (Validator.isBlank(liferayJsResourcesTopHead) &&
			Validator.isBlank(liferayJsResourcesTopHeadAuthenticated)) {

			return null;
		}

		List<String> jsResourcePaths = null;

		if (Validator.isNull(liferayJsResourcesTopHead)) {
			jsResourcePaths = Collections.emptyList();
		}
		else {
			jsResourcePaths = Arrays.asList(
				liferayJsResourcesTopHead.split(StringPool.COMMA));
		}

		List<String> authenticatedJsResourcePaths = null;

		if (Validator.isNull(liferayJsResourcesTopHeadAuthenticated)) {
			authenticatedJsResourcePaths = Collections.emptyList();
		}
		else {
			authenticatedJsResourcePaths = Arrays.asList(
				liferayJsResourcesTopHeadAuthenticated.split(StringPool.COMMA));
		}

		TopHeadResourcesImpl topHeadResourcesImpl = new TopHeadResourcesImpl(
			jsResourcePaths, authenticatedJsResourcePaths);

		int liferayTopHeadWeight = GetterUtil.getInteger(
			headers.get("Liferay-Top-Head-Weight"));

		TopHeadExtension topHeadExtension = new TopHeadExtension(
			bundle, topHeadResourcesImpl, liferayTopHeadWeight);

		topHeadExtension.start();

		return topHeadExtension;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		TopHeadExtension topHeadExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		TopHeadExtension topHeadExtension) {

		topHeadExtension.destroy();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE | Bundle.STARTING, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private BundleTracker<TopHeadExtension> _bundleTracker;

}