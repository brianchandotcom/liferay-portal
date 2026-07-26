/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.jaxrs.application;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.spi.autofix.Autofix;
import com.liferay.seo.studio.spi.autofix.AutofixResult;
import com.liferay.seo.studio.web.internal.web.cache.SEOStudioInstanceAccessTokenWebCacheItem;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;

import java.net.URI;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author David Truong
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/com-liferay-seo-studio-web",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=com.liferay.seo.studio.web.internal.jaxrs.application.SEOStudioApplication",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=false", "liferay.oauth2=false"
	},
	service = Application.class
)
public class SEOStudioApplication extends Application {

	@Path("/autofix")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response autofix(
		@Context HttpServletRequest httpServletRequest,
		@FormParam("insightType") String insightType,
		@FormParam("pageURL") String pageURL,
		@FormParam("value") String value) {

		long companyId = _portal.getCompanyId(httpServletRequest);

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-44511")) {
			return _buildErrorResponse(
				"SEO Studio is not enabled", Response.Status.NOT_FOUND);
		}

		Autofix autofix = _serviceTrackerMap.getService(insightType);

		if (autofix == null) {
			return _buildErrorResponse(
				"Unable to apply the fix for an unrecognized insight type",
				Response.Status.BAD_REQUEST);
		}

		try {
			return _autofix(autofix, companyId, pageURL, value);
		}
		catch (Exception exception) {
			_log.error(exception);

			return _buildErrorResponse(
				"Unable to apply the fix",
				Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/page-content")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPageContent(
		@Context HttpServletRequest httpServletRequest,
		@QueryParam("pageURL") String pageURL) {

		long companyId = _portal.getCompanyId(httpServletRequest);

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-44511")) {
			return _buildErrorResponse(
				"SEO Studio is not enabled", Response.Status.NOT_FOUND);
		}

		try {
			return _getPageContent(companyId, pageURL);
		}
		catch (Exception exception) {
			_log.error(exception);

			return _buildErrorResponse(
				"Unable to fetch the page content",
				Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, Autofix.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(autofix, emitter) -> emitter.emit(autofix.getInsightType())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private Response _autofix(
			Autofix autofix, long companyId, String pageURL, String value)
		throws Exception {

		URI uri = new URI(pageURL);

		Map<String, Serializable> values = _getSEOStudioInstanceValues(
			companyId, uri.getAuthority());

		if (values == null) {
			return _buildErrorResponse(
				"No SEO Studio instance is registered for the page host",
				Response.Status.NOT_FOUND);
		}

		String baseURL = uri.getScheme() + "://" + uri.getAuthority();

		String accessToken = SEOStudioInstanceAccessTokenWebCacheItem.get(
			baseURL, GetterUtil.getString(values.get("clientId")),
			GetterUtil.getString(values.get("clientSecret")), companyId);

		if (Validator.isNull(accessToken)) {
			return _buildErrorResponse(
				"Unable to authenticate against the customer instance",
				Response.Status.BAD_GATEWAY);
		}

		AutofixResult autofixResult = autofix.apply(
			accessToken, baseURL,
			GetterUtil.getString(values.get("siteExternalReferenceCode")),
			uri.getPath(), value);

		if (!autofixResult.isSuccess()) {
			return _buildErrorResponse(
				autofixResult.getMessage(),
				Response.Status.fromStatusCode(autofixResult.getStatus()));
		}

		return Response.ok(
			JSONUtil.put(
				"success", true
			).toString(),
			MediaType.APPLICATION_JSON
		).build();
	}

	private Response _buildErrorResponse(
		String message, Response.Status status) {

		return Response.status(
			status
		).entity(
			JSONUtil.put(
				"error", message
			).toString()
		).type(
			MediaType.APPLICATION_JSON
		).build();
	}

	private Response _getPageContent(long companyId, String pageURL)
		throws Exception {

		URI uri = new URI(pageURL);

		Map<String, Serializable> values = _getSEOStudioInstanceValues(
			companyId, uri.getAuthority());

		if (values == null) {
			return _buildErrorResponse(
				"No SEO Studio instance is registered for the page host",
				Response.Status.NOT_FOUND);
		}

		String html = _http.URLtoString(pageURL);

		if (Validator.isNull(html)) {
			return _buildErrorResponse(
				"Unable to fetch the page at the given URL",
				Response.Status.BAD_GATEWAY);
		}

		String content = HtmlUtil.stripHtml(html);

		content = content.replaceAll("\\s+", " ");
		content = StringUtil.shorten(content.trim(), 5000);

		return Response.ok(
			JSONUtil.put(
				"content", content
			).toString(),
			MediaType.APPLICATION_JSON
		).build();
	}

	private Map<String, Serializable> _getSEOStudioInstanceValues(
			long companyId, String hostname)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_SEO_STUDIO_INSTANCE", companyId);

		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ObjectEntry objectEntry : objectEntries) {
			Map<String, Serializable> values = objectEntry.getValues();

			if (Objects.equals(
					GetterUtil.getString(values.get("hostname")), hostname)) {

				return values;
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SEOStudioApplication.class);

	@Reference
	private Http _http;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, Autofix> _serviceTrackerMap;

}