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

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.templateparser.TemplateNode;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.EnumerationModel;
import freemarker.ext.beans.MapModel;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.ext.beans.StringModel;
import freemarker.ext.util.ModelFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.lang.reflect.Field;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.w3c.dom.Node;

/**
 * @author Mika Koivisto
 */
public class LiferayObjectWrapper extends DefaultObjectWrapper {

	public LiferayObjectWrapper() {
		super(Configuration.getVersion());

		try {
			_cacheClassNamesField.set(
				_classIntrospectorField.get(this),
				new HashSet<Object>() {

					@Override
					public boolean add(Object object) {
						return false;
					}

					@Override
					public void clear() {
					}

					@Override
					public boolean contains(Object object) {
						return false;
					}

					@Override
					public boolean remove(Object object) {
						return false;
					}

				});
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
	}

	@Override
	public TemplateModel wrap(Object object) throws TemplateModelException {
		if (object == null) {
			return null;
		}

		if (object instanceof TemplateModel) {
			return (TemplateModel)object;
		}

		Class<?> clazz = object.getClass();

		String className = clazz.getName();

		if (className.startsWith("com.liferay.")) {
			if (object instanceof TemplateNode) {
				return new LiferayTemplateModel((TemplateNode)object, this);
			}

			if (object instanceof Collection) {
				return new SimpleSequence((Collection)object, this);
			}

			if (object instanceof Map) {
				return new MapModel((Map)object, this);
			}

			return _STRING_MODEL_FACTORY.create(object, this);
		}

		if (_handledClasses.contains(object.getClass())) {
			return _STRING_MODEL_FACTORY.create(object, this);
		}

		return super.wrap(object);
	}

	@Override
	protected TemplateModel handleUnknownType(Object object) {
		if (object instanceof Node) {
			return wrapDomNode(object);
		}

		if (object instanceof ResourceBundle) {
			return new ResourceBundleModel((ResourceBundle)object, this);
		}

		if (object instanceof Enumeration) {
			return new EnumerationModel((Enumeration)object, this);
		}

		_handledClasses.add(object.getClass());

		return _STRING_MODEL_FACTORY.create(object, this);
	}

	private static final ModelFactory _STRING_MODEL_FACTORY =
		new ModelFactory() {

			@Override
			public TemplateModel create(
				Object object, ObjectWrapper objectWrapper) {

				return new StringModel(object, (BeansWrapper)objectWrapper);
			}

		};

	private static final Field _cacheClassNamesField;
	private static final Field _classIntrospectorField;
	private static final Set<Class<?>> _handledClasses =
		Collections.newSetFromMap(
			new ConcurrentReferenceKeyHashMap<>(
				FinalizeManager.SOFT_REFERENCE_FACTORY));

	static {
		try {
			ClassLoader classLoader =
				DefaultObjectWrapper.class.getClassLoader();

			_cacheClassNamesField = ReflectionUtil.getDeclaredField(
				classLoader.loadClass("freemarker.ext.beans.ClassIntrospector"),
				"cacheClassNames");

			_classIntrospectorField = ReflectionUtil.getDeclaredField(
				BeansWrapper.class, "classIntrospector");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}