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

package com.liferay.commerce.product.internal.catalog;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.search.CPDefinitionIndexer;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Andrea Di Giorgi
 */
public class IndexCPCatalogEntryImpl implements CPCatalogEntry {

	public IndexCPCatalogEntryImpl(
		Document document, CPDefinitionLocalService cpDefinitionLocalService,
		Locale locale) {

		_document = document;
		_cpDefinitionLocalService = cpDefinitionLocalService;
		_locale = locale;
	}

	@Override
	public long getCPDefinitionId() {
		return GetterUtil.getLong(_document.get(Field.ENTRY_CLASS_PK));
	}

	@Override
	public List<CPSku> getCPSkus() {
		List<CPSku> cpSkus = new ArrayList<>();

		CPDefinition cpDefinition = _cpDefinitionLocalService.fetchCPDefinition(
			getCPDefinitionId());

		if (cpDefinition == null) {
			return cpSkus;
		}

		List<CPInstance> cpInstances = cpDefinition.getCPInstances();

		for (CPInstance cpInstance : cpInstances) {
			cpSkus.add(new CPSkuImpl(cpInstance));
		}

		return cpSkus;
	}

	@Override
	public String getDefaultImageFileUrl() {
		return _document.get(CPDefinitionIndexer.FIELD_DEFAULT_IMAGE_FILE_URL);
	}

	@Override
	public double getDepth() {
		return GetterUtil.getDouble(
			_document.get(CPDefinitionIndexer.FIELD_DEPTH));
	}

	@Override
	public String getDescription() {
		return _document.get(_locale, Field.DESCRIPTION);
	}

	@Override
	public double getHeight() {
		return GetterUtil.getDouble(
			_document.get(CPDefinitionIndexer.FIELD_HEIGHT));
	}

	@Override
	public String getName() {
		return _document.get(_locale, Field.NAME);
	}

	@Override
	public String getProductTypeName() {
		return _document.get(CPDefinitionIndexer.FIELD_PRODUCT_TYPE_NAME);
	}

	@Override
	public String getShortDescription() {
		return _document.get(
			_locale, CPDefinitionIndexer.FIELD_SHORT_DESCRIPTION);
	}

	@Override
	public String getUrl() {
		return _document.get(_locale, Field.URL);
	}

	@Override
	public boolean isIgnoreSKUCombinations() {
		return GetterUtil.getBoolean(
			_document.get(
				CPDefinitionIndexer.FIELD_IS_IGNORE_SKU_COMBINATIONS));
	}

	private final CPDefinitionLocalService _cpDefinitionLocalService;
	private final Document _document;
	private final Locale _locale;

}