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

package com.liferay.headless.discovery.internal.jaxrs.application;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Javier Gamarra
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/openapi",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION_SELECT + "=(osgi.jaxrs.name=Liferay.Vulcan)",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Headless.Discovery.OpenAPI",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.check.csrf.token=false"
	},
	service = Application.class
)
public class HeadlessDiscoveryOpenAPIApplication extends Application {

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@GET
	@Produces({"application/json", "application/xml"})
	public Map<String, List<String>> openAPI(
		@HeaderParam("Accept") String accept) {

		Map<String, List<String>> pathsMap = new TreeMap<>();

		String serverURL =
			_portal.getPortalURL(_httpServletRequest) + _portal.getPathProxy() +
				Portal.PATH_MODULE;

		RuntimeDTO runtimeDTO = _jaxrsServiceRuntime.getRuntimeDTO();

		List<ApplicationDTO> filteredApplicationsByCompany =
			_filterObjectsApplicationByCompany(runtimeDTO.applicationDTOs);

		for (ApplicationDTO applicationDTO : filteredApplicationsByCompany) {
			List<String> paths = new ArrayList<>();

			String base = applicationDTO.base;

			if (!base.startsWith(StringPool.FORWARD_SLASH)) {
				base = StringPool.FORWARD_SLASH + base;
			}

			for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {
				_addPaths(base, paths, resourceDTO.resourceMethods, serverURL);
			}

			_addPaths(base, paths, applicationDTO.resourceMethods, serverURL);

			if (!paths.isEmpty()) {
				String baseURL = base;

				if ((accept != null) &&
					accept.contains(MediaType.APPLICATION_XML)) {

					baseURL = baseURL.substring(1);
				}

				pathsMap.put(baseURL, paths);
			}
		}

		return pathsMap;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, Application.class, "companyId",
			new ServiceTrackerCustomizer<Application, Application>() {

				@Override
				public Application addingService(
					ServiceReference<Application> serviceReference) {

					_storeObjectApplicationCompanyIds(serviceReference);

					return bundleContext.getService(serviceReference);
				}

				@Override
				public void modifiedService(
					ServiceReference<Application> serviceReference,
					Application application) {

					_storeObjectApplicationCompanyIds(serviceReference);
				}

				@Override
				public void removedService(
					ServiceReference<Application> serviceReference,
					Application application) {

					Object osgiJaxrsBase = serviceReference.getProperty(
						"osgi.jaxrs.application.base");

					if (osgiJaxrsBase instanceof String) {
						_objectApplicationCompanyIdsMap.remove(osgiJaxrsBase);
					}

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _addPaths(
		String basePath, List<String> paths,
		ResourceMethodInfoDTO[] resourceMethodInfoDTOS, String serverURL) {

		for (ResourceMethodInfoDTO resourceMethodInfoDTO :
				resourceMethodInfoDTOS) {

			String path = resourceMethodInfoDTO.path;

			if (path.contains("/openapi")) {
				String openAPIPath = StringUtil.replace(
					resourceMethodInfoDTO.path, "{type:json|yaml}", "yaml");

				paths.add(serverURL + basePath + openAPIPath);
			}
		}
	}

	private List<ApplicationDTO> _filterObjectsApplicationByCompany(
		ApplicationDTO[] applicationDTOS) {

		Stream<ApplicationDTO> applicationDTOStream = Arrays.stream(
			applicationDTOS);

		return applicationDTOStream.filter(
			applicationDTO -> {
				if (_objectApplicationCompanyIdsMap.containsKey(
						applicationDTO.base)) {

					List<String> companyIds =
						_objectApplicationCompanyIdsMap.get(
							applicationDTO.base);

					return companyIds.contains(
						String.valueOf(CompanyThreadLocal.getCompanyId()));
				}

				return true;
			}
		).collect(
			Collectors.toList()
		);
	}

	private void _storeObjectApplicationCompanyIds(
		ServiceReference<Application> serviceReference) {

		Object companyIds = serviceReference.getProperty("companyId");
		Object osgiJaxrsBase = serviceReference.getProperty(
			"osgi.jaxrs.application.base");

		if ((companyIds instanceof List) && (osgiJaxrsBase instanceof String)) {
			_objectApplicationCompanyIdsMap.put(
				(String)osgiJaxrsBase, (List<String>)companyIds);
		}
	}

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private JaxrsServiceRuntime _jaxrsServiceRuntime;

	private final Map<String, List<String>> _objectApplicationCompanyIdsMap =
		new HashMap<>();

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, Application> _serviceTrackerMap;

	@Context
	private UriInfo _uriInfo;

}