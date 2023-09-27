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
public class ContactOrganizationDTOConverterContext
	extends DefaultDTOConverterContext {

	public ContactOrganizationDTOConverterContext(
		Object id, Locale locale, String[] syncedOrganizationIds) {

		super(false, new HashMap<>(), null, id, locale, null, null);

		_syncedOrganizationIds = syncedOrganizationIds;
	}

	public boolean isSelected(String organizationId) {
		return ArrayUtil.contains(_syncedOrganizationIds, organizationId);
	}

	private final String[] _syncedOrganizationIds;

}