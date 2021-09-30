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

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.WebDAVProps;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.service.base.WebDAVPropsLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Alexander Chow
 */
public class WebDAVPropsLocalServiceImpl
	extends WebDAVPropsLocalServiceBaseImpl {

	@Override
	public void deleteWebDAVProps(String className, long classPK) {
		WebDAVProps webDAVProps = webDAVPropsPersistence.fetchByC_C(
			_classNameLocalService.getClassNameId(className), classPK);

		if (webDAVProps != null) {
			webDAVPropsPersistence.remove(webDAVProps);
		}
	}

	@Override
	public WebDAVProps getWebDAVProps(
		long companyId, String className, long classPK) {

		long classNameId = _classNameLocalService.getClassNameId(className);

		WebDAVProps webDAVProps = webDAVPropsPersistence.fetchByC_C(
			classNameId, classPK);

		if (webDAVProps == null) {
			webDAVProps = webDAVPropsPersistence.create(
				counterLocalService.increment());

			Date date = new Date();

			webDAVProps.setCompanyId(companyId);
			webDAVProps.setCreateDate(date);
			webDAVProps.setModifiedDate(date);
			webDAVProps.setClassNameId(classNameId);
			webDAVProps.setClassPK(classPK);

			webDAVPropsLocalService.updateWebDAVProps(webDAVProps);
		}

		return webDAVProps;
	}

	@Override
	public void storeWebDAVProps(WebDAVProps webDAVProps)
		throws PortalException {

		try {
			webDAVProps.store();
		}
		catch (Exception exception) {
			throw new WebDAVException(
				"Problem trying to store WebDAVProps", exception);
		}

		webDAVPropsPersistence.update(webDAVProps);
	}

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

}