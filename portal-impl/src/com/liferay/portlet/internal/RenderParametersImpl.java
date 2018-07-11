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

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.portlet.MutableRenderParameters;

/**
 * @author Neil Griffin
 */
public class RenderParametersImpl
	extends PortletParametersBase<MutableRenderParameters>
	implements LiferayRenderParameters {

	public RenderParametersImpl(
		Map<String, String[]> parameterMap,
		Set<String> publicRenderParameterNames, String namespace) {

		super(
			parameterMap, namespace,
			copiedMap -> new MutableRenderParametersImpl(
				copiedMap, _nullSafe(publicRenderParameterNames)));

		_publicRenderParameterNames = _nullSafe(publicRenderParameterNames);
	}

	@Override
	public Set<String> getPublicRenderParameterNames() {
		return _publicRenderParameterNames;
	}

	@Override
	public boolean isPublic(String name) {
		return _publicRenderParameterNames.contains(name);
	}

	private static Set<String> _nullSafe(Set<String> set) {
		if (set == null) {
			return Collections.emptySet();
		}

		return set;
	}

	private final Set<String> _publicRenderParameterNames;

}