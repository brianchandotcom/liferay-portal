/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.info.field.type;

import com.liferay.info.field.type.InfoFieldType;

/**
 * @author Víctor Galán
 */
public class StepperInfoFieldType implements InfoFieldType {

	public static final StepperInfoFieldType INSTANCE =
		new StepperInfoFieldType();

	@Override
	public String getName() {
		return "stepper";
	}

	private StepperInfoFieldType() {
	}

}