/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.velocity;

import com.liferay.petra.string.StringPool;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.Log4JLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * @author Mika Koivisto
 */
public class VelocityEngineFactory {

	public static VelocityEngine getVelocityEngine() {
		return _velocityEngine;
	}

	private static final VelocityEngine _velocityEngine;

	static {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				VelocityEngineFactory.class.getClassLoader());

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

			_velocityEngine = velocityEngine;
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

}