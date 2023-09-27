/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util;

import java.util.List;

/**
 * @author Ethan Bustad
 */
public interface CPVersionContributorRegistry {

	public CPVersionContributor getCPVersionContributor(String key);

	public List<CPVersionContributor> getCPVersionContributors();

}