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

package com.liferay.commerce.product.catalog.rule;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CPRuleType {

	public void contributeToDocument(CPRuleType cpRuleType, Document document);

	public String getKey();

	public String getLabel(Locale locale);

	public boolean isSatisfied(
			CPDefinition cpDefinition, CPRuleType cpRuleType,
			ServiceContext serviceContext)
		throws PortalException;

	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws PortalException;

}