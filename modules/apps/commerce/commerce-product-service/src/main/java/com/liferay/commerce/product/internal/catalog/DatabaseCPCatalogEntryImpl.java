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
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class DatabaseCPCatalogEntryImpl implements CPCatalogEntry {

	public DatabaseCPCatalogEntryImpl(
		CPDefinition cpDefinition,
		CPDefinitionOptionRelLocalService cpDefinitionOptionRelLocalService,
		CPInstanceLocalService cpInstanceLocalService, Locale locale) {

		_cpDefinition = cpDefinition;
		_cpDefinitionOptionRelLocalService = cpDefinitionOptionRelLocalService;
		_cpInstanceLocalService = cpInstanceLocalService;

		_languageId = LanguageUtil.getLanguageId(locale);
	}

	@Override
	public long getCPDefinitionId() {
		return _cpDefinition.getCPDefinitionId();
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels() {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public long getCProductId() {
		return _cpDefinition.getCProductId();
	}

	@Override
	public List<CPSku> getCPSkus() {
		List<CPSku> cpSkus = new ArrayList<>();

		List<CPInstance> cpInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				_cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpInstances) {
			cpSkus.add(new CPSkuImpl(cpInstance));
		}

		return cpSkus;
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
	public long getGroupId() {
		return _cpDefinition.getGroupId();
	}

	@Override
	public double getHeight() {
		return _cpDefinition.getHeight();
	}

	@Override
	public String getMetaDescription(String languageId) {
		return _cpDefinition.getMetaDescription(languageId);
	}

	@Override
	public String getMetaKeywords(String languageId) {
		return _cpDefinition.getMetaKeywords(languageId);
	}

	@Override
	public String getMetaTitle(String languageId) {
		return _cpDefinition.getMetaTitle(languageId);
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
	public String getUrl() {
		return _cpDefinition.getURL(_languageId);
	}

	@Override
	public boolean isIgnoreSKUCombinations() {
		return _cpDefinition.isIgnoreSKUCombinations();
	}

	private final CPDefinition _cpDefinition;
	private final CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;
	private final CPInstanceLocalService _cpInstanceLocalService;
	private final String _languageId;

}