/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

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

	public static PageElement.Type toExternalType(String internalType) {
		Set<PageElement.Type> externalTypes =
			_externalToInternalValuesMap.keySet();

		for (PageElement.Type externalType : externalTypes) {
			if (Objects.equals(
					internalType,
					_externalToInternalValuesMap.get(externalType))) {

				return externalType;
			}
		}

		throw new UnsupportedOperationException();
	}

	public static String toInternalType(PageElement.Type externalType) {
		if (_externalToInternalValuesMap.containsKey(externalType)) {
			return _externalToInternalValuesMap.get(externalType);
		}

		throw new UnsupportedOperationException();
	}

	private static final Map<PageElement.Type, String>
		_externalToInternalValuesMap = HashMapBuilder.put(
			PageElement.Type.COLLECTION,
			LayoutDataItemTypeConstants.TYPE_COLLECTION
		).put(
			PageElement.Type.COLLECTION_ITEM,
			LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM
		).put(
			PageElement.Type.COLUMN, LayoutDataItemTypeConstants.TYPE_COLUMN
		).put(
			PageElement.Type.CONTAINER,
			LayoutDataItemTypeConstants.TYPE_CONTAINER
		).put(
			PageElement.Type.DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_DROP_ZONE
		).put(
			PageElement.Type.FORM, LayoutDataItemTypeConstants.TYPE_FORM
		).put(
			PageElement.Type.FORM_STEP,
			LayoutDataItemTypeConstants.TYPE_FORM_STEP
		).put(
			PageElement.Type.FORM_STEP_CONTAINER,
			LayoutDataItemTypeConstants.TYPE_FORM_STEP_CONTAINER
		).put(
			PageElement.Type.FRAGMENT, LayoutDataItemTypeConstants.TYPE_FRAGMENT
		).put(
			PageElement.Type.FRAGMENT_DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_FRAGMENT_DROP_ZONE
		).put(
			PageElement.Type.ROW, LayoutDataItemTypeConstants.TYPE_ROW
		).build();

}