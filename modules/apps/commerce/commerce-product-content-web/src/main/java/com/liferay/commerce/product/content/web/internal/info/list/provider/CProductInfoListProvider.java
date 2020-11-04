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

package com.liferay.commerce.product.content.web.internal.info.list.provider;

import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(enabled = false, immediate = true, service = InfoListProvider.class)
public class CProductInfoListProvider implements InfoListProvider<CProduct> {

	@Override
	public List<CProduct> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		return getInfoList(infoListProviderContext, null, null);
	}

	@Override
	public List<CProduct> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		if (pagination != null) {
			return _cProductLocalService.getCProducts(
				pagination.getStart(), pagination.getEnd());
		}

		return _cProductLocalService.getCProducts(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {

		return _cProductLocalService.getCProductsCount();
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(_getResourceBundle(locale), "products");
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Reference
	private CProductLocalService _cProductLocalService;

}