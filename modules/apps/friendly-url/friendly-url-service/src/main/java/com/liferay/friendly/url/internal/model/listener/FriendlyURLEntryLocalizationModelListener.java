/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.model.listener;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = ModelListener.class)
public class FriendlyURLEntryLocalizationModelListener
	extends BaseModelListener<FriendlyURLEntryLocalization> {

	@Override
	public void onAfterRemove(
			FriendlyURLEntryLocalization friendlyURLEntryLocalization)
		throws ModelListenerException {

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				friendlyURLEntryLocalization.getFriendlyURLEntryId());

		if (friendlyURLEntry == null) {
			return;
		}

		List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations =
			_friendlyURLEntryLocalService.getFriendlyURLEntryLocalizations(
				friendlyURLEntryLocalization.getFriendlyURLEntryId());

		if (friendlyURLEntryLocalizations.isEmpty()) {
			try {
				_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
					friendlyURLEntryLocalization.getFriendlyURLEntryId());
			}
			catch (PortalException portalException) {
				throw new ModelListenerException(portalException);
			}
		}
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}