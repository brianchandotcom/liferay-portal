/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.dto.v1_0.converter;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author Riccardo Ferrari
 */
public class ContactAccountGroupDTOConverterContext
	extends DefaultDTOConverterContext {

	public ContactAccountGroupDTOConverterContext(
		Object id, Locale locale, String[] syncedAccountGroupIds) {

		super(false, new HashMap<>(), null, id, locale, null, null);

		_syncedAccountGroupIds = syncedAccountGroupIds;
	}

	public boolean isSelected(String accountGroupId) {
		return ArrayUtil.contains(_syncedAccountGroupIds, accountGroupId);
	}

	private final String[] _syncedAccountGroupIds;

}