/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.field.builder;

import com.liferay.object.constants.ObjectFieldConstants;

/**
 * @author Feliphe Marinho
 */
public class DecimalObjectFieldBuilder extends ObjectFieldBuilder {

	public DecimalObjectFieldBuilder() {
		objectField.setBusinessType(ObjectFieldConstants.BUSINESS_TYPE_DECIMAL);
		objectField.setDBType(ObjectFieldConstants.DB_TYPE_DOUBLE);
	}

}