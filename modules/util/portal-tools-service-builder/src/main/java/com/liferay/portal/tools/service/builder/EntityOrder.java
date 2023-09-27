/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class EntityOrder {

	public EntityOrder(boolean asc, List<EntityColumn> entityColumns) {
		_asc = asc;
		_entityColumns = entityColumns;
	}

	public List<EntityColumn> getEntityColumns() {
		return _entityColumns;
	}

	public boolean isAscending() {
		return _asc;
	}

	private final boolean _asc;
	private final List<EntityColumn> _entityColumns;

}