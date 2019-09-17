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

package com.liferay.batch.engine.internal.writer;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.io.Closeable;
import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import java.util.List;
import java.util.Locale;

import org.osgi.framework.ServiceObjects;

/**
 * @author Ivica cardic
 */
public class BatchEngineTaskItemWriter<T> implements Closeable {

	public BatchEngineTaskItemWriter(
			Company company, Method resourceMethod,
			String[] resourceMethodParameters,
			ServiceObjects<Object> resourceServiceObjects, User user)
		throws Exception {

		_company = company;
		_resourceMethod = resourceMethod;
		_resourceMethodParameters = resourceMethodParameters;
		_resourceServiceObjects = resourceServiceObjects;
		_user = user;

		_resource = _getResource();
	}

	@Override
	public void close() throws IOException {
		if (_resource != null) {
			_resourceServiceObjects.ungetService(_resource);
		}
	}

	public void write(List<? extends T> items) throws Exception {
		for (T item : items) {
			Object[] args = new Object[_resourceMethod.getParameterCount()];

			Parameter[] parameters = _resourceMethod.getParameters();

			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];

				if (parameter.getType() == item.getClass()) {
					args[i] = item;
				}
				else {
					if (_resourceMethodParameters[i] == null) {
						throw new IllegalArgumentException(
							"Unable to find method argument name");
					}

					Class<?> itemClass = item.getClass();

					Field parameterField = itemClass.getDeclaredField(
						_resourceMethodParameters[i]);

					parameterField.setAccessible(true);

					args[i] = parameterField.get(item);
				}
			}

			_resourceMethod.invoke(_resource, args);
		}
	}

	private Object _getResource() throws Exception {
		Object resource = _resourceServiceObjects.getService();

		_setFieldValue(
			"contextAcceptLanguage", resource,
			new AcceptLanguage() {

				@Override
				public List<Locale> getLocales() {
					return null;
				}

				@Override
				public String getPreferredLanguageId() {
					return _user.getLanguageId();
				}

				@Override
				public Locale getPreferredLocale() {
					return LocaleUtil.fromLanguageId(_user.getLanguageId());
				}

			});

		_setFieldValue("contextCompany", resource, _company);
		_setFieldValue("contextUser", resource, _user);

		return resource;
	}

	private void _setFieldValue(String fieldName, Object resource, Object value)
		throws IllegalAccessException, NoSuchFieldException {

		Class<?> resourceClass = resource.getClass();

		Class<?> resourceSuperclass = resourceClass.getSuperclass();

		Field field = resourceSuperclass.getDeclaredField(fieldName);

		field.setAccessible(true);

		field.set(resource, value);
	}

	private final Company _company;
	private final Object _resource;
	private final Method _resourceMethod;
	private final String[] _resourceMethodParameters;
	private final ServiceObjects<Object> _resourceServiceObjects;
	private final User _user;

}