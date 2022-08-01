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
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemException;
import com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemFileEntryIdException;
import com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemUrlException;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.base.CommerceVirtualOrderItemLocalServiceBaseImpl;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem",
	service = AopService.class
)
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

		return commerceVirtualOrderItemPersistence.update(
			commerceVirtualOrderItem);
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
				fetchCPDefinitionVirtualSetting(
					CPInstance.class.getName(),
					commerceOrderItem.getCPInstanceId());

		if ((cpDefinitionVirtualSetting == null) ||
			!cpDefinitionVirtualSetting.isOverride()) {

			cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingLocalService.
					getCPDefinitionVirtualSetting(
						CPDefinition.class.getName(),
						commerceOrderItem.getCPDefinitionId());
		}

		return commerceVirtualOrderItemLocalService.addCommerceVirtualOrderItem(
			commerceOrderItemId, cpDefinitionVirtualSetting.getFileEntryId(),
			cpDefinitionVirtualSetting.getUrl(),
			cpDefinitionVirtualSetting.getActivationStatus(),
			cpDefinitionVirtualSetting.getDuration(), 0,
			cpDefinitionVirtualSetting.getMaxUsages(), serviceContext);
	}

	@Override
	public void checkCommerceVirtualOrderItems() throws PortalException {
		List<CommerceVirtualOrderItem> commerceVirtualOrderItems =
			commerceVirtualOrderItemFinder.findByEndDate(new Date());

		for (CommerceVirtualOrderItem commerceVirtualOrderItem :
				commerceVirtualOrderItems) {

			commerceVirtualOrderItemLocalService.setActive(
				commerceVirtualOrderItem.getCommerceVirtualOrderItemId(),
				false);
		}
	}

	@Override
	public void deleteCommerceVirtualOrderItemByCommerceOrderItemId(
		long commerceOrderItemId) {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemPersistence.fetchByCommerceOrderItemId(
				commerceOrderItemId);

		if (commerceVirtualOrderItem != null) {
			commerceVirtualOrderItemLocalService.deleteCommerceVirtualOrderItem(
				commerceVirtualOrderItem);
		}
	}

	@Override
	public CommerceVirtualOrderItem
		fetchCommerceVirtualOrderItemByCommerceOrderItemId(
			long commerceOrderItemId) {

		return commerceVirtualOrderItemPersistence.fetchByCommerceOrderItemId(
			commerceOrderItemId);
	}

	@Override
	public List<CommerceVirtualOrderItem> getCommerceVirtualOrderItems(
		long groupId, long commerceAccountId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return commerceVirtualOrderItemFinder.findByG_C(
			groupId, commerceAccountId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceVirtualOrderItemsCount(
		long groupId, long commerceAccountId) {

		return commerceVirtualOrderItemFinder.countByG_C(
			groupId, commerceAccountId);
	}

	@Override
	public File getFile(long commerceVirtualOrderItemId) throws Exception {
		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemPersistence.findByPrimaryKey(
				commerceVirtualOrderItemId);

		CommerceOrderItem commerceOrderItem =
			commerceVirtualOrderItem.getCommerceOrderItem();

		InputStream contentInputStream;
		String extension = StringPool.BLANK;

		if (commerceVirtualOrderItem.getFileEntryId() > 0) {
			FileEntry fileEntry = commerceVirtualOrderItem.getFileEntry();

			contentInputStream = fileEntry.getContentStream();

			extension = fileEntry.getExtension();
		}
		else {
			URL url = new URL(commerceVirtualOrderItem.getUrl());

			contentInputStream = url.openStream();

			String mimeType = URLConnection.guessContentTypeFromStream(
				contentInputStream);

			Set<String> extensions = MimeTypesUtil.getExtensions(mimeType);

			if (!extensions.isEmpty()) {
				Iterator<String> iterator = extensions.iterator();

				extension = iterator.next();
			}
		}

		File tempFile = FileUtil.createTempFile(contentInputStream);

		File file = new File(
			tempFile.getParent(),
			commerceOrderItem.getNameCurrentValue() + StringPool.PERIOD +
				extension);

		if (file.exists() && !file.delete()) {
			throw new IOException();
		}

		if (!tempFile.renameTo(file)) {
			file = tempFile;
		}

		return file;
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

		return commerceVirtualOrderItemPersistence.update(
			commerceVirtualOrderItem);
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

		return commerceVirtualOrderItemPersistence.update(
			commerceVirtualOrderItem);
	}

	@Override
	public CommerceVirtualOrderItem updateCommerceVirtualOrderItemDates(
			long commerceVirtualOrderItemId)
		throws PortalException {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			commerceVirtualOrderItemPersistence.fetchByPrimaryKey(
				commerceVirtualOrderItemId);

		commerceVirtualOrderItem = setDurationDates(commerceVirtualOrderItem);

		return commerceVirtualOrderItemPersistence.update(
			commerceVirtualOrderItem);
	}

	protected Date calculateCommerceVirtualOrderItemEndDate(
			CommerceVirtualOrderItem commerceVirtualOrderItem)
		throws PortalException {

		long duration = commerceVirtualOrderItem.getDuration();

		if (duration == 0) {
			return new Date(Long.MIN_VALUE);
		}

		User defaultUser = userLocalService.getDefaultUser(
			commerceVirtualOrderItem.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			defaultUser.getTimeZone());

		calendar.setTimeInMillis(calendar.getTimeInMillis() + duration);

		return calendar.getTime();
	}

	protected CommerceSubscriptionEntry getCommerceSubscriptionEntry(
		long commerceOrderItemId) {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.fetchCommerceOrderItem(
				commerceOrderItemId);

		if (commerceOrderItem == null) {
			return null;
		}

		return _commerceSubscriptionEntryLocalService.
			fetchCommerceSubscriptionEntryByCommerceOrderItemId(
				commerceOrderItemId);
	}

	protected CommerceVirtualOrderItem setDurationDates(
			CommerceVirtualOrderItem commerceVirtualOrderItem)
		throws PortalException {

		Date startDate = new Date();
		Date endDate;

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			getCommerceSubscriptionEntry(
				commerceVirtualOrderItem.getCommerceOrderItemId());

		if (commerceSubscriptionEntry == null) {
			endDate = calculateCommerceVirtualOrderItemEndDate(
				commerceVirtualOrderItem);
		}
		else {
			startDate = commerceSubscriptionEntry.getStartDate();
			endDate = commerceSubscriptionEntry.getNextIterationDate();
		}

		commerceVirtualOrderItem.setStartDate(startDate);

		if (endDate.after(startDate)) {
			commerceVirtualOrderItem.setEndDate(endDate);
		}

		return commerceVirtualOrderItem;
	}

	protected void validate(long fileEntryId, String url)
		throws PortalException {

		if (fileEntryId > 0) {
			try {
				_dlAppLocalService.getFileEntry(fileEntryId);
			}
			catch (NoSuchFileEntryException noSuchFileEntryException) {
				throw new CommerceVirtualOrderItemFileEntryIdException(
					noSuchFileEntryException);
			}
		}
		else if ((fileEntryId <= 0) && Validator.isNull(url)) {
			throw new CommerceVirtualOrderItemException();
		}
		else if (Validator.isNull(url)) {
			throw new CommerceVirtualOrderItemUrlException();
		}
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Reference
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

}