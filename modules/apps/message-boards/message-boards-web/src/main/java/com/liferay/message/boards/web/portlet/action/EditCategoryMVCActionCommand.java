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

package com.liferay.message.boards.web.portlet.action;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.captcha.CaptchaConfigurationException;
import com.liferay.portal.kernel.captcha.CaptchaMaxChallengesException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.exception.CategoryNameException;
import com.liferay.portlet.messageboards.exception.MailingListEmailAddressException;
import com.liferay.portlet.messageboards.exception.MailingListInServerNameException;
import com.liferay.portlet.messageboards.exception.MailingListInUserNameException;
import com.liferay.portlet.messageboards.exception.MailingListOutEmailAddressException;
import com.liferay.portlet.messageboards.exception.MailingListOutServerNameException;
import com.liferay.portlet.messageboards.exception.MailingListOutUserNameException;
import com.liferay.portlet.messageboards.exception.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.MBCategoryService;
import com.liferay.trash.kernel.service.TrashEntryService;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Sanz
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/edit_category"
	},
	service = MVCActionCommand.class
)
public class EditCategoryMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCategories(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteCategoryIds = null;

		long categoryId = ParamUtil.getLong(actionRequest, "mbCategoryId");

		if (categoryId > 0) {
			deleteCategoryIds = new long[] {categoryId};
		}
		else {
			deleteCategoryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCategoryIds"), 0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteCategoryId : deleteCategoryIds) {
			if (moveToTrash) {
				MBCategory category = _mbCategoryService.moveCategoryToTrash(
					deleteCategoryId);

				trashedModels.add(category);
			}
			else {
				_mbCategoryService.deleteCategory(
					themeDisplay.getScopeGroupId(), deleteCategoryId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCategory(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCategories(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteCategories(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreTrashEntries(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeCategory(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeCategory(actionRequest);
			}
		}
		catch (NoSuchCategoryException | PrincipalException e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/message_boards/error.jsp");
		}
		catch (CaptchaConfigurationException | CaptchaMaxChallengesException |
			   CaptchaTextException | CategoryNameException |
			   MailingListEmailAddressException |
			   MailingListInServerNameException |
			   MailingListInUserNameException |
			   MailingListOutEmailAddressException |
			   MailingListOutServerNameException |
			   MailingListOutUserNameException e) {

			SessionErrors.add(actionRequest, e.getClass());
		}
	}

	protected void restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	@Reference(unbind = "-")
	protected void setMBCategoryService(MBCategoryService mbCategoryService) {
		_mbCategoryService = mbCategoryService;
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	protected void subscribeCategory(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long categoryId = ParamUtil.getLong(actionRequest, "mbCategoryId");

		_mbCategoryService.subscribeCategory(
			themeDisplay.getScopeGroupId(), categoryId);
	}

	protected void unsubscribeCategory(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long categoryId = ParamUtil.getLong(actionRequest, "mbCategoryId");

		_mbCategoryService.unsubscribeCategory(
			themeDisplay.getScopeGroupId(), categoryId);
	}

	protected void updateCategory(ActionRequest actionRequest)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "mbCategoryId");

		long parentCategoryId = ParamUtil.getLong(
			actionRequest, "parentCategoryId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String displayStyle = ParamUtil.getString(
			actionRequest, "displayStyle");

		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		String inProtocol = ParamUtil.getString(actionRequest, "inProtocol");
		String inServerName = ParamUtil.getString(
			actionRequest, "inServerName");
		int inServerPort = ParamUtil.getInteger(actionRequest, "inServerPort");
		boolean inUseSSL = ParamUtil.getBoolean(actionRequest, "inUseSSL");
		String inUserName = ParamUtil.getString(actionRequest, "inUserName");
		String inPassword = ParamUtil.getString(actionRequest, "inPassword");
		int inReadInterval = ParamUtil.getInteger(
			actionRequest, "inReadInterval");
		String outEmailAddress = ParamUtil.getString(
			actionRequest, "outEmailAddress");
		boolean outCustom = ParamUtil.getBoolean(actionRequest, "outCustom");
		String outServerName = ParamUtil.getString(
			actionRequest, "outServerName");
		int outServerPort = ParamUtil.getInteger(
			actionRequest, "outServerPort");
		boolean outUseSSL = ParamUtil.getBoolean(actionRequest, "outUseSSL");
		String outUserName = ParamUtil.getString(actionRequest, "outUserName");
		String outPassword = ParamUtil.getString(actionRequest, "outPassword");
		boolean allowAnonymous = ParamUtil.getBoolean(
			actionRequest, "allowAnonymous");
		boolean mailingListActive = ParamUtil.getBoolean(
			actionRequest, "mailingListActive");

		boolean mergeWithParentCategory = ParamUtil.getBoolean(
			actionRequest, "mergeWithParentCategory");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			MBCategory.class.getName(), actionRequest);

		if (categoryId <= 0) {
			if (PropsValues.
					CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY) {

				CaptchaUtil.check(actionRequest);
			}

			// Add category

			_mbCategoryService.addCategory(
				parentCategoryId, name, description, displayStyle, emailAddress,
				inProtocol, inServerName, inServerPort, inUseSSL, inUserName,
				inPassword, inReadInterval, outEmailAddress, outCustom,
				outServerName, outServerPort, outUseSSL, outUserName,
				outPassword, allowAnonymous, mailingListActive, serviceContext);
		}
		else {

			// Update category

			_mbCategoryService.updateCategory(
				categoryId, parentCategoryId, name, description, displayStyle,
				emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
				inUserName, inPassword, inReadInterval, outEmailAddress,
				outCustom, outServerName, outServerPort, outUseSSL, outUserName,
				outPassword, allowAnonymous, mailingListActive,
				mergeWithParentCategory, serviceContext);
		}
	}

	private MBCategoryService _mbCategoryService;
	private TrashEntryService _trashEntryService;

}