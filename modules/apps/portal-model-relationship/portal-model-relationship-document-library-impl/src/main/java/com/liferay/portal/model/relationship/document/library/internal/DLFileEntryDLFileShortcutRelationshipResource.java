/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.relationship.document.library.internal;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.portal.relationship.Relationship;
import com.liferay.portal.relationship.RelationshipResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 *
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = RelationshipResource.class
)
@Deprecated
public class DLFileEntryDLFileShortcutRelationshipResource
	implements RelationshipResource<DLFileEntry> {

	@Override
	public Relationship<DLFileEntry> relationship(
		Relationship.Builder<DLFileEntry> builder) {

		return builder.modelSupplier(
			fileEntryId -> _dlFileEntryLocalService.fetchDLFileEntry(
				fileEntryId)
		).outboundMultiRelationship(
			dlFileEntry -> _dlFileShortcutLocalService.getFileShortcuts(
				dlFileEntry.getFileEntryId())
		).build();
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileShortcutLocalService _dlFileShortcutLocalService;

}