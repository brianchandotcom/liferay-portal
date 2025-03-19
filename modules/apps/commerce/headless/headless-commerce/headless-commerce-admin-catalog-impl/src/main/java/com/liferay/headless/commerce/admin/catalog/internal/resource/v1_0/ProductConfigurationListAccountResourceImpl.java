/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.product.exception.NoSuchCPConfigurationListException;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPConfigurationListRel;
import com.liferay.commerce.product.service.CPConfigurationListRelService;
import com.liferay.commerce.product.service.CPConfigurationListService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfigurationList;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfigurationListAccount;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductConfigurationListAccountResource;
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
	properties = "OSGI-INF/liferay/rest/v1_0/product-configuration-list-account.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = ProductConfigurationListAccountResource.class
)
public class ProductConfigurationListAccountResourceImpl
	extends BaseProductConfigurationListAccountResourceImpl {

	@Override
	public void deleteProductConfigurationListAccount(Long id)
		throws Exception {

		_cpConfigurationListRelService.deleteCPConfigurationListRel(id);
	}

	@Override
	public Page<ProductConfigurationListAccount>
			getProductConfigurationListByExternalReferenceCodeProductConfigurationListAccountsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.
				fetchCPConfigurationListByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpConfigurationList == null) {
			throw new NoSuchCPConfigurationListException(
				"Unable to find product configuration list account with " +
					"external reference code " + externalReferenceCode);
		}

		return getProductConfigurationListIdProductConfigurationListAccountsPage(
			cpConfigurationList.getCPConfigurationListId(), null, null,
			pagination, null);
	}

	@NestedField(
		parentClass = ProductConfigurationList.class,
		value = "productConfigurationListAccounts"
	)
	@Override
	public Page<ProductConfigurationListAccount>
			getProductConfigurationListIdProductConfigurationListAccountsPage(
				Long id, String search, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		return Page.of(
			_toProductConfigurationListAccounts(
				_cpConfigurationListRelService.
					getAccountEntryCPConfigurationListRels(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition())),
			pagination,
			_cpConfigurationListRelService.
				getAccountEntryCPConfigurationListRelsCount(id, search));
	}

	@Override
	public ProductConfigurationListAccount
			postProductConfigurationListByExternalReferenceCodeProductConfigurationListAccount(
				String externalReferenceCode,
				ProductConfigurationListAccount productConfigurationListAccount)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.
				fetchCPConfigurationListByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpConfigurationList == null) {
			throw new NoSuchCPConfigurationListException(
				"Unable to find product configuration list account with " +
					"external reference code " + externalReferenceCode);
		}

		return postProductConfigurationListIdProductConfigurationListAccount(
			cpConfigurationList.getCPConfigurationListId(),
			productConfigurationListAccount);
	}

	@Override
	public ProductConfigurationListAccount
			postProductConfigurationListIdProductConfigurationListAccount(
				Long id,
				ProductConfigurationListAccount productConfigurationListAccount)
		throws Exception {

		CPConfigurationListRel cpConfigurationListRel =
			_addCPConfigurationListRel(
				_cpConfigurationListService.getCPConfigurationList(id),
				productConfigurationListAccount);

		return _toProductConfigurationListAccount(
			cpConfigurationListRel.getCPConfigurationListRelId());
	}

	private CPConfigurationListRel _addCPConfigurationListRel(
			CPConfigurationList cpConfigurationList,
			ProductConfigurationListAccount productConfigurationListAccount)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryService.fetchAccountEntryByExternalReferenceCode(
				GetterUtil.getString(
					productConfigurationListAccount.
						getAccountExternalReferenceCode()),
				cpConfigurationList.getCompanyId());

		if (accountEntry == null) {
			accountEntry = _accountEntryService.getAccountEntry(
				productConfigurationListAccount.getAccountId());
		}

		return _cpConfigurationListRelService.addCPConfigurationListRel(
			AccountEntry.class.getName(), accountEntry.getAccountEntryId(),
			cpConfigurationList.getCPConfigurationListId());
	}

	private Map<String, Map<String, String>> _getActions(
			long cpConfigurationListRelId)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", cpConfigurationListRelId,
				"deleteProductConfigurationListAccount",
				_cpConfigurationListRelModelResourcePermission)
		).build();
	}

	private ProductConfigurationListAccount _toProductConfigurationListAccount(
			Long cpConfigurationListRelId)
		throws Exception {

		return _productConfigurationListAccountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(cpConfigurationListRelId), _dtoConverterRegistry,
				cpConfigurationListRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<ProductConfigurationListAccount>
		_toProductConfigurationListAccounts(
			List<CPConfigurationListRel> cpConfigurationListRels) {

		return transform(
			cpConfigurationListRels,
			cpConfigurationListRel -> _toProductConfigurationListAccount(
				cpConfigurationListRel.getCPConfigurationListRelId()));
	}

	@Reference
	private AccountEntryService _accountEntryService;

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
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductConfigurationListAccountDTOConverter)"
	)
	private DTOConverter
		<CPConfigurationListRel, ProductConfigurationListAccount>
			_productConfigurationListAccountDTOConverter;

}