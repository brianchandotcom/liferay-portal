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

package com.liferay.dynamic.data.mapping.expression.internal.helper;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.internal.pool.DDMExpressionFunctionPooledFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.pool2.impl.GenericObjectPool;

import org.osgi.service.component.ComponentFactory;

/**
 * @author Rafael Praxedes
 */
public class DDMExpressionFunctionTrackerHelper {

	public void addComponentFactory(ComponentFactory componentFactory) {
		GenericObjectPool<DDMExpressionFunction> ddmExpressionFunctionPool =
			createDDMExpressionFunctionPool(componentFactory);

		DDMExpressionFunction ddmExpressionFunction = _getDDMExpressionFunction(
			ddmExpressionFunctionPool);

		ddmExpressionFunctionComponentFactoryMap.put(
			ddmExpressionFunction.getName(), ddmExpressionFunctionPool);

		ungetDDMExpressionFunction(ddmExpressionFunction);
	}

	public void clear() {
		Set<Map.Entry<String, GenericObjectPool<DDMExpressionFunction>>>
			entrySet = ddmExpressionFunctionComponentFactoryMap.entrySet();

		for (Map.Entry<String, GenericObjectPool<DDMExpressionFunction>> entry :
				entrySet) {

			GenericObjectPool<DDMExpressionFunction> ddmExpressionFunctionPool =
				entry.getValue();

			ddmExpressionFunctionPool.close();
		}

		ddmExpressionFunctionComponentFactoryMap.clear();
	}

	public DDMExpressionFunction getDDMExpressionFunction(String functionName) {
		GenericObjectPool<DDMExpressionFunction> ddmExpressionFunctionPool =
			ddmExpressionFunctionComponentFactoryMap.get(functionName);

		if (ddmExpressionFunctionPool == null) {
			return null;
		}

		return _getDDMExpressionFunction(ddmExpressionFunctionPool);
	}

	public void removeComponentFactory(ComponentFactory componentFactory) {
		Set<Map.Entry<String, GenericObjectPool<DDMExpressionFunction>>>
			entrySet = ddmExpressionFunctionComponentFactoryMap.entrySet();

		for (Map.Entry<String, GenericObjectPool<DDMExpressionFunction>> entry :
				entrySet) {

			GenericObjectPool<DDMExpressionFunction> ddmExpressionFunctionPool =
				entry.getValue();

			DDMExpressionFunctionPooledFactory
				ddmExpressionFunctionPooledFactory =
					(DDMExpressionFunctionPooledFactory)
						ddmExpressionFunctionPool.getFactory();

			if (Objects.equals(
					componentFactory,
					ddmExpressionFunctionPooledFactory.getComponentFactory())) {

				ddmExpressionFunctionPool.close();

				ddmExpressionFunctionComponentFactoryMap.remove(entry.getKey());

				break;
			}
		}
	}

	public void ungetDDMExpressionFunction(
		DDMExpressionFunction ddmExpressionFunction) {

		GenericObjectPool<DDMExpressionFunction> ddmExpressionFunctionPool =
			ddmExpressionFunctionComponentFactoryMap.get(
				ddmExpressionFunction.getName());

		ddmExpressionFunctionPool.returnObject(ddmExpressionFunction);
	}

	protected GenericObjectPool<DDMExpressionFunction>
		createDDMExpressionFunctionPool(ComponentFactory componentFactory) {

		DDMExpressionFunctionPooledFactory ddmExpressionFunctionPooledFactory =
			new DDMExpressionFunctionPooledFactory(componentFactory);

		return new GenericObjectPool<>(ddmExpressionFunctionPooledFactory);
	}

	protected Map<String, GenericObjectPool<DDMExpressionFunction>>
		ddmExpressionFunctionComponentFactoryMap = new ConcurrentHashMap<>();

	private DDMExpressionFunction _getDDMExpressionFunction(
		GenericObjectPool<DDMExpressionFunction> ddmExpressionFunctionPool) {

		try {
			if (ddmExpressionFunctionPool == null) {
				return null;
			}

			return ddmExpressionFunctionPool.borrowObject();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMExpressionFunctionTrackerHelper.class);

}