/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.internal.security.permission;

import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Prathima Shreenath
 */
@Component(
	property = "model.class.name=com.liferay.portal.reports.engine.console.model.Entry",
	service = PermissionUpdateHandler.class
)
public class EntryPermissionUpdateHandler implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		Entry entry = _entryLocalService.fetchEntry(
			GetterUtil.getLong(primKey));

		if (entry == null) {
			return;
		}

		entry.setModifiedDate(new Date());

		_entryLocalService.updateEntry(entry);
	}

	@Reference
	private EntryLocalService _entryLocalService;

}