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

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductAccountGroup;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductAccountGroupResource;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/product-account-group.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, ProductAccountGroupResource.class}
)
@CTAware
public class ProductAccountGroupResourceImpl
	extends BaseProductAccountGroupResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteProductAccountGroup(Long id) throws Exception {
		_commerceAccountGroupRelService.deleteCommerceAccountGroupRel(id);
	}

	@Override
	public ProductAccountGroup getProductAccountGroup(Long id)
		throws Exception {

		return toProductAccountGroup(
			_commerceAccountGroupRelService.getCommerceAccountGroupRel(id));
	}

	@Override
	public Page<ProductAccountGroup>
			getProductByExternalReferenceCodeProductAccountGroupsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			return Page.of(Collections.emptyList());
		}

		int commerceAccountGroupRelsCount =
			_commerceAccountGroupRelService.getCommerceAccountGroupRelsCount(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		return Page.of(
			TransformUtil.transform(
				_commerceAccountGroupRelService.getCommerceAccountGroupRels(
					CPDefinition.class.getName(),
					cpDefinition.getCPDefinitionId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::toProductAccountGroup),
			pagination, commerceAccountGroupRelsCount);
	}

	@NestedField(parentClass = Product.class, value = "productAccountGroups")
	@Override
	public Page<ProductAccountGroup> getProductIdProductAccountGroupsPage(
			@NestedFieldId(value = "productId") Long id, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			return Page.of(Collections.emptyList());
		}

		int commerceAccountGroupRelsCount =
			_commerceAccountGroupRelService.getCommerceAccountGroupRelsCount(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		return Page.of(
			TransformUtil.transform(
				_commerceAccountGroupRelService.getCommerceAccountGroupRels(
					CPDefinition.class.getName(),
					cpDefinition.getCPDefinitionId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::toProductAccountGroup),
			pagination, commerceAccountGroupRelsCount);
	}

	public ProductAccountGroup toProductAccountGroup(
			CommerceAccountGroupRel commerceAccountGroupRel)
		throws Exception {

		CommerceAccountGroup commerceAccountGroup =
			commerceAccountGroupRel.getCommerceAccountGroup();

		return new ProductAccountGroup() {
			{
				accountGroupId =
					commerceAccountGroupRel.getCommerceAccountGroupId();
				externalReferenceCode =
					commerceAccountGroup.getExternalReferenceCode();
				id = commerceAccountGroupRel.getCommerceAccountGroupRelId();
				name = commerceAccountGroup.getName();
			}
		};
	}

	@Reference
	private CommerceAccountGroupRelService _commerceAccountGroupRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

}