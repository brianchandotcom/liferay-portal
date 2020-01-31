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

package com.liferay.sharing.web.internal.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.sharing.filter.SharedAssetsFilterItem;
import com.liferay.sharing.web.internal.filter.SharedAssetsFilterItemTracker;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "item.selector.view.order:Integer=100",
	service = ItemSelectorView.class
)
public class SharedAssetsFilterItemItemSelectorView
	implements ItemSelectorView<SharedAssetsFilterItemItemSelectorCriterion> {

	@Override
	public Class<SharedAssetsFilterItemItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return SharedAssetsFilterItemItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "asset-types");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			SharedAssetsFilterItemItemSelectorCriterion
				sharedAssetsFilterItemItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			sharedAssetsFilterItemItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new ItemSelectorViewDescriptor<SharedAssetsFilterItem>() {

				@Override
				public ItemDescriptor getItemDescriptor(
					SharedAssetsFilterItem sharedAssetsFilterItem) {

					return new ItemDescriptor() {

						@Override
						public String getIcon() {
							return sharedAssetsFilterItem.getIcon();
						}

						@Override
						public String getImageURL() {
							return null;
						}

						@Override
						public String getPayload() {
							return sharedAssetsFilterItem.getClassName();
						}

						@Override
						public String getSubtitle(Locale locale) {
							return null;
						}

						@Override
						public String getTitle(Locale locale) {
							return sharedAssetsFilterItem.getLabel(locale);
						}

						@Override
						public boolean isCompact() {
							return true;
						}

					};
				}

				@Override
				public ItemSelectorReturnType getItemSelectorReturnType() {
					return new SharedAssetsFilterItemItemSelectorReturnType();
				}

				@Override
				public SearchContainer getSearchContainer() {
					SearchContainer<SharedAssetsFilterItem>
						entriesSearchContainer = new SearchContainer<>(
							(PortletRequest)servletRequest.getAttribute(
								JavaConstants.JAVAX_PORTLET_REQUEST),
							portletURL, null, null);

					entriesSearchContainer.setResults(
						_sharedAssetsFilterItemTracker.
							getSharedAssetsFilterItems());

					return entriesSearchContainer;
				}

				@Override
				public boolean isShowBreadcrumb() {
					return false;
				}

				@Override
				public boolean isShowManagementToolbar() {
					return false;
				}

			});
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new SharedAssetsFilterItemItemSelectorReturnType());

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<SharedAssetsFilterItemItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private SharedAssetsFilterItemTracker _sharedAssetsFilterItemTracker;

}