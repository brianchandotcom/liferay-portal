/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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