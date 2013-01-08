/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.shard;

import javax.sql.DataSource;

/**
 * @author Alexander Chow
 */
public interface Shard {

	public String[] getAvailableShardNames();

	public String getCurrentShardName();

	public DataSource getDataSource();

	public String getDefaultShardName();

	public boolean isEnabled();

	public String popCompanyService();

	public void pushCompanyService(long companyId);

	public void pushCompanyService(String shardName);

	public String setTargetSource(String shardName);

}