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

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.saml.opensaml.integration.internal.velocity.VelocityEngineFactory;
import com.liferay.saml.runtime.SamlException;

import java.util.Map;

import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.apache.http.client.HttpClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = SamlBindingProvider.class)
public class SamlBindingProvider {

	public SamlBinding getSamlBinding(String communicationProfileId)
		throws PortalException {

		Map<String, SamlBinding> samlBindings =
			_samlBingsDCLSingleton.getSingleton(this::_createSamlBindings);

		SamlBinding samlBinding = samlBindings.get(communicationProfileId);

		if (samlBinding != null) {
			return samlBinding;
		}

		throw new SamlException(
			"Unsupported SAML binding " + communicationProfileId);
	}

	private Map<String, SamlBinding> _createSamlBindings() {
		HttpPostBinding httpPostBinding = new HttpPostBinding(
			_parserPool, VelocityEngineFactory.getVelocityEngine());

		HttpRedirectBinding httpRedirectBinding = new HttpRedirectBinding(
			_parserPool);

		HttpSoap11Binding httpSoap11Binding = new HttpSoap11Binding(
			_parserPool, _httpClient);

		return HashMapBuilder.<String, SamlBinding>put(
			httpPostBinding.getCommunicationProfileId(), httpPostBinding
		).put(
			httpRedirectBinding.getCommunicationProfileId(), httpRedirectBinding
		).put(
			httpSoap11Binding.getCommunicationProfileId(), httpSoap11Binding
		).build();
	}

	@Reference
	private HttpClient _httpClient;

	@Reference
	private ParserPool _parserPool;

	private final DCLSingleton<Map<String, SamlBinding>>
		_samlBingsDCLSingleton = new DCLSingleton<>();

}