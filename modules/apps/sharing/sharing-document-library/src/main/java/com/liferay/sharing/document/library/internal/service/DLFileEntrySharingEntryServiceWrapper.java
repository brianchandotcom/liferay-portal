/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.document.library.internal.service;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.service.SharingEntryServiceWrapper;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = ServiceWrapper.class)
public class DLFileEntrySharingEntryServiceWrapper
	extends SharingEntryServiceWrapper {

	@Override
	public SharingEntry addOrUpdateSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		return super.addOrUpdateSharingEntry(
			toUserId, classNameId, classPK, groupId, shareable,
			_processSharingEntryActions(classNameId, sharingEntryActions),
			expirationDate, serviceContext);
	}

	@Override
	public SharingEntry addSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		return super.addSharingEntry(
			toUserId, classNameId, classPK, groupId, shareable,
			_processSharingEntryActions(classNameId, sharingEntryActions),
			expirationDate, serviceContext);
	}

	@Override
	public SharingEntry updateSharingEntry(
			long sharingEntryId,
			Collection<SharingEntryAction> sharingEntryActions,
			boolean shareable, Date expirationDate,
			ServiceContext serviceContext)
		throws PortalException {

		SharingEntry sharingEntry = _sharingEntryLocalService.fetchSharingEntry(
			sharingEntryId);

		if (sharingEntry == null) {
			return super.updateSharingEntry(
				sharingEntryId, sharingEntryActions, shareable, expirationDate,
				serviceContext);
		}

		return super.updateSharingEntry(
			sharingEntryId,
			_processSharingEntryActions(
				sharingEntry.getClassNameId(), sharingEntryActions),
			shareable, expirationDate, serviceContext);
	}

	private Collection<SharingEntryAction> _processSharingEntryActions(
		long classNameId,
		Collection<SharingEntryAction> originalSharingEntryActions) {

		if (classNameId != _portal.getClassNameId(
				DLFileEntryConstants.getClassName())) {

			return originalSharingEntryActions;
		}

		Set<SharingEntryAction> sharingEntryActions = new HashSet<>(
			originalSharingEntryActions);

		if (originalSharingEntryActions.contains(SharingEntryAction.VIEW)) {
			sharingEntryActions.add(SharingEntryAction.DOWNLOAD);
		}
		else {
			sharingEntryActions.remove(SharingEntryAction.DOWNLOAD);
		}

		return sharingEntryActions;
	}

	@Reference
	private Portal _portal;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}