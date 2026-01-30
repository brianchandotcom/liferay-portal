/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter;

import com.liferay.headless.digital.sales.room.dto.v1_0.InvitedMemberBrief;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = {
		"application.name=Liferay.Headless.Digital.Sales.Room",
		"dto.class.name=com.liferay.headless.digital.sales.room.dto.v1_0.InvitedMemberBrief",
		"version=v1.0"
	},
	service = DTOConverter.class
)
public class InvitedMemberBriefDTOConverter
	implements DTOConverter<Ticket, InvitedMemberBrief> {

	@Override
	public String getContentType() {
		return Ticket.class.getSimpleName();
	}

	@Override
	public InvitedMemberBrief toDTO(
			DTOConverterContext dtoConverterContext, Ticket ticket)
		throws Exception {

		if (ticket == null) {
			return null;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			ticket.getExtraInfo());

		String emailAddress = jsonObject.getString("emailAddress");

		User user = _userLocalService.fetchUserByEmailAddress(
			ticket.getCompanyId(), emailAddress);

		if (user != null) {
			return null;
		}

		return new InvitedMemberBrief() {
			{
				setEmailAddress(() -> jsonObject.getString("emailAddress"));
				setId(ticket::getTicketId);
				setRoleKey(() -> jsonObject.getString("roleKey"));
			}
		};
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private UserLocalService _userLocalService;

}