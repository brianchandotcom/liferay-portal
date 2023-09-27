/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.http.whiteboard.debug.osgi.commands;

import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.runtime.HttpServiceRuntime;
import org.osgi.service.http.runtime.dto.RuntimeDTO;
import org.osgi.service.http.runtime.dto.ServletContextDTO;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {"osgi.command.function=check", "osgi.command.scope=http"},
	service = HttpServiceRuntimeOSGiCommands.class
)
public class HttpServiceRuntimeOSGiCommands {

	public void check() {
		RuntimeDTO runtimeDTO = _httpServiceRuntime.getRuntimeDTO();

		Map<String, Set<ServletContextDTO>> contextPathMap = new HashMap<>();

		for (ServletContextDTO servletContextDTO :
				runtimeDTO.servletContextDTOs) {

			Set<ServletContextDTO> servletContextDTOs =
				contextPathMap.computeIfAbsent(
					servletContextDTO.contextPath, key -> new HashSet<>());

			servletContextDTOs.add(servletContextDTO);
		}

		for (Set<ServletContextDTO> servletContextDTOs :
				contextPathMap.values()) {

			if (servletContextDTOs.size() < 2) {
				continue;
			}

			NavigableSet<ServiceReference<?>> navigableSet = new TreeSet<>();

			for (ServletContextDTO servletContextDTO : servletContextDTOs) {
				navigableSet.add(
					_getServiceReference(servletContextDTO.serviceId));
			}

			ServiceReference<?> lastServiceReference = navigableSet.last();

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
		_bundleContext = bundleContext;
	}

	private ServiceReference<?> _getServiceReference(long serviceId) {
		try {
			ServiceReference<?>[] serviceReferences =
				_bundleContext.getServiceReferences(
					(String)null, "(service.id=" + serviceId + ")");

			if ((serviceReferences == null) ||
				(serviceReferences.length == 0)) {

				return null;
			}

			return serviceReferences[0];
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			throw new IllegalArgumentException(invalidSyntaxException);
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private HttpServiceRuntime _httpServiceRuntime;

}