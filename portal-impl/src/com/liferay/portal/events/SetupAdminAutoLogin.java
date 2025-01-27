/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.events;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.AutoLoginException;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.DefaultAdminUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alvaro Saugar
 */
public class SetupAdminAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doHandleException(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Exception exception)
		throws AutoLoginException {

		if (_log.isDebugEnabled()) {
			_log.debug(exception);
		}

		throw new AutoLoginException(exception);
	}

	@Override
	protected String[] doLogin(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (Validator.isNotNull(PropsValues.DEFAULT_ADMIN_PASSWORD)) {
			return null;
		}

		Company company = PortalUtil.getCompany(httpServletRequest);

		User user = DefaultAdminUtil.fetchDefaultAdmin(company.getCompanyId());

		if (user == null) {
			return null;
		}

		String reminderQueryAnswer = user.getReminderQueryAnswer();

		if (user.isPasswordReset() &&
			reminderQueryAnswer.equals(WorkflowConstants.LABEL_PENDING) &&
			Validator.isNull(user.getReminderQueryQuestion()) &&
			Validator.isNull(user.getLastFailedLoginDate()) &&
			Validator.isNull(user.getLockoutDate())) {

			Date expirationDate = new Date(
				System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10));

			Ticket ticket = TicketLocalServiceUtil.addDistinctTicket(
				user.getCompanyId(), User.class.getName(), user.getUserId(),
				TicketConstants.TYPE_PASSWORD, null, expirationDate,
				new ServiceContext());

			ticket.setKey(PasswordEncryptorUtil.encrypt(ticket.getKey()));

			ticket = TicketLocalServiceUtil.updateTicket(ticket);

			StringBundler sb = new StringBundler(8);

			sb.append(PortalUtil.getPortalURL(httpServletRequest));
			sb.append(PortalUtil.getPathContext());
			sb.append("/c/portal/update_password?p_l_id=");
			sb.append(LayoutConstants.DEFAULT_PLID);
			sb.append("&ticketId=");
			sb.append(ticket.getTicketId());
			sb.append("&ticketKey=");
			sb.append(ticket.getKey());

			httpServletRequest.setAttribute(
				AutoLogin.AUTO_LOGIN_REDIRECT_AND_CONTINUE, sb.toString());

			String[] credentials = new String[3];

			credentials[0] = String.valueOf(user.getUserId());
			credentials[1] = user.getPassword();
			credentials[2] = Boolean.TRUE.toString();

			return credentials;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SetupAdminAutoLogin.class);

}