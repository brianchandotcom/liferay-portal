/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link ClassNameService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClassNameService
 * @generated
 */
public class ClassNameServiceWrapper implements ClassNameService,
	ServiceWrapper<ClassNameService> {
	public ClassNameServiceWrapper(ClassNameService classNameService) {
		_classNameService = classNameService;
	}

	public com.liferay.portal.model.ClassName getClassName(long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _classNameService.getClassName(classNameId);
	}

	public com.liferay.portal.model.ClassName getClassName(
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameService.getClassName(value);
	}

	public long getClassNameId(java.lang.Class<?> clazz) {
		return _classNameService.getClassNameId(clazz);
	}

	public long getClassNameId(java.lang.String value) {
		return _classNameService.getClassNameId(value);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ClassNameService getWrappedClassNameService() {
		return _classNameService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedClassNameService(ClassNameService classNameService) {
		_classNameService = classNameService;
	}

	public ClassNameService getWrappedService() {
		return _classNameService;
	}

	public void setWrappedService(ClassNameService classNameService) {
		_classNameService = classNameService;
	}

	private ClassNameService _classNameService;
}