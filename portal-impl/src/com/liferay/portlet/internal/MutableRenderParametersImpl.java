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

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.portlet.MutableRenderParameters;

/**
 * @author Neil Griffin
 */
public class MutableRenderParametersImpl
	extends MutablePortletParametersBase
	implements LiferayMutableRenderParameters {

	public MutableRenderParametersImpl(
		Map<String, String[]> parameterMap,
		Set<String> publicRenderParameterNames) {

		super(parameterMap);

		_originalParameterMap = deepCopyMap(parameterMap);
		_publicRenderParameterNames = publicRenderParameterNames;
	}

	@Override
	public void clearPrivate() {
		Map<String, String[]> parameterMap = getParameterMap();

		Set<String> parameterNames = parameterMap.keySet();

		Iterator<String> itr = parameterNames.iterator();

		while (itr.hasNext()) {
			String parameterName = itr.next();

			if (!_publicRenderParameterNames.contains(parameterName)) {
				itr.remove();
			}
		}
	}

	@Override
	public void clearPublic() {
		Map<String, String[]> parameterMap = getParameterMap();

		Set<String> parameterNames = parameterMap.keySet();

		Iterator<String> itr = parameterNames.iterator();

		while (itr.hasNext()) {
			String parameterName = itr.next();

			if (_publicRenderParameterNames.contains(parameterName)) {
				itr.remove();
			}
		}
	}

	@Override
	public MutableRenderParameters clone() {
		Map<String, String[]> parameterMap = getParameterMap();

		Map<String, String[]> copiedMap = deepCopyMap(parameterMap);

		return new MutableRenderParametersImpl(
			copiedMap, _publicRenderParameterNames);
	}

	@Override
	public boolean isMutated(String name) {
		return !Arrays.equals(_originalParameterMap.get(name), getValues(name));
	}

	@Override
	public boolean isPublic(String name) {
		return _publicRenderParameterNames.contains(name);
	}

	private final Map<String, String[]> _originalParameterMap;
	private final Set<String> _publicRenderParameterNames;

}