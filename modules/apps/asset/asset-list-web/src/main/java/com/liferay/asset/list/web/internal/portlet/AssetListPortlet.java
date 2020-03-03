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

package com.liferay.asset.list.web.internal.portlet;

import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.constants.AssetListWebKeys;
import com.liferay.asset.list.exception.AssetListEntryTitleException;
import com.liferay.asset.list.exception.DuplicateAssetListEntryTitleException;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.web.internal.display.context.AssetListDisplayContext;
import com.liferay.asset.list.web.internal.display.context.EditAssetListDisplayContext;
import com.liferay.asset.util.AssetRendererFactoryClassProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-asset-list-web",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Asset List",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class AssetListPortlet extends MVCPortlet {

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

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (resourceID.equals("getFieldValue")) {
			getFieldValue(resourceRequest, resourceResponse);
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}

		super.serveResource(resourceRequest, resourceResponse);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			AssetListWebKeys.ASSET_LIST_ASSET_ENTRY_PROVIDER,
			_assetListAssetEntryProvider);

		AssetListDisplayContext assetListDisplayContext =
			new AssetListDisplayContext(
				_assetRendererFactoryClassProvider, renderRequest,
				renderResponse);

		renderRequest.setAttribute(
			AssetListWebKeys.ASSET_LIST_DISPLAY_CONTEXT,
			assetListDisplayContext);
		renderRequest.setAttribute(
			AssetListWebKeys.EDIT_ASSET_LIST_DISPLAY_CONTEXT,
			new EditAssetListDisplayContext(
				_assetRendererFactoryClassProvider, _itemSelector,
				renderRequest, renderResponse,
				_getUnicodeProperties(assetListDisplayContext)));

		renderRequest.setAttribute(
			AssetListWebKeys.ITEM_SELECTOR, _itemSelector);

		super.doDispatch(renderRequest, renderResponse);
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof AssetListEntryTitleException ||
			cause instanceof DuplicateAssetListEntryTitleException) {

			return true;
		}

		return super.isSessionErrorException(cause);
	}

	private UnicodeProperties _getUnicodeProperties(
			AssetListDisplayContext assetListDisplayContext)
		throws IOException {

		AssetListEntry assetListEntry =
			assetListDisplayContext.getAssetListEntry();

		if (assetListEntry == null) {
			return new UnicodeProperties();
		}

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.load(
			assetListEntry.getTypeSettings(
				assetListDisplayContext.getSegmentsEntryId()));

		return unicodeProperties;
	}

	@Reference
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Reference
	private AssetRendererFactoryClassProvider
		_assetRendererFactoryClassProvider;

	@Reference
	private ItemSelector _itemSelector;

}