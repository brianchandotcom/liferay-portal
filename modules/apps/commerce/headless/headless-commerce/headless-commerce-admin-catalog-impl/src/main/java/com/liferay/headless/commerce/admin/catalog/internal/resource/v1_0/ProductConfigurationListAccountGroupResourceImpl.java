/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupService;
import com.liferay.commerce.product.exception.NoSuchCPConfigurationListException;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPConfigurationListRel;
import com.liferay.commerce.product.service.CPConfigurationListRelService;
import com.liferay.commerce.product.service.CPConfigurationListService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfigurationList;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfigurationListAccountGroup;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductConfigurationListAccountGroupResource;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Danny Situ
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product-configuration-list-account-group.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = ProductConfigurationListAccountGroupResource.class
)
public class ProductConfigurationListAccountGroupResourceImpl
	extends BaseProductConfigurationListAccountGroupResourceImpl {

	@Override
	public void deleteProductConfigurationListAccountGroup(Long id)
		throws Exception {

		_cpConfigurationListRelService.deleteCPConfigurationListRel(id);
	}

	@Override
	public Page<ProductConfigurationListAccountGroup>
			getProductConfigurationListByExternalReferenceCodeProductConfigurationListAccountGroupsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.
				fetchCPConfigurationListByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpConfigurationList == null) {
			throw new NoSuchCPConfigurationListException(
				"Unable to find product configuration list with external " +
					"reference code " + externalReferenceCode);
		}

		return getProductConfigurationListIdProductConfigurationListAccountGroupsPage(
			cpConfigurationList.getCPConfigurationListId(), null, null,
			pagination, null);
	}

	@NestedField(
		parentClass = ProductConfigurationList.class,
		value = "productConfigurationListAccountGroups"
	)
	@Override
	public Page<ProductConfigurationListAccountGroup>
			getProductConfigurationListIdProductConfigurationListAccountGroupsPage(
				Long id, String search, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		return Page.of(
			_toProductConfigurationListAccountGroups(
				_cpConfigurationListRelService.
					getAccountGroupCPConfigurationListRels(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition())),
			pagination,
			_cpConfigurationListRelService.
				getAccountGroupCPConfigurationListRelsCount(id, search));
	}

	@Override
	public ProductConfigurationListAccountGroup
			postProductConfigurationListByExternalReferenceCodeProductConfigurationListAccountGroup(
				String externalReferenceCode,
				ProductConfigurationListAccountGroup
					productConfigurationListAccountGroup)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.
				fetchCPConfigurationListByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpConfigurationList == null) {
			throw new NoSuchCPConfigurationListException(
				"Unable to find product configuration list with external " +
					"reference code " + externalReferenceCode);
		}

		return postProductConfigurationListIdProductConfigurationListAccountGroup(
			cpConfigurationList.getCPConfigurationListId(),
			productConfigurationListAccountGroup);
	}

	@Override
	public ProductConfigurationListAccountGroup
			postProductConfigurationListIdProductConfigurationListAccountGroup(
				Long id,
				ProductConfigurationListAccountGroup
					productConfigurationListAccountGroup)
		throws Exception {

		CPConfigurationListRel cpConfigurationListRel =
			_addCPConfigurationListRel(
				_cpConfigurationListService.getCPConfigurationList(id),
				productConfigurationListAccountGroup);

		return _toProductConfigurationListAccountGroup(
			cpConfigurationListRel.getCPConfigurationListRelId());
	}

	private CPConfigurationListRel _addCPConfigurationListRel(
			CPConfigurationList cpConfigurationList,
			ProductConfigurationListAccountGroup
				productConfigurationListAccountGroup)
		throws Exception {

		AccountGroup accountGroup =
			_accountGroupService.fetchAccountGroupByExternalReferenceCode(
				GetterUtil.getString(
					productConfigurationListAccountGroup.
						getAccountGroupExternalReferenceCode()),
				cpConfigurationList.getCompanyId());

		if (accountGroup == null) {
			accountGroup = _accountGroupService.getAccountGroup(
				productConfigurationListAccountGroup.getAccountGroupId());
		}

		return _cpConfigurationListRelService.addCPConfigurationListRel(
			AccountGroup.class.getName(), accountGroup.getAccountGroupId(),
			cpConfigurationList.getCPConfigurationListId());
	}

	private Map<String, Map<String, String>> _getActions(
			long cpConfigurationListRelId)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", cpConfigurationListRelId,
				"deleteProductConfigurationListAccountGroup",
				_cpConfigurationListRelModelResourcePermission)
		).build();
	}

	private ProductConfigurationListAccountGroup
			_toProductConfigurationListAccountGroup(
				Long cpConfigurationListRelId)
		throws Exception {

		return _productConfigurationListAccountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(cpConfigurationListRelId), _dtoConverterRegistry,
				cpConfigurationListRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<ProductConfigurationListAccountGroup>
		_toProductConfigurationListAccountGroups(
			List<CPConfigurationListRel> cpConfigurationListRels) {

		return transform(
			cpConfigurationListRels,
			cpConfigurationListRel -> _toProductConfigurationListAccountGroup(
				cpConfigurationListRel.getCPConfigurationListRelId()));
	}

	@Reference
	private AccountGroupService _accountGroupService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPConfigurationListRel)"
	)
	private ModelResourcePermission<CPConfigurationListRel>
		_cpConfigurationListRelModelResourcePermission;

	@Reference
	private CPConfigurationListRelService _cpConfigurationListRelService;

	@Reference
	private CPConfigurationListService _cpConfigurationListService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductConfigurationListAccountGroupDTOConverter)"
	)
	private DTOConverter
		<CPConfigurationListRel, ProductConfigurationListAccountGroup>
			_productConfigurationListAccountGroupDTOConverter;

}