/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.service;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
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
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Galluzzi
 */
@Component(service = ServiceWrapper.class)
public class ObjectEntrySharingEntryServiceWrapper
	extends SharingEntryServiceWrapper {

	@Override
	public SharingEntry addOrUpdateSharingEntry(
			String externalReferenceCode, long toUserGroupId, long toUserId,
			long classNameId, long classPK, long groupId, boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		return super.addOrUpdateSharingEntry(
			externalReferenceCode, toUserGroupId, toUserId, classNameId,
			classPK, groupId, shareable,
			_processSharingEntryActions(
				classNameId, groupId, sharingEntryActions),
			expirationDate, serviceContext);
	}

	@Override
	public SharingEntry addSharingEntry(
			String externalReferenceCode, long toUserGroupId, long toUserId,
			long classNameId, long classPK, long groupId, boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		return super.addSharingEntry(
			externalReferenceCode, toUserGroupId, toUserId, classNameId,
			classPK, groupId, shareable,
			_processSharingEntryActions(
				classNameId, groupId, sharingEntryActions),
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
				sharingEntry.getClassNameId(), sharingEntry.getGroupId(),
				sharingEntryActions),
			shareable, expirationDate, serviceContext);
	}

	private Collection<SharingEntryAction> _processSharingEntryActions(
			long classNameId, long groupId,
			Collection<SharingEntryAction> originalSharingEntryActions)
		throws PortalException {

		if (originalSharingEntryActions.contains(SharingEntryAction.DOWNLOAD)) {
			return originalSharingEntryActions;
		}

		Group group = _groupLocalService.getGroup(groupId);

		ObjectDefinition cmsBasicDocumentObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_DOCUMENT", group.getCompanyId());

		if ((cmsBasicDocumentObjectDefinition == null) ||
			!Objects.equals(
				_portal.getClassName(classNameId),
				cmsBasicDocumentObjectDefinition.getClassName())) {

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
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}