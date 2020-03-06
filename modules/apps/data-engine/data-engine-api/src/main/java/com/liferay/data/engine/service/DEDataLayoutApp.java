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

package com.liferay.data.engine.service;

import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Leonardo Barros
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor = Exception.class)
public interface DEDataLayoutApp {

	public DEDataLayout addDataLayout(
			DEDataLayout deDataLayout, ServiceContext serviceContext)
		throws Exception;

	public void deleteDataLayout(long dataLayoutId) throws Exception;

	public void deleteDataLayoutDataDefinition(long dataDefinitionId)
		throws Exception;

	public DEDataLayout fetchDataLayout(long dataLayoutId);

	public DEDataLayout fetchDataLayout(
		long groupId, long classNameId, String dataLayoutKey);

	public DEDataLayout getDataLayout(long dataLayoutId) throws Exception;

	public DEDataLayout getDataLayout(
			long groupId, long classNameId, String dataLayoutKey)
		throws Exception;

	public List<DEDataLayout> getDataLayouts(
			long dataDefinitionId, String keywords, int start, int end,
			OrderByComparator orderByComparator)
		throws Exception;

	public int getDataLayoutsCount(long dataDefinitionId, String keywords)
		throws Exception;

	public DEDataLayout updateDataLayout(
			DEDataLayout deDataLayout, ServiceContext serviceContext)
		throws Exception;

}