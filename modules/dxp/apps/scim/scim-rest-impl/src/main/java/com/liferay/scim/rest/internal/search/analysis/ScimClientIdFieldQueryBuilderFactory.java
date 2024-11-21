/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.search.analysis;

import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.FieldQueryBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "service.ranking:Integer=1",
	service = FieldQueryBuilderFactory.class
)
public class ScimClientIdFieldQueryBuilderFactory
	implements FieldQueryBuilderFactory {

	@Override
	public FieldQueryBuilder getQueryBuilder(String fieldName) {
		if (fieldName.contains("scimClientId")) {
			return _scimClientIdFieldQueryBuilder;
		}

		return null;
	}

	@Reference(target = "(query.builder.type=scimClientId)")
	private FieldQueryBuilder _scimClientIdFieldQueryBuilder;

}