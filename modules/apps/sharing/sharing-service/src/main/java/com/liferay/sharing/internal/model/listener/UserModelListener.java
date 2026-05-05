/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.model.listener;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterCreate(User user) throws ModelListenerException {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				ActionableDynamicQuery actionableDynamicQuery =
					_ticketLocalService.getActionableDynamicQuery();

				actionableDynamicQuery.setAddCriteriaMethod(
					dynamicQuery -> {
						dynamicQuery.add(
							RestrictionsFactoryUtil.eq(
								"companyId", user.getCompanyId()));
						dynamicQuery.add(
							RestrictionsFactoryUtil.eq(
								"type",
								TicketConstants.TYPE_INVITE_COLLABORATOR));
					});

				actionableDynamicQuery.setPerformActionMethod(
					(Ticket ticket) -> {
						String extraInfo = ticket.getExtraInfo();

						if (Validator.isNull(extraInfo)) {
							return;
						}

						JSONObject jsonObject;

						try {
							jsonObject = _jsonFactory.createJSONObject(
								extraInfo);
						}
						catch (JSONException jsonException) {
							if (_log.isWarnEnabled()) {
								_log.warn(jsonException);
							}

							return;
						}

						if (!StringUtil.equalsIgnoreCase(
								jsonObject.getString("emailAddress"),
								user.getEmailAddress())) {

							return;
						}

						_updateSharingEntries(ticket, user);

						_ticketLocalService.deleteTicket(ticket);
					});

				actionableDynamicQuery.performActions();

				return null;
			});
	}

	@Override
	public void onBeforeRemove(User user) {
		_sharingEntryLocalService.deleteToUserSharingEntries(user.getUserId());
	}

	private void _updateSharingEntries(Ticket ticket, User user) {
		for (SharingEntry sharingEntry :
				_sharingEntryLocalService.getToTicketSharingEntries(
					ticket.getTicketId())) {

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

				continue;
			}

			sharingEntry.setToTicketId(0);
			sharingEntry.setToUserId(user.getUserId());

			_sharingEntryLocalService.updateSharingEntry(sharingEntry);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

}