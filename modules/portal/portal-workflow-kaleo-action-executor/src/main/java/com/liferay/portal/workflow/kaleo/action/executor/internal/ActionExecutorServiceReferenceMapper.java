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

package com.liferay.portal.workflow.kaleo.action.executor.internal;

import com.liferay.osgi.service.tracker.map.ServiceReferenceMapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.action.executor.ActionExecutor;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Marcellus Tavares
 */
public class ActionExecutorServiceReferenceMapper
	implements ServiceReferenceMapper<String, ActionExecutor> {

	public String getKey(String language, String className) {
		if (Validator.equals(language, _LANGUAGE_JAVA)) {
			return language + StringPool.COLON + className;
		}
		else {
			return language + StringPool.COLON + StringPool.STAR;
		}
	}

	@Override
	public void map(
		ServiceReference<ActionExecutor> serviceReference,
		Emitter<String> emitter) {

		String[] languages = getLanguages(serviceReference);

		String className = getClassName(serviceReference);

		for (String language : languages) {
			String key = getKey(language, className);

			emitter.emit(key);
		}
	}

	protected String getClassName(
		ServiceReference<ActionExecutor> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		BundleContext bundleContext = bundle.getBundleContext();

		ActionExecutor actionExecutor = bundleContext.getService(
			serviceReference);

		Class<?> clazz = actionExecutor.getClass();

		return clazz.getName();
	}

	protected String[] getLanguages(
		ServiceReference<ActionExecutor> serviceReference) {

		Object languageProperty = serviceReference.getProperty(
			"com.liferay.portal.workflow.kaleo.action.executor.language");

		String[] defaultValue = new String[] {String.valueOf(languageProperty)};

		return GetterUtil.getStringValues(languageProperty, defaultValue);
	}

	private static final String _LANGUAGE_JAVA = "java";

}