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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.shopping.DuplicateItemFieldNameException;
import com.liferay.portlet.shopping.DuplicateItemSKUException;
import com.liferay.portlet.shopping.ItemLargeImageNameException;
import com.liferay.portlet.shopping.ItemLargeImageSizeException;
import com.liferay.portlet.shopping.ItemMediumImageNameException;
import com.liferay.portlet.shopping.ItemMediumImageSizeException;
import com.liferay.portlet.shopping.ItemNameException;
import com.liferay.portlet.shopping.ItemSKUException;
import com.liferay.portlet.shopping.ItemSmallImageNameException;
import com.liferay.portlet.shopping.ItemSmallImageSizeException;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCategoryConstants;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.model.ShoppingItemPriceConstants;
import com.liferay.portlet.shopping.service.base.ShoppingItemLocalServiceBaseImpl;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingItemLocalServiceImpl
	extends ShoppingItemLocalServiceBaseImpl {

	@Override
	public ShoppingItem addItem(
			long userId, long groupId, long categoryId, String sku, String name,
			String description, String properties, String fieldsQuantities,
			boolean requiresShipping, int stockQuantity, boolean featured,
			Boolean sale, boolean smallImage, String smallImageURL,
			File smallImageFile, boolean mediumImage, String mediumImageURL,
			File mediumImageFile, boolean largeImage, String largeImageURL,
			File largeImageFile, List<ShoppingItemField> itemFields,
			List<ShoppingItemPrice> itemPrices, ServiceContext serviceContext)
		throws PortalException {

		// Item

		User user = userPersistence.findByPrimaryKey(userId);
		sku = StringUtil.toUpperCase(sku.trim());

		byte[] smallImageBytes = null;
		byte[] mediumImageBytes = null;
		byte[] largeImageBytes = null;

		try {
			smallImageBytes = FileUtil.getBytes(smallImageFile);
			mediumImageBytes = FileUtil.getBytes(mediumImageFile);
			largeImageBytes = FileUtil.getBytes(largeImageFile);
		}
		catch (IOException ioe) {
		}

		validate(
			user.getCompanyId(), 0, sku, name, smallImage, smallImageURL,
			smallImageFile, smallImageBytes, mediumImage, mediumImageURL,
			mediumImageFile, mediumImageBytes, largeImage, largeImageURL,
			largeImageFile, largeImageBytes, itemFields);

		long itemId = counterLocalService.increment();

		ShoppingItem item = shoppingItemPersistence.create(itemId);

		item.setGroupId(groupId);
		item.setCompanyId(user.getCompanyId());
		item.setUserId(user.getUserId());
		item.setUserName(user.getFullName());
		item.setCategoryId(categoryId);
		item.setSku(sku);
		item.setName(name);
		item.setDescription(description);
		item.setProperties(properties);
		item.setFields(!itemFields.isEmpty());
		item.setFieldsQuantities(fieldsQuantities);

		for (ShoppingItemPrice itemPrice : itemPrices) {
			if (itemPrice.getStatus() ==
					ShoppingItemPriceConstants.STATUS_ACTIVE_DEFAULT) {

				item.setMinQuantity(itemPrice.getMinQuantity());
				item.setMaxQuantity(itemPrice.getMaxQuantity());
				item.setPrice(itemPrice.getPrice());
				item.setDiscount(itemPrice.getDiscount());
				item.setTaxable(itemPrice.getTaxable());
				item.setShipping(itemPrice.getShipping());
				item.setUseShippingFormula(itemPrice.getUseShippingFormula());
			}

			if ((sale == null) && (itemPrice.getDiscount() > 0) &&
				((itemPrice.getStatus() ==
					ShoppingItemPriceConstants.STATUS_ACTIVE_DEFAULT) ||
				 (itemPrice.getStatus() ==
					 ShoppingItemPriceConstants.STATUS_ACTIVE))) {

				sale = Boolean.TRUE;
			}
		}

		item.setRequiresShipping(requiresShipping);
		item.setStockQuantity(stockQuantity);
		item.setFeatured(featured);
		item.setSale((sale != null) ? sale.booleanValue() : false);
		item.setSmallImage(smallImage);
		item.setSmallImageId(counterLocalService.increment());
		item.setSmallImageURL(smallImageURL);
		item.setMediumImage(mediumImage);
		item.setMediumImageId(counterLocalService.increment());
		item.setMediumImageURL(mediumImageURL);
		item.setLargeImage(largeImage);
		item.setLargeImageId(counterLocalService.increment());
		item.setLargeImageURL(largeImageURL);

		shoppingItemPersistence.update(item);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addItemResources(
				item, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addItemResources(
				item, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Images

		saveImages(
			smallImage, item.getSmallImageId(), smallImageFile, smallImageBytes,
			mediumImage, item.getMediumImageId(), mediumImageFile,
			mediumImageBytes, largeImage, item.getLargeImageId(),
			largeImageFile, largeImageBytes);

		// Item fields

		for (ShoppingItemField itemField : itemFields) {
			long itemFieldId = counterLocalService.increment();

			itemField.setItemFieldId(itemFieldId);
			itemField.setItemId(itemId);
			itemField.setName(checkItemField(itemField.getName()));
			itemField.setValues(checkItemField(itemField.getValues()));

			shoppingItemFieldPersistence.update(itemField);
		}

		// Item prices

		if (itemPrices.size() > 1) {
			for (ShoppingItemPrice itemPrice : itemPrices) {
				long itemPriceId = counterLocalService.increment();

				itemPrice.setItemPriceId(itemPriceId);
				itemPrice.setItemId(itemId);

				shoppingItemPricePersistence.update(itemPrice);
			}
		}

		return item;
	}

	@Override
	public void addItemResources(
			long itemId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		ShoppingItem item = shoppingItemPersistence.findByPrimaryKey(itemId);

		addItemResources(item, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addItemResources(
			long itemId, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		ShoppingItem item = shoppingItemPersistence.findByPrimaryKey(itemId);

		addItemResources(item, groupPermissions, guestPermissions);
	}

	@Override
	public void addItemResources(
			ShoppingItem item, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			item.getCompanyId(), item.getGroupId(), item.getUserId(),
			ShoppingItem.class.getName(), item.getItemId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addItemResources(
			ShoppingItem item, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			item.getCompanyId(), item.getGroupId(), item.getUserId(),
			ShoppingItem.class.getName(), item.getItemId(), groupPermissions,
			guestPermissions);
	}

	@Override
	public void deleteItem(long itemId) throws PortalException {
		ShoppingItem item = shoppingItemPersistence.findByPrimaryKey(itemId);

		deleteItem(item);
	}

	@Override
	public void deleteItem(ShoppingItem item) throws PortalException {

		// Item

		shoppingItemPersistence.remove(item);

		// Resources

		resourceLocalService.deleteResource(
			item.getCompanyId(), ShoppingItem.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, item.getItemId());

		// Images

		imageLocalService.deleteImage(item.getSmallImageId());
		imageLocalService.deleteImage(item.getMediumImageId());
		imageLocalService.deleteImage(item.getLargeImageId());

		// Item fields

		shoppingItemFieldPersistence.removeByItemId(item.getItemId());

		// Item prices

		shoppingItemPricePersistence.removeByItemId(item.getItemId());
	}

	@Override
	public void deleteItems(long groupId, long categoryId)
		throws PortalException {

		List<ShoppingItem> items = shoppingItemPersistence.findByG_C(
			groupId, categoryId);

		for (ShoppingItem item : items) {
			deleteItem(item);
		}
	}

	@Override
	public int getCategoriesItemsCount(long groupId, List<Long> categoryIds) {
		return shoppingItemFinder.countByG_C(groupId, categoryIds);
	}

	@Override
	public List<ShoppingItem> getFeaturedItems(
		long groupId, long categoryId, int numOfItems) {

		List<ShoppingItem> featuredItems = shoppingItemFinder.findByFeatured(
			groupId, new long[] {categoryId}, numOfItems);

		if (featuredItems.isEmpty()) {
			List<ShoppingCategory> childCategories =
				shoppingCategoryPersistence.findByG_P(groupId, categoryId);

			if (!childCategories.isEmpty()) {
				long[] categoryIds = new long[childCategories.size()];

				for (int i = 0; i < childCategories.size(); i++) {
					ShoppingCategory childCategory = childCategories.get(i);

					categoryIds[i] = childCategory.getCategoryId();
				}

				featuredItems = shoppingItemFinder.findByFeatured(
					groupId, categoryIds, numOfItems);
			}
		}

		return featuredItems;
	}

	@Override
	public ShoppingItem getItem(long itemId) throws PortalException {
		return shoppingItemPersistence.findByPrimaryKey(itemId);
	}

	@Override
	public ShoppingItem getItem(long companyId, String sku)
		throws PortalException {

		return shoppingItemPersistence.findByC_S(companyId, sku);
	}

	@Override
	public ShoppingItem getItemByLargeImageId(long largeImageId)
		throws PortalException {

		return shoppingItemPersistence.findByLargeImageId(largeImageId);
	}

	@Override
	public ShoppingItem getItemByMediumImageId(long mediumImageId)
		throws PortalException {

		return shoppingItemPersistence.findByMediumImageId(mediumImageId);
	}

	@Override
	public ShoppingItem getItemBySmallImageId(long smallImageId)
		throws PortalException {

		return shoppingItemPersistence.findBySmallImageId(smallImageId);
	}

	@Override
	public List<ShoppingItem> getItems(long groupId, long categoryId) {
		return shoppingItemPersistence.findByG_C(groupId, categoryId);
	}

	@Override
	public List<ShoppingItem> getItems(
		long groupId, long categoryId, int start, int end,
		OrderByComparator<ShoppingItem> obc) {

		return shoppingItemPersistence.findByG_C(
			groupId, categoryId, start, end, obc);
	}

	@Override
	public int getItemsCount(long groupId, long categoryId) {
		return shoppingItemPersistence.countByG_C(groupId, categoryId);
	}

	@Override
	public ShoppingItem[] getItemsPrevAndNext(
			long itemId, OrderByComparator<ShoppingItem> obc)
		throws PortalException {

		ShoppingItem item = shoppingItemPersistence.findByPrimaryKey(itemId);

		return shoppingItemPersistence.findByG_C_PrevAndNext(
			item.getItemId(), item.getGroupId(), item.getCategoryId(), obc);
	}

	@Override
	public List<ShoppingItem> getSaleItems(
		long groupId, long categoryId, int numOfItems) {

		List<ShoppingItem> saleItems = shoppingItemFinder.findBySale(
			groupId, new long[] {categoryId}, numOfItems);

		if (saleItems.isEmpty()) {
			List<ShoppingCategory> childCategories =
				shoppingCategoryPersistence.findByG_P(groupId, categoryId);

			if (!childCategories.isEmpty()) {
				long[] categoryIds = new long[childCategories.size()];

				for (int i = 0; i < childCategories.size(); i++) {
					ShoppingCategory childCategory = childCategories.get(i);

					categoryIds[i] = childCategory.getCategoryId();
				}

				saleItems = shoppingItemFinder.findBySale(
					groupId, categoryIds, numOfItems);
			}
		}

		return saleItems;
	}

	@Override
	public List<ShoppingItem> search(
		long groupId, long[] categoryIds, String keywords, int start, int end) {

		return shoppingItemFinder.findByKeywords(
			groupId, categoryIds, keywords, start, end);
	}

	@Override
	public int searchCount(long groupId, long[] categoryIds, String keywords) {
		return shoppingItemFinder.countByKeywords(
			groupId, categoryIds, keywords);
	}

	@Override
	public ShoppingItem updateItem(
			long userId, long itemId, long groupId, long categoryId, String sku,
			String name, String description, String properties,
			String fieldsQuantities, boolean requiresShipping,
			int stockQuantity, boolean featured, Boolean sale,
			boolean smallImage, String smallImageURL, File smallImageFile,
			boolean mediumImage, String mediumImageURL, File mediumImageFile,
			boolean largeImage, String largeImageURL, File largeImageFile,
			List<ShoppingItemField> itemFields,
			List<ShoppingItemPrice> itemPrices, ServiceContext serviceContext)
		throws PortalException {

		// Item

		ShoppingItem item = shoppingItemPersistence.findByPrimaryKey(itemId);

		User user = userPersistence.findByPrimaryKey(userId);
		categoryId = getCategory(item, categoryId);
		sku = StringUtil.toUpperCase(sku.trim());

		byte[] smallImageBytes = null;
		byte[] mediumImageBytes = null;
		byte[] largeImageBytes = null;

		try {
			smallImageBytes = FileUtil.getBytes(smallImageFile);
			mediumImageBytes = FileUtil.getBytes(mediumImageFile);
			largeImageBytes = FileUtil.getBytes(largeImageFile);
		}
		catch (IOException ioe) {
		}

		validate(
			user.getCompanyId(), itemId, sku, name, smallImage, smallImageURL,
			smallImageFile, smallImageBytes, mediumImage, mediumImageURL,
			mediumImageFile, mediumImageBytes, largeImage, largeImageURL,
			largeImageFile, largeImageBytes, itemFields);

		item.setCategoryId(categoryId);
		item.setSku(sku);
		item.setName(name);
		item.setDescription(description);
		item.setProperties(properties);
		item.setFields(!itemFields.isEmpty());
		item.setFieldsQuantities(fieldsQuantities);

		for (ShoppingItemPrice itemPrice : itemPrices) {
			if (itemPrice.getStatus() ==
					ShoppingItemPriceConstants.STATUS_ACTIVE_DEFAULT) {

				item.setMinQuantity(itemPrice.getMinQuantity());
				item.setMaxQuantity(itemPrice.getMaxQuantity());
				item.setPrice(itemPrice.getPrice());
				item.setDiscount(itemPrice.getDiscount());
				item.setTaxable(itemPrice.getTaxable());
				item.setShipping(itemPrice.getShipping());
				item.setUseShippingFormula(itemPrice.getUseShippingFormula());
			}

			if ((sale == null) && (itemPrice.getDiscount() > 0) &&
				((itemPrice.getStatus() ==
					ShoppingItemPriceConstants.STATUS_ACTIVE_DEFAULT) ||
				 (itemPrice.getStatus() ==
					 ShoppingItemPriceConstants.STATUS_ACTIVE))) {

				sale = Boolean.TRUE;
			}
		}

		item.setRequiresShipping(requiresShipping);
		item.setStockQuantity(stockQuantity);
		item.setFeatured(featured);
		item.setSale((sale != null) ? sale.booleanValue() : false);
		item.setSmallImage(smallImage);
		item.setSmallImageURL(smallImageURL);
		item.setMediumImage(mediumImage);
		item.setMediumImageURL(mediumImageURL);
		item.setLargeImage(largeImage);
		item.setLargeImageURL(largeImageURL);

		shoppingItemPersistence.update(item);

		// Images

		saveImages(
			smallImage, item.getSmallImageId(), smallImageFile, smallImageBytes,
			mediumImage, item.getMediumImageId(), mediumImageFile,
			mediumImageBytes, largeImage, item.getLargeImageId(),
			largeImageFile, largeImageBytes);

		// Item fields

		shoppingItemFieldPersistence.removeByItemId(itemId);

		for (ShoppingItemField itemField : itemFields) {
			long itemFieldId = counterLocalService.increment();

			itemField.setItemFieldId(itemFieldId);
			itemField.setItemId(itemId);
			itemField.setName(checkItemField(itemField.getName()));
			itemField.setValues(checkItemField(itemField.getValues()));

			shoppingItemFieldPersistence.update(itemField);
		}

		// Item prices

		shoppingItemPricePersistence.removeByItemId(itemId);

		if (itemPrices.size() > 1) {
			for (ShoppingItemPrice itemPrice : itemPrices) {
				long itemPriceId = counterLocalService.increment();

				itemPrice.setItemPriceId(itemPriceId);
				itemPrice.setItemId(itemId);

				shoppingItemPricePersistence.update(itemPrice);
			}
		}

		return item;
	}

	protected String checkItemField(String value) {
		return StringUtil.replace(
			value,
			new String[] {
				StringPool.AMPERSAND, StringPool.APOSTROPHE, StringPool.EQUAL,
				StringPool.PIPE, StringPool.QUOTE
			},
			new String[] {
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK
			}
		);
	}

	protected long getCategory(ShoppingItem item, long categoryId) {
		if ((item.getCategoryId() != categoryId) &&
			(categoryId !=
				ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID)) {

			ShoppingCategory newCategory =
				shoppingCategoryPersistence.fetchByPrimaryKey(categoryId);

			if ((newCategory == null) ||
				(item.getGroupId() != newCategory.getGroupId())) {

				categoryId = item.getCategoryId();
			}
		}

		return categoryId;
	}

	protected void saveImages(
			boolean smallImage, long smallImageId, File smallImageFile,
			byte[] smallImageBytes, boolean mediumImage, long mediumImageId,
			File mediumImageFile, byte[] mediumImageBytes, boolean largeImage,
			long largeImageId, File largeImageFile, byte[] largeImageBytes)
		throws PortalException {

		// Small image

		if (smallImage) {
			if ((smallImageFile != null) && (smallImageBytes != null)) {
				imageLocalService.updateImage(smallImageId, smallImageBytes);
			}
		}
		else {
			imageLocalService.deleteImage(smallImageId);
		}

		// Medium image

		if (mediumImage) {
			if ((mediumImageFile != null) && (mediumImageBytes != null)) {
				imageLocalService.updateImage(mediumImageId, mediumImageBytes);
			}
		}
		else {
			imageLocalService.deleteImage(mediumImageId);
		}

		// Large image

		if (largeImage) {
			if ((largeImageFile != null) && (largeImageBytes != null)) {
				imageLocalService.updateImage(largeImageId, largeImageBytes);
			}
		}
		else {
			imageLocalService.deleteImage(largeImageId);
		}
	}

	protected void validate(
			long companyId, long itemId, String sku, String name,
			boolean smallImage, String smallImageURL, File smallImageFile,
			byte[] smallImageBytes, boolean mediumImage, String mediumImageURL,
			File mediumImageFile, byte[] mediumImageBytes, boolean largeImage,
			String largeImageURL, File largeImageFile, byte[] largeImageBytes,
			List<ShoppingItemField> itemFields)
		throws PortalException {

		if (Validator.isNull(sku)) {
			throw new ItemSKUException();
		}

		ShoppingItem item = shoppingItemPersistence.fetchByC_S(companyId, sku);

		if ((item != null) && (item.getItemId() != itemId)) {
			StringBundler sb = new StringBundler(5);

			sb.append("{companyId=");
			sb.append(companyId);
			sb.append(", sku=");
			sb.append(sku);
			sb.append("}");

			throw new DuplicateItemSKUException(sb.toString());
		}

		if (Validator.isNull(name)) {
			throw new ItemNameException();
		}

		if (!itemFields.isEmpty()) {
			List<String> itemFieldNames = new ArrayList<>();
			List<String> duplicateItemFieldNames = new ArrayList<>();

			StringBundler sb = new StringBundler(itemFields.size());

			for (ShoppingItemField itemField : itemFields) {
				if (itemFieldNames.contains(itemField.getName())) {
					if (!duplicateItemFieldNames.contains(
							itemField.getName())) {

						duplicateItemFieldNames.add(itemField.getName());

						sb.append(itemField.getName());
						sb.append(StringPool.COMMA_AND_SPACE);
					}
				}
				else {
					itemFieldNames.add(itemField.getName());
				}
			}

			if (!duplicateItemFieldNames.isEmpty()) {
				sb.setIndex(sb.index() - 1);

				throw new DuplicateItemFieldNameException(sb.toString());
			}
		}

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.SHOPPING_IMAGE_EXTENSIONS, StringPool.COMMA);

		// Small image

		if (smallImage && Validator.isNull(smallImageURL) &&
			(smallImageFile != null) && (smallImageBytes != null)) {

			String smallImageName = smallImageFile.getName();

			if (smallImageName != null) {
				boolean validSmallImageExtension = false;

				for (String imageExtension : imageExtensions) {
					if (StringPool.STAR.equals(imageExtension) ||
						StringUtil.endsWith(
							smallImageName, imageExtension)) {

						validSmallImageExtension = true;

						break;
					}
				}

				if (!validSmallImageExtension) {
					throw new ItemSmallImageNameException(smallImageName);
				}
			}

			long smallImageMaxSize = PrefsPropsUtil.getLong(
				PropsKeys.SHOPPING_IMAGE_SMALL_MAX_SIZE);

			if ((smallImageMaxSize > 0) &&
				((smallImageBytes == null) ||
				 (smallImageBytes.length > smallImageMaxSize))) {

				throw new ItemSmallImageSizeException();
			}
		}

		// Medium image

		if (mediumImage && Validator.isNull(mediumImageURL) &&
			(mediumImageFile != null) && (mediumImageBytes != null)) {

			String mediumImageName = mediumImageFile.getName();

			if (mediumImageName != null) {
				boolean validMediumImageExtension = false;

				for (String imageExtension : imageExtensions) {
					if (StringPool.STAR.equals(imageExtension) ||
						StringUtil.endsWith(
							mediumImageName, imageExtension)) {

						validMediumImageExtension = true;

						break;
					}
				}

				if (!validMediumImageExtension) {
					throw new ItemMediumImageNameException(mediumImageName);
				}
			}

			long mediumImageMaxSize = PrefsPropsUtil.getLong(
				PropsKeys.SHOPPING_IMAGE_MEDIUM_MAX_SIZE);

			if ((mediumImageMaxSize > 0) &&
				((mediumImageBytes == null) ||
				 (mediumImageBytes.length > mediumImageMaxSize))) {

				throw new ItemMediumImageSizeException();
			}
		}

		// Large image

		if (!largeImage || Validator.isNotNull(largeImageURL) ||
			(largeImageFile == null) || (largeImageBytes == null)) {

			return;
		}

		String largeImageName = largeImageFile.getName();

		if (largeImageName != null) {
			boolean validLargeImageExtension = false;

			for (String imageExtension : imageExtensions) {
				if (StringPool.STAR.equals(imageExtension) ||
					StringUtil.endsWith(
						largeImageName, imageExtension)) {

					validLargeImageExtension = true;

					break;
				}
			}

			if (!validLargeImageExtension) {
				throw new ItemLargeImageNameException(largeImageName);
			}
		}

		long largeImageMaxSize = PrefsPropsUtil.getLong(
			PropsKeys.SHOPPING_IMAGE_LARGE_MAX_SIZE);

		if ((largeImageMaxSize > 0) &&
			((largeImageBytes == null) ||
			 (largeImageBytes.length > largeImageMaxSize))) {

			throw new ItemLargeImageSizeException();
		}
	}

}