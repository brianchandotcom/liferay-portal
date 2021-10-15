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

package com.liferay.commerce.order.rule.web.internal.portlet.action;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.rule.constants.COREntryPortletKeys;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Objects;

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
		"javax.portlet.name=" + COREntryPortletKeys.COR_ENTRY,
		"mvc.command.name=/cor_entry/edit_cor_entry_qualifiers"
	},
	service = MVCActionCommand.class
)
public class EditCOREntryQualifiersMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCOREntryQualifiers(actionRequest);
			}
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	private void _deleteAccountEntryCOREntryRels(long corEntryId)
		throws Exception {

		int accountEntryCOREntryRelsCount =
			_corEntryRelService.getAccountEntryCOREntryRelsCount(
				corEntryId, null);

		if (accountEntryCOREntryRelsCount == 0) {
			return;
		}

		_corEntryRelService.deleteCOREntryRels(
			AccountEntry.class.getName(), corEntryId);
	}

	private void _deleteAccountGroupCOREntryRels(long corEntryId)
		throws Exception {

		int accountGroupCOREntryRelsCount =
			_corEntryRelService.getAccountGroupCOREntryRelsCount(
				corEntryId, null);

		if (accountGroupCOREntryRelsCount == 0) {
			return;
		}

		_corEntryRelService.deleteCOREntryRels(
			AccountGroup.class.getName(), corEntryId);
	}

	private void _updateCOREntryQualifiers(ActionRequest actionRequest)
		throws Exception {

		long corEntryId = ParamUtil.getLong(actionRequest, "corEntryId");

		String accountQualifiers = ParamUtil.getString(
			actionRequest, "accountQualifiers");

		if (Objects.equals(accountQualifiers, "all")) {
			_deleteAccountEntryCOREntryRels(corEntryId);
			_deleteAccountGroupCOREntryRels(corEntryId);
		}
		else if (Objects.equals(accountQualifiers, "accounts")) {
			_deleteAccountGroupCOREntryRels(corEntryId);
		}
		else {
			_deleteAccountEntryCOREntryRels(corEntryId);
		}

		String channelQualifiers = ParamUtil.getString(
			actionRequest, "channelQualifiers");

		if (Objects.equals(channelQualifiers, "all")) {
			_corEntryRelService.deleteCOREntryRels(
				CommerceChannel.class.getName(), corEntryId);
		}

		String orderTypeQualifiers = ParamUtil.getString(
			actionRequest, "orderTypeQualifiers");

		if (Objects.equals(orderTypeQualifiers, "all")) {
			_corEntryRelService.deleteCOREntryRels(
				CommerceOrderType.class.getName(), corEntryId);
		}
	}

	@Reference
	private COREntryRelService _corEntryRelService;

}