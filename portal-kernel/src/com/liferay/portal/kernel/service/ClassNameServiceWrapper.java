/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public ClassNameServiceWrapper() {
		this(null);
	}

	public ClassNameServiceWrapper(ClassNameService classNameService) {
		_classNameService = classNameService;
	}

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