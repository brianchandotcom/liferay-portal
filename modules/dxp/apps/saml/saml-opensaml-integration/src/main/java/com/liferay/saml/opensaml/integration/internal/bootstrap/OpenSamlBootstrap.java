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

package com.liferay.saml.opensaml.integration.internal.bootstrap;

import java.util.HashMap;
import java.util.Map;

import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.XMLParserException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = OpenSamlBootstrap.class)
public class OpenSamlBootstrap extends DefaultBootstrap {

	public static synchronized void bootstrap() throws ConfigurationException {
		initializeXMLSecurity();

		initializeXMLTooling(_XML_TOOLING_CONFIGS);

		initializeArtifactBuilderFactories();

		initializeGlobalSecurityConfiguration();

		initializeParserPool();

		initializeESAPI();
	}

	@Activate
	public synchronized void activate(BundleContext bundleContext)
		throws ConfigurationException {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		try {
			Bundle bundle = bundleContext.getBundle();

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			currentThread.setContextClassLoader(bundleWiring.getClassLoader());

			bootstrap();

			_parserPoolServiceRegistration = bundleContext.registerService(
				ParserPool.class, Configuration.getParserPool(), null);
		}
		finally {
			currentThread.setContextClassLoader(classLoader);
		}
	}

	protected static void initializeParserPool() throws ConfigurationException {
		BasicParserPool parserPool = new BasicParserPool();

		Map<String, Boolean> builderFeatures = new HashMap<>();

		builderFeatures.put(
			"http://apache.org/xml/features/disallow-doctype-decl",
			Boolean.TRUE);
		builderFeatures.put(
			"http://javax.xml.XMLConstants/feature/secure-processing",
			Boolean.TRUE);
		builderFeatures.put(
			"http://xml.org/sax/features/external-general-entities",
			Boolean.FALSE);
		builderFeatures.put(
			"http://xml.org/sax/features/external-parameter-entities",
			Boolean.FALSE);

		parserPool.setBuilderFeatures(builderFeatures);

		parserPool.setDTDValidating(false);
		parserPool.setExpandEntityReferences(false);
		parserPool.setMaxPoolSize(50);

		try {
			parserPool.getBuilder();
		}
		catch (XMLParserException xmlpe) {
			throw new ConfigurationException(
				"Unable to initialize parser pool: " + xmlpe.getMessage(),
				xmlpe);
		}

		Configuration.setParserPool(parserPool);
	}

	@Deactivate
	protected void deactivate() {
		if (_parserPoolServiceRegistration != null) {
			_parserPoolServiceRegistration.unregister();
		}
	}

	private static final String[] _XML_TOOLING_CONFIGS = {
		"/default-config.xml", "/encryption-config.xml",
		"/encryption-validation-config.xml", "/saml1-metadata-config.xml",
		"/saml2-assertion-config.xml",
		"/saml2-assertion-delegation-restriction-config.xml",
		"/saml2-core-validation-config.xml", "/saml2-metadata-config.xml",
		"/saml2-metadata-idp-discovery-config.xml",
		"/saml2-metadata-query-config.xml",
		"/saml2-metadata-validation-config.xml", "/saml2-protocol-config.xml",
		"/saml2-protocol-thirdparty-config.xml", "/schema-config.xml",
		"/signature-config.xml", "/signature-validation-config.xml",
		"/soap11-config.xml"
	};

	private ServiceRegistration<ParserPool> _parserPoolServiceRegistration;

}