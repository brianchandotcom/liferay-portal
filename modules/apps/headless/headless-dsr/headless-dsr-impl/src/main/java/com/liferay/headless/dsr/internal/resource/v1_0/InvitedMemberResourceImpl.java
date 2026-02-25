/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.dsr.internal.resource.v1_0;

import com.liferay.headless.dsr.dto.v1_0.InvitedMember;
import com.liferay.headless.dsr.resource.v1_0.InvitedMemberResource;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.site.dsr.site.initializer.constants.DSRTicketConstants;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/invited-member.properties",
	scope = ServiceScope.PROTOTYPE, service = InvitedMemberResource.class
)
public class InvitedMemberResourceImpl extends BaseInvitedMemberResourceImpl {

	@Override
	public void deleteRoomInvitedMember(Long roomId, Long invitedMemberId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _getGroup(roomId);
		Ticket ticket = _ticketLocalService.getTicket(invitedMemberId);

		if (!Objects.equals(Group.class.getName(), ticket.getClassName()) ||
			(group.getGroupId() != ticket.getClassPK())) {

			throw new NoSuchModelException();
		}

		_ticketLocalService.deleteTicket(ticket.getTicketId());
	}

	@Override
	public Page<InvitedMember> getRoomInvitedMembersPage(Long roomId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _getGroup(roomId);

		return Page.of(
			transform(
				_ticketLocalService.getTickets(
					group.getCompanyId(), Group.class.getName(),
					group.getGroupId(), DSRTicketConstants.TYPE_INVITE_MEMBER),
				this::_toInvitedMember));
	}

	private Group _getGroup(long roomId) throws Exception {
		ObjectEntry objectEntry = _objectEntryService.getObjectEntry(roomId);

		Map<String, Serializable> values = objectEntry.getValues();

		return _groupService.getGroup(GetterUtil.getLong(values.get("siteId")));
	}

	private InvitedMember _toInvitedMember(Ticket ticket) throws Exception {
		return _invitedMemberDTOConverter.toDTO(
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
		target = "(component.name=com.liferay.headless.dsr.internal.dto.v1_0.converter.InvitedMemberDTOConverter)"
	)
	private DTOConverter<Ticket, InvitedMember> _invitedMemberDTOConverter;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private TicketLocalService _ticketLocalService;

}