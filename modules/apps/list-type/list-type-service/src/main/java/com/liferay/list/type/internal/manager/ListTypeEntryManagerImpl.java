/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.manager;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.list.type.manager.ListTypeEntryManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mátyás Wollner
 */
@Component(service = ListTypeEntryManager.class)
public class ListTypeEntryManagerImpl implements ListTypeEntryManager {

	@Override
	public long getListTypeEntryIdByListTypeDefinitionExternalReferenceCode(
		String externalReferenceCode, long companyId, String key) {

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					externalReferenceCode, companyId);

		if (listTypeDefinition == null) {
			return 0;
		}

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.fetchListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), key);

		if (listTypeEntry == null) {
			return 0;
		}

		return listTypeEntry.getListTypeEntryId();
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

}