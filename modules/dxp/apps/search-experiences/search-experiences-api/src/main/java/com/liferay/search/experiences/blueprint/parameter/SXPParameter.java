/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.blueprint.parameter;

import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public interface SXPParameter {

	public boolean evaluateContains(Object value);

	public boolean evaluateEquals(Object object);

	public boolean evaluateEquals(String format, Object object);

	public boolean evaluateIn(Object value);

	public boolean evaluateRange(Object gt, Object gte, Object lt, Object lte);

	public boolean evaluateRange(
		String format, Object gt, Object gte, Object lt, Object lte);

	public String evaluateToString(Map<String, String> options);

	public String getName();

	public String getTemplateVariable();

	public Object getValue();

	public boolean isTemplateVariable();

}