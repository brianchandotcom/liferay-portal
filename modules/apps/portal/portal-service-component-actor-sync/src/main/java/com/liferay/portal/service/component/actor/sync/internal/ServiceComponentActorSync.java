/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.component.actor.sync.internal;

import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;

import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;

/**
 * @author Tina Tian
 */
@Component(service = {})
public class ServiceComponentActorSync {

	@Activate
	protected void activate() {
		DependencyManagerSyncUtil.registerSyncCallable(
			() -> {
				ComponentDescriptionDTO componentDescriptionDTO =
					_serviceComponentRuntime.getComponentDescriptionDTO(
						FrameworkUtil.getBundle(
							ServiceComponentActorSync.class),
						ServiceComponentActorSync.class.getName());

				Promise<Void> promise = 
					_serviceComponentRuntime.disableComponent(
						componentDescriptionDTO);

				promise.getValue();

				return null;
			});
	}

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

}