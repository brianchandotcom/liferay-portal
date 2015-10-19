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

import com.liferay.arquillian.extension.junit.bridge.deployment.JUnitBridgeAuxiliaryArchiveAppender;
import com.liferay.arquillian.extension.junit.bridge.observer.JUnitBridgeObserver;

import java.net.URL;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.junit.container.JUnitDeploymentAppender;

/**
 * @author Shuyang Zhou
 */
public class LiferayArquillianJUnitBridgeExtension
	implements RemoteLoadableExtension {

	@Override
	public void register(ExtensionBuilder extensionBuilder) {
		URL url = LiferayArquillianJUnitBridgeExtension.class.getResource(
			"/arquillian.remote.marker");

		if (url == null) {
			extensionBuilder.override(
				AuxiliaryArchiveAppender.class, JUnitDeploymentAppender.class,
				JUnitBridgeAuxiliaryArchiveAppender.class);
		}
		else {
			extensionBuilder.observer(JUnitBridgeObserver.class);
		}
	}

}