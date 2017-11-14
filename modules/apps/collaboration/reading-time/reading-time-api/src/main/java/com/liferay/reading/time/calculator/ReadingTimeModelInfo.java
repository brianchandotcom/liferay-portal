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

package com.liferay.reading.time.calculator;

import com.liferay.portal.kernel.model.GroupedModel;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public interface ReadingTimeModelInfo<T extends GroupedModel> {

	public String getContent(T model);

	public String getContentType(T model);

	public Locale getLocale(T model);

}