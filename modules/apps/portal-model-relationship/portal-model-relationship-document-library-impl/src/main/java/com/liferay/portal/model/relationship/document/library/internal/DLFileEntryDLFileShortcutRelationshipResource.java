/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = RelationshipResource.class
)
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