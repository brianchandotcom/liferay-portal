/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.model.listener;

import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.TicketTable;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.SharingEntryTable;
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
		_updateSharingEntries(user);
	}

	@Override
	public void onAfterUpdate(User originalUser, User user) {
		_updateSharingEntries(user);
	}

	@Override
	public void onBeforeRemove(User user) {
		_sharingEntryLocalService.deleteToUserSharingEntries(user.getUserId());
	}

	private Predicate _getPredicate() {
		return SharingEntryTable.INSTANCE.classNameId.eq(
			TicketTable.INSTANCE.classNameId
		).and(
			SharingEntryTable.INSTANCE.classPK.eq(TicketTable.INSTANCE.classPK)
		).and(
			SharingEntryTable.INSTANCE.toTicketId.eq(
				TicketTable.INSTANCE.ticketId)
		);
	}

	private void _updateSharingEntries(User user) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				List<Ticket> tickets =
					(List<Ticket>)_ticketLocalService.dslQuery(
						DSLQueryFactoryUtil.selectDistinct(
							TicketTable.INSTANCE
						).from(
							TicketTable.INSTANCE
						).innerJoinON(
							SharingEntryTable.INSTANCE, _getPredicate()
						).where(
							_wherePredicate(user)
						));

				for (Ticket ticket : tickets) {
					for (SharingEntry sharingEntry :
							_sharingEntryLocalService.getToTicketSharingEntries(
								ticket.getTicketId())) {

						_updateSharingEntry(sharingEntry, user);
					}

					_ticketLocalService.deleteTicket(ticket);
				}

				return null;
			});
	}

	private void _updateSharingEntry(SharingEntry sharingEntry, User user) {
		SharingEntry existingSharingEntry =
			_sharingEntryLocalService.fetchSharingEntry(
				0, sharingEntry.getToUserGroupId(), user.getUserId(),
				sharingEntry.getClassNameId(), sharingEntry.getClassPK());

		if (existingSharingEntry != null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"A sharing entry already exists for user ",
						user.getUserId(), " with classNameId ",
						sharingEntry.getClassNameId(), " and classPK ",
						sharingEntry.getClassPK()));
			}

			_sharingEntryLocalService.deleteSharingEntry(sharingEntry);

			return;
		}

		sharingEntry.setToTicketId(0);
		sharingEntry.setToUserId(user.getUserId());

		_sharingEntryLocalService.updateSharingEntry(sharingEntry);
	}

	private Predicate _wherePredicate(User user) {
		return TicketTable.INSTANCE.companyId.eq(
			user.getCompanyId()
		).and(
			TicketTable.INSTANCE.type.eq(
				TicketConstants.TYPE_INVITE_COLLABORATOR)
		).and(
			TicketTable.INSTANCE.expirationDate.gt(new Date())
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

}