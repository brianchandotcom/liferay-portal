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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Locale;

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
		long classNameId, String dataLayoutKey, long siteId);

	public DEDataLayout getDataLayout(long dataLayoutId) throws Exception;

	public DEDataLayout getDataLayout(
			long classNameId, String dataLayoutKey, long siteId)
		throws Exception;

	public Page<DEDataLayout> getDataLayouts(
			long dataDefinitionId, String keywords, Locale locale,
			Pagination pagination, Sort[] sorts)
		throws Exception;

	public DEDataLayout updateDataLayout(
			DEDataLayout deDataLayout, ServiceContext serviceContext)
		throws Exception;

}