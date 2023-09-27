/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public interface DefaultDDMStructureHelper {

	public void addDDMStructures(
			long userId, long groupId, long classNameId,
			ClassLoader classLoader, String fileName,
			ServiceContext serviceContext)
		throws Exception;

	public void addOrUpdateDDMStructures(
			long userId, long groupId, long classNameId,
			ClassLoader classLoader, String fileName,
			ServiceContext serviceContext)
		throws Exception;

	public String getDynamicDDMStructureDefinition(
			ClassLoader classLoader, String fileName,
			String dynamicDDMStructureName, Locale locale)
		throws Exception;

}