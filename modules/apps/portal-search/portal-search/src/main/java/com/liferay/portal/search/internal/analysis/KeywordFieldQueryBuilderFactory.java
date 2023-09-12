/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.analysis;

import com.liferay.portal.kernel.search.query.QueryPreProcessConfiguration;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.FieldQueryBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joshua Cords
 */
@Component(service = FieldQueryBuilderFactory.class)
public class KeywordFieldQueryBuilderFactory
	implements FieldQueryBuilderFactory {

	@Override
	public FieldQueryBuilder getQueryBuilder(String fieldName) {
		if (queryPreProcessConfiguration.isKeywordFieldName(fieldName)) {
			return keywordFieldQueryBuilder;
		}

		return null;
	}

	@Reference(target = "(query.builder.type=keyword)")
	protected FieldQueryBuilder keywordFieldQueryBuilder;

	@Reference
	protected QueryPreProcessConfiguration queryPreProcessConfiguration;

}