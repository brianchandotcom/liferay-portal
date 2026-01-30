/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.resource.v1_0;

import com.liferay.digital.sales.room.constants.DigitalSalesRoomTicketConstants;
import com.liferay.headless.digital.sales.room.dto.v1_0.InvitedMemberBrief;
import com.liferay.headless.digital.sales.room.resource.v1_0.InvitedMemberBriefResource;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/invited-member-brief.properties",
	scope = ServiceScope.PROTOTYPE, service = InvitedMemberBriefResource.class
)
public class InvitedMemberBriefResourceImpl
	extends BaseInvitedMemberBriefResourceImpl {

	@Override
	public void deleteDigitalSalesRoomInvitedMemberBrief(
			Long digitalSalesRoomId, Long invitedMemberBriefId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);

		Ticket ticket = _ticketLocalService.getTicket(invitedMemberBriefId);

		if (!Objects.equals(Group.class.getName(), ticket.getClassName()) ||
			(group.getGroupId() != ticket.getClassPK())) {

			throw new NoSuchModelException();
		}

		_ticketLocalService.deleteTicket(ticket.getTicketId());
	}

	@Override
	public Page<InvitedMemberBrief> getDigitalSalesRoomInvitedMemberBriefsPage(
			Long digitalSalesRoomId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);

		return Page.of(
			transform(
				_ticketLocalService.getTickets(
					group.getCompanyId(), Group.class.getName(),
					group.getGroupId(),
					DigitalSalesRoomTicketConstants.TYPE_INVITE_MEMBER),
				this::_toInvitedMemberBrief));
	}

	private InvitedMemberBrief _toInvitedMemberBrief(Ticket ticket)
		throws Exception {

		return _invitedMemberBriefDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				true, null, _dtoConverterRegistry, contextUser.getUserId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			ticket);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupService _groupService;

	@Reference(
		target = "(component.name=com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.InvitedMemberBriefDTOConverter)"
	)
	private DTOConverter<Ticket, InvitedMemberBrief>
		_invitedMemberBriefDTOConverter;

	@Reference
	private TicketLocalService _ticketLocalService;

}