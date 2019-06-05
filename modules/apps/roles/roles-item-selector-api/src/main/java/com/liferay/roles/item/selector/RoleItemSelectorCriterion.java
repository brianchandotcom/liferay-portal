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

package com.liferay.roles.item.selector;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class RoleItemSelectorCriterion extends BaseItemSelectorCriterion {

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