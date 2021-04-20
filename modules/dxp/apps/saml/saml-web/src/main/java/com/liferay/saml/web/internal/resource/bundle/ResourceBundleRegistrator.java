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

package com.liferay.saml.web.internal.resource.bundle;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
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
public class ResourceBundleRegistrator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			ResourceBundle resourceBundle =
				_resourceBundleLoaderWeb.loadResourceBundle(locale);

			ServiceRegistration<ResourceBundle> serviceRegistration =
				bundleContext.registerService(
					ResourceBundle.class, resourceBundle,
					new HashMapDictionary<>(
						HashMapBuilder.put(
							"language.id", LocaleUtil.toLanguageId(locale)
						).build()));

			_serviceRegistrations.add(serviceRegistration);
		}
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<ResourceBundle> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.saml.api)"
	)
	private ResourceBundleLoader _resourceBundleLoaderAPI;

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.saml.lang)"
	)
	private ResourceBundleLoader _resourceBundleLoaderLang;

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.saml.web)"
	)
	private ResourceBundleLoader _resourceBundleLoaderWeb;

	private final List<ServiceRegistration<ResourceBundle>>
		_serviceRegistrations = new ArrayList<>();

}