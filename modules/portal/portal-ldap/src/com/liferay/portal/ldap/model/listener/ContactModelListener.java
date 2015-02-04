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

package com.liferay.portal.ldap.model.listener;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.ldap.exportimport.UserImportTransactionThreadLocal;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.User;
import com.liferay.portal.security.exportimport.UserExporter;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalService;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Scott Lee
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@Component(immediate = true, service = ModelListener.class)
public class ContactModelListener extends BaseModelListener<Contact> {

	@Override
	public void onAfterCreate(Contact contact) throws ModelListenerException {
		try {
			exportToLDAP(contact);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(Contact contact) throws ModelListenerException {
		try {
			exportToLDAP(contact);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference
	public void setUserExporter(UserExporter userExporter) {
		_userExporter = userExporter;
	}

	@Reference
	public void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void exportToLDAP(Contact contact) throws Exception {
		if (UserImportTransactionThreadLocal.isOriginatesFromImport()) {
			return;
		}

		User user = _userLocalService.fetchUser(contact.getUserId());

		if ((user == null) || user.isDefaultUser()) {
			return;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Map<String, Serializable> expandoBridgeAttributes = null;

		if (serviceContext != null) {
			expandoBridgeAttributes =
				serviceContext.getExpandoBridgeAttributes();
		}

		_userExporter.exportUser(contact, expandoBridgeAttributes);
	}

	private UserExporter _userExporter;
	private UserLocalService _userLocalService;

}