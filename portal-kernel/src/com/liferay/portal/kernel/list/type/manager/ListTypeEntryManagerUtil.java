/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.list.type.manager;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Mátyás Wollner
 */
public class ListTypeEntryManagerUtil {

	public static Long
		getListTypeEntryIdByListTypeDefinitionExternalReferenceCode(
			String externalReferenceCode, long companyId, String key) {

		ListTypeEntryManager listTypeEntryManager =
			_listTypeEntryManagerSnapshot.get();

		return listTypeEntryManager.
			getListTypeEntryIdByListTypeDefinitionExternalReferenceCode(
				externalReferenceCode, companyId, key);
	}

	private static final Snapshot<ListTypeEntryManager>
		_listTypeEntryManagerSnapshot = new Snapshot<>(
			ListTypeEntryManagerUtil.class, ListTypeEntryManager.class);

}