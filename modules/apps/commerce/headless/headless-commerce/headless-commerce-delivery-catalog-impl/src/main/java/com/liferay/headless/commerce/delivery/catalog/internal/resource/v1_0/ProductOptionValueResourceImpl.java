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

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.product.exception.NoSuchCProductException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.util.CommerceAccountHelper;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ProductOptionValueResource;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product-option-value.properties",
	scope = ServiceScope.PROTOTYPE, service = ProductOptionValueResource.class
)
@CTAware
public class ProductOptionValueResourceImpl
	extends BaseProductOptionValueResourceImpl {

	@Override
	public Page<ProductOptionValue>
			getChannelProductProductOptionProductOptionValuesPage(
				Long channelId, Long productId, Long productOptionId,
				Long accountId, Long productOptionValueId, Long skuId,
				Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCProductException();
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		_commerceProductViewPermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			_getAccountEntryId(accountId, commerceChannel),
			commerceChannel.getGroupId(), cpDefinition.getCPDefinitionId());

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			_cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRels(
					productOptionId, pagination.getStartPosition(),
					pagination.getEndPosition());

		int totalItems =
			_cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRelsCount(productOptionId);

		return Page.of(
			_toProductOptionValues(
				_commerceContextFactory.create(
					contextCompany.getCompanyId(), commerceChannel.getGroupId(),
					contextUser.getUserId(), 0, accountId),
				cpDefinitionOptionValueRels, productOptionValueId, skuId),
			pagination, totalItems);
	}

	private Long _getAccountEntryId(
			Long accountId, CommerceChannel commerceChannel)
		throws Exception {

		int userAccountEntriesCount =
			_commerceAccountHelper.countUserCommerceAccounts(
				contextUser.getUserId(), commerceChannel.getGroupId());

		if (userAccountEntriesCount > 1) {
			if (accountId == null) {
				throw new NoSuchEntryException();
			}
		}
		else {
			long[] accountEntryIds =
				_commerceAccountHelper.getUserCommerceAccountIds(
					contextUser.getUserId(), commerceChannel.getGroupId());

			if (accountEntryIds.length == 0) {
				AccountEntry accountEntry =
					_accountEntryLocalService.getGuestAccountEntry(
						contextCompany.getCompanyId());

				accountEntryIds = new long[] {accountEntry.getAccountEntryId()};
			}

			return accountEntryIds[0];
		}

		return accountId;
	}

	private List<ProductOptionValue> _toProductOptionValues(
			CommerceContext commerceContext,
			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
			Long productOptionValueId, Long skuId)
		throws Exception {

		List<ProductOptionValue> productOptionValues = new ArrayList<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			DefaultDTOConverterContext defaultDTOConverterContext =
				new DefaultDTOConverterContext(
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId(),
					contextAcceptLanguage.getPreferredLocale());

			defaultDTOConverterContext.setAttribute(
				"commerceContext", commerceContext);
			defaultDTOConverterContext.setAttribute(
				"productOptionValueId", productOptionValueId);
			defaultDTOConverterContext.setAttribute("skuId", skuId);

			productOptionValues.add(
				_productOptionValueDTOConverter.toDTO(
					defaultDTOConverterContext));
		}

		return productOptionValues;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference(
		target = DTOConverterConstants.PRODUCT_OPTION_VALUE_DTO_CONVERTER
	)
	private DTOConverter<CPDefinitionOptionValueRel, ProductOptionValue>
		_productOptionValueDTOConverter;

}