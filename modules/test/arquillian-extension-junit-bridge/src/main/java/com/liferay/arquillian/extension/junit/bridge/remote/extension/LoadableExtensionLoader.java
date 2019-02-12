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

package com.liferay.arquillian.extension.junit.bridge.remote.extension;

import com.liferay.arquillian.extension.junit.bridge.LiferayArquillianJUnitBridgeExtension;

import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.core.spi.context.Context;
import org.jboss.arquillian.core.spi.event.ManagerProcessing;

/**
 * @author Matthew Tambara
 */
public class LoadableExtensionLoader {

	public void load(@Observes final ManagerProcessing managerProcessing) {
		LoadableExtension loadableExtension =
			new LiferayArquillianJUnitBridgeExtension();

		loadableExtension.register(
			new LoadableExtension.ExtensionBuilder() {

				@Override
				public LoadableExtension.ExtensionBuilder context(
					Class<? extends Context> context) {

					managerProcessing.context(context);

					return this;
				}

				@Override
				public LoadableExtension.ExtensionBuilder observer(
					Class<?> handler) {

					managerProcessing.observer(handler);

					return this;
				}

				@Override
				public <T> LoadableExtension.ExtensionBuilder override(
					Class<T> service, Class<? extends T> oldServiceImpl,
					Class<? extends T> newServiceImpl) {

					return this;
				}

				@Override
				public <T> LoadableExtension.ExtensionBuilder service(
					Class<T> service, Class<? extends T> impl) {

					return this;
				}

			});
	}

}