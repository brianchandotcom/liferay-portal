/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.http.whiteboard.debug.osgi.commands;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.string.StringBundler;

import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {"osgi.command.function=check", "osgi.command.scope=http"},
	service = OSGiCommands.class
)
public class CheckServletContextHelperOSGiCommands implements OSGiCommands {

	public void check() {
		for (List<ServiceReference<ServletContextHelper>> serviceReferences :
				_serviceTrackerMap.values()) {

			if (serviceReferences.size() < 2) {
				continue;
			}

			NavigableSet<ServiceReference<ServletContextHelper>> navigableSet =
				new TreeSet<>(serviceReferences);

			ServiceReference<ServletContextHelper> lastServiceReference =
				navigableSet.last();

			for (ServiceReference<?> serviceReference :
					navigableSet.headSet(lastServiceReference, false)) {

				System.out.println(
					StringBundler.concat(
						"Servlet context with path ",
						serviceReference.getProperty(
							HttpWhiteboardConstants.
								HTTP_WHITEBOARD_CONTEXT_PATH),
						" and service ID ",
						serviceReference.getProperty("service.id"),
						" might fail because it is shadowed by service ",
						lastServiceReference.getProperty("service.id")));
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ServletContextHelper.class,
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
			new ServiceTrackerCustomizer
				<ServletContextHelper,
				 ServiceReference<ServletContextHelper>>() {

				@Override
				public ServiceReference<ServletContextHelper> addingService(
					ServiceReference<ServletContextHelper> serviceReference) {

					return serviceReference;
				}

				@Override
				public void modifiedService(
					ServiceReference<ServletContextHelper> serviceReference,
					ServiceReference<ServletContextHelper>
						serviceServiceReference) {
				}

				@Override
				public void removedService(
					ServiceReference<ServletContextHelper> serviceReference,
					ServiceReference<ServletContextHelper>
						serviceServiceReference) {
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap
		<String, List<ServiceReference<ServletContextHelper>>>
			_serviceTrackerMap;

}