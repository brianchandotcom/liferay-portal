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

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.saml.opensaml.integration.internal.servlet.profile.BaseProfile;
import com.liferay.saml.opensaml.integration.internal.velocity.VelocityEngineFactory;

import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.apache.http.client.HttpClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = {})
public class HttpBindingsRegistrator {

	@Activate
	protected void activate() {
		SamlBinding httpPostBinding = new HttpPostBinding(
			_parserPool, _velocityEngineFactory.getVelocityEngine());

		SamlBinding httpRedirectBinding = new HttpRedirectBinding(_parserPool);

		SamlBinding httpSoap11Binding = new HttpSoap11Binding(
			_parserPool, _httpClient);

		BaseProfile.setSamlBindings(
			HashMapBuilder.put(
				httpPostBinding.getCommunicationProfileId(), httpPostBinding
			).put(
				httpRedirectBinding.getCommunicationProfileId(),
				httpRedirectBinding
			).put(
				httpSoap11Binding.getCommunicationProfileId(), httpSoap11Binding
			).build());
	}

	@Reference
	private HttpClient _httpClient;

	@Reference
	private ParserPool _parserPool;

	@Reference
	private VelocityEngineFactory _velocityEngineFactory;

}