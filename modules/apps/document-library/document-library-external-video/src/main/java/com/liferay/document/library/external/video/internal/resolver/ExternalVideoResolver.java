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

package com.liferay.document.library.external.video.internal.resolver;

import com.liferay.document.library.external.video.internal.ExternalVideo;
import com.liferay.document.library.external.video.internal.provider.ExternalVideoProvider;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = ExternalVideoResolver.class)
public class ExternalVideoResolver {

	public ExternalVideo resolve(String url) {
		for (ExternalVideoProvider externalVideoProvider :
				_externalVideoProviders) {

			ExternalVideo externalVideo =
				externalVideoProvider.getExternalVideo(url);

			if (externalVideo != null) {
				return externalVideo;
			}
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_externalVideoProviders = ServiceTrackerListFactory.open(
			bundleContext, ExternalVideoProvider.class);
	}

	@Deactivate
	protected void deactivate() {
		_externalVideoProviders.close();
	}

	private ServiceTrackerList<ExternalVideoProvider, ExternalVideoProvider>
		_externalVideoProviders;

}