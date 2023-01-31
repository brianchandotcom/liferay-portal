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
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.type.grouped.constants.GroupedCPTypeConstants;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.commerce.shop.by.diagram.constants.CSDiagramCPTypeConstants;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.LinkedProduct;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.LinkedProductDTOConverter;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.LinkedProductDTOConverterContext;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.LinkedProductResource;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/linked-product.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {LinkedProductResource.class, NestedFieldSupport.class}
)
public class LinkedProductResourceImpl
	extends BaseLinkedProductResourceImpl implements NestedFieldSupport {

	@NestedField(parentClass = Product.class, value = "linkedProducts")
	@Override
	public Page<LinkedProduct> getChannelProductLinkedProductsPage(
			@NestedFieldId(value = "channelId") Long channelId,
			@NestedFieldId(value = "productId") Long productId, Long accountId,
			Pagination pagination)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		long selectedAccountId = _getSelectedAccountId(
			accountId, commerceChannel);

		CProduct cProduct = _cProductLocalService.getCProduct(productId);

		_commerceProductViewPermission.check(
			PermissionThreadLocal.getPermissionChecker(), selectedAccountId,
			commerceChannel.getGroupId(),
			cProduct.getPublishedCPDefinitionId());

		List<LinkedProduct> linkedProductList = ListUtil.concat(
			transform(
				_cpDefinitionGroupedEntryService.
					getEntryCProductCPDefinitionGroupedEntries(
						productId, pagination.getStartPosition(),
						pagination.getEndPosition(), null),
				cpDefinitionGroupedEntry -> _linkedProductDTOConverter.toDTO(
					new LinkedProductDTOConverterContext(
						contextAcceptLanguage.isAcceptAllLanguages(),
						selectedAccountId, null,
						commerceChannel.getCommerceChannelId(),
						_dtoConverterRegistry,
						cpDefinitionGroupedEntry.
							getCPDefinitionGroupedEntryId(),
						contextAcceptLanguage.getPreferredLocale(),
						GroupedCPTypeConstants.NAME, contextUriInfo,
						contextUser))),
			transform(
				_csDiagramEntryService.getCProductCSDiagramEntries(
					productId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				csDiagramEntry -> _linkedProductDTOConverter.toDTO(
					new LinkedProductDTOConverterContext(
						contextAcceptLanguage.isAcceptAllLanguages(),
						selectedAccountId, null,
						commerceChannel.getCommerceChannelId(),
						_dtoConverterRegistry,
						csDiagramEntry.getCSDiagramEntryId(),
						contextAcceptLanguage.getPreferredLocale(),
						CSDiagramCPTypeConstants.NAME, contextUriInfo,
						contextUser))));

		return Page.of(linkedProductList, pagination, linkedProductList.size());
	}

	private Long _getSelectedAccountId(
			Long accountId, CommerceChannel commerceChannel)
		throws Exception {

		int countUserCommerceAccounts =
			_commerceAccountHelper.countUserCommerceAccounts(
				contextUser.getUserId(), commerceChannel.getGroupId());

		if (countUserCommerceAccounts > 1) {
			if (accountId == null) {
				MultivaluedMap<String, String> queryParameters =
					contextUriInfo.getQueryParameters();

				String queryParameterAccountId = queryParameters.getFirst(
					"accountId");

				if (queryParameterAccountId != null) {
					accountId = Long.valueOf(queryParameterAccountId);
				}
				else {
					throw new NoSuchAccountException();
				}
			}
		}
		else {
			long[] commerceAccountIds =
				_commerceAccountHelper.getUserCommerceAccountIds(
					contextUser.getUserId(), commerceChannel.getGroupId());

			if (commerceAccountIds.length == 0) {
				CommerceAccount commerceAccount =
					_commerceAccountLocalService.getGuestCommerceAccount(
						contextUser.getCompanyId());

				commerceAccountIds = new long[] {
					commerceAccount.getCommerceAccountId()
				};
			}

			return commerceAccountIds[0];
		}

		return accountId;
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPDefinitionGroupedEntryService _cpDefinitionGroupedEntryService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private CSDiagramEntryService _csDiagramEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private LinkedProductDTOConverter _linkedProductDTOConverter;

}