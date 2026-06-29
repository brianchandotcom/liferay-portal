/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.spi;

/**
 * @author Christopher Kian
 */
public interface SecurityModuleProvider {

	public ModuleStatus getModuleStatus();

	public boolean isAllowedCompany(long companyId);

	public SelfTestResult performSelfTests();

}