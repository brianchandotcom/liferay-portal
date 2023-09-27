/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.item.selector;

import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Alessio Antonio Rendina
 * @deprecated As of Mueller (7.2.x), , with no direct replacement
 */
@Deprecated
public class RoleItemSelectorCriterion extends BaseRoleItemSelectorCriterion {

	public RoleItemSelectorCriterion() {
	}

	public RoleItemSelectorCriterion(int type) {
		_validateType(type);

		_type = type;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_validateType(type);

		_type = type;
	}

	private void _validateType(int type) {
		if (!ArrayUtil.contains(
				RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE, type)) {

			throw new IllegalArgumentException(
				"Role type must have a value of 1, 2, or 3");
		}
	}

	private int _type = RoleConstants.TYPE_REGULAR;

}