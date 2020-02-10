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

package com.liferay.roles.admin.kernel.validator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.RoleNameException;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Pei-Jung Lan
 */
public class RoleNameValidator {

	public static void validate(String roleName) throws RoleNameException {
		String[] invalidCharacters = StringUtil.split(
			RoleConstants.NAME_INVALID_CHARACTERS, StringPool.SPACE);

		if (Validator.isNull(roleName) ||
			(StringUtil.indexOfAny(roleName, invalidCharacters) > -1)) {

			throw new RoleNameException();
		}

		if (!_ROLES_NAME_ALLOW_NUMERIC && Validator.isNumber(roleName)) {
			throw new RoleNameException();
		}
	}

	private static final boolean _ROLES_NAME_ALLOW_NUMERIC =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.ROLES_NAME_ALLOW_NUMERIC));

}