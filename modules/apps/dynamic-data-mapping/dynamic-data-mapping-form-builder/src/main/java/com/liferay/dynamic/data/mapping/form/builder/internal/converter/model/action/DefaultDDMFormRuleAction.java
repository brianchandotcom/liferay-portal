/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action;

import com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer.DefaultDDMFormRuleActionSerializer;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleActionSerializer;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;
import com.liferay.petra.lang.HashUtil;

import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class DefaultDDMFormRuleAction implements SPIDDMFormRuleAction {

	public DefaultDDMFormRuleAction() {
	}

	public DefaultDDMFormRuleAction(String action, String target) {
		_action = action;
		_target = target;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DefaultDDMFormRuleAction)) {
			return false;
		}

		DefaultDDMFormRuleAction ddmFormRuleAction =
			(DefaultDDMFormRuleAction)object;

		if (Objects.equals(_action, ddmFormRuleAction._action) &&
			Objects.equals(_target, ddmFormRuleAction._target)) {

			return true;
		}

		return false;
	}

	@Override
	public String getAction() {
		return _action;
	}

	public String getTarget() {
		return _target;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _action);

		return HashUtil.hash(hash, _target);
	}

	@Override
	public String serialize(
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext) {

		SPIDDMFormRuleActionSerializer spiDDMFormRuleActionSerializer =
			new DefaultDDMFormRuleActionSerializer(this);

		return spiDDMFormRuleActionSerializer.serialize(
			spiDDMFormRuleSerializerContext);
	}

	public void setAction(String action) {
		_action = action;
	}

	public void setTarget(String target) {
		_target = target;
	}

	private String _action;
	private String _target;

}