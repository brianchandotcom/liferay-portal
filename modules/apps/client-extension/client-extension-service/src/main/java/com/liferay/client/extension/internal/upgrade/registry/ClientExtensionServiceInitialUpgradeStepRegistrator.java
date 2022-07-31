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

package com.liferay.client.extension.internal.upgrade.registry;

import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = true, immediate = true, service = UpgradeStepRegistrator.class
)
public class ClientExtensionServiceInitialUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialUpgradeSteps(
			new UpgradeProcess() {

				@Override
				protected void doUpgrade() throws Exception {
					Release remoteAppRelease =
						_releaseLocalService.fetchRelease(
							"com.liferay.remote.app.service");

					if (remoteAppRelease != null) {
						Release clientExtensionRelease =
							_releaseLocalService.fetchRelease(
								"com.liferay.client.extension.service");

						clientExtensionRelease.setSchemaVersion(
							remoteAppRelease.getSchemaVersion());

						_releaseLocalService.updateRelease(
							clientExtensionRelease);

						_releaseLocalService.deleteRelease(remoteAppRelease);
					}

					_componentContext.enableComponent(
						ClientExtensionUpgradeStepRegistrator.class.getName());
				}

			});
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;
	}

	private ComponentContext _componentContext;

	@Reference
	private ReleaseLocalService _releaseLocalService;

}