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

package com.liferay.shopping.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.shopping.constants.ShoppingPortletKeys;
import com.liferay.shopping.exception.DuplicateItemFieldNameException;
import com.liferay.shopping.exception.DuplicateItemSKUException;
import com.liferay.shopping.exception.ItemLargeImageNameException;
import com.liferay.shopping.exception.ItemLargeImageSizeException;
import com.liferay.shopping.exception.ItemMediumImageNameException;
import com.liferay.shopping.exception.ItemMediumImageSizeException;
import com.liferay.shopping.exception.ItemNameException;
import com.liferay.shopping.exception.ItemSKUException;
import com.liferay.shopping.exception.ItemSmallImageNameException;
import com.liferay.shopping.exception.ItemSmallImageSizeException;
import com.liferay.shopping.exception.NoSuchCategoryException;
import com.liferay.shopping.exception.NoSuchItemException;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.model.ShoppingItemField;
import com.liferay.shopping.model.ShoppingItemPrice;
import com.liferay.shopping.model.ShoppingItemPriceConstants;
import com.liferay.shopping.model.impl.ShoppingItemImpl;
import com.liferay.shopping.service.ShoppingItemService;
import com.liferay.shopping.service.persistence.ShoppingItemFieldUtil;
import com.liferay.shopping.service.persistence.ShoppingItemPriceUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING,
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING_ADMIN,
		"mvc.command.name=/shopping/edit_item"
	},
	service = MVCActionCommand.class
)
public class EditItemMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteItem(ActionRequest actionRequest) throws Exception {
		long itemId = ParamUtil.getLong(actionRequest, "itemId");

