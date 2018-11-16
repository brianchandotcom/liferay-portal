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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.security.exportimport.UserExporter;
import com.liferay.portal.security.exportimport.UserOperation;
import com.liferay.portal.security.ldap.internal.UserImportTransactionThreadLocal;

import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = ModelListener.class)
public class UserGroupModelListener extends BaseModelListener<UserGroup> {

	@Override
	public void onAfterAddAssociation(
		Object userGroupId, String associationClassName,
		Object associationClassPK) {

		try {
			if (associationClassName.equals(User.class.getName())) {
				exportToLDAP(
					(Long)associationClassPK, (Long)userGroupId,
					UserOperation.ADD);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to export user group with user ID " +
					associationClassPK + " to LDAP on after add association",
				e);
		}
	}

	@Override
	public void onAfterRemoveAssociation(
		Object userGroupId, String associationClassName,
		Object associationClassPK) {

		try {
			if (associationClassName.equals(User.class.getName())) {
				exportToLDAP(
					(Long)associationClassPK, (Long)userGroupId,
					UserOperation.REMOVE);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to export user group with user ID " +
					associationClassPK + " to LDAP on after remove association",
				e);
		}
	}

	protected void exportToLDAP(
		final long userId, final long userGroupId,
		final UserOperation userOperation) {

		if (UserImportTransactionThreadLocal.isOriginatesFromImport()) {
			return;
		}

		Callable<Void> callable = CallableUtil.getCallable(
			expandoBridgeAttributes -> {
				if ((_userLocalService.fetchUser(userId) == null) ||
					(_userGroupLocalService.fetchUserGroup(userGroupId) ==
						null)) {

					return;
				}

				try {
					_userExporter.exportUser(
						userId, userGroupId, userOperation);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e, e);
					}
				}

				if (_log.isDebugEnabled()) {
					if (_log.isDebugEnabled()) {
						StringBundler.concat(
							"Exporting user ", userId, " to user group ",
							userGroupId, " with user operation ",
							userOperation.name());
					}
				}
			});

		TransactionCommitCallbackUtil.registerCallback(callable);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupModelListener.class);

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile UserExporter _userExporter;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}