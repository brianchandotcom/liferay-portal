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

package com.liferay.batch.engine.internal.item;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.io.Closeable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import java.util.List;
import java.util.Locale;

import org.osgi.framework.ServiceObjects;

/**
 * @author Ivica cardic
 */
public class BatchEngineTaskItemResourceDelegate implements Closeable {

	public BatchEngineTaskItemResourceDelegate(
			Company company, String[] itemClassFieldNames,
			Method resourceMethod,
			ServiceObjects<Object> resourceServiceObjects, User user)
		throws ReflectiveOperationException {

		_company = company;
		_itemClassFieldNames = itemClassFieldNames;
		_resourceMethod = resourceMethod;
		_resourceServiceObjects = resourceServiceObjects;
		_user = user;

		_resource = _getResource();
	}

	public void addItems(List<?> items) throws Exception {
		for (Object item : items) {
			Object[] args = new Object[_resourceMethod.getParameterCount()];

			Parameter[] parameters = _resourceMethod.getParameters();

			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];

				if (parameter.getType() == item.getClass()) {
					args[i] = item;
				}
				else {
					if (_itemClassFieldNames[i] == null) {
						throw new IllegalArgumentException(
							"Unable to find method argument name");
					}

					Class<?> itemClass = item.getClass();

					Field field = itemClass.getDeclaredField(
						_itemClassFieldNames[i]);

					field.setAccessible(true);

					args[i] = field.get(item);
				}
			}

			_resourceMethod.invoke(_resource, args);
		}
	}

	@Override
	public void close() {
		_resourceServiceObjects.ungetService(_resource);
	}

	private Object _getResource() throws ReflectiveOperationException {
		Object resource = _resourceServiceObjects.getService();

		_setFieldValue(
			resource, "contextAcceptLanguage",
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
		_setFieldValue(resource, "contextCompany", _company);
		_setFieldValue(resource, "contextUser", _user);

		return resource;
	}

	private void _setFieldValue(Object resource, String fieldName, Object value)
		throws ReflectiveOperationException {

		Class<?> resourceClass = resource.getClass();

		Class<?> resourceSuperclass = resourceClass.getSuperclass();

		Field field = resourceSuperclass.getDeclaredField(fieldName);

		field.setAccessible(true);

		field.set(resource, value);
	}

	private final Company _company;
	private final String[] _itemClassFieldNames;
	private final Object _resource;
	private final Method _resourceMethod;
	private final ServiceObjects<Object> _resourceServiceObjects;
	private final User _user;

}