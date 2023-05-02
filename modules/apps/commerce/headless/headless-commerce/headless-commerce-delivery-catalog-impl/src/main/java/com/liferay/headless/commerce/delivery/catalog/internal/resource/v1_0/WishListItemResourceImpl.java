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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishList;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishListItem;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.WishListItemResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Mahmoud Azzam
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/wish-list-item.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, WishListItemResource.class}
)
public class WishListItemResourceImpl
	extends BaseWishListItemResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteWishListItem(Long wishListItemId) throws Exception {
		_commerceWishListItemService.deleteCommerceWishListItem(wishListItemId);
	}

	@Override
	public WishListItem getWishListItem(Long wishListItemId, Long accountId)
		throws Exception {

		CommerceWishListItem commerceWishListItem =
			_commerceWishListItemService.getCommerceWishListItem(
				wishListItemId);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				commerceWishListItem.getGroupId());

		if (commerceChannel == null) {
			throw new NoSuchChannelException();
		}

		CommerceContext commerceContext = _commerceContextFactory.create(
			commerceChannel.getCompanyId(), commerceChannel.getGroupId(),
			commerceChannel.getUserId(), 0,
			_getCommerceAccountId(accountId, commerceChannel));

		return _toWishListItem(commerceWishListItem, commerceContext);
	}

	@NestedField(parentClass = WishList.class, value = "wishListItems")
	@Override
	public Page<WishListItem> getWishlistWishListWishListItemsPage(
			@NestedFieldId("id") Long wishListId, Long accountId,
			Pagination pagination)
		throws Exception {

		CommerceWishList commerceWishList =
			_commerceWishListService.getCommerceWishList(wishListId);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				commerceWishList.getGroupId());

		if (commerceChannel == null) {
			throw new NoSuchChannelException();
		}

		CommerceContext commerceContext = _commerceContextFactory.create(
			commerceChannel.getCompanyId(), commerceChannel.getGroupId(),
			commerceChannel.getUserId(), 0,
			_getCommerceAccountId(accountId, commerceChannel));

		return Page.of(
			transform(
				_commerceWishListItemService.getCommerceWishListItems(
					wishListId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				commerceWishListItem -> _toWishListItem(
					commerceWishListItem, commerceContext)),
			pagination,
			_commerceWishListItemService.getCommerceWishListItemsCount(
				wishListId));
	}

	@Override
	public WishListItem postWishlistWishListWishListItem(
			Long wishListId, Long accountId, WishListItem wishListItem)
		throws Exception {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			GetterUtil.getLong(wishListItem.getSkuId()));

		String cpInstanceUuid = StringPool.BLANK;

		if (cpInstance != null) {
			cpInstanceUuid = cpInstance.getCPInstanceUuid();
		}

		CommerceWishList commerceWishList =
			_commerceWishListService.getCommerceWishList(wishListId);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				commerceWishList.getGroupId());

		if (commerceChannel == null) {
			throw new NoSuchChannelException();
		}

		CommerceWishListItem commerceWishListItem =
			_commerceWishListItemService.addCommerceWishListItem(
				_getCommerceAccountId(accountId, commerceChannel), wishListId,
				wishListItem.getProductId(), cpInstanceUuid,
				wishListItem.toString(),
				_serviceContextHelper.getServiceContext(
					commerceChannel.getSiteGroupId()));

		return _toWishListItem(
			commerceWishListItem,
			_commerceContextFactory.create(
				commerceChannel.getCompanyId(), commerceChannel.getGroupId(),
				commerceChannel.getUserId(), 0,
				_getCommerceAccountId(accountId, commerceChannel)));
	}

	private Long _getCommerceAccountId(
			Long accountId, CommerceChannel commerceChannel)
		throws Exception {

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

			if (commerceAccountIds.length == 0) {
				AccountEntry accountEntry =
					_accountEntryLocalService.getGuestAccountEntry(
						contextCompany.getCompanyId());

				commerceAccountIds = new long[] {
					accountEntry.getAccountEntryId()
				};
			}

			return commerceAccountIds[0];
		}

		return accountId;
	}

	private WishListItem _toWishListItem(
			CommerceWishListItem commerceWishListItem,
			CommerceContext commerceContext)
		throws Exception {

		return _wishListItemDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				_dtoConverterRegistry,
				commerceWishListItem.getCommerceWishListItemId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			commerceContext);
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
	private CommerceWishListItemService _commerceWishListItemService;

	@Reference
	private CommerceWishListService _commerceWishListService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.WishListItemDTOConverter)"
	)
	private DTOConverter<CommerceContext, WishListItem>
		_wishListItemDTOConverter;

}