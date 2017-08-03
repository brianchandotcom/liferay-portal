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

package com.liferay.saml.runtime.configuration;

import com.liferay.saml.runtime.credential.KeyStoreManager;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true)
public class SamlKeystoreManagerMetaTypeRegistrator {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		ServicesDropDownMetaTypeProvider servicesDropDownMetaTypeProvider =
			new ServicesDropDownMetaTypeProvider(
				bundleContext, KeyStoreManager.class.getName(),
				"com.liferay.saml.runtime.configuration." +
					"SamlKeyStoreManagerConfiguration",
				"saml-keystore-manager-configuration-name", null,
				"KeyStoreManager.target", "KeyStoreManager.target",
				"saml-keystore-manager-description");

		_metaTypeRegistrator = new MetaTypeVirtualBundleRegistrator(
			bundleContext, servicesDropDownMetaTypeProvider);

		_metaTypeRegistrator.importPackage(
			"com.liferay.saml.runtime.configuration");
		_metaTypeRegistrator.requireLanguageKeys(
			"(bundle.symbolic.name=com.liferay.saml.api)");

		_metaTypeRegistrator.open();
	}

	@Deactivate
	protected void deactivate() {
		_metaTypeRegistrator.close();
	}

	private MetaTypeVirtualBundleRegistrator _metaTypeRegistrator;

}