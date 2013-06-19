/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.shopping.DuplicateItemSKUException;
import com.liferay.portlet.shopping.ItemLargeImageNameException;
import com.liferay.portlet.shopping.ItemLargeImageSizeException;
import com.liferay.portlet.shopping.ItemMediumImageNameException;
import com.liferay.portlet.shopping.ItemMediumImageSizeException;
import com.liferay.portlet.shopping.ItemNameException;
import com.liferay.portlet.shopping.ItemSKUException;
import com.liferay.portlet.shopping.ItemSmallImageNameException;
import com.liferay.portlet.shopping.ItemSmallImageSizeException;
import com.liferay.portlet.shopping.NoSuchCategoryException;
import com.liferay.portlet.shopping.NoSuchItemException;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.model.ShoppingItemPriceConstants;
import com.liferay.portlet.shopping.service.ShoppingItemServiceUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class EditItemAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
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

				setForward(actionRequest, "portlet.shopping.error");
			}
			else if (e instanceof DuplicateItemSKUException ||
					 e instanceof ItemLargeImageNameException ||
					 e instanceof ItemLargeImageSizeException ||
					 e instanceof ItemMediumImageNameException ||
					 e instanceof ItemMediumImageSizeException ||
					 e instanceof ItemNameException ||
					 e instanceof ItemSKUException ||
					 e instanceof ItemSmallImageNameException ||
					 e instanceof ItemSmallImageSizeException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getItem(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.shopping.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.shopping.edit_item"));
	}

	protected void deleteItem(ActionRequest actionRequest) throws Exception {
		long itemId = ParamUtil.getLong(actionRequest, "itemId");

		ShoppingItemServiceUtil.deleteItem(itemId);
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

		List<ShoppingItemField> itemFields = new ArrayList<ShoppingItemField>();

		for (int i = 0; i < fieldsCount; i ++) {
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

		List<ShoppingItemPrice> itemPrices = new ArrayList<ShoppingItemPrice>();

		for (int i = 0; i < pricesCount; i ++) {
			int minQuantity = ParamUtil.getInteger(
				uploadPortletRequest, "minQuantity" + i);
			int maxQuantity = ParamUtil.getInteger(
				uploadPortletRequest, "maxQuantity" + i);
			double price = ParamUtil.getDouble(
				uploadPortletRequest, "price" + i);
			double discount = ParamUtil.getDouble(
				uploadPortletRequest, "discount" + i) / 100;
			boolean taxable = ParamUtil.getBoolean(
				uploadPortletRequest, "taxable" + i);
			double shipping = ParamUtil.getDouble(
				uploadPortletRequest, "shipping" + i);
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

			ShoppingItemServiceUtil.addItem(
				groupId, categoryId, sku, name, description, properties,
				fieldsQuantities, requiresShipping, stockQuantity, featured,
				sale, smallImage, smallImageURL, smallFile, mediumImage,
				mediumImageURL, mediumFile, largeImage, largeImageURL,
				largeFile, itemFields, itemPrices, serviceContext);
		}
		else {

			// Update item

			ShoppingItemServiceUtil.updateItem(
				itemId, groupId, categoryId, sku, name, description, properties,
				fieldsQuantities, requiresShipping, stockQuantity, featured,
				sale, smallImage, smallImageURL, smallFile, mediumImage,
				mediumImageURL, mediumFile, largeImage, largeImageURL,
				largeFile, itemFields, itemPrices, serviceContext);
		}
	}

}