/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.GetterMethodImpl;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.property.access.spi.SetterMethodImpl;

/**
 * @author Dante Wang
 */
public class LiferayPropertyAccessor implements PropertyAccessStrategy {

	@Override
	public PropertyAccess buildPropertyAccess(
		Class containerJavaType, String propertyName) {

		return new LiferayPropertyAccess(this, containerJavaType, propertyName);
	}

	public static class LiferayPropertyAccess implements PropertyAccess {

		public LiferayPropertyAccess(
			PropertyAccessStrategy propertyAccessStrategy, Class<?> clazz,
			String propertyName) {

			_propertyAccessStrategy = propertyAccessStrategy;

			_getter = _createGetter(clazz, propertyName);
			_setter = _createSetter(clazz, propertyName);
		}

		@Override
		public Getter getGetter() {
			return _getter;
		}

		@Override
		public PropertyAccessStrategy getPropertyAccessStrategy() {
			return _propertyAccessStrategy;
		}

		@Override
		public Setter getSetter() {
			return _setter;
		}

		protected String formatPropertyName(String propertyName) {
			return TextFormatter.format(propertyName, TextFormatter.G);
		}

		@SuppressWarnings("unchecked")
		private Getter _createGetter(Class<?> clazz, String propertyName)
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
			catch (NoSuchMethodException noSuchMethodException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Getter not found for ", clazz.getName(),
							StringPool.POUND, propertyName),
						noSuchMethodException);
				}

				Method getterMethod = ReflectHelper.findGetterMethod(
					clazz, propertyName);

				return new GetterMethodImpl(clazz, propertyName, getterMethod);
			}
		}

		@SuppressWarnings("unchecked")
		private Setter _createSetter(Class<?> clazz, String propertyName)
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
			catch (NoSuchMethodException noSuchMethodException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Setter not found for ", clazz.getName(),
							StringPool.POUND, propertyName),
						noSuchMethodException);
				}

				Method getterMethod = ReflectHelper.findGetterMethod(
					clazz, propertyName);

				Method setterMethod = ReflectHelper.findSetterMethod(
					clazz, propertyName, getterMethod.getReturnType());

				return new SetterMethodImpl(clazz, propertyName, setterMethod);
			}
		}

		private LiferayPropertyMutator _getLiferayPropertyMutator(
			Class<?> clazz, String name) {

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

					Map<String, Function<Object, Object>>
						attributeGetterFunctions = null;
					Map<String, BiConsumer<Object, Object>>
						attributeSetterBiConsumers = null;

					try {
						ClassLoader classLoader = modelClass.getClassLoader();

						String modelClassName = modelClass.getName();

						Class<?> attributeGetterFunctionsHolderClass =
							classLoader.loadClass(
								modelClassName.concat(
									"$AttributeGetterFunctionsHolder"));

						Field attributeGetterFunctionsField =
							attributeGetterFunctionsHolderClass.
								getDeclaredField("_attributeGetterFunctions");

						attributeGetterFunctionsField.setAccessible(true);

						attributeGetterFunctions =
							(Map<String, Function<Object, Object>>)
								attributeGetterFunctionsField.get(null);

						Class<?> attributeSetterBiConsumersHolderClass =
							classLoader.loadClass(
								modelClassName.concat(
									"$AttributeSetterBiConsumersHolder"));

						Field attributeSetterBiConsumersField =
							attributeSetterBiConsumersHolderClass.
								getDeclaredField("_attributeSetterBiConsumers");

						attributeSetterBiConsumersField.setAccessible(true);

						attributeSetterBiConsumers =
							(Map<String, BiConsumer<Object, Object>>)
								attributeSetterBiConsumersField.get(null);
					}
					catch (ReflectiveOperationException
								reflectiveOperationException) {

						if (_log.isDebugEnabled()) {
							_log.debug(reflectiveOperationException);
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

			Function<Object, Object> getterFunction =
				attributeGetterFunctions.get(name);
			BiConsumer<Object, Object> setterBiConsumer =
				attributeSetterBiConsumers.get(name);

			if ((getterFunction == null) || (setterBiConsumer == null)) {
				return null;
			}

			return new LiferayPropertyMutator(getterFunction, setterBiConsumer);
		}

		private static final Log _log = LogFactoryUtil.getLog(
			LiferayPropertyAccess.class);

		private static final Map<Class<?>, ModelMutators> _modelMutators =
			new ConcurrentReferenceValueHashMap<>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);

		private final Getter _getter;
		private final PropertyAccessStrategy _propertyAccessStrategy;
		private final Setter _setter;

		private static class LiferayPropertyGetter implements Getter {

			@Override
			public Object get(Object target) throws PropertyAccessException {
				try {
					return _method.invoke(target);
				}
				catch (IllegalAccessException | IllegalArgumentException |
					   InvocationTargetException exception) {

					throw new PropertyAccessException(
						exception, exception.getMessage(), false,
						_method.getDeclaringClass(), _propertyName);
				}
			}

			@Override
			public Object getForInsert(
					Object target, Map mergeMap,
					SharedSessionContractImplementor
						sharedSessionContractImplementor)
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
				SharedSessionContractImplementor
					sharedSessionContractImplementor) {

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
				Object target, Object value,
				SessionFactoryImplementor factory) {

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
					   InvocationTargetException | NullPointerException
						   exception) {

					throw new PropertyAccessException(
						exception, exception.getMessage(), true,
						_method.getDeclaringClass(), _propertyName);
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

}