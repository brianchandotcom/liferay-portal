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

package com.liferay.portal.kernel.upgrade.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public interface UpgradeTable {

	public void appendColumn(StringBuilder sb, Object value, boolean last)
		throws Exception;

	public void appendColumn(
			StringBuilder sb, ResultSet rs, String name, Integer type,
			boolean last)
		throws Exception;

	public void copyTable(
			Connection sourceConnection, Connection targetConnection)
		throws Exception;

	public String getCreateSQL() throws Exception;

	public String getDeleteSQL() throws Exception;

	public String[] getIndexesSQL() throws Exception;

	public String getInsertSQL() throws Exception;

	public String getSelectSQL() throws Exception;

	public String getTempFileName();

	public boolean isAllowUniqueIndexes() throws Exception;

	public boolean isDeleteTempFile();

	public void setAllowUniqueIndexes(boolean allowUniqueIndexes)
		throws Exception;

	public void setColumn(
			PreparedStatement ps, int index, Integer type, String value)
		throws Exception;

	public void setCreateSQL(String createSQL) throws Exception;

	public void setDeleteTempFile(boolean deleteTempFile);

	public void setIndexesSQL(String[] indexesSQL) throws Exception;

	public void updateTable() throws Exception;

}