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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.base.ClassNameServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassNameServiceImpl extends ClassNameServiceBaseImpl {

	@Override
	public ClassName fetchClassName(String value) throws SystemException {
		return classNameLocalService.fetchClassName(value);
	}

	@Override
	public long fetchClassNameId(Class<?> clazz) {
		return classNameLocalService.fetchClassNameId(clazz);
	}

	@Override
	public long fetchClassNameId(String value) {
		return classNameLocalService.fetchClassNameId(value);
	}

}