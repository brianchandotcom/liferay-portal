/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.validation.rule.setting.builder;

import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.service.persistence.ObjectValidationRuleSettingUtil;

/**
 * @author Carolina Barbosa
 */
public class ObjectValidationRuleSettingBuilder {

	public ObjectValidationRuleSetting build() {
		return _objectValidationRuleSetting;
	}

	public ObjectValidationRuleSettingBuilder name(String name) {
		_objectValidationRuleSetting.setName(name);

		return this;
	}

	public ObjectValidationRuleSettingBuilder value(String value) {
		_objectValidationRuleSetting.setValue(value);

		return this;
	}

	private final ObjectValidationRuleSetting _objectValidationRuleSetting =
		ObjectValidationRuleSettingUtil.create(0L);

}