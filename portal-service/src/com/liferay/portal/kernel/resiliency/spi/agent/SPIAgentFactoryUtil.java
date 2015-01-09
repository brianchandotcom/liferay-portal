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

package com.liferay.portal.kernel.resiliency.spi.agent;

import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;

import java.lang.reflect.Constructor;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class SPIAgentFactoryUtil {

	public static SPIAgent createSPIAgent(
		SPIConfiguration spiConfiguration,
		RegistrationReference registrationReference) {

		String spiAgentClassName = spiConfiguration.getSPIAgentClassName();

		if (spiAgentClassName == null) {
			throw new NullPointerException("Missing SPI agent class name");
		}

		Class<? extends SPIAgent> spiAgentClass = _spiAgentClasses.get(
			spiAgentClassName);

		if (spiAgentClass == null) {
			throw new IllegalArgumentException(
				"Unkown SPI agent class name " + spiAgentClassName);
		}

		try {
			Constructor<? extends SPIAgent> constructor =
				spiAgentClass.getConstructor(
					SPIConfiguration.class, RegistrationReference.class);

			return constructor.newInstance(
				spiConfiguration, registrationReference);
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to instantiate " + spiAgentClass, e);
		}
	}

	public static Set<String> getSPIAgentClassNames() {
		return _spiAgentClasses.keySet();
	}

	public static Class<? extends SPIAgent> registerSPIAgentClass(
		Class<? extends SPIAgent> spiAgentClass) {

		return _spiAgentClasses.put(spiAgentClass.getName(), spiAgentClass);
	}

	public static Class<? extends SPIAgent> unregisterSPIAgentClass(
		String spiAgentClassName) {

		return _spiAgentClasses.remove(spiAgentClassName);
	}

	public void setSPIAgentClasses(Set<String> spiAgentClassNames)
		throws ClassNotFoundException {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		for (String spiAgentClassName : spiAgentClassNames) {
			Class<? extends SPIAgent> agentClass =
				(Class<? extends SPIAgent>)classLoader.loadClass(
					spiAgentClassName);

			_spiAgentClasses.put(spiAgentClassName, agentClass);
		}
	}

	private static final Map<String, Class<? extends SPIAgent>>
		_spiAgentClasses = new ConcurrentHashMap<>();

}