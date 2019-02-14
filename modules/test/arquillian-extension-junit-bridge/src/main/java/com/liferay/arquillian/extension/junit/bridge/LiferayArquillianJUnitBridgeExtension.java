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

import com.liferay.arquillian.extension.junit.bridge.event.controller.ContainerEventController;
import com.liferay.arquillian.extension.junit.bridge.protocol.jmx.JMXMethodExecutor;
import com.liferay.arquillian.extension.junit.bridge.remote.executor.LocalTestExecutor;
import com.liferay.arquillian.extension.junit.bridge.remote.observer.JUnitBridgeObserver;

import java.net.URL;

import java.util.Arrays;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class LiferayArquillianJUnitBridgeExtension {

	public static List<Class<?>> getObservers() {
		if (_isClient()) {
			return Arrays.asList(
				ContainerEventController.class, JMXMethodExecutor.class
			);
		}

		return Arrays.asList(
			JUnitBridgeObserver.class, LocalTestExecutor.class
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