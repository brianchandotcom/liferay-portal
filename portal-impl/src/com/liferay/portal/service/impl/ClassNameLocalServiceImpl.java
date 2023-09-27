/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.transaction.Propagation;
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
@CTAware
public class ClassNameLocalServiceImpl
	extends ClassNameLocalServiceBaseImpl implements CacheRegistryItem {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ClassName addClassName(String value) {
		ClassName className = classNamePersistence.fetchByValue(value);

		if (className == null) {
			long classNameId = counterLocalService.increment();

			className = classNamePersistence.create(classNameId);

			className.setValue(value);

			className = classNamePersistence.update(className);
		}

		return className;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkClassNames() {
		List<ClassName> classNames = classNamePersistence.findAll();

		for (ClassName className : classNames) {
			_classNames.put(_getKey(className.getValue()), className);
		}

		List<String> models = ModelHintsUtil.getModels();

		for (String model : models) {
			getClassName(model);
		}
	}

	@Override
	public ClassName deleteClassName(ClassName className) {
		_classNames.remove(_getKey(className.getValue()));

		return classNamePersistence.remove(className);
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

		ClassName className = _classNames.computeIfAbsent(
			_getKey(value), key -> classNamePersistence.fetchByValue(value));

		if (className == null) {
			return _nullClassName;
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

		ClassName className = _classNames.computeIfAbsent(
			_getKey(value),
			key -> {
				try {
					return classNameLocalService.addClassName(value);
				}
				catch (Throwable throwable) {
					if (_log.isDebugEnabled()) {
						_log.debug(throwable);
					}

					return null;
				}
			});

		if (className == null) {
			return classNameLocalService.fetchClassName(value);
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

	private String _getKey(String value) {
		if (DBPartition.isPartitionEnabled()) {
			return StringBundler.concat(
				value, StringPool.AT, DBPartitionUtil.getCurrentCompanyId());
		}

		return value;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClassNameLocalServiceImpl.class);

	private static final Map<String, ClassName> _classNames =
		new ConcurrentHashMap<>();
	private static final ClassName _nullClassName = new ClassNameImpl();

}