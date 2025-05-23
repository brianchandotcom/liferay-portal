/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

/**
 * @author István András Dézsi
 */
public class PreupgradeVerifyProcessSuite extends PreupgradeVerifyProcess {

	@Override
	public void verify() throws VerifyException {
		verify(new PreupgradeVerifyProperties());
		verify(new PreupgradeVerifyDefaultUser());
	}

}