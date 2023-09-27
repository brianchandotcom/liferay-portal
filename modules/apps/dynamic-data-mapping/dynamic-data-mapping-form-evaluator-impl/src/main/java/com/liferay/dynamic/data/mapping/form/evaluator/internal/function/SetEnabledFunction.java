/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

/**
 * @author Leonardo Barros
 */
public class SetEnabledFunction extends BaseSetPropertyFunction<Boolean> {

	public static final String NAME = "setEnabled";

	@Override
	public Boolean apply(String field, Boolean value) {
		return super.apply(field, !value);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected String getPropertyName() {
		return "readOnly";
	}

}