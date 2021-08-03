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

package com.liferay.commerce.product.asset.categories.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=com_liferay_asset_categories_admin_web_portlet_AssetCategoriesAdminPortlet",
		"mvc.command.name=/commerce_product_asset_categories/edit_asset_category_cp_attachment_file_entry"
	},
	service = MVCActionCommand.class
)
public class EditAssetCategoryCPAttachmentFileEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCPAttachmentFileEntry(ActionRequest actionRequest)
		throws Exception {

		long cpAttachmentFileEntryId = ParamUtil.getLong(
			actionRequest, "cpAttachmentFileEntryId");

		if (cpAttachmentFileEntryId > 0) {
			_cpAttachmentFileEntryService.deleteCPAttachmentFileEntry(
				cpAttachmentFileEntryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCPAttachmentFileEntry(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPAttachmentFileEntry(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchFileEntryException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				_log.error(exception, exception);

				throw exception;
			}
		}
	}

	protected void updateCPAttachmentFileEntry(ActionRequest actionRequest)
		throws Exception {

		long cpAttachmentFileEntryId = ParamUtil.getLong(
			actionRequest, "cpAttachmentFileEntryId");

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		boolean cdnEnabled = ParamUtil.getBoolean(actionRequest, "cdnEnabled");
		String cdnURL = ParamUtil.getString(actionRequest, "cdnURL");
		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		int type = ParamUtil.getInteger(actionRequest, "type");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPAttachmentFileEntry.class.getName(), actionRequest);

		if (cpAttachmentFileEntryId > 0) {
			_cpAttachmentFileEntryService.updateCPAttachmentFileEntry(
				cpAttachmentFileEntryId, fileEntryId, cdnEnabled, cdnURL,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, titleMap, null, priority,
				type, serviceContext);
		}
		else {
			long classNameId = _portal.getClassNameId(AssetCategory.class);

			long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

			AssetCategory assetCategory =
				_assetCategoryLocalService.getAssetCategory(categoryId);

			_cpAttachmentFileEntryService.addCPAttachmentFileEntry(
				assetCategory.getGroupId(), classNameId, categoryId,
				fileEntryId, cdnEnabled, cdnURL, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, titleMap, null, priority, type, serviceContext);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditAssetCategoryCPAttachmentFileEntryMVCActionCommand.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private Portal _portal;

}