/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.field.builder;

import com.liferay.object.constants.ObjectFieldConstants;

/**
 * @author Marco Leo
 */
public class PhoneNumberObjectFieldBuilder extends ObjectFieldBuilder {

	public PhoneNumberObjectFieldBuilder() {
		objectField.setBusinessType(
			ObjectFieldConstants.BUSINESS_TYPE_PHONE_NUMBER);
		objectField.setDBType(ObjectFieldConstants.DB_TYPE_STRING);
	}

}