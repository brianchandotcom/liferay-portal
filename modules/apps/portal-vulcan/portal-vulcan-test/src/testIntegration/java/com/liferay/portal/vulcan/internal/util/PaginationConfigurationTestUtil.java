/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.util;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.MapUtil;

/**
 * @author Sergio Jiménez del Coso
 */
public class PaginationConfigurationTestUtil {

	public static void withPageSizeLimit(
			int pageSizeLimit, UnsafeRunnable<Exception> runnable)
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						"com.liferay.portal.vulcan.internal.configuration." +
							"HeadlessAPICompanyConfiguration",
						MapUtil.singletonDictionary(
							"pageSizeLimit", pageSizeLimit))) {

			runnable.run();
		}
	}

}