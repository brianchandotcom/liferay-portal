/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import java.util.Locale;

/**
 * @author Michael C. Han
 */
public interface FullNameGenerator {

	public String getFullName(
		String firstName, String middleName, String lastName);

	public String getLocalizedFullName(
		String firstName, String middleName, String lastName, Locale locale,
		long prefixListTypeId, long suffixListTypeId);

	public String[] splitFullName(String fullName);

}