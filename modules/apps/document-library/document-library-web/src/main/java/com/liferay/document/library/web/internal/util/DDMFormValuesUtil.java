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

package com.liferay.document.library.web.internal.util;

import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tai Pham
 */
@Component(service = {})
public class DDMFormValuesUtil {

	public static DDMFormValues getDDMFormValuesHttpServletRequest(
		DDMStructure ddmStructure, HttpServletRequest httpServletRequest) {

		return _ddmFormValuesFactory.create(
			_getDDMStructureHttpServletRequest(
				httpServletRequest, ddmStructure.getStructureId()),
			_ddmBeanTranslator.translate(ddmStructure.getDDMForm()));
	}

	@Reference(unbind = "-")
	protected void setDDMBeanTranslator(DDMBeanTranslator ddmBeanTranslator) {
		_ddmBeanTranslator = ddmBeanTranslator;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesFactory(
		DDMFormValuesFactory ddmFormValuesFactory) {

		_ddmFormValuesFactory = ddmFormValuesFactory;
	}

	private static HttpServletRequest _getDDMStructureHttpServletRequest(
		HttpServletRequest httpServletRequest, long structureId) {

		DynamicServletRequest dynamicServletRequest = new DynamicServletRequest(
			httpServletRequest, new HashMap<>());

		String namespace = String.valueOf(structureId) + StringPool.UNDERLINE;

		Map<String, String[]> parameterMap =
			httpServletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();

			if (StringUtil.startsWith(parameterName, namespace)) {
				dynamicServletRequest.setParameterValues(
					parameterName.substring(namespace.length()),
					entry.getValue());
			}
		}

		return dynamicServletRequest;
	}

	private static DDMBeanTranslator _ddmBeanTranslator;
	private static DDMFormValuesFactory _ddmFormValuesFactory;

}