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

package com.liferay.commerce.product.content.web.internal.layout.display.page;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class CProductLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<CProduct> {

	public CProductLayoutDisplayPageObjectProvider(
		CProduct cProduct, long groupId) {

		_cProduct = cProduct;
		_groupId = groupId;

		_cpDefinition =
			CPDefinitionLocalServiceUtil.fetchCPDefinitionByCProductId(
				cProduct.getCProductId());
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(CProduct.class);
	}

	@Override
	public long getClassPK() {
		return _cProduct.getCProductId();
	}

	@Override
	public long getClassTypeId() {
		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		return _cpDefinition.getDescription(LanguageUtil.getLanguageId(locale));
	}

	@Override
	public CProduct getDisplayObject() {
		return _cProduct;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public String getKeywords(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _cpDefinition.getName(LanguageUtil.getLanguageId(locale));
	}

	@Override
	public String getURLTitle(Locale locale) {
		return _cpDefinition.getURL(LanguageUtil.getLanguageId(locale));
	}

	private final CPDefinition _cpDefinition;
	private final CProduct _cProduct;
	private final long _groupId;

}