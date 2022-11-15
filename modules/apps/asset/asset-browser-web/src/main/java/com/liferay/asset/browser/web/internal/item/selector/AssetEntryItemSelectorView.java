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

package com.liferay.asset.browser.web.internal.item.selector;

import com.liferay.asset.browser.web.internal.display.context.AssetBrowserDisplayContext;
import com.liferay.asset.browser.web.internal.display.context.AssetBrowserManagementToolbarDisplayContext;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.AssetEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.asset.criterion.AssetEntryItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ItemSelectorView.class)
public class AssetEntryItemSelectorView
	implements ItemSelectorView<AssetEntryItemSelectorCriterion> {

	@Override
	public Class<? extends AssetEntryItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return AssetEntryItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "select-asset");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			AssetEntryItemSelectorCriterion itemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		try {
			HttpServletRequest httpServletRequest = _getDynamicServletRequest(
				itemSelectorCriterion, servletRequest);

			RenderRequest renderRequest =
				(RenderRequest)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);
			RenderResponse renderResponse =
				(RenderResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			AssetBrowserDisplayContext assetBrowserDisplayContext =
				new AssetBrowserDisplayContext(
					_assetEntryLocalService, _assetHelper, _depotEntryService,
					_groupService, httpServletRequest, _language, _portal,
					portletURL, renderRequest, renderResponse);

			_itemSelectorViewDescriptorRenderer.renderHTML(
				httpServletRequest, servletResponse, itemSelectorCriterion,
				portletURL, itemSelectedEventName, search,
				new AssetEntryItemSelectorViewDescriptor(
					httpServletRequest, assetBrowserDisplayContext,
					new AssetBrowserManagementToolbarDisplayContext(
						httpServletRequest,
						_portal.getLiferayPortletRequest(renderRequest),
						_portal.getLiferayPortletResponse(renderResponse),
						assetBrowserDisplayContext)));
		}
		catch (PortalException | PortletException exception) {
			throw new ServletException(exception);
		}
	}

	private DynamicServletRequest _getDynamicServletRequest(
		AssetEntryItemSelectorCriterion assetEntryItemSelectorCriterion,
		ServletRequest servletRequest) {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)servletRequest;

		return new DynamicServletRequest(
			httpServletRequest,
			HashMapBuilder.put(
				"groupId",
				_toStringArray(
					_getGroupId(
						assetEntryItemSelectorCriterion, servletRequest))
			).put(
				"multipleSelection",
				_toStringArray(
					!assetEntryItemSelectorCriterion.isSingleSelect())
			).put(
				"scopeGroupType",
				_toStringArray(
					assetEntryItemSelectorCriterion.getScopeGroupType())
			).put(
				"selectedGroupIds",
				_toStringArray(
					StringUtil.merge(
						assetEntryItemSelectorCriterion.getSelectedGroupIds(),
						StringPool.COMMA))
			).put(
				"showNonindexable",
				_toStringArray(
					assetEntryItemSelectorCriterion.isShowNonindexable())
			).put(
				"showScheduled",
				_toStringArray(
					assetEntryItemSelectorCriterion.isShowScheduled())
			).put(
				"subtypeSelectionId",
				_toStringArray(
					assetEntryItemSelectorCriterion.getSubtypeSelectionId())
			).put(
				"typeSelection",
				_toStringArray(
					assetEntryItemSelectorCriterion.getTypeSelection())
			).build());
	}

	private long _getGroupId(
		AssetEntryItemSelectorCriterion assetEntryItemSelectorCriterion,
		ServletRequest servletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)servletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (assetEntryItemSelectorCriterion.getGroupId() ==
				themeDisplay.getRefererGroupId()) {

			return themeDisplay.getScopeGroupId();
		}

		return assetEntryItemSelectorCriterion.getGroupId();
	}

	private <T> String[] _toStringArray(T value) {
		return new String[] {String.valueOf(value)};
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new AssetEntryItemSelectorReturnType());

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private GroupService _groupService;

	@Reference
	private ItemSelectorViewDescriptorRenderer<AssetEntryItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.asset.browser.web)")
	private ServletContext _servletContext;

}