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

package com.liferay.saml.opensaml.integration.internal.bootstrap;

import com.liferay.petra.concurrent.DCLSingleton;

import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = ParserPoolProvider.class)
public class ParserPoolProvider {

	public ParserPool getParserPool() {
		return _parserPoolDCLSingleton.getSingleton(this::_createParserPool);
	}

	private ParserPool _createParserPool() {
		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		try {
			Bundle bundle = FrameworkUtil.getBundle(ParserPoolProvider.class);

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			currentThread.setContextClassLoader(bundleWiring.getClassLoader());

			OpenSamlBootstrap.bootstrap();

			XMLObjectProviderRegistry xmlObjectProviderRegistry =
				ConfigurationService.get(XMLObjectProviderRegistry.class);

			return xmlObjectProviderRegistry.getParserPool();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		finally {
			currentThread.setContextClassLoader(classLoader);
		}
	}

	private final DCLSingleton<ParserPool> _parserPoolDCLSingleton =
		new DCLSingleton<>();

}