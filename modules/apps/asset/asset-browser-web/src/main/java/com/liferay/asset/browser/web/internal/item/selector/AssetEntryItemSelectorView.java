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
import com.liferay.asset.constants.AssetWebKeys;
import com.liferay.asset.util.AssetHelper;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.AssetEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.asset.criterion.AssetEntryItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
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

		HttpServletRequest httpServletRequest = _getDynamicServletRequest(
			itemSelectorCriterion, servletRequest);

		httpServletRequest.setAttribute(
			AssetBrowserDisplayContext.class.getName(),
			new AssetBrowserDisplayContext(
				_assetHelper, httpServletRequest, portletURL,
				(RenderRequest)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST),
				(RenderResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE)));
		httpServletRequest.setAttribute(
			AssetWebKeys.ASSET_HELPER, _assetHelper);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/view.jsp");

		requestDispatcher.include(httpServletRequest, servletResponse);
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
	private AssetHelper _assetHelper;

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.asset.browser.web)")
	private ServletContext _servletContext;

}