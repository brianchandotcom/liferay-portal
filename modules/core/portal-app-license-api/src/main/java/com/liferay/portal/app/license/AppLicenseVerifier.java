/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.app.license;

import org.osgi.framework.Bundle;

/**
 * @author Amos Fong
 */
public interface AppLicenseVerifier {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #verify(String,
	 *             String, String, String[])}
	 */
	@Deprecated
	public void verify(
			Bundle bundle, String productId, String productType,
			String productVersion)
		throws Exception;

	public void verify(
			String productId, String productType, String productVersion,
			String... bundleSymbolicNames)
		throws Exception;

}