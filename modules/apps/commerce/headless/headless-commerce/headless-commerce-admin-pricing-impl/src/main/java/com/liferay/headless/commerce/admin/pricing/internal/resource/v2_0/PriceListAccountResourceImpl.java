/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccount;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceListAccountUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListAccountResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/price-list-account.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = PriceListAccountResource.class
)
public class PriceListAccountResourceImpl
	extends BasePriceListAccountResourceImpl {

	@Override
	public void deletePriceListAccount(Long id) throws Exception {
		_commercePriceListAccountRelService.deleteCommercePriceListAccountRel(
			id);
	}

	@Override
	public Page<PriceListAccount>
			getPriceListByExternalReferenceCodePriceListAccountsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find price list with external reference code " +
					externalReferenceCode);
		}

		List<CommercePriceListAccountRel> commercePriceListAccountRels =
			_commercePriceListAccountRelService.getCommercePriceListAccountRels(
				commercePriceList.getCommercePriceListId(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commercePriceListAccountRelService.
				getCommercePriceListAccountRelsCount(
					commercePriceList.getCommercePriceListId());

		return Page.of(
			_toPriceListAccounts(commercePriceListAccountRels), pagination,
			totalItems);
	}

	@NestedField(parentClass = PriceList.class, value = "priceListAccounts")
	@Override
	public Page<PriceListAccount> getPriceListIdPriceListAccountsPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<CommercePriceListAccountRel> commercePriceListAccountRels =
			_commercePriceListAccountRelService.getCommercePriceListAccountRels(
				id, search, pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems =
			_commercePriceListAccountRelService.
				getCommercePriceListAccountRelsCount(id, search);

		return Page.of(
			_toPriceListAccounts(commercePriceListAccountRels), pagination,
			totalItems);
	}

	@Override
	public PriceListAccount
			postPriceListByExternalReferenceCodePriceListAccount(
				String externalReferenceCode, PriceListAccount priceListAccount)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find price list with external reference code " +
					externalReferenceCode);
		}

		CommercePriceListAccountRel commercePriceListAccountRel =
			PriceListAccountUtil.addCommercePriceListAccountRel(
				_accountEntryService, _commercePriceListAccountRelService,
				priceListAccount, commercePriceList, _serviceContextHelper);

		return _toPriceListAccount(
			commercePriceListAccountRel.getCommercePriceListAccountRelId());
	}

	@Override
	public PriceListAccount postPriceListIdPriceListAccount(
			Long id, PriceListAccount priceListAccount)
		throws Exception {

		CommercePriceListAccountRel commercePriceListAccountRel =
			PriceListAccountUtil.addCommercePriceListAccountRel(
				_accountEntryService, _commercePriceListAccountRelService,
				priceListAccount,
				_commercePriceListService.getCommercePriceList(id),
				_serviceContextHelper);

		return _toPriceListAccount(
			commercePriceListAccountRel.getCommercePriceListAccountRelId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommercePriceListAccountRel commercePriceListAccountRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE",
				commercePriceListAccountRel.getCommercePriceListAccountRelId(),
				"deletePriceListAccount",
				_commercePriceListAccountRelModelResourcePermission)
		).build();
	}

	private PriceListAccount _toPriceListAccount(
			Long commercePriceListAccountRelId)
		throws Exception {

		CommercePriceListAccountRel commercePriceListAccountRel =
			_commercePriceListAccountRelService.getCommercePriceListAccountRel(
				commercePriceListAccountRelId);

		return _priceListAccountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commercePriceListAccountRel), _dtoConverterRegistry,
				commercePriceListAccountRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<PriceListAccount> _toPriceListAccounts(
			List<CommercePriceListAccountRel> commercePriceListAccountRels)
		throws Exception {

		List<PriceListAccount> priceListAccounts = new ArrayList<>();

		for (CommercePriceListAccountRel commercePriceListAccountRel :
				commercePriceListAccountRels) {

			priceListAccounts.add(
				_toPriceListAccount(
					commercePriceListAccountRel.
						getCommercePriceListAccountRelId()));
		}

		return priceListAccounts;
	}

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceListAccountRel)"
	)
	private ModelResourcePermission<CommercePriceListAccountRel>
		_commercePriceListAccountRelModelResourcePermission;

	@Reference
	private CommercePriceListAccountRelService
		_commercePriceListAccountRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.PriceListAccountDTOConverter)"
	)
	private DTOConverter<CommercePriceListAccountRel, PriceListAccount>
		_priceListAccountDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}