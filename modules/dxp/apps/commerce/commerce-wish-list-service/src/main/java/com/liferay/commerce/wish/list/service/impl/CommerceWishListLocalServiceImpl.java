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

package com.liferay.commerce.wish.list.service.impl;

import com.liferay.commerce.wish.list.exception.CommerceWishListNameException;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.base.CommerceWishListLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWishListLocalServiceImpl
	extends CommerceWishListLocalServiceBaseImpl {

	@Override
	public CommerceWishList addCommerceWishList(
			String name, boolean defaultWishList, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(0, groupId, user.getUserId(), name, defaultWishList);

		long commerceWishListId = counterLocalService.increment();

		CommerceWishList commerceWishList = commerceWishListPersistence.create(
			commerceWishListId);

		commerceWishList.setUuid(serviceContext.getUuid());
		commerceWishList.setGroupId(groupId);
		commerceWishList.setCompanyId(user.getCompanyId());
		commerceWishList.setUserId(user.getUserId());
		commerceWishList.setUserName(user.getFullName());
		commerceWishList.setName(name);
		commerceWishList.setDefaultWishList(defaultWishList);

		commerceWishListPersistence.update(commerceWishList);

		return commerceWishList;
	}

	@Override
	public CommerceWishList deleteCommerceWishList(
		CommerceWishList commerceWishList) {

		// Commerce wish list

		commerceWishListPersistence.remove(commerceWishList);

		// Commerce wish list items

		commerceWishListItemLocalService.deleteCommerceWishListItems(
			commerceWishList.getCommerceWishListId());

		return commerceWishList;
	}

	@Override
	public CommerceWishList deleteCommerceWishList(long commerceWishListId)
		throws PortalException {

		CommerceWishList commerceWishList =
			commerceWishListPersistence.findByPrimaryKey(commerceWishListId);

		return commerceWishListLocalService.deleteCommerceWishList(
			commerceWishList);
	}

	@Override
	public void deleteCommerceWishListsByGroupId(long groupId) {
		List<CommerceWishList> commerceWishLists =
			commerceWishListPersistence.findByGroupId(groupId);

		for (CommerceWishList commerceWishList : commerceWishLists) {
			commerceWishListLocalService.deleteCommerceWishList(
				commerceWishList);
		}
	}

	@Override
	public void deleteCommerceWishListsByUserId(long userId) {
		List<CommerceWishList> commerceWishLists =
			commerceWishListPersistence.findByUserId(userId);

		for (CommerceWishList commerceWishList : commerceWishLists) {
			commerceWishListLocalService.deleteCommerceWishList(
				commerceWishList);
		}
	}

	@Override
	public List<CommerceWishList> getCommerceWishLists(
		long groupId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return commerceWishListPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWishList> getCommerceWishLists(
		long groupId, long userId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return commerceWishListPersistence.findByG_U(
			groupId, userId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWishListsCount(long groupId) {
		return commerceWishListPersistence.countByGroupId(groupId);
	}

	@Override
	public int getCommerceWishListsCount(long groupId, long userId) {
		return commerceWishListPersistence.countByG_U(groupId, userId);
	}

	@Override
	public CommerceWishList getDefaultCommerceWishList(
			long groupId, long userId)
		throws PortalException {

		CommerceWishList commerceWishList =
			commerceWishListPersistence.fetchByG_U_D_First(
				groupId, userId, true, null);

		if (commerceWishList == null) {
			commerceWishList = commerceWishListPersistence.fetchByG_U_D_First(
				groupId, userId, false, null);

			if (commerceWishList != null) {
				commerceWishList.setDefaultWishList(true);

				commerceWishListPersistence.update(commerceWishList);
			}
		}

		if (commerceWishList == null) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setScopeGroupId(groupId);
			serviceContext.setUserId(userId);

			commerceWishList = commerceWishListLocalService.addCommerceWishList(
				"default", true, serviceContext);
		}

		return commerceWishList;
	}

	@Override
	public CommerceWishList updateCommerceWishList(
			long commerceWishListId, String name, boolean defaultWishList)
		throws PortalException {

		CommerceWishList commerceWishList =
			commerceWishListPersistence.findByPrimaryKey(commerceWishListId);

		validate(
			commerceWishList.getCommerceWishListId(),
			commerceWishList.getGroupId(), commerceWishList.getUserId(), name,
			defaultWishList);

		commerceWishList.setName(name);
		commerceWishList.setDefaultWishList(defaultWishList);

		commerceWishListPersistence.update(commerceWishList);

		return commerceWishList;
	}

	protected void validate(
			long commerceWishListId, long groupId, long userId, String name,
			boolean defaultWishList)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new CommerceWishListNameException();
		}

		if (defaultWishList) {
			List<CommerceWishList> commerceWishLists =
				commerceWishListPersistence.findByG_U_D(groupId, userId, true);

			for (CommerceWishList commerceWishList : commerceWishLists) {
				if (commerceWishList.getCommerceWishListId() !=
						commerceWishListId) {

					commerceWishList.setDefaultWishList(false);

					commerceWishListPersistence.update(commerceWishList);
				}
			}
		}
	}

}