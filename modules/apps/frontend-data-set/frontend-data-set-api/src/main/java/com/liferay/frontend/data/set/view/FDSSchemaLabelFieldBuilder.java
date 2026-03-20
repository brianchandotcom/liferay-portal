/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.view;

import java.util.Map;

/**
 * @author Daniel Sanz
 */
public class FDSSchemaLabelFieldBuilder {

	public static FDSSchemaLabelField create(
		String displayTypeKey, Map<String, String> displayTypeValues,
		String value) {

		FDSSchemaLabelField fdsSchemaLabelField = new FDSSchemaLabelField();

		fdsSchemaLabelField.setDisplayTypeKey(displayTypeKey);
		fdsSchemaLabelField.setDisplayTypeValues(displayTypeValues);
		fdsSchemaLabelField.setValue(value);

		return fdsSchemaLabelField;
	}

	public static FDSSchemaLabelField create(String displayType, String value) {
		FDSSchemaLabelField fdsSchemaLabelField = new FDSSchemaLabelField();

		fdsSchemaLabelField.setDisplayType(displayType);
		fdsSchemaLabelField.setValue(value);

		return fdsSchemaLabelField;
	}

}