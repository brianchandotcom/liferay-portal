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

package com.liferay.portal.instances.on.demand.admin.internal.helper;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instances.on.demand.admin.internal.constants.OnDemandAdminConstants;
import com.liferay.portal.instances.on.demand.admin.internal.generator.OnDemandAdminTokenGenerator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = OnDemandAdminHelper.class)
public class OnDemandAdminHelper {

	public String getLoginURL(Company company, long userId)
		throws PortalException {

		StringBundler sb = new StringBundler(3);

		sb.append(
			_portal.getPortalURL(
				company.getVirtualHostname(),
				_portal.getPortalServerPort(false), false));
		sb.append("?ticketKey=");

		Ticket ticket = _onDemandAdminTokenGenerator.generate(company, userId);

		sb.append(ticket.getKey());

		return sb.toString();
	}

	public boolean isOnDemandAdminUser(User user) {
		if ((user != null) &&
			StringUtil.startsWith(
				user.getScreenName(),
				OnDemandAdminConstants.ON_DEMAND_ADMIN_SCREEN_NAME_PREFIX)) {

			return true;
		}

		return false;
	}

	@Reference
	private OnDemandAdminTokenGenerator _onDemandAdminTokenGenerator;

	@Reference
	private Portal _portal;

}