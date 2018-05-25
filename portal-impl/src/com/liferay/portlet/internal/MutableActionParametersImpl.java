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

package com.liferay.portlet.internal;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.MutableActionParameters;

/**
 * @author Neil Griffin
 */
public class MutableActionParametersImpl
	extends MutablePortletParametersBase
	implements LiferayMutablePortletParameters, MutableActionParameters {

	public MutableActionParametersImpl() {
		this(new LinkedHashMap<>());
	}

	public MutableActionParametersImpl(Map<String, String[]> parameterMap) {
		super(parameterMap);
	}

	@Override
	public MutableActionParameters clone() {
		Map<String, String[]> parameterMap = getParameterMap();

		Map<String, String[]> copiedMap = deepCopyMap(parameterMap);

		return new MutableActionParametersImpl(copiedMap);
	}

}