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

package com.liferay.data.engine.manager;

import com.liferay.data.engine.DataLayout;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Leonardo Barros
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor = Exception.class)
public interface DataLayoutManager {

	public DataLayout addDataLayout(DataLayout dataLayout) throws Exception;

	public void deleteDataLayout(long dataLayoutId) throws Exception;

	public void deleteDataLayoutDataDefinition(long dataDefinitionId)
		throws Exception;

	public DataLayout fetchDataLayout(long dataLayoutId);

	public DataLayout fetchDataLayout(
		long classNameId, String dataLayoutKey, long groupId);

	public DataLayout getDataLayout(long dataLayoutId) throws Exception;

	public DataLayout getDataLayout(
			long classNameId, String dataLayoutKey, long groupId)
		throws Exception;

	public List<DataLayout> getDataLayouts(
			long dataDefinitionId, int end, String keywords,
			OrderByComparator orderByComparator, int start)
		throws Exception;

	public int getDataLayoutsCount(long dataDefinitionId, String keywords)
		throws Exception;

	public DataLayout updateDataLayout(DataLayout dataLayout) throws Exception;

}