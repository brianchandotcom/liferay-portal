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

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link ClassNameService}.
 *
 * @author Brian Wing Shun Chan
 * @see ClassNameService
 * @generated
 */
public class ClassNameServiceWrapper
	implements ClassNameService, ServiceWrapper<ClassNameService> {

	public ClassNameServiceWrapper(ClassNameService classNameService) {
		_classNameService = classNameService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ClassNameServiceUtil} to access the class name remote service. Add custom service methods to <code>com.liferay.portal.service.impl.ClassNameServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.kernel.model.ClassName fetchByClassNameId(
		long classNameId) {

		return _classNameService.fetchByClassNameId(classNameId);
	}

	@Override
	public com.liferay.portal.kernel.model.ClassName fetchClassName(
		java.lang.String value) {

		return _classNameService.fetchClassName(value);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _classNameService.getOSGiServiceIdentifier();
	}

	@Override
	public ClassNameService getWrappedService() {
		return _classNameService;
	}

	@Override
	public void setWrappedService(ClassNameService classNameService) {
		_classNameService = classNameService;
	}

	private ClassNameService _classNameService;

}