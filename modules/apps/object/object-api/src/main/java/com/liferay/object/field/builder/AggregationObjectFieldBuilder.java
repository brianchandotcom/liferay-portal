/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.field.builder;

import com.liferay.object.constants.ObjectFieldConstants;

/**
 * @author Feliphe Marinho
 */
public class AggregationObjectFieldBuilder extends ObjectFieldBuilder {

	public AggregationObjectFieldBuilder() {
		objectField.setBusinessType(
			ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION);
		objectField.setDBType(ObjectFieldConstants.DB_TYPE_STRING);
		objectField.setReadOnly(ObjectFieldConstants.READ_ONLY_TRUE);
	}

}