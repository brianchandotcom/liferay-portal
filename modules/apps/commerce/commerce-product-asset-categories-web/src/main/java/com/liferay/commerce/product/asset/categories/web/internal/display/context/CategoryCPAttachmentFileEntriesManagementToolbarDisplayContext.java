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

package com.liferay.commerce.product.asset.categories.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class CategoryCPAttachmentFileEntriesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public CategoryCPAttachmentFileEntriesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<CPAttachmentFileEntry> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public CreationMenu getCreationMenu() {
		AssetCategory assetCategory =
			(AssetCategory)httpServletRequest.getAttribute(
				WebKeys.ASSET_CATEGORY);

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						liferayPortletResponse
					).setMVCRenderCommandName(
						"/commerce_product_asset_categories" +
							"/edit_asset_category_cp_attachment_file_entry"
					).setRedirect(
						currentURLObj.toString()
					).setParameter(
						"categoryId",
						String.valueOf(assetCategory.getCategoryId())
					).build());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-image"));
			}
		).build();
	}

	@Override
	public String getSearchContainerId() {
		return "cpAttachmentFileEntries";
	}

	@Override
	public String getSortingURL() {
		return null;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String getDisplayStyle() {
		return "list";
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list"};
	}

}