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

import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.commerce.wish.list.exception.CommerceWishListNameException;
import com.liferay.commerce.wish.list.exception.GuestWishListMaxAllowedException;
import com.liferay.commerce.wish.list.internal.configuration.CommerceWishListConfiguration;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.base.CommerceWishListLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

		if (user.isDefaultUser()) {
			validateGuestWishLists();
		}

		validate(0, groupId, user.getUserId(), name, defaultWishList);

		long commerceWishListId = counterLocalService.increment();

		CommerceWishList commerceWishList = commerceWishListPersistence.create(
			commerceWishListId);

		commerceWishList.setGroupId(groupId);
		commerceWishList.setCompanyId(user.getCompanyId());
		commerceWishList.setUserId(user.getUserId());
		commerceWishList.setUserName(user.getFullName());
		commerceWishList.setName(name);
		commerceWishList.setDefaultWishList(defaultWishList);

		return commerceWishListPersistence.update(commerceWishList);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
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
	public void deleteCommerceWishLists(long userId, Date date) {
		commerceWishListPersistence.removeByU_LtC(userId, date);
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
	public CommerceWishList fetchCommerceWishList(
		long groupId, long userId, boolean defaultWishList,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return commerceWishListPersistence.fetchByG_U_D_First(
			groupId, userId, defaultWishList, orderByComparator);
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
	@ThreadLocalCachable
	public CommerceWishList getDefaultCommerceWishList(
			long groupId, long userId, String guestUuid)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		CommerceWishList guestCommerceWishList = null;

		if (Validator.isNotNull(guestUuid)) {
			guestCommerceWishList =
				commerceWishListLocalService.
					fetchCommerceWishListByUuidAndGroupId(guestUuid, groupId);

			if ((guestCommerceWishList != null) &&
				!guestCommerceWishList.isGuestWishList()) {

				guestCommerceWishList = null;
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(user.getUserId());

		if (user.isDefaultUser()) {
			return guestCommerceWishList;
		}

		CommerceWishList commerceWishList =
			commerceWishListPersistence.fetchByG_U_D_First(
				groupId, userId, true, null);

		if (commerceWishList == null) {
			commerceWishList = commerceWishListPersistence.fetchByG_U_D_First(
				groupId, userId, false, null);

			if (commerceWishList != null) {
				commerceWishList.setDefaultWishList(true);

				commerceWishList = commerceWishListPersistence.update(
					commerceWishList);
			}
		}

		if (guestCommerceWishList != null) {
			mergeCommerceWishList(
				guestCommerceWishList.getCommerceWishListId(),
				commerceWishList.getCommerceWishListId(), serviceContext);
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

		return commerceWishListPersistence.update(commerceWishList);
	}

	protected String getCookieName(long groupId) {
		return CommerceWishList.class.getName() + StringPool.POUND + groupId;
	}

	protected void mergeCommerceWishList(
			long fromCommerceWishListId, long toCommerceWishListId,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce wish list items

		List<CommerceWishListItem> fromCommerceWishListItems =
			commerceWishListItemLocalService.getCommerceWishListItems(
				fromCommerceWishListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		List<CommerceWishListItem> toCommerceWishListItems =
			commerceWishListItemLocalService.getCommerceWishListItems(
				toCommerceWishListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		for (CommerceWishListItem fromCommerceWishListItem :
				fromCommerceWishListItems) {

			String json = fromCommerceWishListItem.getJson();

			boolean found = false;

			for (CommerceWishListItem toCommerceWishListItem :
					toCommerceWishListItems) {

				if ((fromCommerceWishListItem.getCProductId() ==
						toCommerceWishListItem.getCProductId()) &&
					Objects.equals(
						fromCommerceWishListItem.getCPInstanceUuid(),
						toCommerceWishListItem.getCPInstanceUuid()) &&
					Objects.equals(json, toCommerceWishListItem.getJson())) {

					found = true;

					break;
				}
			}

			if (!found) {
				commerceWishListItemLocalService.addCommerceWishListItem(
					toCommerceWishListId,
					fromCommerceWishListItem.getCProductId(),
					fromCommerceWishListItem.getCPInstanceUuid(), json,
					serviceContext);
			}
		}

		// Commerce wish list

		commerceWishListLocalService.deleteCommerceWishList(
			fromCommerceWishListId);
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

	protected void validateGuestWishLists() throws PortalException {
		int count = commerceWishListPersistence.countByUserId(
			UserConstants.USER_ID_DEFAULT);

		if (count >= _commerceWishListConfiguration.guestWishListMaxAllowed()) {
			throw new GuestWishListMaxAllowedException();
		}
	}

	@ServiceReference(type = CommerceWishListConfiguration.class)
	private CommerceWishListConfiguration _commerceWishListConfiguration;

	@ServiceReference(type = DDMFormValuesHelper.class)
	private DDMFormValuesHelper _ddmFormValuesHelper;

}