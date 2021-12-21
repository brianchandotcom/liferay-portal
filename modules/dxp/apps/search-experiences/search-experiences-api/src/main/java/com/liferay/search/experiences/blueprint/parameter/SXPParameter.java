/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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