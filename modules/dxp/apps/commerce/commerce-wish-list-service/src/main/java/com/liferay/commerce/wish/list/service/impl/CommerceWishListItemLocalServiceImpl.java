/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.wish.list.service.impl;

import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.wish.list.exception.GuestWishListItemMaxAllowedException;
import com.liferay.commerce.wish.list.internal.configuration.CommerceWishListConfiguration;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.base.CommerceWishListItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWishListItemLocalServiceImpl
	extends CommerceWishListItemLocalServiceBaseImpl {

	@Override
	public CommerceWishListItem addCommerceWishListItem(
			long commerceWishListId, long cpDefinitionId, long cpInstanceId,
			String json, ServiceContext serviceContext)
		throws PortalException {

		CommerceWishList commerceWishList =
			commerceWishListLocalService.getCommerceWishList(
				commerceWishListId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		validate(commerceWishList, cpDefinitionId, cpInstanceId);

		long commerceWishListItemId = counterLocalService.increment();

		CommerceWishListItem commerceWishListItem =
			commerceWishListItemPersistence.create(commerceWishListItemId);

		commerceWishListItem.setGroupId(commerceWishList.getGroupId());
		commerceWishListItem.setCompanyId(user.getCompanyId());
		commerceWishListItem.setUserId(user.getUserId());
		commerceWishListItem.setUserName(user.getFullName());
		commerceWishListItem.setCommerceWishListId(
			commerceWishList.getCommerceWishListId());
		commerceWishListItem.setCPDefinitionId(cpDefinitionId);
		commerceWishListItem.setCPInstanceId(cpInstanceId);
		commerceWishListItem.setJson(json);

		commerceWishListItemPersistence.update(commerceWishListItem);

		return commerceWishListItem;
	}

	@Override
	public void deleteCommerceWishListItems(long commerceWishListId) {
		commerceWishListItemPersistence.removeByCommerceWishListId(
			commerceWishListId);
	}

	@Override
	public void deleteCommerceWishListItemsByCPDefinitionId(
		long cpDefinitionId) {

		commerceWishListItemPersistence.removeByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public void deleteCommerceWishListItemsByCPInstanceId(long cpInstanceId) {
		commerceWishListItemPersistence.removeByCPInstanceId(cpInstanceId);
	}

	@Override
	public List<CommerceWishListItem> getCommerceWishListItems(
		long commerceWishListId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return commerceWishListItemPersistence.findByCommerceWishListId(
			commerceWishListId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWishListItemsCount(long commerceWishListId) {
		return commerceWishListItemPersistence.countByCommerceWishListId(
			commerceWishListId);
	}

	protected void validate(
			CommerceWishList commerceWishList, long cpDefinitionId,
			long cpInstanceId)
		throws PortalException {

		if (commerceWishList.getUserId() == 0) {
			int count =
				commerceWishListItemPersistence.countByCommerceWishListId(
					commerceWishList.getCommerceWishListId());

			if (count >=
					_commerceWishListConfiguration.
						guestWishListItemMaxAllowed()) {

				throw new GuestWishListItemMaxAllowedException();
			}
		}

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (cpInstanceId > 0) {
			CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
				cpInstanceId);

			if (cpInstance.getCPDefinitionId() !=
					cpDefinition.getCPDefinitionId()) {

				throw new NoSuchCPInstanceException(
					"CPInstance " + cpInstance.getCPInstanceId() +
						" belongs to a different CPDefinition than " +
							cpDefinition.getCPDefinitionId());
			}
		}
	}

	@ServiceReference(type = CommerceWishListConfiguration.class)
	private CommerceWishListConfiguration _commerceWishListConfiguration;

	@ServiceReference(type = CPDefinitionLocalService.class)
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@ServiceReference(type = CPInstanceLocalService.class)
	private CPInstanceLocalService _cpInstanceLocalService;

}