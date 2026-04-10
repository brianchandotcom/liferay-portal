/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.frontend.data.set.provider;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Daniel Sanz
 */
public class FDSDataProviderUtil {

	public static List<SystemFDSEntry> getSystemFDSEntries(
		String keywords, SystemFDSEntryRegistry systemFDSEntryRegistry) {

		return TransformUtil.transform(
			systemFDSEntryRegistry.getSystemFDSNames(),
			(String systemFDSName) -> {
				SystemFDSEntry systemFDSEntry =
					systemFDSEntryRegistry.getSystemFDSEntry(systemFDSName);

				if (systemFDSEntry == null) {
					return null;
				}

				if (Validator.isNotNull(keywords) &&
					(Validator.isNull(systemFDSEntry.getTitle()) ||
					 !StringUtil.matchesIgnoreCase(
						 systemFDSEntry.getTitle(), keywords)) &&
					!StringUtil.matchesIgnoreCase(
						systemFDSEntry.getName(), keywords)) {

					return null;
				}

				return systemFDSEntry;
			});
	}

}