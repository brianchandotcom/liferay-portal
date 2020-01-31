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

package com.liferay.asset.publisher.web.internal.portlet;

import com.liferay.asset.constants.AssetWebKeys;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.constants.AssetPublisherWebKeys;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.internal.action.AssetEntryActionRegistry;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.internal.display.context.AssetPublisherDisplayContext;
import com.liferay.asset.publisher.web.internal.util.AssetPublisherCustomizerRegistry;
import com.liferay.asset.publisher.web.internal.util.AssetPublisherWebUtil;
import com.liferay.asset.publisher.web.internal.util.AssetRSSUtil;
import com.liferay.asset.util.AssetHelper;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration",
	immediate = true,
	property = {
		"com.liferay.fragment.entry.processor.portlet.alias=asset-list",
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-asset-publisher",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.display-category=category.highlighted",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Asset Publisher",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=resetCur",
		"javax.portlet.supported-public-render-parameter=tag",
		"javax.portlet.supported-public-render-parameter=tags"
	},
	service = {AssetPublisherPortlet.class, Portlet.class}
)
public class AssetPublisherPortlet extends MVCPortlet {

	public void getFieldValue(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				resourceRequest);

			long structureId = ParamUtil.getLong(
				resourceRequest, "structureId");

			Fields fields = (Fields)serviceContext.getAttribute(
				Fields.class.getName() + structureId);

			if (fields == null) {
				String fieldsNamespace = ParamUtil.getString(
					resourceRequest, "fieldsNamespace");

				fields = DDMUtil.getFields(
					structureId, fieldsNamespace, serviceContext);
			}

			String fieldName = ParamUtil.getString(resourceRequest, "name");

			Field field = fields.get(fieldName);

			Serializable fieldValue = field.getValue(
				themeDisplay.getLocale(), 0);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if (fieldValue != null) {
				jsonObject.put("success", true);
			}
			else {
				jsonObject.put("success", false);

				writeJSON(resourceRequest, resourceResponse, jsonObject);

				return;
			}

			DDMStructure ddmStructure = field.getDDMStructure();

			String type = ddmStructure.getFieldType(fieldName);

			Serializable displayValue = DDMUtil.getDisplayFieldValue(
				themeDisplay, fieldValue, type);

			jsonObject.put("displayValue", String.valueOf(displayValue));

			if (fieldValue instanceof Boolean) {
				jsonObject.put("value", (Boolean)fieldValue);
			}
			else if (fieldValue instanceof Date) {
				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyyMM ddHHmmss");

				jsonObject.put("value", dateFormat.format(fieldValue));
			}
			else if (fieldValue instanceof Double) {
				jsonObject.put("value", (Double)fieldValue);
			}
			else if (fieldValue instanceof Float) {
				jsonObject.put("value", (Float)fieldValue);
			}
			else if (fieldValue instanceof Integer) {
				jsonObject.put("value", (Integer)fieldValue);
			}
			else if (fieldValue instanceof Number) {
				jsonObject.put("value", String.valueOf(fieldValue));
			}
			else {
				jsonObject.put("value", (String)fieldValue);
			}

