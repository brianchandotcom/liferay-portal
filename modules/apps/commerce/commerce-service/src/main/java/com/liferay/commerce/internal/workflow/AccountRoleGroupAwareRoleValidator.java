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

package com.liferay.commerce.internal.workflow;

import com.liferay.commerce.account.model.CommerceAccount;
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
		long classNameId = _portal.getClassNameId(
			CommerceAccount.class.getName());

		if (group.getClassNameId() == classNameId) {
			return true;
		}

		return false;
	}

	@Reference
	private Portal _portal;

}