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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerImpl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class StagedModelDataHandlerRegistryImpl
	implements StagedModelDataHandlerRegistry {

	public StagedModelDataHandler getStagedModelDataHandler(String className) {
		if (Validator.isNull(className) ||
			!_stagedModelDataHandlers.containsKey(className)) {

			return null;
		}

		return _stagedModelDataHandlers.get(className);
	}

	public List<StagedModelDataHandler> getStagedModelDataHandlerList() {
		return ListUtil.fromMapValues(_stagedModelDataHandlers);
	}

	public Map<String, StagedModelDataHandler> getStagedModelDataHandlers() {
		return _stagedModelDataHandlers;
	}

	public void register(StagedModelDataHandler stagedModelDataHandler) {
		if (stagedModelDataHandler == null) {
			return;
		}

		String stagedModelClassName = getStagedModelClassName(
			stagedModelDataHandler);

		_stagedModelDataHandlers.put(
			stagedModelClassName, stagedModelDataHandler);
	}

	public void unregister(StagedModelDataHandler stagedModelDataHandler) {
		if (stagedModelDataHandler == null) {
			return;
		}

		String stagedModelClassName = getStagedModelClassName(
			stagedModelDataHandler);

		if (stagedModelClassName == null) {
			return;
		}

		if (!_stagedModelDataHandlers.containsKey(stagedModelClassName)) {
			return;
		}

		_stagedModelDataHandlers.remove(stagedModelClassName);
	}

	protected String getStagedModelClassName(
		StagedModelDataHandler stagedModelDataHandler) {

		Class clazz = stagedModelDataHandler.getClass();

		Class superClazz = clazz.getSuperclass();

		if ((superClazz == null) ||
			!superClazz.getName().equals(
				StagedModelDataHandlerImpl.class.getName())) {

			return null;
		}

		ParameterizedType type =
			(ParameterizedType)clazz.getGenericSuperclass();

		Type[] actualTypeArgs = type.getActualTypeArguments();

		if ((actualTypeArgs != null) && (actualTypeArgs.length > 0)) {
			return ((Class)actualTypeArgs[0]).getName();
		}

		return null;
	}

	private Map<String, StagedModelDataHandler> _stagedModelDataHandlers =
		new HashMap<String, StagedModelDataHandler>();

}