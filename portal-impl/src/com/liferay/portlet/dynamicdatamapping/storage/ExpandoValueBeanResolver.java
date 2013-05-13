/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

/**
 * @author Marcellus Tavares
 */
public class ExpandoValueBeanResolver implements BeanResolver {

	public ExpandoValueBeanResolver(List<ExpandoValue> expandoValues) {
		_expandoValues = new HashMap<String, ExpandoValue>();

		try {
			for (ExpandoValue expandoValue : expandoValues) {
				ExpandoColumn expandoColumn = expandoValue.getColumn();

				_expandoValues.put(expandoColumn.getName(), expandoValue);
			}
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	@Override
	public Object resolve(EvaluationContext context, String beanName) {
		return _expandoValues.get(beanName);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ExpandoValueBeanResolver.class);

	private Map<String, ExpandoValue> _expandoValues;

}