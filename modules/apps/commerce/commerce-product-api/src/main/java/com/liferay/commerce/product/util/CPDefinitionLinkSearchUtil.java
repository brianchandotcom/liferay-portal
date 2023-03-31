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

package com.liferay.commerce.product.util;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionLinkSearchUtil {

	public static SearchContext getCPDefinitionLinkSearchContext(
		AccountEntry accountEntry, CommerceAccountHelper commerceAccountHelper,
		long companyId, long cpDefinitionId, String definitionLinkType) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED
			).put(
				"commerceAccountGroupIds",
				() -> {
					if (accountEntry == null) {
						return null;
					}

					return commerceAccountHelper.getCommerceAccountGroupIds(
						accountEntry.getAccountEntryId());
				}
			).put(
				"definitionLinkCPDefinitionId", cpDefinitionId
			).put(
				"definitionLinkType", definitionLinkType
			).put(
				"excludedCPDefinitionId", cpDefinitionId
			).build());
		searchContext.setCompanyId(companyId);

		return searchContext;
	}

}