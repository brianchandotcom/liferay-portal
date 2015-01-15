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

package com.liferay.portal.ldap.listeners;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.security.exportimport.UserExporterUtil;
import com.liferay.portal.security.exportimport.UserImportTransactionThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Scott Lee
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
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

	protected void exportToLDAP(Contact contact) throws Exception {
		if (UserImportTransactionThreadLocal.isOriginatesFromImport()) {
			return;
		}

		User user = UserLocalServiceUtil.fetchUser(contact.getUserId());

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

		UserExporterUtil.exportUser(contact, expandoBridgeAttributes);
	}

}