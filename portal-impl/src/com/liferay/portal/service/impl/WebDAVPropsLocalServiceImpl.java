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

import com.liferay.portal.NoSuchWebDAVPropsException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.model.WebDAVProps;
import com.liferay.portal.service.base.WebDAVPropsLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Alexander Chow
 */
public class WebDAVPropsLocalServiceImpl
	extends WebDAVPropsLocalServiceBaseImpl {

	@Override
	public void deleteWebDAVProps(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		try {
			webDAVPropsPersistence.removeByC_C(classNameId, classPK);
		}
		catch (NoSuchWebDAVPropsException nswdavpe) {
		}
	}

	@Override
	public WebDAVProps getWebDAVProps(
		long companyId, String className, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		WebDAVProps webDavProps = webDAVPropsPersistence.fetchByC_C(
			classNameId, classPK);

		if (webDavProps == null) {
			webDavProps = webDAVPropsPersistence.create(
				counterLocalService.increment());

			Date now = new Date();

			webDavProps.setCompanyId(companyId);
			webDavProps.setCreateDate(now);
			webDavProps.setModifiedDate(now);
			webDavProps.setClassNameId(classNameId);
			webDavProps.setClassPK(classPK);

			webDAVPropsLocalService.updateWebDAVProps(webDavProps);
		}

		return webDavProps;
	}

	@Override
	public void storeWebDAVProps(WebDAVProps webDavProps)
		throws PortalException {

		try {
			webDavProps.store();
		}
		catch (Exception e) {
			throw new WebDAVException("Problem trying to store WebDAVProps", e);
		}

		webDAVPropsPersistence.update(webDavProps);
	}

}