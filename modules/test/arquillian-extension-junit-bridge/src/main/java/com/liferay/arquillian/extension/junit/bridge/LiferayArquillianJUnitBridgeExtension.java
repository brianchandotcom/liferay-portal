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

package com.liferay.arquillian.extension.junit.bridge;

import com.liferay.arquillian.extension.junit.bridge.container.ContainerDeploymentContextHandler;
import com.liferay.arquillian.extension.junit.bridge.context.ContainerContextImpl;
import com.liferay.arquillian.extension.junit.bridge.context.DeploymentContextImpl;
import com.liferay.arquillian.extension.junit.bridge.event.controller.ContainerEventController;
import com.liferay.arquillian.extension.junit.bridge.protocol.jmx.JMXMethodExecutor;
import com.liferay.arquillian.extension.junit.bridge.remote.context.ClassContextImpl;
import com.liferay.arquillian.extension.junit.bridge.remote.context.SuiteContextImpl;
import com.liferay.arquillian.extension.junit.bridge.remote.context.TestContextImpl;
import com.liferay.arquillian.extension.junit.bridge.remote.context.handler.TestContextHandler;
import com.liferay.arquillian.extension.junit.bridge.remote.executor.LocalTestExecutor;
import com.liferay.arquillian.extension.junit.bridge.remote.observer.JUnitBridgeObserver;

import java.net.URL;

import java.util.Arrays;
import java.util.List;

import org.jboss.arquillian.core.spi.context.Context;

/**
 * @author Shuyang Zhou
 */
public class LiferayArquillianJUnitBridgeExtension {

	public static List<Class<? extends Context>> getContexts() {
		if (_isClient()) {
			return Arrays.asList(
				ClassContextImpl.class, ContainerContextImpl.class,
				DeploymentContextImpl.class, SuiteContextImpl.class,
				TestContextImpl.class
			);
		}

		return Arrays.asList(
			ClassContextImpl.class, SuiteContextImpl.class,
			TestContextImpl.class
		);
	}

	public static List<Class<?>> getObservers() {
		if (_isClient()) {
			return Arrays.asList(
				ContainerDeploymentContextHandler.class,
				ContainerEventController.class, JMXMethodExecutor.class,
				TestContextHandler.class
			);
		}

		return Arrays.asList(
			JUnitBridgeObserver.class, LocalTestExecutor.class,
			TestContextHandler.class
		);
	}

	private static boolean _isClient() {
		URL url = LiferayArquillianJUnitBridgeExtension.class.getResource(
			"/arquillian.remote.marker");

		if (url == null) {
			return true;
		}

		return false;
	}

}