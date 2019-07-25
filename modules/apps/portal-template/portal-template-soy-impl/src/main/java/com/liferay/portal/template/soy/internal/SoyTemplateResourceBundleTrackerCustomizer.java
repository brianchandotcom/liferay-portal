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
import com.liferay.portal.template.soy.internal.util.SoyTemplateResourcesCollectorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

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
public class SoyTemplateResourceBundleTrackerCustomizer
	implements BundleTrackerCustomizer<List<TemplateResource>> {

	public SoyTemplateResourceBundleTrackerCustomizer(
		SoyTofuCacheHandler soyTofuCacheHandler,
		SoyProviderCapabilityBundleRegister soyProviderCapabilityBundleRegister,
		SoyTemplateResourceFactory soyTemplateResourceFactory) {

		_soyTofuCacheHandler = soyTofuCacheHandler;
		_soyProviderCapabilityBundleRegister =
			soyProviderCapabilityBundleRegister;
		_soyTemplateResourceFactory = soyTemplateResourceFactory;
	}

	@Override
	public List<TemplateResource> addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<BundleCapability> bundleCapabilities =
			bundleWiring.getCapabilities("soy");

		if (ListUtil.isEmpty(bundleCapabilities)) {
			return null;
		}

		List<TemplateResource> templateResources = new ArrayList<>();

		try {
			SoyTemplateResourcesCollectorUtil.collectBundleTemplateResources(
				bundle, StringPool.SLASH, templateResources);

			_soyProviderCapabilityBundleRegister.register(bundle);
		}
		catch (TemplateException te) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to add template resources for bundle " +
						bundle.getBundleId(),
					te);
			}
		}

		for (BundleWire bundleWire : bundleWiring.getRequiredWires("soy")) {
			BundleRevision bundleRevision = bundleWire.getProvider();

			Bundle providerBundle = bundleRevision.getBundle();

			try {
				SoyTemplateResourcesCollectorUtil.
					collectBundleTemplateResources(
						providerBundle, StringPool.SLASH, templateResources);

				_soyProviderCapabilityBundleRegister.register(providerBundle);
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

		_templateResources.addAll(templateResources);

		_soyTemplateResource = null;

		return templateResources;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public List<TemplateResource> getAllTemplateResources() {
		return new ArrayList<>(_templateResources);
	}

	public SoyTemplateResource getSoyTemplateResource() {
		SoyTemplateResource soyTemplateResource = _soyTemplateResource;

		if (soyTemplateResource == null) {
			soyTemplateResource =
				_soyTemplateResourceFactory.createSoyTemplateResource(
					new ArrayList<>(_templateResources));
		}

		_soyTemplateResource = soyTemplateResource;

		return soyTemplateResource;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		List<TemplateResource> templateResources) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		List<TemplateResource> templateResources) {

		_templateResources.removeAll(templateResources);

		_soyTemplateResource = null;

		_soyTofuCacheHandler.removeIfAny(templateResources);

		_soyProviderCapabilityBundleRegister.unregister(bundle);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SoyTemplateResourceBundleTrackerCustomizer.class);

	private static final Set<TemplateResource> _templateResources =
		new CopyOnWriteArraySet<>();

	private final SoyProviderCapabilityBundleRegister
		_soyProviderCapabilityBundleRegister;
	private volatile SoyTemplateResource _soyTemplateResource;
	private final SoyTemplateResourceFactory _soyTemplateResourceFactory;
	private final SoyTofuCacheHandler _soyTofuCacheHandler;

}