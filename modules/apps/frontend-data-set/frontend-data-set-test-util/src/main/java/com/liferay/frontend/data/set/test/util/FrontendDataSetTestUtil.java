/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.test.util;

import com.liferay.batch.engine.test.util.BatchEngineTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Daniel Sanz
 */
public class FrontendDataSetTestUtil {

	public static void initialize(Class<?> clazz) throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET", TestPropsValues.getCompanyId());

		if (objectDefinition != null) {
			return;
		}

		BatchEngineTestUtil.processBatchEngineUnits(
			_BUNDLE_SYMBOLIC_NAME + ".impl", clazz,
			new String[] {
				"." + _BUNDLE_SYMBOLIC_NAME +
					".internal.batch.01.object.definition"
			});
	}

	private static final String _BUNDLE_SYMBOLIC_NAME =
		"com.liferay.frontend.data.set";

}