			writeJSON(resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	public void getRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		PortletPreferences portletPreferences =
			resourceRequest.getPreferences();

		boolean enableRss = GetterUtil.getBoolean(
			portletPreferences.getValue("enableRss", null));

		if (!portal.isRSSFeedsEnabled() || !enableRss) {
			try {
				portal.sendRSSFeedsDisabledError(
					resourceRequest, resourceResponse);
			}
			catch (ServletException servletException) {
			}

			return;
		}

		resourceResponse.setContentType(ContentTypes.TEXT_XML_UTF8);

		try (OutputStream outputStream =
				resourceResponse.getPortletOutputStream()) {

			String rootPortletId = PortletIdCodec.decodePortletName(
				portal.getPortletId(resourceRequest));

			AssetPublisherDisplayContext assetPublisherDisplayContext =
				new AssetPublisherDisplayContext(
					assetEntryActionRegistry, assetHelper,
					assetListAssetEntryProvider,
					assetPublisherCustomizerRegistry.
						getAssetPublisherCustomizer(rootPortletId),
					assetPublisherHelper, assetPublisherWebConfiguration,
					assetPublisherWebUtil, infoListProviderTracker,
					itemSelector, resourceRequest, resourceResponse,
					resourceRequest.getPreferences());

			resourceRequest.setAttribute(
				AssetPublisherWebKeys.ASSET_PUBLISHER_DISPLAY_CONTEXT,
				assetPublisherDisplayContext);

			byte[] bytes = assetRSSUtil.getRSS(
				resourceRequest, resourceResponse);

			outputStream.write(bytes);
		}
		catch (Exception exception) {
			_log.error("Unable to get RSS feed", exception);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			AssetPublisherWebKeys.ASSET_PUBLISHER_WEB_UTIL,
			assetPublisherWebUtil);

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (resourceID.equals("getFieldValue")) {
			getFieldValue(resourceRequest, resourceResponse);
		}
		else if (resourceID.equals("getRSS")) {
			getRSS(resourceRequest, resourceResponse);
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	public void subscribe(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		assetPublisherWebUtil.subscribe(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			themeDisplay.getPlid(), themeDisplay.getPpid());
	}

	public void unsubscribe(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		assetPublisherWebUtil.unsubscribe(
			themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
			themeDisplay.getPpid());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		assetPublisherWebConfiguration = ConfigurableUtil.createConfigurable(
			AssetPublisherWebConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			renderRequest.setAttribute(AssetWebKeys.ASSET_HELPER, assetHelper);

			String rootPortletId = PortletIdCodec.decodePortletName(
				portal.getPortletId(renderRequest));

			AssetPublisherDisplayContext assetPublisherDisplayContext =
				new AssetPublisherDisplayContext(
					assetEntryActionRegistry, assetHelper,
					assetListAssetEntryProvider,
					assetPublisherCustomizerRegistry.
						getAssetPublisherCustomizer(rootPortletId),
					assetPublisherHelper, assetPublisherWebConfiguration,
					assetPublisherWebUtil, infoListProviderTracker,
					itemSelector, renderRequest, renderResponse,
					renderRequest.getPreferences());

			renderRequest.setAttribute(
				AssetPublisherWebKeys.ASSET_PUBLISHER_DISPLAY_CONTEXT,
				assetPublisherDisplayContext);

			renderRequest.setAttribute(
				AssetPublisherWebKeys.ASSET_PUBLISHER_HELPER,
				assetPublisherHelper);

			renderRequest.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}
		catch (Exception exception) {
			_log.error("Unable to get asset publisher customizer", exception);
		}

		if (SessionErrors.contains(
				renderRequest, NoSuchGroupException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof NoSuchGroupException ||
			cause instanceof PrincipalException) {

			return true;
		}

		return false;
	}

	@Reference
	protected AssetEntryActionRegistry assetEntryActionRegistry;

	@Reference
	protected AssetHelper assetHelper;

	@Reference
	protected AssetListAssetEntryProvider assetListAssetEntryProvider;

	@Reference
	protected AssetPublisherCustomizerRegistry assetPublisherCustomizerRegistry;

	@Reference
	protected AssetPublisherHelper assetPublisherHelper;

	protected AssetPublisherWebConfiguration assetPublisherWebConfiguration;

	@Reference
	protected AssetPublisherWebUtil assetPublisherWebUtil;

	@Reference
	protected AssetRSSUtil assetRSSUtil;

	@Reference
	protected InfoListProviderTracker infoListProviderTracker;

	@Reference
	protected ItemSelector itemSelector;

	@Reference
	protected Portal portal;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.asset.publisher.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))"
	)
	protected Release release;

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherPortlet.class);

}