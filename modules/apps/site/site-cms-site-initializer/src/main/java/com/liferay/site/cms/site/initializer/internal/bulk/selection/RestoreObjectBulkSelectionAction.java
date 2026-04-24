/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.bulk.selection;

import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.cms.site.initializer.bulk.selection.BaseObjectBulkSelectionAction;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "bulk.selection.action.key=restore.object",
	service = BulkSelectionAction.class
)
public class RestoreObjectBulkSelectionAction
	extends BaseObjectBulkSelectionAction {

	@Override
	protected void doExecute(
			User user, Map<String, Serializable> inputMap, Object object)
		throws Exception {

		if (object instanceof ObjectEntry) {
			_objectEntryService.restoreObjectEntryFromTrash(
				(ObjectEntry)object, new ServiceContext());
		}
		else if (object instanceof ObjectEntryFolder) {
			_objectEntryFolderService.restoreObjectEntryFolderFromTrash(
				(ObjectEntryFolder)object, new ServiceContext());
		}
		else {
			throw new IllegalArgumentException("Unsupported object " + object);
		}
	}

	@Reference
	private ObjectEntryFolderService _objectEntryFolderService;

	@Reference
	private ObjectEntryService _objectEntryService;

}