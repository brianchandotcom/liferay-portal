/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.service.base.ClassNameServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
@JSONWebService
public class ClassNameServiceImpl extends ClassNameServiceBaseImpl {

	@Override
	public ClassName fetchByClassNameId(long classNameId) {
		return classNameLocalService.fetchByClassNameId(classNameId);
	}

	@Override
	public ClassName fetchClassName(String value) {
		return classNameLocalService.fetchClassName(value);
	}

}