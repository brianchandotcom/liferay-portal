/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portlet.expando.model.ExpandoValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

/**
 * @author Marcellus Tavares
 */
public class ExpandoValueBeanResolver implements BeanResolver {

	public ExpandoValueBeanResolver(List<ExpandoValue> expandoValues) {
		_expandoValueMap = new HashMap<String, ExpandoValue>();

		try {
			for (ExpandoValue expandoValue : expandoValues) {
				_expandoValueMap.put(
					expandoValue.getColumn().getName(), expandoValue);
			}
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	public Object resolve(EvaluationContext context, String beanName)
		throws AccessException {

		return _expandoValueMap.get(beanName);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ExpandoValueBeanResolver.class);

	private Map<String, ExpandoValue> _expandoValueMap;

}