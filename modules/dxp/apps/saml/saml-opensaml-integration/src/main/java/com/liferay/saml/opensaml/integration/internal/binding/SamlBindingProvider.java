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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.saml.opensaml.integration.internal.velocity.VelocityEngineFactory;
import com.liferay.saml.runtime.SamlException;

import java.util.HashMap;
import java.util.Map;

import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.apache.http.client.HttpClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = SamlBindingProvider.class)
public class SamlBindingProvider {

	public SamlBinding getSamlBinding(String communicationProfileId)
		throws PortalException {

		SamlBinding samlBinding = _samlBindings.get(communicationProfileId);

		if (samlBinding != null) {
			return samlBinding;
		}

		throw new SamlException(
			"Unsupported SAML binding " + communicationProfileId);
	}

	@Activate
	protected void activate() {
		HttpPostBinding httpPostBinding = new HttpPostBinding(
			_parserPool, VelocityEngineFactory.getVelocityEngine());

		_samlBindings.put(
			httpPostBinding.getCommunicationProfileId(), httpPostBinding);

		HttpRedirectBinding httpRedirectBinding = new HttpRedirectBinding(
			_parserPool);

		_samlBindings.put(
			httpRedirectBinding.getCommunicationProfileId(),
			httpRedirectBinding);

		HttpSoap11Binding httpSoap11Binding = new HttpSoap11Binding(
			_parserPool, _httpClient);

		_samlBindings.put(
			httpSoap11Binding.getCommunicationProfileId(), httpSoap11Binding);
	}

	@Reference
	private HttpClient _httpClient;

	@Reference
	private ParserPool _parserPool;

	private final Map<String, SamlBinding> _samlBindings = new HashMap<>();

}