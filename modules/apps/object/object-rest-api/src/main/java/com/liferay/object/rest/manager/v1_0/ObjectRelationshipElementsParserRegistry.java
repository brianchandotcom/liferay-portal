/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.manager.v1_0;

/**
 * @author Carlos Correa
 * @author Sergio Jimenez del Coso
 */
public interface ObjectRelationshipElementsParserRegistry {

	public ObjectRelationshipElementsParser getObjectRelationshipElementsParser(
			String className, long companyId, String type)
		throws Exception;

}