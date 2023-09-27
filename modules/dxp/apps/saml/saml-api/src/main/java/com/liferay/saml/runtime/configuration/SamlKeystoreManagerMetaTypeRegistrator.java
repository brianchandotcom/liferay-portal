/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = {})
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

		_metaTypeRegistrator.open();
	}

	@Deactivate
	protected void deactivate() {
		_metaTypeRegistrator.close();
	}

	private MetaTypeVirtualBundleRegistrator _metaTypeRegistrator;

}