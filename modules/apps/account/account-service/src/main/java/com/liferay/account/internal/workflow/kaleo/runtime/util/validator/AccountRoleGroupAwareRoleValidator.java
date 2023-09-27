/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.workflow.kaleo.runtime.util.validator;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.workflow.kaleo.runtime.util.validator.GroupAwareRoleValidator;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = GroupAwareRoleValidator.class)
public class AccountRoleGroupAwareRoleValidator
	implements GroupAwareRoleValidator {

	@Override
	public boolean isValidGroup(Group group, Role role) throws PortalException {
		if ((group != null) && _isAccountEntryGroup(group) &&
			(role.getType() == RoleConstants.TYPE_ACCOUNT)) {

			return true;
		}

		return false;
	}

	private boolean _isAccountEntryGroup(Group group) {
		if (Objects.equals(
				_portal.getClassNameId(AccountEntry.class),
				group.getClassNameId())) {

			return true;
		}

		return false;
	}

	@Reference
	private Portal _portal;

}