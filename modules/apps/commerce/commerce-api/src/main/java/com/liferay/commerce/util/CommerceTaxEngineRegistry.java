/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import com.liferay.commerce.tax.CommerceTaxEngine;

import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceTaxEngineRegistry {

	public CommerceTaxEngine getCommerceTaxEngine(String key);

	public Map<String, CommerceTaxEngine> getCommerceTaxEngines();

}