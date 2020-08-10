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

package com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0;

import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.exception.NoSuchCProductException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.ProductDTOConverter;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.ProductDTOConverterContext;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ProductResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product.properties",
	scope = ServiceScope.PROTOTYPE, service = ProductResource.class
)
public class ProductResourceImpl extends BaseProductResourceImpl {

	@Override
	public Product getChannelProduct(
			@NotNull Long channelId, @NotNull Long productId, Long accountId)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCProductException();
		}

		_commerceProductViewPermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			_getAccountId(accountId, commerceChannel),
			commerceChannel.getGroupId(), cpDefinition.getCPDefinitionId());

		return _toProduct(cpDefinition);
	}

	@Override
	public Page<Product> getChannelProductsPage(
			@NotNull Long channelId, Long accountId, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		long companyId = contextCompany.getCompanyId();
		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.STATUS, WorkflowConstants.STATUS_APPROVED);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		attributes.put("commerceChannelGroupId", commerceChannel.getGroupId());

		long[] commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				_getAccountId(accountId, commerceChannel));

		searchContext.setAttribute(
			"commerceAccountGroupIds", commerceAccountGroupIds);

		searchContext.setAttributes(attributes);
		searchContext.setCompanyId(companyId);

		CPQuery cpQuery = new CPQuery();

		cpQuery.setOrderByCol1("title");
		cpQuery.setOrderByCol2("modifiedDate");
		cpQuery.setOrderByType1("ASC");
		cpQuery.setOrderByType2("DESC");

		CPDataSourceResult cpDataSourceResult = _cpDefinitionHelper.search(
			commerceChannel.getGroupId(), searchContext, cpQuery,
			pagination.getStartPosition(), pagination.getEndPosition());

		return Page.of(
			_toProducts(cpDataSourceResult), pagination,
			cpDataSourceResult.getLength());
	}

	private Long _getAccountId(Long accountId, CommerceChannel commerceChannel)
		throws PortalException {

		int countUserCommerceAccounts =
			_commerceAccountHelper.countUserCommerceAccounts(
				contextUser.getUserId(), commerceChannel.getGroupId());

		if (countUserCommerceAccounts > 1) {
			if (accountId == null) {
				throw new NoSuchAccountException();
			}
		}
		else {
			long[] commerceAccountIds =
				_commerceAccountHelper.getUserCommerceAccountIds(
					contextUser.getUserId(), commerceChannel.getGroupId());

			return commerceAccountIds[0];
		}

		return accountId;
	}

	private Product _toProduct(CPDefinition cpDefinition) throws Exception {
		return _productDTOConverter.toDTO(
			new ProductDTOConverterContext(
				contextAcceptLanguage.getPreferredLocale(),
				cpDefinition.getCPDefinitionId(), cpDefinition));
	}

	private List<Product> _toProducts(CPDataSourceResult cpDataSourceResult)
		throws Exception {

		List<Product> products = new ArrayList<>();

		for (CPCatalogEntry cpCatalogEntry :
				cpDataSourceResult.getCPCatalogEntries()) {

			products.add(
				_productDTOConverter.toDTO(
					new ProductDTOConverterContext(
						contextAcceptLanguage.getPreferredLocale(),
						cpCatalogEntry.getCPDefinitionId(), cpCatalogEntry)));
		}

		return products;
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private ProductDTOConverter _productDTOConverter;

}