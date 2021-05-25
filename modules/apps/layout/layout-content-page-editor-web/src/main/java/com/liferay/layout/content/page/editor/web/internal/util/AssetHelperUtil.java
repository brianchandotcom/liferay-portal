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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.AssetPublisherAddItemHolder;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayRenderRequest;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.RequestContextMapper;

import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletResponse;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = {})
public class AssetHelperUtil {

	public static List<AssetPublisherAddItemHolder>
			getAssetPublisherAddItemHolders(
				AssetListEntry assetListEntry,
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
		throws Exception {

		long[] segmentsEntryIds = _segmentsEntryRetriever.getSegmentsEntryIds(
			_portal.getScopeGroupId(httpServletRequest),
			_portal.getUserId(httpServletRequest),
			_requestContextMapper.map(httpServletRequest));

		AssetEntryQuery assetEntryQuery =
			_assetListAssetEntryProvider.getAssetEntryQuery(
				assetListEntry, segmentsEntryIds);

		long[] allTagIds = assetEntryQuery.getAllTagIds();

		String[] allTagNames = new String[allTagIds.length];

		int index = 0;

		for (long tagId : allTagIds) {
			AssetTag assetTag = _assetTagLocalService.getAssetTag(tagId);

			allTagNames[index++] = assetTag.getName();
		}

		LiferayPortletRequest liferayPortletRequest = _createRenderRequest(
			httpServletRequest, httpServletResponse);

		PortletResponse portletResponse =
			(PortletResponse)liferayPortletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(portletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _assetHelper.getAssetPublisherAddItemHolders(
			liferayPortletRequest, liferayPortletResponse,
			assetListEntry.getGroupId(), assetEntryQuery.getClassNameIds(),
			assetEntryQuery.getClassTypeIds(),
			assetEntryQuery.getAllCategoryIds(), allTagNames,
			themeDisplay.getURLCurrent());
	}

	@Reference(unbind = "-")
	protected void setAssetHelper(AssetHelper assetHelper) {
		_assetHelper = assetHelper;
	}

	@Reference(unbind = "-")
	protected void setAssetListAssetEntryProvider(
		AssetListAssetEntryProvider assetListAssetEntryProvider) {

		_assetListAssetEntryProvider = assetListAssetEntryProvider;
	}

	@Reference(unbind = "-")
	protected void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {

		_assetTagLocalService = assetTagLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Reference(unbind = "-")
	protected void setRequestContextMapper(
		RequestContextMapper requestContextMapper) {

		_requestContextMapper = requestContextMapper;
	}

	@Reference(unbind = "-")
	protected void setSegmentsEntryRetriever(
		SegmentsEntryRetriever segmentsEntryRetriever) {

		_segmentsEntryRetriever = segmentsEntryRetriever;
	}

	private static LiferayRenderRequest _createRenderRequest(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ServletContext servletContext =
			(ServletContext)httpServletRequest.getAttribute(WebKeys.CTX);

		Portlet portlet = _portletLocalService.getPortletById(
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				httpServletRequest, portlet.getPortletId());

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.getStrictPreferences(
				portletPreferencesIds);

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		PortletContext portletContext = portletConfig.getPortletContext();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LiferayRenderRequest liferayRenderRequest = RenderRequestFactory.create(
			httpServletRequest, portlet, invokerPortlet, portletContext,
			WindowState.NORMAL, PortletMode.VIEW, portletPreferences,
			themeDisplay.getPlid());

		liferayRenderRequest.setPortletRequestDispatcherRequest(
			httpServletRequest);

		PortletResponse portletResponse = RenderResponseFactory.create(
			httpServletResponse, liferayRenderRequest);

		liferayRenderRequest.defineObjects(portletConfig, portletResponse);

		return liferayRenderRequest;
	}

	private static AssetHelper _assetHelper;
	private static AssetListAssetEntryProvider _assetListAssetEntryProvider;
	private static AssetTagLocalService _assetTagLocalService;
	private static Portal _portal;
	private static PortletLocalService _portletLocalService;
	private static PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private static RequestContextMapper _requestContextMapper;
	private static SegmentsEntryRetriever _segmentsEntryRetriever;

}