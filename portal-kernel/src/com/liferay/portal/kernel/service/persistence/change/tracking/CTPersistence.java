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

package com.liferay.portal.kernel.service.persistence.change.tracking;

import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Preston Crary
 */
public interface CTPersistence<T extends CTModel<T>>
	extends BasePersistence<T> {

	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType);

	public List<String> getMappingTableNames();

	public Map<String, Integer> getTableColumnsMap();

	public String getTableName();

	public List<String[]> getUniqueIndexColumnNames();

}