/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import java.util.Objects;

/**
 * @author Marco Leo
 */
public class ObjectValidationRuleSettingImpl
	extends ObjectValidationRuleSettingBaseImpl {

	@Override
	public boolean compareName(String name) {
		if (Objects.equals(getName(), name)) {
			return true;
		}

		return false;
	}

}