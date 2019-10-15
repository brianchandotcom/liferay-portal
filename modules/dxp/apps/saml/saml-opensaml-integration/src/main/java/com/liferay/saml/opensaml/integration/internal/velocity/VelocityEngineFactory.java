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

package com.liferay.saml.opensaml.integration.internal.velocity;

import com.liferay.portal.kernel.util.StringPool;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.Log4JLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = VelocityEngineFactory.class)
public class VelocityEngineFactory {

	public VelocityEngine getVelocityEngine() {
		return _velocityEngine;
	}

	public VelocityEngine getVelocityEngine(ClassLoader classLoader) {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(classLoader);

			VelocityEngine velocityEngine = new VelocityEngine();

			velocityEngine.setProperty(
				Log4JLogChute.RUNTIME_LOG_LOG4J_LOGGER,
				VelocityEngineFactory.class.getName());
			velocityEngine.setProperty(
				RuntimeConstants.ENCODING_DEFAULT, StringPool.UTF8);
			velocityEngine.setProperty(
				RuntimeConstants.OUTPUT_ENCODING, StringPool.UTF8);
			velocityEngine.setProperty(
				RuntimeConstants.RESOURCE_LOADER, "classpath");
			velocityEngine.setProperty(
				RuntimeConstants.RUNTIME_LOG_LOGSYSTEM + ".log4j.category",
				"org.apache.velocity");
			velocityEngine.setProperty(
				RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
				Log4JLogChute.class.getName());
			velocityEngine.setProperty(
				"classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());

			velocityEngine.init();

			return velocityEngine;
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to initialize Velocity engine", e);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> propertiesMap) {

		Bundle bundle = bundleContext.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		_velocityEngine = getVelocityEngine(bundleWiring.getClassLoader());
	}

	private VelocityEngine _velocityEngine;

}