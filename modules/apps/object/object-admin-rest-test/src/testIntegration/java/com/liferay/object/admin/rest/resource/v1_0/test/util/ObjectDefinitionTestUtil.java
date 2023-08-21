/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.resource.v1_0.test.util;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;

/**
 * @author Murilo Stodolni
 */
public class ObjectDefinitionTestUtil {

	public static ObjectDefinition addCustomObjectDefinition()
		throws PortalException {

		return addCustomObjectDefinition(0);
	}

	public static ObjectDefinition addCustomObjectDefinition(
			boolean enableLocalization)
		throws PortalException {

		return addCustomObjectDefinition(0, enableLocalization);
	}

	public static ObjectDefinition addCustomObjectDefinition(
			long objectFolderId)
		throws PortalException {

		return addCustomObjectDefinition(objectFolderId, false);
	}

	public static ObjectDefinition addCustomObjectDefinition(
			long objectFolderId, boolean enableLocalization)
		throws PortalException {

		String value = "A" + RandomTestUtil.randomString();

		return ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
			TestPropsValues.getUserId(), objectFolderId, false,
			enableLocalization, false, LocalizedMapUtil.getLocalizedMap(value),
			value, null, null, LocalizedMapUtil.getLocalizedMap(value), true,
			ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
			Collections.emptyList());
	}

}