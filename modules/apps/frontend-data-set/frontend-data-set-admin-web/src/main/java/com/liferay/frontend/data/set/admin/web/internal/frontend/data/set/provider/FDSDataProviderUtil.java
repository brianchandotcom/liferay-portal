/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.frontend.data.set.provider;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Daniel Sanz
 */
public class FDSDataProviderUtil {

	public static List<SystemFDSEntry> getSystemFDSEntries(
		String keywords, SystemFDSEntryRegistry systemFDSEntryRegistry) {

		Set<String> systemFDSNames = systemFDSEntryRegistry.getSystemFDSNames();

		if (systemFDSNames == null) {
			return Collections.emptyList();
		}

		List<SystemFDSEntry> systemFDSEntries = new ArrayList<>();

		for (String systemFDSName : systemFDSNames) {
			SystemFDSEntry systemFDSEntry =
				systemFDSEntryRegistry.getSystemFDSEntry(systemFDSName);

			if (systemFDSEntry == null) {
				continue;
			}

			String label = systemFDSEntry.getTitle();

			if (Validator.isNull(label)) {
				label = systemFDSEntry.getName();
			}

			if (Validator.isNotNull(keywords) &&
				!StringUtil.matchesIgnoreCase(label, keywords) &&
				!StringUtil.matchesIgnoreCase(
					systemFDSEntry.getName(), keywords)) {

				continue;
			}

			systemFDSEntries.add(systemFDSEntry);
		}

		return systemFDSEntries;
	}

}