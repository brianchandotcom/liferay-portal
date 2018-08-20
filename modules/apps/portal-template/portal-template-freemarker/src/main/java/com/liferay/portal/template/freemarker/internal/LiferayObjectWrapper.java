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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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

	public void setFreeMarkerEngineConfiguration(
		FreeMarkerEngineConfiguration freeMarkerEngineConfiguration) {

		String[] allowedClassNames = GetterUtil.getStringValues(
			freeMarkerEngineConfiguration.allowedClasses());

		_allowedClassNames = new ArrayList(allowedClassNames.length);

		for (String allowedClassName : allowedClassNames) {
			allowedClassName = StringUtil.trim(allowedClassName);

			if (Validator.isBlank(allowedClassName)) {
				continue;
			}

			_allowedClassNames.add(allowedClassName);
		}

		_restrictedPackageNames = new ArrayList<>();

		String[] restrictedClassNames = GetterUtil.getStringValues(
			freeMarkerEngineConfiguration.restrictedClasses());

		_restrictedClasses = new ArrayList<>(restrictedClassNames.length);

		for (String restrictedClassName : restrictedClassNames) {
			restrictedClassName = StringUtil.trim(restrictedClassName);

			if (Validator.isBlank(restrictedClassName)) {
				continue;
			}

			try {
				_restrictedClasses.add(Class.forName(restrictedClassName));
			}
			catch (ClassNotFoundException cnfe) {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Unable to find restricted class ",
							restrictedClassName,
							". Registering as package name"),
						cnfe);
				}

				_restrictedPackageNames.add(restrictedClassName);
			}
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

		if (!_allowedClassNames.contains(StringPool.STAR) &&
			!_allowedClassNames.contains(className)) {

			for (Class restrictedClass : _restrictedClasses) {
				if (restrictedClass.isAssignableFrom(clazz)) {
					throw new TemplateModelException(
						StringBundler.concat(
							"Denied to resolve class ", className,
							" due to security reasons, restricted by ",
							restrictedClass.getName()));
				}
			}

			Package clazzPackage = clazz.getPackage();

			if (clazzPackage != null) {
				String packageName = clazzPackage.getName() + StringPool.PERIOD;

				for (String restrictedPackageName : _restrictedPackageNames) {
					if (packageName.startsWith(restrictedPackageName)) {
						throw new TemplateModelException(
							StringBundler.concat(
								"Denied to resolve class ", className,
								" due to security reasons, restricted by ",
								restrictedPackageName));
					}
				}
			}
		}

		if (className.startsWith("com.liferay.")) {
			if (object instanceof TemplateNode) {
				return new LiferayTemplateModel((TemplateNode)object, this);
			}

			if (object instanceof Collection) {
				return _COLLECTION_MODEL_FACTORY.create(object, this);
			}

			if (object instanceof Map) {
				return _MAP_MODEL_FACTORY.create(object, this);
			}

			return _STRING_MODEL_FACTORY.create(object, this);
		}

		ModelFactory modelFactory = _modelFactories.get(object.getClass());

		if (modelFactory != null) {
			return modelFactory.create(object, this);
		}

		return super.wrap(object);
	}

	@Override
	protected TemplateModel handleUnknownType(Object object) {
		if (object instanceof Node) {
			return wrapDomNode(object);
		}

		if (object instanceof TemplateNode) {
			return new LiferayTemplateModel((TemplateNode)object, this);
		}

		if (object instanceof ResourceBundle) {
			return _RESOURCE_BUNDLE_MODEL_FACTORY.create(object, this);
		}

		if (object instanceof Enumeration) {
			return _ENUMERATION_MODEL_FACTORY.create(object, this);
		}

		if (object instanceof Collection) {
			return _COLLECTION_MODEL_FACTORY.create(object, this);
		}

		if (object instanceof Map) {
			return _MAP_MODEL_FACTORY.create(object, this);
		}

		_modelFactories.put(object.getClass(), _STRING_MODEL_FACTORY);

		return _STRING_MODEL_FACTORY.create(object, this);
	}

	private static final ModelFactory _COLLECTION_MODEL_FACTORY =
		new ModelFactory() {

			@Override
			public TemplateModel create(
				Object object, ObjectWrapper objectWrapper) {

				return new SimpleSequence((Collection)object, objectWrapper);
			}

		};

	private static final ModelFactory _ENUMERATION_MODEL_FACTORY =
		new ModelFactory() {

			@Override
			public TemplateModel create(
				Object object, ObjectWrapper objectWrapper) {

				return new EnumerationModel(
					(Enumeration)object, (BeansWrapper)objectWrapper);
			}

		};

	private static final ModelFactory _MAP_MODEL_FACTORY = new ModelFactory() {

		@Override
		public TemplateModel create(
			Object object, ObjectWrapper objectWrapper) {

			return new MapModel((Map)object, (BeansWrapper)objectWrapper);
		}

	};

	private static final ModelFactory _RESOURCE_BUNDLE_MODEL_FACTORY =
		new ModelFactory() {

			@Override
			public TemplateModel create(
				Object object, ObjectWrapper objectWrapper) {

				return new ResourceBundleModel(
					(ResourceBundle)object, (BeansWrapper)objectWrapper);
			}

		};

	private static final ModelFactory _STRING_MODEL_FACTORY =
		new ModelFactory() {

			@Override
			public TemplateModel create(
				Object object, ObjectWrapper objectWrapper) {

				return new StringModel(object, (BeansWrapper)objectWrapper);
			}

		};

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayObjectWrapper.class);

	private static final Field _cacheClassNamesField;
	private static final Field _classIntrospectorField;
	private static final Map<Class<?>, ModelFactory> _modelFactories =
		new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.SOFT_REFERENCE_FACTORY);

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

	private List<String> _allowedClassNames;
	private List<Class> _restrictedClasses;
	private List<String> _restrictedPackageNames;

}