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

package com.liferay.layout.type.controller.collection.internal.product.navigation.control.menu;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.AssetPublisherAddItemHolder;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.type.controller.collection.internal.constants.CollectionPageLayoutTypeControllerWebKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.product.navigation.control.menu.BaseJSPProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuPortletKeys;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.RequestContextMapper;

import java.io.IOException;

import java.util.List;
import java.util.Objects;

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
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.TOOLS,
		"product.navigation.control.menu.entry.order:Integer=150"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class AddCollectionItemProductNavigationControlMenuEntry
	extends BaseJSPProductNavigationControlMenuEntry
	implements ProductNavigationControlMenuEntry {

	@Override
	public String getIconJspPath() {
		return "/entries/add_collection_item.jsp";
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long collectionPK = GetterUtil.getLong(
			layout.getTypeSettingsProperty("collectionPK"));

		try {
			AssetListEntry assetListEntry =
				_assetListEntryService.getAssetListEntry(collectionPK);

			long[] segmentEntryIds = _getSegmentsEntryIds(httpServletRequest);

			AssetEntryQuery assetEntryQuery =
				_assetListAssetEntryProvider.getAssetEntryQuery(
					assetListEntry, segmentEntryIds);

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

			List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders =
				_assetHelper.getAssetPublisherAddItemHolders(
					liferayPortletRequest, liferayPortletResponse,
					assetListEntry.getGroupId(),
					assetEntryQuery.getClassNameIds(),
					assetEntryQuery.getClassTypeIds(),
					assetEntryQuery.getAllCategoryIds(), allTagNames,
					_getRedirect(
						collectionPK, httpServletRequest,
						liferayPortletResponse, themeDisplay));

			httpServletRequest.setAttribute(
				CollectionPageLayoutTypeControllerWebKeys.
					ASSET_PUBLISHER_ADD_ITEM_HOLDERS,
				assetPublisherAddItemHolders);
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}

		return super.includeIcon(httpServletRequest, httpServletResponse);
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeContent()) {
			return false;
		}

		String collectionType = layout.getTypeSettingsProperty(
			"collectionType");

		if (!Objects.equals(
				collectionType,
				InfoListItemSelectorReturnType.class.getName())) {

			return false;
		}

		return super.isShow(httpServletRequest);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.type.controller.collection)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private LiferayRenderRequest _createRenderRequest(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ServletContext servletContext =
			(ServletContext)httpServletRequest.getAttribute(WebKeys.CTX);

		Portlet portlet = _portletLocalService.getPortletById(
			ProductNavigationControlMenuPortletKeys.
				PRODUCT_NAVIGATION_CONTROL_MENU);

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

	private String _getRedirect(
			long assetListEntryId, HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String currentURL = _http.addParameter(
			_portal.getLayoutRelativeURL(
				themeDisplay.getLayout(), themeDisplay),
			"p_l_mode",
			ParamUtil.getString(
				httpServletRequest, "p_l_mode", Constants.VIEW));

		return _http.addParameter(
			PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setActionName(
				"/control_menu/add_collection_item"
			).setRedirect(
				currentURL
			).setParameter(
				"assetListEntryId", assetListEntryId
			).buildString(),
			"portletResource",
			ProductNavigationControlMenuPortletKeys.
				PRODUCT_NAVIGATION_CONTROL_MENU);
	}

	private long[] _getSegmentsEntryIds(HttpServletRequest httpServletRequest)
		throws Exception {

		return _segmentsEntryRetriever.getSegmentsEntryIds(
			_portal.getScopeGroupId(httpServletRequest),
			_portal.getUserId(httpServletRequest),
			_requestContextMapper.map(httpServletRequest));
	}

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private SegmentsEntryRetriever _segmentsEntryRetriever;

}