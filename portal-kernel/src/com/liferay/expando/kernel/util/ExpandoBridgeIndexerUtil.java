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

package com.liferay.expando.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.search.Document;

/**
 * @author Raymond Augé
 */
public class ExpandoBridgeIndexerUtil {

	public static void addAttributes(
		Document doc, ExpandoBridge expandoBridge) {

		getExpandoBridgeIndexer().addAttributes(doc, expandoBridge);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #encodeFieldName(String, int)}
	 */
	@Deprecated
	public static String encodeFieldName(String columnName) {
		return getExpandoBridgeIndexer().encodeFieldName(columnName);
	}

	public static String encodeFieldName(String columnName, int indexType) {
		return getExpandoBridgeIndexer().encodeFieldName(columnName, indexType);
	}

	public static ExpandoBridgeIndexer getExpandoBridgeIndexer() {
		return _expandoBridgeIndexer;
	}

	public void setExpandoBridgeIndexer(
		ExpandoBridgeIndexer expandoBridgeIndexer) {

		_expandoBridgeIndexer = expandoBridgeIndexer;
	}

	private static ExpandoBridgeIndexer _expandoBridgeIndexer;

}