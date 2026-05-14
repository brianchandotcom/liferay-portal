/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.model.listener;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterCreate(User user) {
		if (user.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		_updateSharingEntries(user);
	}

	@Override
	public void onAfterUpdate(User originalUser, User user) {
		if ((originalUser.getStatus() == user.getStatus()) ||
			(originalUser.getStatus() == WorkflowConstants.STATUS_APPROVED) ||
			(user.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		_updateSharingEntries(user);
	}

	@Override
	public void onBeforeRemove(User user) {
		_sharingEntryLocalService.deleteToUserSharingEntries(user.getUserId());
	}

	private void _updateSharingEntries(User user) {
		List<Ticket> tickets = _ticketLocalService.getTickets(
			user.getCompanyId(), TicketConstants.TYPE_INVITE_COLLABORATOR,
			user.getEmailAddress());

		Date now = new Date();

		for (Ticket ticket : tickets) {
			Date expirationDate = ticket.getExpirationDate();

			if ((expirationDate != null) && !expirationDate.after(now)) {
				continue;
			}

			List<SharingEntry> pendingSharingEntries =
				_sharingEntryLocalService.getToTicketSharingEntries(
					ticket.getTicketId());

			for (SharingEntry pendingSharingEntry : pendingSharingEntries) {
				SharingEntry existingSharingEntry =
					_sharingEntryLocalService.fetchSharingEntry(
						0, pendingSharingEntry.getToUserGroupId(),
						user.getUserId(), pendingSharingEntry.getClassNameId(),
						pendingSharingEntry.getClassPK());

				if (existingSharingEntry != null) {
					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"A sharing entry already exists for user ",
								user.getUserId(), " with classNameId ",
								pendingSharingEntry.getClassNameId(),
								" and classPK ", pendingSharingEntry.getClassPK()));
					}

					_sharingEntryLocalService.deleteSharingEntry(
						pendingSharingEntry);
				}
				else {
					pendingSharingEntry.setToTicketId(0);
					pendingSharingEntry.setToUserId(user.getUserId());

					_sharingEntryLocalService.updateSharingEntry(
						pendingSharingEntry);
				}
			}

			try {
				_ticketLocalService.deleteTicket(ticket.getTicketId());
			}
			catch (PortalException portalException) {
				throw new ModelListenerException(portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

}