		_shoppingItemService.deleteItem(itemId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateItem(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteItem(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCategoryException ||
				e instanceof NoSuchItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof DuplicateItemFieldNameException ||
					 e instanceof DuplicateItemSKUException ||
					 e instanceof ItemLargeImageNameException ||
					 e instanceof ItemLargeImageSizeException ||
					 e instanceof ItemMediumImageNameException ||
					 e instanceof ItemMediumImageSizeException ||
					 e instanceof ItemNameException ||
					 e instanceof ItemSKUException ||
					 e instanceof ItemSmallImageNameException ||
					 e instanceof ItemSmallImageSizeException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setShoppingItemService(
		ShoppingItemService shoppingItemService) {

		_shoppingItemService = shoppingItemService;
	}

	protected void updateItem(ActionRequest actionRequest) throws Exception {
		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long itemId = ParamUtil.getLong(uploadPortletRequest, "itemId");

		long groupId = themeDisplay.getScopeGroupId();
		long categoryId = ParamUtil.getLong(uploadPortletRequest, "categoryId");
		String sku = ParamUtil.getString(uploadPortletRequest, "sku");
		String name = ParamUtil.getString(uploadPortletRequest, "name");
		String description = ParamUtil.getString(
			uploadPortletRequest, "description");
		String properties = ParamUtil.getString(
			uploadPortletRequest, "properties");

		int fieldsCount = ParamUtil.getInteger(
			uploadPortletRequest, "fieldsCount", 1);

		List<ShoppingItemField> itemFields = new ArrayList<>();

		for (int i = 0; i < fieldsCount; i++) {
			String fieldName = ParamUtil.getString(
				uploadPortletRequest, "fieldName" + i);
			String fieldValues = ParamUtil.getString(
				uploadPortletRequest, "fieldValues" + i);
			String fieldDescription = ParamUtil.getString(
				uploadPortletRequest, "fieldDescription" + i);

			ShoppingItemField itemField = ShoppingItemFieldUtil.create(0);

			itemField.setName(fieldName);
			itemField.setValues(fieldValues);
			itemField.setDescription(fieldDescription);

			itemFields.add(itemField);
		}

		String fieldsQuantities = ParamUtil.getString(
			uploadPortletRequest, "fieldsQuantities");

		int pricesCount = ParamUtil.getInteger(
			uploadPortletRequest, "pricesCount", 1);

		List<ShoppingItemPrice> itemPrices = new ArrayList<>();

		for (int i = 0; i < pricesCount; i++) {
			int minQuantity = ParamUtil.getInteger(
				uploadPortletRequest, "minQuantity" + i);
			int maxQuantity = ParamUtil.getInteger(
				uploadPortletRequest, "maxQuantity" + i);
			double price = ParamUtil.getDouble(
				uploadPortletRequest, "price" + i, themeDisplay.getLocale());

			double discount = ParamUtil.getDouble(
				uploadPortletRequest, "discount" + i, themeDisplay.getLocale());

			discount = discount / 100;

			boolean taxable = ParamUtil.getBoolean(
				uploadPortletRequest, "taxable" + i);
			double shipping = ParamUtil.getDouble(
				uploadPortletRequest, "shipping" + i, themeDisplay.getLocale());
			boolean useShippingFormula = ParamUtil.getBoolean(
				uploadPortletRequest, "useShippingFormula" + i);
			boolean active = ParamUtil.getBoolean(
				uploadPortletRequest, "active" + i);
			int defaultPrice = ParamUtil.getInteger(
				uploadPortletRequest, "defaultPrice");

			int status = ShoppingItemPriceConstants.STATUS_ACTIVE_DEFAULT;

			if ((defaultPrice != i) && active) {
				status = ShoppingItemPriceConstants.STATUS_ACTIVE;
			}
			else if ((defaultPrice != i) && !active) {
				status = ShoppingItemPriceConstants.STATUS_INACTIVE;
			}

			ShoppingItemPrice itemPrice = ShoppingItemPriceUtil.create(0);

			itemPrice.setMinQuantity(minQuantity);
			itemPrice.setMaxQuantity(maxQuantity);
			itemPrice.setPrice(price);
			itemPrice.setDiscount(discount);
			itemPrice.setTaxable(taxable);
			itemPrice.setShipping(shipping);
			itemPrice.setUseShippingFormula(useShippingFormula);
			itemPrice.setStatus(status);

			itemPrices.add(itemPrice);
		}

		boolean requiresShipping = ParamUtil.getBoolean(
			uploadPortletRequest, "requiresShipping");

		int stockQuantity = ParamUtil.getInteger(
			uploadPortletRequest, "stockQuantity");

		if (ParamUtil.getBoolean(uploadPortletRequest, "infiniteStock")) {
			stockQuantity = ShoppingItemImpl.STOCK_QUANTITY_INFINITE_STOCK;
		}

		boolean featured = ParamUtil.getBoolean(
			uploadPortletRequest, "featured");
		Boolean sale = null;

		boolean smallImage = ParamUtil.getBoolean(
			uploadPortletRequest, "smallImage");
		String smallImageURL = ParamUtil.getString(
			uploadPortletRequest, "smallImageURL");
		File smallFile = uploadPortletRequest.getFile("smallFile");

		boolean mediumImage = ParamUtil.getBoolean(
			uploadPortletRequest, "mediumImage");
		String mediumImageURL = ParamUtil.getString(
			uploadPortletRequest, "mediumImageURL");
		File mediumFile = uploadPortletRequest.getFile("mediumFile");

		boolean largeImage = ParamUtil.getBoolean(
			uploadPortletRequest, "largeImage");
		String largeImageURL = ParamUtil.getString(
			uploadPortletRequest, "largeImageURL");
		File largeFile = uploadPortletRequest.getFile("largeFile");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ShoppingItem.class.getName(), actionRequest);

		if (itemId <= 0) {

			// Add item

			_shoppingItemService.addItem(
				groupId, categoryId, sku, name, description, properties,
				fieldsQuantities, requiresShipping, stockQuantity, featured,
				sale, smallImage, smallImageURL, smallFile, mediumImage,
				mediumImageURL, mediumFile, largeImage, largeImageURL,
				largeFile, itemFields, itemPrices, serviceContext);
		}
		else {

			// Update item

			_shoppingItemService.updateItem(
				itemId, groupId, categoryId, sku, name, description, properties,
				fieldsQuantities, requiresShipping, stockQuantity, featured,
				sale, smallImage, smallImageURL, smallFile, mediumImage,
				mediumImageURL, mediumFile, largeImage, largeImageURL,
				largeFile, itemFields, itemPrices, serviceContext);
		}
	}

	private ShoppingItemService _shoppingItemService;

}