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
	immediate = true,
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

				StringBundler sb = new StringBundler(6);

				sb.append("Servlet context with path ");
				sb.append(
					serviceReference.getProperty(
						HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH));
				sb.append(" and service ID ");
				sb.append(serviceReference.getProperty("service.id"));
				sb.append(" might fail because it is shadowed by service ");
				sb.append(lastServiceReference.getProperty("service.id"));

				System.out.println(sb.toString());
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