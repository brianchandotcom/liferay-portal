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

import com.liferay.headless.discovery.internal.dto.Hint;
import com.liferay.headless.discovery.internal.dto.Resource;
import com.liferay.headless.discovery.internal.dto.Resources;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/api",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION_SELECT + "=(osgi.jaxrs.name=Liferay.Vulcan)",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Headless.Discovery.API",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.check.csrf.token=false"
	},
	service = Application.class
)
public class HeadlessDiscoveryAPIApplication extends Application {

	@GET
	@Produces({"application/json", "application/xml"})
	public Response discovery(
			@HeaderParam("Accept") String accept,
			@Context HttpServletRequest httpServletRequest,
			@Context HttpServletResponse httpServletResponse)
		throws Exception {

		if ((accept != null) && accept.contains(MediaType.TEXT_HTML)) {
			return _getHTMLResponse(httpServletRequest, httpServletResponse);
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

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	private Response _getHTMLResponse(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String templateId = "/headless-discovery-web.ftl";

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			templateId, getClass().getResource(templateId));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

		ServletContext servletContext = ServletContextPool.get(
			StringPool.BLANK);

		httpServletRequest.setAttribute(WebKeys.CTX, servletContext);

		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(httpServletRequest));

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.prepare(httpServletRequest);

		template.put("themeServletContext", servletContext);

		template.prepareTaglib(httpServletRequest, httpServletResponse);

		template.put(TemplateConstants.WRITER, unsyncStringWriter);

		template.processTemplate(unsyncStringWriter);

		return Response.ok(
			unsyncStringWriter.toString()
		).build();
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

		URI uri = _uriInfo.getAbsolutePath();

		String absolutePath = uri.toString();

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

	private ThemeDisplay _getThemeDisplay(HttpServletRequest httpServletRequest)
		throws Exception {

		ServicePreAction servicePreAction = new ServicePreAction();

		DummyHttpServletResponse dummyHttpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			httpServletRequest, dummyHttpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(httpServletRequest, dummyHttpServletResponse);

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Reference
	private JaxrsServiceRuntime _jaxrsServiceRuntime;

	@Context
	private UriInfo _uriInfo;

}