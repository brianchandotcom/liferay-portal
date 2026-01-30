/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.test.util;

import com.liferay.batch.engine.test.util.BatchEngineTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Stefano Motta
 */
public class DigitalSalesRoomTestUtil {

	public static ObjectDefinition getObjectDefinition(Class<?> clazz)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", TestPropsValues.getCompanyId());

		if (objectDefinition != null) {
			return objectDefinition;
		}

		BatchEngineTestUtil.processBatchEngineUnits(
			_BUNDLE_SYMBOLIC_NAME + ".impl", clazz,
			new String[] {
				"." + _BUNDLE_SYMBOLIC_NAME +
					".internal.batch.01.object.folder",
				"." + _BUNDLE_SYMBOLIC_NAME +
					".internal.batch.02.object.definition",
				"." + _BUNDLE_SYMBOLIC_NAME +
					".internal.batch.03.notification.template"
			});

		return ObjectDefinitionLocalServiceUtil.
			getObjectDefinitionByExternalReferenceCode(
				"L_DSR_ROOM", TestPropsValues.getCompanyId());
	}

	private static final String _BUNDLE_SYMBOLIC_NAME =
		"com.liferay.digital.sales.room";

}