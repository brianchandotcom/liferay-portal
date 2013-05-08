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

package com.liferay.portal.service;

/**
 * Provides a wrapper for {@link ClassNameService}.
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

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _classNameService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_classNameService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.ClassName fetchClassName(
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameService.fetchClassName(value);
	}

	public long fetchClassNameId(java.lang.Class<?> clazz) {
		return _classNameService.fetchClassNameId(clazz);
	}

	public long fetchClassNameId(java.lang.String value) {
		return _classNameService.fetchClassNameId(value);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public ClassNameService getWrappedClassNameService() {
		return _classNameService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
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