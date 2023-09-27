/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.manager.v1_0;

import com.liferay.object.model.ObjectRelationship;

import java.util.List;

/**
 * @author Carlos Correa
 * @author Sergio Jimenez del Coso
 */
public interface ObjectRelationshipElementsParser<T> {

	public String getClassName();

	public long getCompanyId();

	public String getObjectRelationshipType();

	public List<T> parse(ObjectRelationship objectRelationship, Object value)
		throws Exception;

}