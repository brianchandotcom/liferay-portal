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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Andrea Di Giorgi
 */
public class DatabaseCPCatalogEntryImpl implements CPCatalogEntry {

	public DatabaseCPCatalogEntryImpl(
		CPDefinition cpDefinition, Locale locale) {

		_cpDefinition = cpDefinition;

		_languageId = LanguageUtil.getLanguageId(locale);
	}

	@Override
	public long getCPDefinitionId() {
		return _cpDefinition.getCPDefinitionId();
	}

	@Override
	public String getDefaultImageFileUrl() {
		try {
			return _cpDefinition.getDefaultImageFileURL();
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	@Override
	public double getDepth() {
		return _cpDefinition.getDepth();
	}

	@Override
	public String getDescription() {
		return _cpDefinition.getDescription(_languageId);
	}

	@Override
	public double getHeight() {
		return _cpDefinition.getHeight();
	}

	@Override
	public String getName() {
		return _cpDefinition.getName(_languageId);
	}

	@Override
	public String getProductTypeName() {
		return _cpDefinition.getProductTypeName();
	}

	@Override
	public String getShortDescription() {
		return _cpDefinition.getShortDescription(_languageId);
	}

	@Override
	public String getSku() {
		return null;
	}

	@Override
	public String getUrl() {
		return _cpDefinition.getURL(_languageId);
	}

	@Override
	public boolean isIgnoreSKUCombinations() {
		return _cpDefinition.isIgnoreSKUCombinations();
	}

	private final CPDefinition _cpDefinition;
	private final String _languageId;

}