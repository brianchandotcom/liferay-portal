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

package com.liferay.asset.categories.item.selector.web.internal;

import com.liferay.asset.categories.item.selector.AssetCategoryTreeNodeItemSelectorReturnType;
import com.liferay.asset.categories.item.selector.criterion.AssetCategoryTreeNodeItemSelectorCriterion;
import com.liferay.asset.categories.item.selector.web.internal.constants.AssetCategoryItemSelectorWebKeys;
import com.liferay.asset.categories.item.selector.web.internal.display.context.SelectAssetCategoryTreeNodeDisplayContext;
import com.liferay.asset.categories.item.selector.web.internal.display.context.SelectAssetVocabularyDisplayContext;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "item.selector.view.order:Integer=100",
	service = ItemSelectorView.class
)
public class AssetCategoryTreeNodeItemSelectorView
	implements ItemSelectorView<AssetCategoryTreeNodeItemSelectorCriterion> {

	@Override
	public Class<AssetCategoryTreeNodeItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return AssetCategoryTreeNodeItemSelectorCriterion.class;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, AssetCategoryTreeNodeItemSelectorView.class);

		return ResourceBundleUtil.getString(resourceBundle, "source");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			AssetCategoryTreeNodeItemSelectorCriterion
				assetCategoryTreeNodeItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		SelectAssetCategoryTreeNodeDisplayContext
			selectAssetCategoryLevelDisplayContext =
				new SelectAssetCategoryTreeNodeDisplayContext(
					(HttpServletRequest)servletRequest, itemSelectedEventName,
					portletURL);

		servletRequest.setAttribute(
			AssetCategoryItemSelectorWebKeys.
				SELECT_ASSET_CATEGORY_TREE_NODE_ITEM_SELECTOR_DISPLAY_CONTEXT,
			selectAssetCategoryLevelDisplayContext);

		SelectAssetVocabularyDisplayContext
			selectAssetVocabularyDisplayContext =
				new SelectAssetVocabularyDisplayContext(
					(HttpServletRequest)servletRequest, portletURL);

		servletRequest.setAttribute(
			AssetCategoryItemSelectorWebKeys.
				SELECT_ASSET_VOCABULARY_DISPLAY_CONTEXT,
			selectAssetVocabularyDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/select_asset_vocabulary.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new AssetCategoryTreeNodeItemSelectorReturnType());

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.categories.item.selector.web)"
	)
	private ServletContext _servletContext;

}