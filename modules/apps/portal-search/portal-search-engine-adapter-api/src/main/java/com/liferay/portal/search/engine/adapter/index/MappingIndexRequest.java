/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

/**
 * @author Leon Chi
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public interface MappingIndexRequest<T extends IndexResponse>
	extends IndexRequest<T> {

	public String getMappingName();

}