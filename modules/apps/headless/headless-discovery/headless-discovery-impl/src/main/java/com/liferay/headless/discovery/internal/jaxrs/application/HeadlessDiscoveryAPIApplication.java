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

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.discovery.internal.configuration.HeadlessDiscoveryConfiguration;
import com.liferay.headless.discovery.internal.dto.Hint;
import com.liferay.headless.discovery.internal.dto.Resource;
import com.liferay.headless.discovery.internal.dto.Resources;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
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
	configurationPid = "com.liferay.headless.discovery.internal.configuration.HeadlessDiscoveryConfiguration",
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/api",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION_SELECT + "=(osgi.jaxrs.name=Liferay.Vulcan)",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Headless.Discovery.API",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.check.csrf.token=false"
	},
	service = Application.class
)
public class HeadlessDiscoveryAPIApplication extends Application {

	@GET
	@Produces({"application/json", "application/xml", "text/html"})
	public Response discovery(
			@HeaderParam("Accept") String accept,
			@Context HttpServletRequest httpServletRequest,
			@Context HttpServletResponse httpServletResponse)
		throws Exception {

		if ((accept != null) && accept.contains(MediaType.TEXT_HTML) &&
			_headlessDiscoveryConfiguration.enableAPIExplorer()) {

			URL url = _getURL("index.html");

			if (url == null) {
				return Response.serverError(
				).build();
			}

			InputStream urlInputStream = url.openStream();

			Scanner scanner = new Scanner(urlInputStream, "UTF-8");

			scanner.useDelimiter("\\A");

			String html = StringUtil.replace(
				scanner.next(), "%CSRF-TOKEN%",
				AuthTokenUtil.getToken(httpServletRequest));

			html = StringUtil.replace(
				html, "href=\"main.css\"", "href=\"/o/api/main.css\"");
			html = StringUtil.replace(
				html, "src=\"headless-discovery-web-min.js\"",
				"src=\"/o/api/headless-discovery-web-min.js\"");

			String finalHtml = html;

			return Response.ok(
				(StreamingOutput)streamingOutput -> {
					InputStream htmlInputStream = new ByteArrayInputStream(
						finalHtml.getBytes());

					byte[] buffer = new byte[1024];
					int read = 0;

					while ((read = htmlInputStream.read(buffer)) != -1) {
						streamingOutput.write(buffer, 0, read);
					}
				}
			).build();
		}

		Map<String, List<ResourceMethodInfoDTO>> resourceMethodInfoDTOsMap =
			_getResourceMethodInfoDTOsMap();

		Map<String, Resource> resourcesMap = new TreeMap<>();

		for (Map.Entry<String, List<ResourceMethodInfoDTO>> entry :
				resourceMethodInfoDTOsMap.entrySet()) {

			resourcesMap.put(entry.getKey(), _getResource(entry.getValue()));
		}

		Resources resources = new Resources(resourcesMap);

		if ((accept != null) &&
			accept.contains(MediaType.APPLICATION_XHTML_XML)) {

			ObjectMapper objectMapper = new ObjectMapper();

			return Response.ok(
				objectMapper.writerWithDefaultPrettyPrinter(
				).writeValueAsString(
					resources
				),
				MediaType.APPLICATION_JSON_TYPE
			).build();
		}

		return Response.ok(
			resources
		).build();
	}

	@GET
	@Path("/{parameter}")
	@Produces({"text/css", "text/javascript"})
	public Response discoveryParameter(
			@HeaderParam("Accept") String accept,
			@Context HttpServletRequest httpServletRequest,
			@Context HttpServletResponse httpServletResponse,
			@PathParam("parameter") String parameter)
		throws Exception {

		if (parameter.contains("..")) {
			return Response.status(
				Response.Status.FORBIDDEN
			).build();
		}

		URL url = _getURL(parameter);

		if (url == null) {
			return Response.serverError(
			).build();
		}

		InputStream urlInputStream = url.openStream();

		Response.ResponseBuilder responseBuilder = Response.ok(
			(StreamingOutput)streamingOutput -> {
				byte[] buffer = new byte[1024];
				int read = 0;

				while ((read = urlInputStream.read(buffer)) != -1) {
					streamingOutput.write(buffer, 0, read);
				}
			});

		if (parameter.contains("main.css")) {
			responseBuilder.type("text/css");
		}
		else {
			responseBuilder.type("text/javascript");
		}

		return responseBuilder.build();
	}

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;
		_headlessDiscoveryConfiguration = ConfigurableUtil.createConfigurable(
			HeadlessDiscoveryConfiguration.class, properties);
	}

	private Resource _getResource(
		List<ResourceMethodInfoDTO> resourceMethodInfoDTOS) {

		Resource resource = new Resource();

		Stream<ResourceMethodInfoDTO> stream = resourceMethodInfoDTOS.stream();

		String[] verbs = stream.map(
			dto -> dto.method
		).toArray(
			String[]::new
		);

		ResourceMethodInfoDTO resourceMethodInfoDTO =
			resourceMethodInfoDTOS.get(0);

		resource.setHint(
			new Hint(verbs, resourceMethodInfoDTO.producingMimeType));

		String resourcePath = resourceMethodInfoDTO.path;

		if (resourcePath.contains("{")) {
			resource.setHrefTemplate(resourcePath);
		}
		else {
			resource.setHref(resourcePath);
		}

		return resource;
	}

	private Map<String, List<ResourceMethodInfoDTO>>
		_getResourceMethodInfoDTOsMap() {

		Map<String, List<ResourceMethodInfoDTO>> resourcesMap = new TreeMap<>();

		String absolutePath = String.valueOf(_uriInfo.getAbsolutePath());

		String serverURL = StringUtil.removeSubstring(absolutePath, "/api/");

		RuntimeDTO runtimeDTO = _jaxrsServiceRuntime.getRuntimeDTO();

		for (ApplicationDTO applicationDTO : runtimeDTO.applicationDTOs) {
			for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {
				for (ResourceMethodInfoDTO resourceMethodInfoDTO :
						resourceDTO.resourceMethods) {

					resourceMethodInfoDTO.path =
						applicationDTO.base + resourceMethodInfoDTO.path;

					String path = serverURL + resourceMethodInfoDTO.path;

					List<ResourceMethodInfoDTO> resourceMethodInfoDTOS =
						resourcesMap.get(path);

					if (resourceMethodInfoDTOS == null) {
						resourceMethodInfoDTOS = new ArrayList<>();
					}

					resourceMethodInfoDTOS.add(resourceMethodInfoDTO);

					resourcesMap.put(path, resourceMethodInfoDTOS);
				}
			}
		}

		return resourcesMap;
	}

	private URL _getURL(String parameter) {
		for (Bundle bundle : _bundleContext.getBundles()) {
			if (StringUtil.equals(
					bundle.getSymbolicName(),
					"com.liferay.headless.discovery.web")) {

				return bundle.getEntry("META-INF/resources/" + parameter);
			}
		}

		return null;
	}

	private volatile BundleContext _bundleContext;
	private volatile HeadlessDiscoveryConfiguration
		_headlessDiscoveryConfiguration;

	@Reference
	private JaxrsServiceRuntime _jaxrsServiceRuntime;

	@Context
	private UriInfo _uriInfo;

}