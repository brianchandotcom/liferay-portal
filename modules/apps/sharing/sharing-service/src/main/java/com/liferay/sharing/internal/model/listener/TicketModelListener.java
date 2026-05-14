/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.model.listener;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.sharing.service.SharingEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = ModelListener.class)
public class TicketModelListener extends BaseModelListener<Ticket> {

	@Override
	public void onBeforeRemove(Ticket ticket) {
		_sharingEntryLocalService.deleteToTicketSharingEntries(
			ticket.getTicketId());
	}

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}