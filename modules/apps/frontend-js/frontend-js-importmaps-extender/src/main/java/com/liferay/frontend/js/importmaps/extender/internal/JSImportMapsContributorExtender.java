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

package com.liferay.frontend.js.importmaps.extender.internal;

import com.liferay.frontend.js.importmaps.extender.JSImportMapsContributor;
import com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib.JSImportMapsExtenderTopHeadDynamicInclude;
import com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib.JSImportMapsRegistration;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = {})
public class JSImportMapsContributorExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext, JSImportMapsContributor.class,
			_serviceTrackerCustomizer);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;

		_bundleContext = null;
	}

	private BundleContext _bundleContext;

	@Reference
	private JSImportMapsExtenderTopHeadDynamicInclude
		_jsImportMapsExtenderTopHeadDynamicInclude;

	private ServiceTracker<JSImportMapsContributor, JSImportMapsRegistration>
		_serviceTracker;

	private final ServiceTrackerCustomizer
		<JSImportMapsContributor, JSImportMapsRegistration>
			_serviceTrackerCustomizer =
				new ServiceTrackerCustomizer
					<JSImportMapsContributor, JSImportMapsRegistration>() {

					@Override
					public JSImportMapsRegistration addingService(
						ServiceReference<JSImportMapsContributor>
							serviceReference) {

						JSImportMapsContributor jsImportMapsContributor =
							_bundleContext.getService(serviceReference);

						return _jsImportMapsExtenderTopHeadDynamicInclude.
							register(
								jsImportMapsContributor.getScope(),
								jsImportMapsContributor.
									getImportMapsJSONObject());
					}

					@Override
					public void modifiedService(
						ServiceReference serviceReference,
						JSImportMapsRegistration jsImportMapsRegistration) {
					}

					@Override
					public void removedService(
						ServiceReference serviceReference,
						JSImportMapsRegistration jsImportMapsRegistration) {

						jsImportMapsRegistration.unregister();
					}

				};

}