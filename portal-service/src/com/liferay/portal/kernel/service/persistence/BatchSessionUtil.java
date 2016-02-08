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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author     Brian Wing Shun Chan
 * @see        BatchSession
 * @deprecated As of 6.2.0, see LPS-30598.
 */
@Deprecated
public class BatchSessionUtil {

	public static void delete(Session session, BaseModel<?> model)
		throws ORMException {

		getBatchSession().delete(session, model);
	}

	public static BatchSession getBatchSession() {
		return _batchSession;
	}

	public static boolean isEnabled() {
		return getBatchSession().isEnabled();
	}

	public static void setEnabled(boolean enabled) {
		getBatchSession().setEnabled(enabled);
	}

	public static void update(
			Session session, BaseModel<?> model, boolean merge)
		throws ORMException {

		getBatchSession().update(session, model, merge);
	}

	public void setBatchSession(BatchSession batchSession) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_batchSession = batchSession;
	}

	private static BatchSession _batchSession;

}