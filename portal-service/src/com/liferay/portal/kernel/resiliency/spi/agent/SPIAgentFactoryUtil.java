/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

		String agentClassName = spiConfiguration.getAgentClassName();

		if (agentClassName == null) {
			throw new NullPointerException("Missing agentClassName");
		}

		Class<? extends SPIAgent> agentClass = _spiAgentClassMap.get(
			agentClassName);

		if (agentClass == null) {
			throw new IllegalArgumentException(
				"Unkown SPIAgent class name " + agentClassName);
		}

		try {
			Constructor<? extends SPIAgent> constructor =
				agentClass.getConstructor(
					SPIConfiguration.class, RegistrationReference.class);

			return constructor.newInstance(
				spiConfiguration, registrationReference);
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to create instance of class " + agentClass, e);
		}
	}

	public static Set<String> getAgentClassNames() {
		return _spiAgentClassMap.keySet();
	}

	public static Class<? extends SPIAgent> registerAgentClass(
		Class<? extends SPIAgent> agentClass) {

		return _spiAgentClassMap.put(agentClass.getName(), agentClass);
	}

	public static Class<? extends SPIAgent> unregisterAgentClass(
		String agentClassName) {

		return _spiAgentClassMap.remove(agentClassName);
	}

	public void setAgentClasses(Set<String> agentClassNames)
		throws ClassNotFoundException {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		for (String agentClassName : agentClassNames) {
			Class<? extends SPIAgent> agentClass =
				(Class<? extends SPIAgent>)classLoader.loadClass(
					agentClassName);

			_spiAgentClassMap.put(agentClassName, agentClass);
		}
	}

	private static final Map<String, Class<? extends SPIAgent>>
		_spiAgentClassMap =
			new ConcurrentHashMap<String, Class<? extends SPIAgent>>();

}