/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.listener;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.spi.exception.CTEventException;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = CTEventListener.class)
public class CTOnDemandUserEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) throws CTEventException {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if (ctCollection.getOnDemandUserId() <= 0) {
			return;
		}

		_deleteOnDemandUser(ctCollection);
	}

	@Override
	public void onBeforeRemove(long ctCollectionId) throws CTEventException {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if ((ctCollection == null) || (ctCollection.getOnDemandUserId() <= 0)) {
			return;
		}

		_deleteOnDemandUser(ctCollection);
	}

	private void _deleteOnDemandUser(CTCollection ctCollection)
		throws CTEventException {

		try {
			_userLocalService.deleteUser(ctCollection.getOnDemandUserId());

			ctCollection.setOnDemandUserId(0);

			_ctCollectionLocalService.updateCTCollection(ctCollection);
		}
		catch (PortalException portalException) {
			throw new CTEventException(portalException);
		}
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}