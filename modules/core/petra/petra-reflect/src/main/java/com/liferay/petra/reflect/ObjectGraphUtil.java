/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class ObjectGraphUtil {

	public static void walkObjectGraph(Object object, Visitor visitor) {
		Queue<Object> queue = new LinkedList<>();

		queue.offer(object);

		Set<Object> visitedObjects = Collections.newSetFromMap(
			new IdentityHashMap<>());

		while ((object = queue.poll()) != null) {
			if (!visitedObjects.add(object)) {
				continue;
			}

			Class<?> clazz = object.getClass();

			if (clazz.isArray()) {
				clazz = clazz.getComponentType();

				if (clazz.isPrimitive()) {
					continue;
				}

				for (int i = 0; i < Array.getLength(object); i++) {
					Object element = Array.get(object, i);

					if (element != null) {
						queue.offer(element);
					}
				}

				continue;
			}

			while (clazz != null) {
				for (Field field : clazz.getDeclaredFields()) {
					if (Modifier.isStatic(field.getModifiers())) {
						continue;
					}

					field.setAccessible(true);

					try {
						Object value = visitor.visit(field, object);

						Class<?> type = field.getType();

						if ((value != null) && !type.isPrimitive()) {
							queue.offer(value);
						}
					}
					catch (Exception exception) {
						ReflectionUtil.throwException(exception);
					}
				}

				clazz = clazz.getSuperclass();
			}
		}
	}

	public abstract static class AnnotatedFieldMappingVisitor
		implements Visitor {

		public AnnotatedFieldMappingVisitor(
			Set<Class<?>> linkedClasses,
			Set<Class<? extends Annotation>> annotationClasses,
			Set<Class<?>> fieldTypeClasses) {

			_linkedClasses = linkedClasses;
			_annotationClasses = annotationClasses;
			_fieldTypeClasses = fieldTypeClasses;
		}

		@Override
		public Object visit(Field field, Object target) throws Exception {
			Object value = field.get(target);

			if ((value == null) || !isLinkedClass(field.getDeclaringClass())) {
				return null;
			}

			if (!hasAnnotation(field.getAnnotations()) ||
				!isFieldTypeClass(field.getType())) {

				return value;
			}

			field = ReflectionUtil.unfinalField(field);

			field.set(target, mapValue(field, value));

			return null;
		}

		protected abstract Object doMap(Field field, Object value);

		protected boolean hasAnnotation(Annotation[] annotations) {
			for (Annotation annotation : annotations) {
				for (Class<? extends Annotation> annotationClass :
						_annotationClasses) {

					if (annotationClass.isInstance(annotation)) {
						return true;
					}
				}
			}

			return false;
		}

		protected boolean isFieldTypeClass(Class<?> clazz) {
			Class<?> componentType = clazz;
			Class<?> currentComponentType = clazz.getComponentType();

			while (currentComponentType != null) {
				componentType = currentComponentType;

				currentComponentType = currentComponentType.getComponentType();
			}

			for (Class<?> fieldTypeClass : _fieldTypeClasses) {
				if (fieldTypeClass.isAssignableFrom(componentType)) {
					return true;
				}
			}

			return false;
		}

		protected boolean isLinkedClass(Class<?> clazz) {
			for (Class<?> linkedClass : _linkedClasses) {
				if (linkedClass.isAssignableFrom(clazz)) {
					return true;
				}
			}

			return false;
		}

		protected Object mapValue(Field field, Object value) {
			for (Class<?> fieldTypeClass : _fieldTypeClasses) {
				if (fieldTypeClass.isInstance(value)) {
					return doMap(field, value);
				}
			}

			value = ReflectionUtil.arrayClone(value);

			for (int i = 0; i < Array.getLength(value); i++) {
				Array.set(value, i, mapValue(field, Array.get(value, i)));
			}

			return value;
		}

		private final Set<Class<? extends Annotation>> _annotationClasses;
		private final Set<Class<?>> _fieldTypeClasses;
		private final Set<Class<?>> _linkedClasses;

	}

	public interface Visitor {

		public Object visit(Field field, Object target) throws Exception;

	}

}