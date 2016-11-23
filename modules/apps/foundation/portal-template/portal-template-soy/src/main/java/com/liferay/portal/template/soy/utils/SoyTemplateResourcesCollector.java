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

package com.liferay.portal.template.soy.utils;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.soy.internal.SoyTemplateResourcesTracker;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Marcellus Tavares
 */
public class SoyTemplateResourcesCollector {

	public SoyTemplateResourcesCollector(Bundle bundle, String templatePath) {
		_bundle = bundle;
		_templatePath = templatePath;
	}

	public List<TemplateResource> getAllTemplateResources()
		throws TemplateException {

		List<TemplateResource> templateResources = new ArrayList<>();

		for (Bundle bundle : SoyTemplateResourcesTracker.getBundles()) {
			collectBundleTemplateResources(bundle, templateResources);
		}

		return templateResources;
	}

	public List<TemplateResource> getTemplateResources()
		throws TemplateException {

		List<TemplateResource> templateResources = new ArrayList<>();

		collectBundleTemplateResources(templateResources);
		collectProviderBundlesTemplateResources(templateResources);

		return templateResources;
	}

	protected void collectBundleTemplateResources(
		Bundle bundle, List<TemplateResource> templateResources) {

		List<URL> urls = getSoyResourceURLs(bundle, _templatePath);

		for (URL url : urls) {
			String templateId = getTemplateId(bundle.getBundleId(), url);

			templateResources.add(new URLTemplateResource(templateId, url));
		}
	}

	protected void collectBundleTemplateResources(
		List<TemplateResource> templateResources) {

		collectBundleTemplateResources(_bundle, templateResources);
	}

	protected void collectProviderBundlesTemplateResources(
			List<TemplateResource> templateResources)
		throws TemplateException {

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		for (BundleWire bundleWire : bundleWiring.getRequiredWires("soy")) {
			Bundle providerBundle = getProviderBundle(bundleWire);

			List<URL> urls = getSoyResourceURLs(
				providerBundle, StringPool.SLASH);

			for (URL url : urls) {
				String templateId = getTemplateId(
					providerBundle.getBundleId(), url);

				TemplateResource templateResource =
					TemplateResourceLoaderUtil.getTemplateResource(
						TemplateConstants.LANG_TYPE_SOY, templateId);

				templateResources.add(templateResource);
			}
		}
	}

	protected Bundle getProviderBundle(BundleWire bundleWire) {
		BundleRevision bundleRevision = bundleWire.getProvider();

		return bundleRevision.getBundle();
	}

	protected List<URL> getSoyResourceURLs(Bundle bundle, String templatePath) {
		Enumeration<URL> urls = bundle.findEntries(
			"META-INF/resources" + templatePath, _SOY_FILE_EXTENSION, true);

		if (urls == null) {
			return Collections.emptyList();
		}

		return Collections.list(urls);
	}

	protected String getTemplateId(long bundleId, URL url) {
		return String.valueOf(bundleId).concat(
			TemplateConstants.BUNDLE_SEPARATOR).concat(url.getPath());
	}

	private static final String _SOY_FILE_EXTENSION = "*.soy";

	private final Bundle _bundle;
	private final String _templatePath;

}