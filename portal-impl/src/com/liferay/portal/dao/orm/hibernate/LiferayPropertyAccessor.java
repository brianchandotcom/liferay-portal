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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.util.TextFormatter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.hibernate.PropertyAccessException;
import org.hibernate.PropertyNotFoundException;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.property.BasicPropertyAccessor;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;

/**
 * @author Preston Crary
 */
public class LiferayPropertyAccessor extends BasicPropertyAccessor {

	@Override
	@SuppressWarnings("unchecked")
	public Getter getGetter(Class clazz, String propertyName)
		throws PropertyNotFoundException {

		LiferayPropertyMutator liferayPropertyMutator =
			_getLiferayPropertyMutator(clazz, propertyName);

		if (liferayPropertyMutator != null) {
			return liferayPropertyMutator;
		}

		String methodNameSuffix = formatPropertyName(propertyName);

		String getterMethodName = "get".concat(methodNameSuffix);

		try {
			Method getterMethod = clazz.getMethod(getterMethodName);

			return new LiferayPropertyGetter(getterMethod, propertyName);
		}
		catch (NoSuchMethodException nsme) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Getter not found for ", clazz.getName(),
						StringPool.POUND, propertyName),
					nsme);
			}

			return super.getGetter(clazz, propertyName);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Setter getSetter(Class clazz, String propertyName)
		throws PropertyNotFoundException {

		LiferayPropertyMutator liferayPropertyMutator =
			_getLiferayPropertyMutator(clazz, propertyName);

		if (liferayPropertyMutator != null) {
			return liferayPropertyMutator;
		}

		String methodNameSuffix = formatPropertyName(propertyName);

		String getterMethodName = "get".concat(methodNameSuffix);
		String setterMethodName = "set".concat(methodNameSuffix);

		try {
			Method getterMethod = clazz.getMethod(getterMethodName);

			Method setterMethod = clazz.getMethod(
				setterMethodName, getterMethod.getReturnType());

			return new LiferayPropertySetter(setterMethod, propertyName);
		}
		catch (NoSuchMethodException nsme) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Setter not found for ", clazz.getName(),
						StringPool.POUND, propertyName),
					nsme);
			}

			return super.getSetter(clazz, propertyName);
		}
	}

	protected String formatPropertyName(String propertyName) {
		return TextFormatter.format(propertyName, TextFormatter.G);
	}

	private LiferayPropertyMutator _getLiferayPropertyMutator(
		Class clazz, String name) {

		ModelMutators modelMutators = _modelMutators.computeIfAbsent(
			clazz,
			modelClass -> {
				if (!BaseModelImpl.class.isAssignableFrom(modelClass)) {
					return new ModelMutators(null, null);
				}

				Class<?> superClass = modelClass.getSuperclass();

				while (BaseModelImpl.class != superClass) {
					modelClass = superClass;

					superClass = modelClass.getSuperclass();
				}

				Map<String, Function<Object, Object>> attributeGetterFunctions =
					null;
				Map<String, BiConsumer<Object, Object>>
					attributeSetterBiConsumers = null;

				try {
					Field attributeGetterFunctionsField =
						modelClass.getDeclaredField(
							"_attributeGetterFunctions");

					attributeGetterFunctionsField.setAccessible(true);

					attributeGetterFunctions =
						(Map<String, Function<Object, Object>>)
							attributeGetterFunctionsField.get(null);

					Field attributeSetterBiConsumersField =
						modelClass.getDeclaredField(
							"_attributeSetterBiConsumers");

					attributeSetterBiConsumersField.setAccessible(true);

					attributeSetterBiConsumers =
						(Map<String, BiConsumer<Object, Object>>)
							attributeSetterBiConsumersField.get(null);
				}
				catch (ReflectiveOperationException roe) {
					if (_log.isDebugEnabled()) {
						_log.debug(roe, roe);
					}
				}

				return new ModelMutators(
					attributeGetterFunctions, attributeSetterBiConsumers);
			});

		Map<String, Function<Object, Object>> attributeGetterFunctions =
			modelMutators._attributeGetterFunctions;

		Map<String, BiConsumer<Object, Object>> attributeSetterBiConsumers =
			modelMutators._attributeSetterBiConsumers;

		if (attributeSetterBiConsumers == null) {
			return null;
		}

		Function<Object, Object> getterFunction = attributeGetterFunctions.get(
			name);
		BiConsumer<Object, Object> setterBiConsumer =
			attributeSetterBiConsumers.get(name);

		if ((getterFunction == null) || (setterBiConsumer == null)) {
			return null;
		}

		return new LiferayPropertyMutator(getterFunction, setterBiConsumer);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayPropertyAccessor.class);

	private static final Map<Class, ModelMutators> _modelMutators =
		new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

	private static class LiferayPropertyGetter implements Getter {

		@Override
		public Object get(Object target) throws PropertyAccessException {
			try {
				return _method.invoke(target);
			}
			catch (IllegalAccessException | IllegalArgumentException |
				   InvocationTargetException e) {

				throw new PropertyAccessException(
					e, e.getMessage(), false, _method.getDeclaringClass(),
					_propertyName);
			}
		}

		@Override
		public Object getForInsert(
				Object target, Map mergeMap,
				SessionImplementor sessionImplementor)
			throws PropertyAccessException {

			return get(target);
		}

		@Override
		public Member getMember() {
			return _method;
		}

		@Override
		public Method getMethod() {
			return _method;
		}

		@Override
		public String getMethodName() {
			return _method.getName();
		}

		@Override
		public Class getReturnType() {
			return _method.getReturnType();
		}

		private LiferayPropertyGetter(Method method, String propertyName) {
			_method = method;
			_propertyName = propertyName;
		}

		private final Method _method;
		private final String _propertyName;

	}

	private static class LiferayPropertyMutator implements Getter, Setter {

		@Override
		public Object get(Object target) {
			return _getterFunction.apply(target);
		}

		@Override
		public Object getForInsert(
			Object target, Map mergeMap,
			SessionImplementor sessionImplementor) {

			return _getterFunction.apply(target);
		}

		@Override
		public Member getMember() {
			return null;
		}

		@Override
		public Method getMethod() {
			return null;
		}

		@Override
		public String getMethodName() {
			return null;
		}

		@Override
		public Class getReturnType() {
			return null;
		}

		@Override
		public void set(
			Object target, Object value, SessionFactoryImplementor factory) {

			_setterBiConsumer.accept(target, value);
		}

		private LiferayPropertyMutator(
			Function<Object, Object> getterFunction,
			BiConsumer<Object, Object> setterBiConsumer) {

			_getterFunction = getterFunction;
			_setterBiConsumer = setterBiConsumer;
		}

		private final Function<Object, Object> _getterFunction;
		private final BiConsumer<Object, Object> _setterBiConsumer;

	}

	private static class LiferayPropertySetter implements Setter {

		@Override
		public Method getMethod() {
			return _method;
		}

		@Override
		public String getMethodName() {
			return _method.getName();
		}

		@Override
		public void set(
				Object target, Object value,
				SessionFactoryImplementor sessionFactoryImplementor)
			throws PropertyAccessException {

			try {
				_method.invoke(target, value);
			}
			catch (IllegalAccessException | IllegalArgumentException |
				   InvocationTargetException | NullPointerException e) {

				throw new PropertyAccessException(
					e, e.getMessage(), true, _method.getDeclaringClass(),
					_propertyName);
			}
		}

		private LiferayPropertySetter(Method method, String propertyName) {
			_method = method;
			_propertyName = propertyName;
		}

		private final Method _method;
		private final String _propertyName;

	}

	private static class ModelMutators {

		private ModelMutators(
			Map<String, Function<Object, Object>> attributeGetterFunctions,
			Map<String, BiConsumer<Object, Object>>
				attributeSetterBiConsumers) {

			_attributeGetterFunctions = attributeGetterFunctions;
			_attributeSetterBiConsumers = attributeSetterBiConsumers;
		}

		private final Map<String, Function<Object, Object>>
			_attributeGetterFunctions;
		private final Map<String, BiConsumer<Object, Object>>
			_attributeSetterBiConsumers;

	}

}