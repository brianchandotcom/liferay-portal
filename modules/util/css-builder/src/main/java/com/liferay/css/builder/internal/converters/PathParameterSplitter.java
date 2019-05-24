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

package com.liferay.css.builder.internal.converters;

import com.beust.jcommander.converters.IParameterSplitter;

import java.io.File;

import java.util.Arrays;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class PathParameterSplitter implements IParameterSplitter {

	@Override
	public List<String> split(String value) {
		return Arrays.asList(value.split(",|" + File.pathSeparator));
	}

}