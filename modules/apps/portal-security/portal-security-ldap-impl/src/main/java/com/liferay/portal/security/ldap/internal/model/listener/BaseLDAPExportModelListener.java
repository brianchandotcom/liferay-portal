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

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PasswordModificationThreadLocal;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.security.exportimport.UserExporter;
import com.liferay.portal.security.ldap.internal.UserImportTransactionThreadLocal;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @author Minhchau Dang
 */
public abstract class BaseLDAPExportModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	protected void exportToLDAP(
			final User user, final UserExporter userExporter,
			final LDAPSettings ldapSettings)
		throws Exception {

		if ((user == null) || user.isDefaultUser() ||
			UserImportTransactionThreadLocal.isOriginatesFromImport()) {

			return;
		}

		Callable<Void> callable = () -> {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Map<String, Serializable> expandoBridgeAttributes = null;

			if (serviceContext != null) {
				expandoBridgeAttributes =
					serviceContext.getExpandoBridgeAttributes();
			}

			boolean oldPasswordModified =
				PasswordModificationThreadLocal.isPasswordModified();

			if (oldPasswordModified) {
				String lastPasswordUnencrypted = _lastPasswordUnencrypted.get();
				String newPasswordUnencrypted =
					PasswordModificationThreadLocal.getPasswordUnencrypted();

				boolean newPasswordModified = !Objects.equals(
					lastPasswordUnencrypted, newPasswordUnencrypted);

				_lastPasswordUnencrypted.set(newPasswordUnencrypted);

				PasswordModificationThreadLocal.setPasswordModified(
					newPasswordModified);
			}

			try {
				userExporter.exportUser(user, expandoBridgeAttributes);
			}
			finally {
				PasswordModificationThreadLocal.setPasswordModified(
					oldPasswordModified);
			}

			return null;
		};

		if (ldapSettings.isPasswordPolicyEnabled(user.getCompanyId()) &&
			PasswordModificationThreadLocal.isPasswordModified()) {

			callable.call();
		}
		else {
			TransactionCommitCallbackUtil.registerCallback(callable);
		}
	}

	private static final CentralizedThreadLocal<String>
		_lastPasswordUnencrypted = new CentralizedThreadLocal<>(
			BaseLDAPExportModelListener.class.getName() +
				"._lastPasswordUnencrypted");

}