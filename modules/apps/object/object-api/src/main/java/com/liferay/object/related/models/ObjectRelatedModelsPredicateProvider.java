/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models;

import com.liferay.object.model.ObjectRelationship;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Luis Miguel Barcos
 */
public interface ObjectRelatedModelsPredicateProvider {

	public String getClassName();

	public String getObjectRelationshipType();

	public Predicate getPredicate(
			ObjectRelationship objectRelationship, Predicate predicate)
		throws PortalException;

}