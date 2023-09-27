/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.workflow;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.workflow.kaleo.runtime.util.validator.GroupAwareRoleValidator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = GroupAwareRoleValidator.class)
public class AccountRoleGroupAwareRoleValidator
	implements GroupAwareRoleValidator {

	@Override
	public boolean isValidGroup(Group group, Role role) throws PortalException {
		if ((group != null) && _isAccount(group) &&
			(role.getType() == RoleConstants.TYPE_SITE)) {

			return true;
		}

		return false;
	}

	private boolean _isAccount(Group group) {
		long classNameId = _portal.getClassNameId(AccountEntry.class.getName());

		if (group.getClassNameId() == classNameId) {
			return true;
		}

		return false;
	}

	@Reference
	private Portal _portal;

}