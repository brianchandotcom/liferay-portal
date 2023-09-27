/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.util.Tuple;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public interface ModelHints {

	public String buildCustomValidatorName(String validatorName);

	public Map<String, String> getDefaultHints(String model);

	public Object getFieldsElement(String model, String field);

	public Map<String, String> getHints(String model, String field);

	public int getMaxLength(String model, String field);

	public List<String> getModels();

	public Tuple getSanitizeTuple(String model, String field);

	public List<Tuple> getSanitizeTuples(String model);

	public String getType(String model, String field);

	public List<Tuple> getValidators(String model, String field);

	public String getValue(
		String model, String field, String name, String defaultValue);

	public boolean hasField(String model, String field);

	public boolean isCustomValidator(String validatorName);

	public boolean isLocalized(String model, String field);

	public void read(ClassLoader classLoader, InputStream inputStream)
		throws Exception;

	public void read(ClassLoader classLoader, String source) throws Exception;

	public String trimString(String model, String field, String value);

}