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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.amazonrankings.model.AmazonRankings;
import com.liferay.portlet.amazonrankings.util.AmazonRankingsUtil;
import com.liferay.portlet.shopping.AmazonException;
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
import com.liferay.util.PwdGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingItemLocalServiceImpl
	extends ShoppingItemLocalServiceBaseImpl {

	@Override
	public void addBookItems(
			long userId, long groupId, long categoryId, String[] isbns)
		throws PortalException {

		try {
			doAddBookItems(userId, groupId, categoryId, isbns);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

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

		Date now = new Date();

		validate(
			user.getCompanyId(), 0, sku, name, smallImage, smallImageURL,
			smallImageFile, smallImageBytes, mediumImage, mediumImageURL,
			mediumImageFile, mediumImageBytes, largeImage, largeImageURL,
			largeImageFile, largeImageBytes);

		long itemId = counterLocalService.increment();

		ShoppingItem item = shoppingItemPersistence.create(itemId);

		item.setGroupId(groupId);
		item.setCompanyId(user.getCompanyId());
		item.setUserId(user.getUserId());
		item.setUserName(user.getFullName());
		item.setCreateDate(now);
		item.setModifiedDate(now);
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
		OrderByComparator obc) {

		return shoppingItemPersistence.findByG_C(
			groupId, categoryId, start, end, obc);
	}

	@Override
	public int getItemsCount(long groupId, long categoryId) {
		return shoppingItemPersistence.countByG_C(groupId, categoryId);
	}

	@Override
	public ShoppingItem[] getItemsPrevAndNext(
			long itemId, OrderByComparator obc)
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
			largeImageFile, largeImageBytes);

		item.setModifiedDate(new Date());
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
				"\"", "&", "'", ".", "=", "|"
			},
			new String[] {
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK
			}
		);
	}

	protected void doAddBookItems(
			long userId, long groupId, long categoryId, String[] isbns)
		throws IOException, PortalException, SystemException {

		if (!AmazonRankingsUtil.isEnabled()) {
			throw new AmazonException("Amazon integration is not enabled");
		}

		String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

		for (int i = 0; (i < isbns.length) && (i < 50); i++) {
			String isbn = isbns[i];

			AmazonRankings amazonRankings =
				AmazonRankingsUtil.getAmazonRankings(isbn);

			if (amazonRankings == null) {
				continue;
			}

			String name = amazonRankings.getProductName();
			String description = StringPool.BLANK;
			String properties = getBookProperties(amazonRankings);

			int minQuantity = 0;
			int maxQuantity = 0;
			double price = amazonRankings.getListPrice();
			double discount = 1 - amazonRankings.getOurPrice() / price;
			boolean taxable = true;
			double shipping = 0.0;
			boolean useShippingFormula = true;

			ShoppingItemPrice itemPrice = shoppingItemPricePersistence.create(
				0);

			itemPrice.setMinQuantity(minQuantity);
			itemPrice.setMaxQuantity(maxQuantity);
			itemPrice.setPrice(price);
			itemPrice.setDiscount(discount);
			itemPrice.setTaxable(taxable);
			itemPrice.setShipping(shipping);
			itemPrice.setUseShippingFormula(useShippingFormula);
			itemPrice.setStatus(
				ShoppingItemPriceConstants.STATUS_ACTIVE_DEFAULT);

			boolean requiresShipping = true;
			int stockQuantity = 0;
			boolean featured = false;
			Boolean sale = null;

			// Small image

			boolean smallImage = true;
			String smallImageURL = StringPool.BLANK;
			File smallImageFile = new File(
				tmpDir + File.separatorChar +
					PwdGenerator.getPassword(8, PwdGenerator.KEY2) + ".jpg");

			byte[] smallImageBytes = HttpUtil.URLtoByteArray(
				amazonRankings.getSmallImageURL());

			if (smallImageBytes.length < 1024) {
				smallImage = false;
			}
			else {
				OutputStream os = new FileOutputStream(smallImageFile);

				os.write(smallImageBytes);

				os.close();
			}

			// Medium image

			boolean mediumImage = true;
			String mediumImageURL = StringPool.BLANK;
			File mediumImageFile = new File(
				tmpDir + File.separatorChar +
					PwdGenerator.getPassword(8, PwdGenerator.KEY2) + ".jpg");

			byte[] mediumImageBytes = HttpUtil.URLtoByteArray(
				amazonRankings.getMediumImageURL());

			if (mediumImageBytes.length < 1024) {
				mediumImage = false;
			}
			else {
				OutputStream os = new FileOutputStream(mediumImageFile);

				os.write(mediumImageBytes);

				os.close();
			}

			// Large image

			boolean largeImage = true;
			String largeImageURL = StringPool.BLANK;
			File largeImageFile = new File(
				tmpDir + File.separatorChar +
					PwdGenerator.getPassword(8, PwdGenerator.KEY2) + ".jpg");

			byte[] largeImageBytes = HttpUtil.URLtoByteArray(
				amazonRankings.getLargeImageURL());

			if (largeImageBytes.length < 1024) {
				largeImage = false;
			}
			else {
				OutputStream os = new FileOutputStream(largeImageFile);

				os.write(largeImageBytes);

				os.close();
			}

			List<ShoppingItemField> itemFields =
				new ArrayList<ShoppingItemField>();

			List<ShoppingItemPrice> itemPrices =
				new ArrayList<ShoppingItemPrice>();

			itemPrices.add(itemPrice);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			addItem(
				userId, groupId, categoryId, isbn, name, description,
				properties, StringPool.BLANK, requiresShipping, stockQuantity,
				featured, sale, smallImage, smallImageURL, smallImageFile,
				mediumImage, mediumImageURL, mediumImageFile, largeImage,
				largeImageURL, largeImageFile, itemFields, itemPrices,
				serviceContext);

			smallImageFile.delete();
			mediumImageFile.delete();
			largeImageFile.delete();
		}
	}

	protected String getBookProperties(AmazonRankings amazonRankings) {
		String isbn = amazonRankings.getISBN();

		String authors = StringUtil.merge(amazonRankings.getAuthors(), ", ");

		String publisher =
			amazonRankings.getManufacturer() + "; (" +
				amazonRankings.getReleaseDateAsString() + ")";

		String properties =
			"ISBN=" + isbn + "\nAuthor=" + authors + "\nPublisher=" + publisher;

		return properties;
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
			String largeImageURL, File largeImageFile, byte[] largeImageBytes)
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

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.SHOPPING_IMAGE_EXTENSIONS, StringPool.COMMA);

		// Small image

		if (smallImage && Validator.isNull(smallImageURL) &&
			(smallImageFile != null) && (smallImageBytes != null)) {

			String smallImageName = smallImageFile.getName();

			if (smallImageName != null) {
				boolean validSmallImageExtension = false;

				for (int i = 0; i < imageExtensions.length; i++) {
					if (StringPool.STAR.equals(imageExtensions[i]) ||
						StringUtil.endsWith(
							smallImageName, imageExtensions[i])) {

						validSmallImageExtension = true;

						break;
					}
				}

				if (!validSmallImageExtension) {
					throw new ItemSmallImageNameException(smallImageName);
				}
			}

			long smallImageMaxSize = PrefsPropsUtil.getLong(
				PropsKeys.SHOPPING_IMAGE_MEDIUM_MAX_SIZE);

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

				for (int i = 0; i < imageExtensions.length; i++) {
					if (StringPool.STAR.equals(imageExtensions[i]) ||
						StringUtil.endsWith(
							mediumImageName, imageExtensions[i])) {

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

			for (int i = 0; i < imageExtensions.length; i++) {
				if (StringPool.STAR.equals(imageExtensions[i]) ||
					StringUtil.endsWith(
						largeImageName, imageExtensions[i])) {

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