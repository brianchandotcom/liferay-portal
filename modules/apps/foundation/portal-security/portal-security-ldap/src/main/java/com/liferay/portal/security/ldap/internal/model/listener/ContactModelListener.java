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

package com.liferay.portal.security.ldap.internal.model.listener;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.security.exportimport.UserExporter;
import com.liferay.portal.security.ldap.internal.UserImportTransactionThreadLocal;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Scott Lee
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@Component(immediate = true, service = ModelListener.class)
public class ContactModelListener extends BaseModelListener<Contact> {

	@Override
	public void onAfterCreate(Contact contact) {
		try {
			exportToLDAP(contact);
		}
		catch (Exception e) {
			_log.error(
				"Unable to export contact with user ID " + contact.getUserId() +
					" to LDAP on after create",
				e);
		}
	}

	@Override
	public void onAfterUpdate(Contact contact) {
		try {
			exportToLDAP(contact);
		}
		catch (Exception e) {
			_log.error(
				"Unable to export contact with user ID " + contact.getUserId() +
					" to LDAP on after update",
				e);
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		ContactModelListener.class);

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private UserExporter _userExporter;

	@Reference
	private UserLocalService _userLocalService;

}