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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleFactoryHelper {

	public DDMFormRuleFactoryHelper(DDMForm ddmForm) {
		_ddmForm = ddmForm;
	}

	public List<com.liferay.dynamic.data.mapping.model.DDMFormRule>
		createDDMFormRules() {

		List<com.liferay.dynamic.data.mapping.model.DDMFormRule> ddmFormRules =
			new ArrayList<>();

		for (DDMFormRule ddmFormRule : _ddmForm.rules()) {
			ddmFormRules.add(
				new com.liferay.dynamic.data.mapping.model.DDMFormRule(
					ddmFormRule.condition(),
					ListUtil.fromArray(ddmFormRule.actions())));
		}

		return ddmFormRules;
	}

	private final DDMForm _ddmForm;

}