/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.util;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.model.Company;

/**
 * @author Alejandro Tardín
 */
public class EnableUtil {

	public static void checkEnabled(Company company) {
		if (!(LazyReferencingThreadLocal.isEnabled() ||
			  ExportImportThreadLocal.isExportInProcess() ||
			  ExportImportThreadLocal.isImportInProcess()) &&
			!FeatureFlagManagerUtil.isEnabled(
				company.getCompanyId(), "LPD-35443")) {

			throw new UnsupportedOperationException();
		}
	}

}