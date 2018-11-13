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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.service.base.ClassNameLocalServiceBaseImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassNameLocalServiceImpl
	extends ClassNameLocalServiceBaseImpl implements CacheRegistryItem {

	@Override
	public ClassName addClassName(String value) {
		ClassName className = classNamePersistence.fetchByValue(value);

		if (className == null) {
			long classNameId = counterLocalService.increment();

			className = classNamePersistence.create(classNameId);

			className.setValue(value);

			classNamePersistence.update(className);
		}

		return className;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkClassNames() {
		List<ClassName> classNames = classNamePersistence.findAll();

		for (ClassName className : classNames) {
			_classNames.put(className.getValue(), className);
		}

		List<String> models = ModelHintsUtil.getModels();

		for (String model : models) {
			getClassName(model);
		}
	}

	@Override
	public ClassName fetchByClassNameId(long classNameId) {
		return classNamePersistence.fetchByPrimaryKey(classNameId);
	}

	@Override
	public ClassName fetchClassName(String value) {
		if (Validator.isNull(value)) {
			return _nullClassName;
		}

		ClassName className = _classNames.get(value);

		if (className == null) {
			className = classNamePersistence.fetchByValue(value);

			if (className == null) {
				return _nullClassName;
			}

			final ClassName callbackClassName = className;

			TransactionCommitCallbackUtil.registerCallback(
				() -> _classNames.put(value, callbackClassName));
		}

		return className;
	}

	@Override
	@Transactional(enabled = false)
	public ClassName getClassName(String value) {
		if (Validator.isNull(value)) {
			return _nullClassName;
		}

		// Always cache the class name. This table exists to improve
		// performance. Create the class name if one does not exist.

		ClassName className = _classNames.get(value);

		if (className == null) {
			try {
				className = classNameLocalService.addClassName(value);

				final ClassName callbackClassName = className;

				TransactionCommitCallbackUtil.registerCallback(
					() -> _classNames.put(value, callbackClassName));
			}
			catch (Throwable t) {
				className = classNameLocalService.fetchClassName(value);

				if (className == _nullClassName) {
					throw t;
				}
			}
		}

		return className;
	}

	@Override
	@Transactional(enabled = false)
	public long getClassNameId(Class<?> clazz) {
		return getClassNameId(clazz.getName());
	}

	@Override
	@Transactional(enabled = false)
	public long getClassNameId(String value) {
		ClassName className = getClassName(value);

		return className.getClassNameId();
	}

	@Override
	public String getRegistryName() {
		return ClassNameLocalServiceImpl.class.getName();
	}

	@Override
	public void invalidate() {
		_classNames.clear();
	}

	private static final Map<String, ClassName> _classNames =
		new ConcurrentHashMap<>();
	private static final ClassName _nullClassName = new ClassNameImpl();

}