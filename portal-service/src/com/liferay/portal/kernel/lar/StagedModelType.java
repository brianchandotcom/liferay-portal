/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.util.HashCode;
import com.liferay.portal.kernel.util.HashCodeFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Zsolt Berentey
 */
public class StagedModelType {

	public static final int REFERRER_CLASS_NAME_ID_ALL = -1;

	public StagedModelType(Class<?> clazz) {
		setClassName(clazz.getName());
	}

	public StagedModelType(Class<?> clazz, Class<?> referrerClass) {
		setClassName(clazz.getName());
		setReferrerClassName(referrerClass.getName());
	}

	public StagedModelType(long classNameId) {
		setClassNameId(classNameId);
	}

	public StagedModelType(long classNameId, long referrerClassNameId) {
		setClassNameId(classNameId);
		setReferrerClassNameId(referrerClassNameId);
	}

	public StagedModelType(String className) {
		setClassName(className);
	}

	public StagedModelType(String className, String referrerClassName) {
		setClassName(className);
		setReferrerClassName(referrerClassName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || !(obj instanceof StagedModelType)) {
			return false;
		}

		StagedModelType stagedModelType = (StagedModelType)obj;

		if ((stagedModelType._classNameId != _classNameId) ||
			(stagedModelType._referrerClassNameId != _referrerClassNameId)) {

			return false;
		}

		return true;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public String getClassSimpleName() {
		return _classSimpleName;
	}

	public String getReferrerClassName() {
		return _referrerClassName;
	}

	public long getReferrerClassNameId() {
		return _referrerClassNameId;
	}

	@Override
	public int hashCode() {
		HashCode hashCode = HashCodeFactoryUtil.getHashCode();

		hashCode.append(_classNameId);
		hashCode.append(_referrerClassNameId);

		return hashCode.toHashCode();
	}

	public void setClassName(String className) {
		_className = className;
		_classSimpleName = getSimpleName(_className);

		if (Validator.isNotNull(className)) {
			_classNameId = PortalUtil.getClassNameId(className);
		}
		else {
			_classNameId = 0;
		}
	}

	public void setClassNameId(long classNameId) {
		if (classNameId > 0) {
			_className = PortalUtil.getClassName(classNameId);
			_classSimpleName = getSimpleName(_className);
		}
		else {
			_className = null;
			_classSimpleName = null;
		}

		_classNameId = classNameId;
	}

	public void setClassSimpleName(String classSimpleName) {
		_classSimpleName = classSimpleName;
	}

	public void setReferrerClassName(String referrerClassName) {
		_referrerClassName = referrerClassName;

		if (Validator.isNotNull(referrerClassName)) {
			_referrerClassNameId = PortalUtil.getClassNameId(referrerClassName);
		}
		else {
			_referrerClassNameId = 0;
		}
	}

	public void setReferrerClassNameId(long referrerClassNameId) {
		if (referrerClassNameId > 0) {
			_referrerClassName = PortalUtil.getClassName(referrerClassNameId);
		}
		else {
			_referrerClassName = null;
		}

		_referrerClassNameId = referrerClassNameId;
	}

	@Override
	public String toString() {
		if (_referrerClassNameId <= 0) {
			return _className;
		}

		return _className.concat(StringPool.POUND).concat(_referrerClassName);
	}

	private String getSimpleName(String className) {
		int pos = className.lastIndexOf(StringPool.PERIOD) + 1;

		return className.substring(pos);
	}

	private String _className;
	private long _classNameId;
	private String _classSimpleName;
	private String _referrerClassName;
	private long _referrerClassNameId;

}