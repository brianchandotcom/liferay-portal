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

package com.liferay.commerce.product.type.virtual.order.service.impl;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemException;
import com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemFileEntryIdException;
import com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemUrlException;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.base.CommerceVirtualOrderItemLocalServiceBaseImpl;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVirtualOrderItemLocalServiceImpl
	extends CommerceVirtualOrderItemLocalServiceBaseImpl {

	@Override
	public CommerceVirtualOrderItem addCommerceVirtualOrderItem(
			long commerceOrderItemId, long fileEntryId, String url,
			int activationStatus, long duration, int usages, int maxUsages,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		if (Validator.isNotNull(url)) {
			fileEntryId = 0;
		}
		else {
			url = null;
		}

		validate(fileEntryId, url);

		long commerceVirtualOrderItemId = counterLocalService.increment();

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemPersistence.create(
				commerceVirtualOrderItemId);

		commerceVirtualOrderItem.setUuid(serviceContext.getUuid());
		commerceVirtualOrderItem.setGroupId(groupId);
		commerceVirtualOrderItem.setCompanyId(user.getCompanyId());
		commerceVirtualOrderItem.setUserId(user.getUserId());
		commerceVirtualOrderItem.setUserName(user.getFullName());
		commerceVirtualOrderItem.setCommerceOrderItemId(commerceOrderItemId);
		commerceVirtualOrderItem.setFileEntryId(fileEntryId);
		commerceVirtualOrderItem.setUrl(url);
		commerceVirtualOrderItem.setActivationStatus(activationStatus);
		commerceVirtualOrderItem.setDuration(duration);
		commerceVirtualOrderItem.setUsages(usages);
		commerceVirtualOrderItem.setMaxUsages(maxUsages);

		if (Objects.equals(
				commerceVirtualOrderItem.getActivationStatus(),
				commerceOrder.getOrderStatus())) {

			commerceVirtualOrderItem.setActive(true);

			commerceVirtualOrderItem = setDurationDates(
				commerceVirtualOrderItem);
		}

		commerceVirtualOrderItemPersistence.update(commerceVirtualOrderItem);

		return commerceVirtualOrderItem;
	}

	@Override
	public CommerceVirtualOrderItem addCommerceVirtualOrderItem(
			long commerceOrderItemId, ServiceContext serviceContext)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItemId);

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			_cpDefinitionVirtualSettingLocalService.
				getCPDefinitionVirtualSettingByCPDefinitionId(
					commerceOrderItem.getCPDefinitionId());

		return commerceVirtualOrderItemLocalService.addCommerceVirtualOrderItem(
			commerceOrderItemId, cpDefinitionVirtualSetting.getFileEntryId(),
			cpDefinitionVirtualSetting.getUrl(),
			cpDefinitionVirtualSetting.getActivationStatus(),
			cpDefinitionVirtualSetting.getDuration(), 0,
			cpDefinitionVirtualSetting.getMaxUsages(), serviceContext);
	}

	@Override
	public CommerceVirtualOrderItem
		fetchCommerceVirtualOrderItemByCommerceOrderItemId(
			long commerceOrderItemId) {

		return commerceVirtualOrderItemPersistence.fetchByCommerceOrderItemId(
			commerceOrderItemId);
	}

	@Override
	public File getFile(long commerceVirtualOrderItemId) throws Exception {
		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemPersistence.findByPrimaryKey(
				commerceVirtualOrderItemId);

		InputStream contentStream;
		FileEntry fileEntry = commerceVirtualOrderItem.getFileEntry();

		if (commerceVirtualOrderItem.getFileEntryId() > 0) {
			contentStream = fileEntry.getContentStream();
		}
		else {
			URL url = new URL(commerceVirtualOrderItem.getUrl());

			contentStream = url.openStream();
		}

		CommerceOrderItem commerceOrderItem =
			commerceVirtualOrderItem.getCommerceOrderItem();

		File tempFile = FileUtil.createTempFile(contentStream);

		File file = new File(
			tempFile.getParent(),
			commerceOrderItem.getNameCurrentValue() + StringPool.PERIOD +
				fileEntry.getExtension());

		if (file.exists()) {
			file.delete();
		}

		if (!tempFile.renameTo(file)) {
			file = tempFile;
		}

		return file;
	}

	@Override
	public List<CommerceVirtualOrderItem>
		getOrganizationCommerceVirtualOrderItems(
			long groupId, long organizationId, int start, int end,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return commerceVirtualOrderItemFinder.findByG_O(
			groupId, organizationId, start, end, orderByComparator);
	}

	@Override
	public int getOrganizationCommerceVirtualOrderItemsCount(
		long groupId, long organizationId) {

		List<CommerceVirtualOrderItem> commerceVirtualOrderItems =
			commerceVirtualOrderItemFinder.findByG_O(
				groupId, organizationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		return commerceVirtualOrderItems.size();
	}

	@Override
	public List<CommerceVirtualOrderItem> getUserCommerceVirtualOrderItems(
		long groupId, long user, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return commerceVirtualOrderItemFinder.findByG_U(
			groupId, user, start, end, orderByComparator);
	}

	@Override
	public int getUserCommerceVirtualOrderItemsCount(
		long groupId, long userId) {

		List<CommerceVirtualOrderItem> commerceVirtualOrderItems =
			commerceVirtualOrderItemFinder.findByG_U(
				groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return commerceVirtualOrderItems.size();
	}

	@Override
	public CommerceVirtualOrderItem incrementCommerceVirtualOrderItemUsages(
			long commerceVirtualOrderItemId)
		throws PortalException {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemPersistence.findByPrimaryKey(
				commerceVirtualOrderItemId);

		commerceVirtualOrderItem.setUsages(
			commerceVirtualOrderItem.getUsages() + 1);

		commerceVirtualOrderItemPersistence.update(commerceVirtualOrderItem);

		return commerceVirtualOrderItem;
	}

	@Override
	public void setActive(long commerceVirtualOrderItemId, boolean active)
		throws PortalException {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemPersistence.findByPrimaryKey(
				commerceVirtualOrderItemId);

		commerceVirtualOrderItem.setActive(active);

		commerceVirtualOrderItemPersistence.update(commerceVirtualOrderItem);
	}

	@Override
	public CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
			long commerceVirtualOrderItemId, long fileEntryId, String url,
			int activationStatus, long duration, int usages, int maxUsages,
			boolean active)
		throws PortalException {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemPersistence.fetchByPrimaryKey(
				commerceVirtualOrderItemId);

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceVirtualOrderItem.getCommerceOrderItemId());

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		if (Validator.isNotNull(url)) {
			fileEntryId = 0;
		}
		else {
			url = null;
		}

		validate(fileEntryId, url);

		commerceVirtualOrderItem.setFileEntryId(fileEntryId);
		commerceVirtualOrderItem.setUrl(url);
		commerceVirtualOrderItem.setActivationStatus(activationStatus);

		if (duration > commerceVirtualOrderItem.getDuration()) {
			duration = duration - commerceVirtualOrderItem.getDuration();
		}
		else {
			duration = 0;
		}

		commerceVirtualOrderItem.setDuration(duration);
		commerceVirtualOrderItem.setUsages(usages);
		commerceVirtualOrderItem.setMaxUsages(maxUsages);
		commerceVirtualOrderItem.setActive(active);

		if (Objects.equals(
				commerceVirtualOrderItem.getActivationStatus(),
				commerceOrder.getOrderStatus())) {

			commerceVirtualOrderItem = setDurationDates(
				commerceVirtualOrderItem);
		}

		commerceVirtualOrderItemPersistence.update(commerceVirtualOrderItem);

		return commerceVirtualOrderItem;
	}

	protected CommerceVirtualOrderItem setDurationDates(
			CommerceVirtualOrderItem commerceVirtualOrderItem)
		throws PortalException {

		commerceVirtualOrderItem.setStartDate(new Date());

		long duration = commerceVirtualOrderItem.getDuration();

		if (duration > 0) {
			User defaultUser = userLocalService.getDefaultUser(
				commerceVirtualOrderItem.getCompanyId());

			Calendar calendar = CalendarFactoryUtil.getCalendar(
				defaultUser.getTimeZone());

			duration = calendar.getTimeInMillis() + duration;

			calendar.setTimeInMillis(duration);

			commerceVirtualOrderItem.setEndDate(calendar.getTime());
		}

		return commerceVirtualOrderItem;
	}

	protected void validate(long fileEntryId, String url)
		throws PortalException {

		if (fileEntryId > 0) {
			try {
				dlAppLocalService.getFileEntry(fileEntryId);
			}
			catch (NoSuchFileEntryException nsfee) {
				throw new CommerceVirtualOrderItemFileEntryIdException(nsfee);
			}
		}
		else if ((fileEntryId <= 0) && Validator.isNull(url)) {
			throw new CommerceVirtualOrderItemException();
		}
		else if (Validator.isNull(url)) {
			throw new CommerceVirtualOrderItemUrlException();
		}
	}

	@ServiceReference(type = CommerceOrderItemLocalService.class)
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@ServiceReference(type = CPDefinitionVirtualSettingLocalService.class)
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

}