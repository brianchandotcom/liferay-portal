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

package com.liferay.site.initializer.extender.internal;

import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.site.initializer.SiteInitializer;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Preston Crary
 */
public class SiteInitializerRegistrar {

	public SiteInitializerRegistrar(
		Bundle bundle, BundleContext bundleContext,
		DocumentResource.Factory documentResourceFactory,
		FragmentsImporter fragmentsImporter, JSONFactory jsonFactory,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		UserLocalService userLocalService) {

		_bundle = bundle;
		_bundleContext = bundleContext;
		_documentResourceFactory = documentResourceFactory;
		_fragmentsImporter = fragmentsImporter;
		_jsonFactory = jsonFactory;
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;
		_taxonomyVocabularyResourceFactory = taxonomyVocabularyResourceFactory;
		_userLocalService = userLocalService;
	}

	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected void start() {
		_serviceRegistration = _bundleContext.registerService(
			SiteInitializer.class,
			new BundleSiteInitializer(
				_bundle, _documentResourceFactory, _fragmentsImporter,
				_jsonFactory, _objectDefinitionResourceFactory, _servletContext,
				_taxonomyVocabularyResourceFactory, _userLocalService),
			MapUtil.singletonDictionary(
				"site.initializer.key", _bundle.getSymbolicName()));
	}

	protected void stop() {
		_serviceRegistration.unregister();
	}

	private final Bundle _bundle;
	private final BundleContext _bundleContext;
	private final DocumentResource.Factory _documentResourceFactory;
	private final FragmentsImporter _fragmentsImporter;
	private final JSONFactory _jsonFactory;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private ServiceRegistration<?> _serviceRegistration;
	private ServletContext _servletContext;
	private final TaxonomyVocabularyResource.Factory
		_taxonomyVocabularyResourceFactory;
	private final UserLocalService _userLocalService;

}