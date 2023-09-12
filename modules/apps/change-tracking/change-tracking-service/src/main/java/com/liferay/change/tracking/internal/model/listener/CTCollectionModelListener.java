/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.model.listener;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.service.TicketLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ModelListener.class)
public class CTCollectionModelListener extends BaseModelListener<CTCollection> {

	@Override
	public void onAfterRemove(CTCollection ctCollection) {
		_ctPreferencesLocalService.resetCTPreferences(
			ctCollection.getCtCollectionId());
	}

	@Override
	public void onAfterUpdate(
			CTCollection originalCTCollection, CTCollection ctCollection)
		throws ModelListenerException {

		if (ctCollection.isShareable() ||
			(ctCollection.isShareable() !=
				originalCTCollection.isShareable())) {

			return;
		}

		for (Ticket ticket :
				_ticketLocalService.getTickets(
					ctCollection.getCompanyId(), CTCollection.class.getName(),
					ctCollection.getCtCollectionId(),
					TicketConstants.TYPE_ON_DEMAND_USER_LOGIN)) {

			_ticketLocalService.deleteTicket(ticket);
		}
	}

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

}