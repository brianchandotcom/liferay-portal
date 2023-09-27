/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = ProductHelper.class)
public class ProductHelper {

	public Page<Product> getProductsPage(
			long companyId, String search, Filter filter, Pagination pagination,
			Sort[] sorts,
			UnsafeFunction<Document, Product, Exception>
				transformUnsafeFunction,
			Locale preferredLocale)
		throws Exception {

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CPDefinition.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			object -> {
				SearchContext searchContext = (SearchContext)object;

				searchContext.setCompanyId(companyId);

				long[] commerceCatalogGroupIds =
					TransformUtil.transformToLongArray(
						_commerceCatalogLocalService.search(companyId),
						CommerceCatalog::getGroupId);

				if ((commerceCatalogGroupIds != null) &&
					(commerceCatalogGroupIds.length > 0)) {

					searchContext.setGroupIds(commerceCatalogGroupIds);
				}

				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_ANY);

				if (preferredLocale != null) {
					searchContext.setLocale(preferredLocale);
				}
			},
			sorts, transformUnsafeFunction);
	}

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

}