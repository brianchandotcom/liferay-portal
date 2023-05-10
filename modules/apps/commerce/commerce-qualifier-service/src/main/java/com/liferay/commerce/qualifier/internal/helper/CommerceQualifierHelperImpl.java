/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.qualifier.internal.helper;

import com.liferay.commerce.qualifier.helper.CommerceQualifierHelper;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadata;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadataRegistry;
import com.liferay.commerce.qualifier.model.CommerceQualifierEntryTable;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(service = CommerceQualifierHelper.class)
public class CommerceQualifierHelperImpl implements CommerceQualifierHelper {

	public CommerceQualifierEntryTable getAliasCommerceQualifierEntryTable(
		String sourceCommerceQualifierMetadataKey,
		String targetCommerceQualifierMetadataKey) {

		return CommerceQualifierEntryTable.INSTANCE.as(
			StringBundler.concat(
				_getTableNameByCommerceQualifierMetadataKey(
					sourceCommerceQualifierMetadataKey),
				StringPool.UNDERLINE,
				_getTableNameByCommerceQualifierMetadataKey(
					targetCommerceQualifierMetadataKey)));
	}

	private String _getTableNameByCommerceQualifierMetadataKey(
		String commerceQualifierMetadataKey) {

		CommerceQualifierMetadata commerceQualifierMetadata =
			_commerceQualifierMetadataRegistry.getCommerceQualifierMetadata(
				commerceQualifierMetadataKey);

		if (commerceQualifierMetadata == null) {
			return StringPool.BLANK;
		}

		Table table = commerceQualifierMetadata.getTable();

		return table.getName();
	}

	@Reference
	private CommerceQualifierMetadataRegistry
		_commerceQualifierMetadataRegistry;

}