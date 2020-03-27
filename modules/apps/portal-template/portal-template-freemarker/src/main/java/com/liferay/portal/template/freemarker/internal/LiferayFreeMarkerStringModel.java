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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.InvalidPropertyException;
import freemarker.ext.beans.StringModel;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.Set;

/**
 * @author Marta Medio
 */
public class LiferayFreeMarkerStringModel extends StringModel {

	public LiferayFreeMarkerStringModel(Object object, BeansWrapper wrapper) {
		super(object, wrapper);
	}

	@Override
	public TemplateModel get(String key) throws TemplateModelException {
		if (_restrictedMethodNames == null) {
			throw new InvalidPropertyException(
				StringBundler.concat(
					"Denied access to method or field ", key, " of ",
					object.getClass()));
		}

		String methodOrFieldName = StringUtil.toLowerCase(key);

		for (String restrictedMethodName : _restrictedMethodNames) {
			if (restrictedMethodName.endsWith(methodOrFieldName)) {
				throw new InvalidPropertyException(
					StringBundler.concat(
						"Denied access to method or field ", key, " of ",
						object.getClass()));
			}
		}

		return super.get(key);
	}

	public void setRestrictedMethodNames(Set<String> restrictedMethodNames) {
		_restrictedMethodNames = restrictedMethodNames;
	}

	private Set<String> _restrictedMethodNames;

}