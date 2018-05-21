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

package com.liferay.commerce.product.internal.catalog.rule;

import com.liferay.commerce.product.catalog.rule.CPRuleType;
import com.liferay.commerce.product.constants.CPRuleConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.rule.type.key=" + CPRuleConstants.TYPE_ASSET_CATEGORY,
		"commerce.product.rule.type.order:Integer=10"
	},
	service = CPRuleType.class
)
public class AssetCategoryCPRuleTypeImpl implements CPRuleType {

	@Override
	public void contributeToDocument(CPRuleType cpRuleType, Document document) {
	}

	@Override
	public String getKey() {
		return CPRuleConstants.TYPE_ASSET_CATEGORY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "categories");
	}

	@Override
	public boolean isSatisfied(
			CPDefinition cpDefinition, CPRuleType cpRuleType,
			ServiceContext serviceContext)
		throws PortalException {

		return false;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws PortalException {
	}

}