/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query.field;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.query.field.FieldQueryBuilder;
import com.liferay.portal.search.query.field.FieldQueryBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = FieldQueryBuilderFactory.class)
public class AssetTagNamesFieldQueryBuilderFactory
	implements FieldQueryBuilderFactory {

	@Override
	public FieldQueryBuilder getQueryBuilder(String fieldName) {
		if (fieldName.startsWith(Field.ASSET_TAG_NAMES)) {
			return titleFieldQueryBuilder;
		}

		return null;
	}

	@Reference(target = "(query.builder.type=title)")
	protected FieldQueryBuilder titleFieldQueryBuilder;

}