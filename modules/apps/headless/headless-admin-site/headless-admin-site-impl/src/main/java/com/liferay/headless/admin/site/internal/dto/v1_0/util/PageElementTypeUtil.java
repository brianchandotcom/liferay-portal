/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.PageDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Lourdes Fernández Besada
 */
public class PageElementTypeUtil {

	public static PageDefinition.Type toExternalType(String internalType) {
		Set<PageDefinition.Type> externalTypes =
			_externalToInternalValuesMap.keySet();

		for (PageDefinition.Type externalType : externalTypes) {
			if (Objects.equals(
					internalType,
					_externalToInternalValuesMap.get(externalType))) {

				return externalType;
			}
		}

		throw new UnsupportedOperationException();
	}

	public static String toInternalType(PageDefinition.Type externalType) {
		if (_externalToInternalValuesMap.containsKey(externalType)) {
			return _externalToInternalValuesMap.get(externalType);
		}

		throw new UnsupportedOperationException();
	}

	private static final Map<PageDefinition.Type, String>
		_externalToInternalValuesMap = HashMapBuilder.put(
			PageDefinition.Type.COLLECTION,
			LayoutDataItemTypeConstants.TYPE_COLLECTION
		).put(
			PageDefinition.Type.COLLECTION_ITEM,
			LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM
		).put(
			PageDefinition.Type.COLUMN, LayoutDataItemTypeConstants.TYPE_COLUMN
		).put(
			PageDefinition.Type.CONTAINER,
			LayoutDataItemTypeConstants.TYPE_CONTAINER
		).put(
			PageDefinition.Type.DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_DROP_ZONE
		).put(
			PageDefinition.Type.FORM, LayoutDataItemTypeConstants.TYPE_FORM
		).put(
			PageDefinition.Type.FORM_STEP,
			LayoutDataItemTypeConstants.TYPE_FORM_STEP
		).put(
			PageDefinition.Type.FORM_STEP_CONTAINER,
			LayoutDataItemTypeConstants.TYPE_FORM_STEP_CONTAINER
		).put(
			PageDefinition.Type.FRAGMENT, LayoutDataItemTypeConstants.TYPE_FRAGMENT
		).put(
			PageDefinition.Type.FRAGMENT_DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_FRAGMENT_DROP_ZONE
		).put(
			PageDefinition.Type.ROW, LayoutDataItemTypeConstants.TYPE_ROW
		).build();

}