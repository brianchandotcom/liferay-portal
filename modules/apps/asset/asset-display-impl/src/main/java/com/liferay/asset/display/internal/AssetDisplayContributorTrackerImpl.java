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

package com.liferay.asset.display.internal;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.display.contributor.AssetInfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetDisplayContributorTracker.class)
public class AssetDisplayContributorTrackerImpl
	implements AssetDisplayContributorTracker {

	@Override
	public AssetDisplayContributor getAssetDisplayContributor(
		String className) {

		return _assetDisplayContributor.get(className);
	}

	@Override
	public AssetDisplayContributor
		getAssetDisplayContributorByAssetURLSeparator(
			String assetURLSeparator) {

		return _assetDisplayContributorByAssetURLSeparator.get(
			assetURLSeparator);
	}

	@Override
	public List<AssetDisplayContributor> getAssetDisplayContributors() {
		return new ArrayList(_assetDisplayContributor.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setAssetDisplayContributor(
		AssetDisplayContributor assetDisplayContributor) {

		_assetDisplayContributor.put(
			assetDisplayContributor.getClassName(), assetDisplayContributor);
		_assetDisplayContributorByAssetURLSeparator.put(
			assetDisplayContributor.getAssetURLSeparator(),
			assetDisplayContributor);

		ServiceRegistration<InfoDisplayContributor> serviceRegistration =
			_bundleContext.registerService(
				InfoDisplayContributor.class,
				new AssetInfoDisplayContributor(assetDisplayContributor), null);

		_serviceRegistrations.put(
			assetDisplayContributor.getClassName(), serviceRegistration);
	}

	protected void unsetAssetDisplayContributor(
		AssetDisplayContributor assetDisplayContributor) {

		_assetDisplayContributor.remove(assetDisplayContributor.getClassName());
		_assetDisplayContributorByAssetURLSeparator.remove(
			assetDisplayContributor.getAssetURLSeparator());

		_serviceRegistrations.remove(assetDisplayContributor.getClassName());
	}

	private final Map<String, AssetDisplayContributor>
		_assetDisplayContributor = new ConcurrentHashMap<>();
	private final Map<String, AssetDisplayContributor>
		_assetDisplayContributorByAssetURLSeparator = new ConcurrentHashMap<>();
	private BundleContext _bundleContext;
	private final Map<String, ServiceRegistration<InfoDisplayContributor>>
		_serviceRegistrations = new ConcurrentHashMap<>();

}