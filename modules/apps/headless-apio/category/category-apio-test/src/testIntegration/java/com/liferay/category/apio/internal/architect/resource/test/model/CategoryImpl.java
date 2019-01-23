/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.category.apio.internal.architect.resource.test.model;

import com.liferay.category.apio.architect.model.Category;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rubén Pulido
 */
public class CategoryImpl implements Category {

	public CategoryImpl(String name, String description) {
		_name = name;
		_description = description;
	}

	@Override
	public Map<Locale, String> getDescriptionMap(Locale locale) {
		return Collections.singletonMap(locale, _description);
	}

	@Override
	public Map<Locale, String> getNameMap(Locale locale) {
		return Collections.singletonMap(locale, _name);
	}

	private final String _description;
	private final String _name;

}