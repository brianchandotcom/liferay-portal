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

package com.liferay.portal.freemarker;

import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import freemarker.core.Environment;
import freemarker.core.TemplateClassResolver;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.utility.ObjectConstructor;

/**
 * @author Raymond Augé
 */
public class LiferayTemplateClassResolver implements TemplateClassResolver {

	@Override
	public Class<?> resolve(
			String className, Environment environment, Template template)
		throws TemplateException {

		if (className.equals(ObjectConstructor.class.getName())) {
			throw new TemplateException(
				"Instantiating " + className + " is not allowed in the " +
					"template for security reasons",
				environment);
		}

		for (String restrictedClassName :
				PropsValues.FREEMARKER_ENGINE_RESTRICTED_CLASSES) {

			if (className.equals(restrictedClassName)) {
				throw new TemplateException(
					"Instantiating " + className + " is not allowed in the " +
						"template for security reasons",
					environment);
			}
		}

		for (String restrictedPackageName :
				PropsValues.FREEMARKER_ENGINE_RESTRICTED_PACKAGES) {

			if (className.startsWith(restrictedPackageName)) {
				throw new TemplateException(
					"Instantiating " + className + " is not allowed in the " +
						"template for security reasons",
					environment);
			}
		}

		try {
			return Class.forName(
				className, true, ClassLoaderUtil.getContextClassLoader());
		}
		catch (Exception e) {
			throw new TemplateException(e, environment);
		}
	}

}