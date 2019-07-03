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

package com.liferay.portal.template.soy.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.template.soy.SoyTemplateResource;
import com.liferay.portal.template.soy.SoyTemplateResourceFactory;
import com.liferay.portal.template.soy.internal.util.SoyTemplateResourcesCollector;
import com.liferay.portal.template.soy.internal.util.SoyTemplateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Bruno Basto
 */
public class SoyCapabilityBundleTrackerCustomizer
	implements BundleTrackerCustomizer<List<BundleCapability>> {

	public SoyCapabilityBundleTrackerCustomizer(
		SoyTofuCacheHandler soyTofuCacheHandler,
		SoyProviderCapabilityBundleRegister soyProviderCapabilityBundleRegister,
		SoyTemplateResourceFactory soyTemplateResourceFactory) {

		_soyTofuCacheHandler = soyTofuCacheHandler;
		_soyProviderCapabilityBundleRegister =
			soyProviderCapabilityBundleRegister;
		_soyTemplateResourceFactory = soyTemplateResourceFactory;
	}

	@Override
	public List<BundleCapability> addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		if (bundleWiring == null) {
			return null;
		}

		List<BundleCapability> bundleCapabilities =
			bundleWiring.getCapabilities("soy");

		if (ListUtil.isEmpty(bundleCapabilities)) {
			return bundleCapabilities;
		}

		for (BundleWire bundleWire : bundleWiring.getRequiredWires("soy")) {
			BundleRevision bundleRevision = bundleWire.getProvider();

			Bundle requiredBundle = bundleRevision.getBundle();

			_soyProviderCapabilityBundleRegister.register(requiredBundle);

			_addTemplateResourcesToList(requiredBundle);
		}

		_soyProviderCapabilityBundleRegister.register(bundle);

		_addTemplateResourcesToList(bundle);

		_soyTemplateResource = null;

		return bundleCapabilities;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public List<TemplateResource> getAllTemplateResources() {
		return _templateResources;
	}

	public SoyTemplateResource getSoyTemplateResource() {
		SoyTemplateResource soyTemplateResource = _soyTemplateResource;

		if (soyTemplateResource == null) {
			soyTemplateResource =
				_soyTemplateResourceFactory.createSoyTemplateResource(
					Collections.unmodifiableList(_templateResources));
		}

		_soyTemplateResource = soyTemplateResource;

		return soyTemplateResource;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		List<BundleCapability> bundleCapabilities) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		List<BundleCapability> bundleCapabilities) {

		List<TemplateResource> removedTemplateResources =
			_removeBundleTemplateResourcesFromList(bundle);

		_soyTemplateResource = null;

		_soyTofuCacheHandler.removeIfAny(removedTemplateResources);

		_soyProviderCapabilityBundleRegister.unregister(bundle);
	}

	private void _addTemplateResourcesToList(Bundle bundle) {
		SoyTemplateResourcesCollector soyTemplateResourcesCollector =
			new SoyTemplateResourcesCollector(bundle, StringPool.SLASH);

		try {
			List<TemplateResource> templateResources =
				soyTemplateResourcesCollector.getTemplateResources();

			Stream<TemplateResource> templateResourcesStream =
				templateResources.stream();

			templateResourcesStream.forEach(
				templateResource -> {
					if ((templateResource != null) &&
						!_templateResources.contains(templateResource)) {

						_templateResources.add(templateResource);
					}
				});
		}
		catch (TemplateException te) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to add template resources for bundle " +
						bundle.getBundleId(),
					te);
			}
		}
	}

	private List<TemplateResource> _removeBundleTemplateResourcesFromList(
		Bundle bundle) {

		List<TemplateResource> removedTemplateResources = new ArrayList<>();

		Iterator<TemplateResource> iterator = _templateResources.iterator();

		while (iterator.hasNext()) {
			TemplateResource templateResource = iterator.next();

			long bundleId = SoyTemplateUtil.getBundleId(
				templateResource.getTemplateId());

			if (bundle.getBundleId() == bundleId) {
				removedTemplateResources.add(templateResource);
			}
		}

		_templateResources.removeAll(removedTemplateResources);

		return removedTemplateResources;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SoyCapabilityBundleTrackerCustomizer.class);

	private static final List<TemplateResource> _templateResources =
		new CopyOnWriteArrayList<>();

	private final SoyProviderCapabilityBundleRegister
		_soyProviderCapabilityBundleRegister;
	private volatile SoyTemplateResource _soyTemplateResource;
	private final SoyTemplateResourceFactory _soyTemplateResourceFactory;
	private final SoyTofuCacheHandler _soyTofuCacheHandler;

}