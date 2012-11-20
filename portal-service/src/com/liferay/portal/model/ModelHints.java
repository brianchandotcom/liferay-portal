/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model;

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public interface ModelHints {

	public String buildCustomValidatorName(String validatorName);

	public Map<String, String> getDefaultHints(String model);

	public Element getFieldsEl(String model, String field);

	public Map<String, String> getHints(String model, String field);

	public int getMaxLength(String model, String field);

	public List<String> getModels();

	public Tuple getSanitizeTuple(String model, String field);

	public List<Tuple> getSanitizeTuples(String model);

	public String getType(String model, String field);

	public List<Tuple> getValidators(String model, String field);

	public String getValue(
		String model, String field, String name, String defaultValue);

	public boolean isCustomValidator(String validatorName);

	public boolean isLocalized(String model, String field);

	public void read(ClassLoader classLoader, String source) throws Exception;

	public String trimString(String model, String field, String value);

}