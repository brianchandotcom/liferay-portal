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

package com.liferay.saml.web.internal;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marta Medio
 */
@Component(immediate = true, service = {})
public class SamlLangRegistrator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			HashMapDictionary<String, String> dictionary =
				new HashMapDictionary<>(
					HashMapBuilder.put(
						"language.id", LocaleUtil.toLanguageId(locale)
					).build());

			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(locale);

			ServiceRegistration<ResourceBundle> serviceRegistration =
				bundleContext.registerService(
					ResourceBundle.class, resourceBundle, dictionary);

			_serviceRegistrations.add(serviceRegistration);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistrations != null) {
			for (ServiceRegistration<ResourceBundle> serviceRegistration :
					_serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.saml.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.saml.api)"
	)
	private ResourceBundleLoader _resourceBundleLoaderApi;

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.saml.lang)"
	)
	private ResourceBundleLoader _resourceBundleLoaderLang;

	private final List<ServiceRegistration<ResourceBundle>>
		_serviceRegistrations = new ArrayList<>();

}