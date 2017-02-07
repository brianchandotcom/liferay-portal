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

package com.liferay.mail.reader.web.portlet.action;

import com.liferay.mail.reader.model.Account;
import com.liferay.mail.reader.service.AccountLocalService;
import com.liferay.mail.reader.web.util.MailManager;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Scott Lee
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = {"key=login.events.post"},
	service = LifecycleAction.class
)
public class LoginPostAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			initiateSynchronization(request);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void initiateSynchronization(HttpServletRequest request)
		throws PortalException {

		long userId = PortalUtil.getUserId(request);

		if (userId <= 0) {
			return;
		}

		List<Account> accounts = _accountLocalService.getAccounts(userId);

		if (accounts.isEmpty()) {
			return;
		}

		MailManager mailManager = MailManager.getInstance(request);

		for (Account account : accounts) {
			mailManager.synchronizeAccount(account.getAccountId());
		}
	}

	@Reference
	private AccountLocalService _accountLocalService;

}