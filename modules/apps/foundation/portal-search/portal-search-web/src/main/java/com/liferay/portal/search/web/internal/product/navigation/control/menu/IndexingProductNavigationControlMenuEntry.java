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

package com.liferay.portal.search.web.internal.product.navigation.control.menu;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.TOOLS,
		"product.navigation.control.menu.entry.order:Integer=500"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class IndexingProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry
	implements ProductNavigationControlMenuEntry {

	@Override
	public Map<String, Object> getData(HttpServletRequest request) {
		return _data;
	}

	@Override
	public String getIcon(HttpServletRequest request) {
		return "reload";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle, "the-portal-is-currently-reindexing");
	}

	@Override
	public String getURL(HttpServletRequest request) {
		return null;
	}

	@Override
	public boolean isShow(HttpServletRequest request) throws PortalException {
		int count = _indexWriterHelper.getReindexTaskCount(
			CompanyConstants.SYSTEM, false);

		if (count == 0) {
			return false;
		}

		return super.isShow(request);
	}

	@Reference(unbind = "-")
	public void setIndexWriterHelper(IndexWriterHelper indexWriterHelper) {
		_indexWriterHelper = indexWriterHelper;
	}

	private static final Map<String, Object> _data =
		Collections.<String, Object>singletonMap("qa-id", "indexing");

	private IndexWriterHelper _indexWriterHelper;

}