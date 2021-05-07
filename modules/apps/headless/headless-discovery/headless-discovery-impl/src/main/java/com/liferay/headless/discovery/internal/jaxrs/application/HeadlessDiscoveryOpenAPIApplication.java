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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.UriInfoUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

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

		String serverURL = StringUtil.removeSubstring(
			UriInfoUtil.getAbsolutePath(_uriInfo), "/openapi/");

		RuntimeDTO runtimeDTO = _jaxrsServiceRuntime.getRuntimeDTO();

		for (ApplicationDTO applicationDTO : runtimeDTO.applicationDTOs) {
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

	@Reference
	private JaxrsServiceRuntime _jaxrsServiceRuntime;

	@Context
	private UriInfo _uriInfo;

}