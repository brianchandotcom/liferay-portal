/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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