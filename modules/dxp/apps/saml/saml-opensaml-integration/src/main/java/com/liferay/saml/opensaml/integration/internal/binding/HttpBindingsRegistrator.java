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

package com.liferay.saml.opensaml.integration.internal.binding;

import com.liferay.saml.opensaml.integration.SamlBinding;
import com.liferay.saml.opensaml.integration.internal.velocity.VelocityEngineFactory;

import java.util.Hashtable;
import java.util.Map;

import org.apache.http.client.HttpClient;

import org.opensaml.xml.parse.ParserPool;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = HttpBindingsRegistrator.class)
public class HttpBindingsRegistrator {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> propertiesMap) {

		Hashtable<String, Object> properties = new Hashtable<>(propertiesMap);

		HttpPostBinding httpPostBinding = new HttpPostBinding(
			_parserPool, _velocityEngineFactory.getVelocityEngine());

		HttpRedirectBinding httpRedirectBinding = new HttpRedirectBinding(
			_parserPool);

		HttpSoap11Binding httpSoap11Binding = new HttpSoap11Binding(
			_parserPool, _httpClient);

		_samlHttpPostBindingServiceRegistration = bundleContext.registerService(
			SamlBinding.class, httpPostBinding, properties);

		_samlHttpRedirectBindingServiceRegistration =
			bundleContext.registerService(
				SamlBinding.class, httpRedirectBinding, properties);

		_samlHttpSoap11BindingServiceRegistration =
			bundleContext.registerService(
				SamlBinding.class, httpSoap11Binding, properties);
	}

	@Deactivate
	protected void deactivate() {
		_samlHttpPostBindingServiceRegistration.unregister();

		_samlHttpRedirectBindingServiceRegistration.unregister();

		_samlHttpSoap11BindingServiceRegistration.unregister();
	}

	@Reference
	private HttpClient _httpClient;

	@Reference
	private ParserPool _parserPool;

	private ServiceRegistration<SamlBinding>
		_samlHttpPostBindingServiceRegistration;
	private ServiceRegistration<SamlBinding>
		_samlHttpRedirectBindingServiceRegistration;
	private ServiceRegistration<SamlBinding>
		_samlHttpSoap11BindingServiceRegistration;

	@Reference
	private VelocityEngineFactory _velocityEngineFactory;

}