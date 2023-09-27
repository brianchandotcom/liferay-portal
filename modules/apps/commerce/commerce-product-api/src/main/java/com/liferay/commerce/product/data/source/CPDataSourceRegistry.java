/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.data.source;

import java.util.List;

/**
 * @author Marco Leo
 */
public interface CPDataSourceRegistry {

	public CPDataSource getCPDataSource(String key);

	public List<CPDataSource> getCPDataSources();

}