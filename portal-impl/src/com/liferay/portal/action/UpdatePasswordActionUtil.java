/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.action;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Alvaro Saugar
 */
public class UpdatePasswordActionUtil {

	public static String generateUpdatePasswordURL(
			HttpServletRequest httpServletRequest, User user)
		throws PwdEncryptorException {

		StringBundler sb = new StringBundler(8);

		sb.append(PortalUtil.getPortalURL(httpServletRequest));
		sb.append(PortalUtil.getPathContext());
		sb.append("/c/portal/update_password?p_l_id=");
		sb.append(LayoutConstants.DEFAULT_PLID);
		sb.append("&ticketId=");

		Ticket ticket = TicketLocalServiceUtil.addDistinctTicket(
			user.getCompanyId(), User.class.getName(), user.getUserId(),
			TicketConstants.TYPE_PASSWORD, null,
			new Date(
				System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)),
			new ServiceContext());

		sb.append(ticket.getTicketId());

		sb.append("&ticketKey=");
		sb.append(ticket.getKey());

		ticket.setKey(PasswordEncryptorUtil.encrypt(ticket.getKey()));

		TicketLocalServiceUtil.updateTicket(ticket);

		return sb.toString();
	}

}