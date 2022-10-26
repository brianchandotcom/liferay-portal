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