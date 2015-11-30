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

package com.liferay.portal.ldap.internal.model.listener;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.ldap.internal.UserImportTransactionThreadLocal;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.exportimport.UserExporter;
import com.liferay.portal.security.exportimport.UserOperation;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = ModelListener.class)
public class UserGroupModelListener extends BaseModelListener<UserGroup> {

	@Override
	public void onAfterAddAssociation(
			Object userGroupId, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			if (associationClassName.equals(User.class.getName())) {
				exportToLDAP(
					(Long)associationClassPK, (Long)userGroupId,
					UserOperation.ADD);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemoveAssociation(
			Object userGroupId, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			if (associationClassName.equals(User.class.getName())) {
				exportToLDAP(
					(Long)associationClassPK, (Long)userGroupId,
					UserOperation.REMOVE);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference(unbind = "-")
	public void setUserExporter(UserExporter userExporter) {
		_userExporter = userExporter;
	}

	protected void exportToLDAP(
			long userId, long userGroupId, UserOperation userOperation)
		throws Exception {

		if (UserImportTransactionThreadLocal.isOriginatesFromImport()) {
			return;
		}

		_userExporter.exportUser(userId, userGroupId, userOperation);
	}

	private volatile UserExporter _userExporter;

}