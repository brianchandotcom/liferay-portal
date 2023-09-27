/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectValidationRuleSetting;

import java.util.List;
import java.util.Objects;

/**
 * @author Marco Leo
 */
public class ObjectValidationRuleImpl extends ObjectValidationRuleBaseImpl {

	@Override
	public boolean compareOutputType(String outputType) {
		if (Objects.equals(getOutputType(), outputType)) {
			return true;
		}

		return false;
	}

	@Override
	public List<ObjectValidationRuleSetting> getObjectValidationRuleSettings() {
		return _objectValidationRuleSettings;
	}

	@Override
	public void setObjectValidationRuleSettings(
		List<ObjectValidationRuleSetting> objectValidationRuleSettings) {

		_objectValidationRuleSettings = objectValidationRuleSettings;
	}

	private List<ObjectValidationRuleSetting> _objectValidationRuleSettings;

}