/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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