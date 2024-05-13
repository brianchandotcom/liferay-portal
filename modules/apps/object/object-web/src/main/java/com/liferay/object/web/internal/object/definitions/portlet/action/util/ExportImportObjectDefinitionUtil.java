/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ExportImportObjectDefinitionUtil {

	public static void prepareObjectDefinitionForExport(
		JSONFactory jsonFactory, ObjectDefinition objectDefinition) {

		if (objectDefinition == null) {
			return;
		}

		for (ObjectAction objectAction : objectDefinition.getObjectActions()) {
			Map<String, Object> parameters =
				(Map<String, Object>)objectAction.getParameters();

			Object object = parameters.get("predefinedValues");

			if (object == null) {
				continue;
			}

			parameters.put(
				"predefinedValues",
				ListUtil.toList(
					(ArrayList<LinkedHashMap>)object,
					jsonFactory::createJSONObject));
		}
	}

}