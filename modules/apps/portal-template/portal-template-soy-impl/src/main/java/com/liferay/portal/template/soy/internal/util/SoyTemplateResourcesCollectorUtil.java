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

package com.liferay.portal.template.soy.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.SetUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Marcellus Tavares
 */
public class SoyTemplateResourcesCollectorUtil {

	public static void collectBundleTemplateResources(
			Bundle bundle, String templatePath,
			List<TemplateResource> templateResources,
			Map<Bundle, Collection<URL>> collectionURLsMap)
		throws TemplateException {

		Enumeration<URL> enumeration = _findEntries(
			bundle, templatePath, collectionURLsMap);

		if (enumeration == null) {
			return;
		}

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String templateId = _getTemplateId(bundle.getBundleId(), url);

			try {
				TemplateResource templateResource = _getTemplateResource(
					templateId, url);

				templateResources.add(templateResource);
			}
			catch (IllegalStateException illegalStateException) {
				_log.error(
					String.format(
						"{providerBundle=%s, templateId=%s}",
						bundle.getSymbolicName(), templateId));

				throw illegalStateException;
			}
		}
	}

	public static List<TemplateResource> getTemplateResources(
			Bundle bundle, String templatePath)
		throws TemplateException {

		List<TemplateResource> templateResources = new ArrayList<>();

		collectBundleTemplateResources(
			bundle, templatePath, templateResources, null);

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		for (BundleWire bundleWire : bundleWiring.getRequiredWires("soy")) {
			BundleRevision bundleRevision = bundleWire.getProvider();

			Bundle providerBundle = bundleRevision.getBundle();

			collectBundleTemplateResources(
				providerBundle, StringPool.SLASH, templateResources, null);
		}

		return templateResources;
	}

	private static Enumeration<URL> _findEntries(
		Bundle bundle, String templatePath,
		Map<Bundle, Collection<URL>> collectionURLsMap) {

		if (collectionURLsMap != null) {
			Collection<URL> collection = collectionURLsMap.get(bundle);

			if (collection != null) {
				return Collections.enumeration(collection);
			}
		}

		Enumeration<URL> enumeration = bundle.findEntries(
			templatePath, _SOY_FILE_EXTENSION, true);

		if (enumeration == null) {
			if (collectionURLsMap != null) {
				collectionURLsMap.put(bundle, Collections.emptySet());
			}

			return null;
		}

		if (collectionURLsMap != null) {
			Collection<URL> collection = SetUtil.fromEnumeration(enumeration);

			collectionURLsMap.put(bundle, collection);

			return Collections.enumeration(collection);
		}

		return enumeration;
	}

	private static String _getTemplateId(long bundleId, URL url) {
		return String.valueOf(
			bundleId
		).concat(
			TemplateConstants.BUNDLE_SEPARATOR
		).concat(
			url.getPath()
		);
	}

	private static TemplateResource _getTemplateResource(
			String templateId, URL url)
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
		SoyTemplateResourcesCollectorUtil.class);

}