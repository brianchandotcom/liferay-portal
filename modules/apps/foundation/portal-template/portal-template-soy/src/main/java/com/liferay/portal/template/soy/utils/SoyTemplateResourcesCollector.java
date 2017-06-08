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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.StringPool;

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

			try {
				TemplateResource templateResource = _getTemplateResource(
					templateId, url);

				templateResources.add(templateResource);
			}
			catch (TemplateException te) {
				throw new IllegalStateException(
					"Unable to collect template reosurces for bundle " +
						bundle.getBundleId(),
					te);
			}
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

				TemplateResource templateResource = null;

				try {
					templateResource = _getTemplateResource(templateId, url);
				}
				catch (IllegalStateException ise) {
					_log.error(
						String.format(
							"{providerBundle=%s, templateId=%s}",
							providerBundle.getSymbolicName(), templateId));

					throw ise;
				}

				templateResources.add(templateResource);
			}
		}
	}

	protected Bundle getProviderBundle(BundleWire bundleWire) {
		BundleRevision bundleRevision = bundleWire.getProvider();

		return bundleRevision.getBundle();
	}

	protected List<URL> getSoyResourceURLs(Bundle bundle, String templatePath) {
		int bundleState = bundle.getState();

		if (bundleState == Bundle.UNINSTALLED) {
			return Collections.emptyList();
		}

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

	private TemplateResource _getTemplateResource(String templateId, URL url)
		throws TemplateException {

		TemplateResource templateResource;

		if (TemplateResourceLoaderUtil.hasTemplateResourceLoader(
				TemplateConstants.LANG_TYPE_SOY)) {

			templateResource = TemplateResourceLoaderUtil.getTemplateResource(
				TemplateConstants.LANG_TYPE_SOY, templateId);
		}
		else {
			templateResource = new URLTemplateResource(templateId, url);
		}

		return templateResource;
	}

	private static final String _SOY_FILE_EXTENSION = "*.soy";

	private static final Log _log = LogFactoryUtil.getLog(
		SoyTemplateResourcesCollector.class);

	private final Bundle _bundle;
	private final String _templatePath;

}