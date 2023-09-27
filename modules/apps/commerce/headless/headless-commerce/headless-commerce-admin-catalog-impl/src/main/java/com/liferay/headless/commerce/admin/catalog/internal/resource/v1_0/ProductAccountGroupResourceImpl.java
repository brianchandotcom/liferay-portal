/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductAccountGroup;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductAccountGroupResource;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product-account-group.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = ProductAccountGroupResource.class
)
@CTAware
public class ProductAccountGroupResourceImpl
	extends BaseProductAccountGroupResourceImpl {

	@Override
	public void deleteProductAccountGroup(Long id) throws Exception {
		_accountGroupRelLocalService.deleteAccountGroupRel(id);
	}

	@Override
	public ProductAccountGroup getProductAccountGroup(Long id)
		throws Exception {

		return toProductAccountGroup(
			_accountGroupRelLocalService.getAccountGroupRel(id));
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

		return Page.of(
			transform(
				_accountGroupRelLocalService.getAccountGroupRels(
					CPDefinition.class.getName(),
					cpDefinition.getCPDefinitionId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::toProductAccountGroup),
			pagination,
			_accountGroupRelLocalService.getAccountGroupRelsCount(
				CPDefinition.class.getName(),
				cpDefinition.getCPDefinitionId()));
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

		return Page.of(
			transform(
				_accountGroupRelLocalService.getAccountGroupRels(
					CPDefinition.class.getName(),
					cpDefinition.getCPDefinitionId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::toProductAccountGroup),
			pagination,
			_accountGroupRelLocalService.getAccountGroupRelsCount(
				CPDefinition.class.getName(),
				cpDefinition.getCPDefinitionId()));
	}

	public ProductAccountGroup toProductAccountGroup(
			AccountGroupRel accountGroupRel)
		throws Exception {

		AccountGroup accountGroup = _accountGroupLocalService.getAccountGroup(
			accountGroupRel.getAccountGroupId());

		return new ProductAccountGroup() {
			{
				accountGroupId = accountGroupRel.getAccountGroupId();
				externalReferenceCode = accountGroup.getExternalReferenceCode();
				id = accountGroupRel.getAccountGroupRelId();
				name = accountGroup.getName();
			}
		};
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

}