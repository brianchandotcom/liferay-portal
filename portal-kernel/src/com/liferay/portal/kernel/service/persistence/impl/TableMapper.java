/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Shuyang Zhou
 */
@ProviderType
public interface TableMapper<L extends BaseModel<L>, R extends BaseModel<R>> {

	public boolean addTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey);

	public long[] addTableMappings(
		long companyId, long leftPrimaryKey, long[] rightPrimaryKeys);

	public long[] addTableMappings(
		long companyId, long[] leftPrimaryKeys, long rightPrimaryKey);

	public boolean containsTableMapping(
		long leftPrimaryKey, long rightPrimaryKey);

	public int deleteLeftPrimaryKeyTableMappings(long leftPrimaryKey);

	public int deleteRightPrimaryKeyTableMappings(long rightPrimaryKey);

	public boolean deleteTableMapping(
		long leftPrimaryKey, long rightPrimaryKey);

	public long[] deleteTableMappings(
		long leftPrimaryKey, long[] rightPrimaryKeys);

	public long[] deleteTableMappings(
		long[] leftPrimaryKeys, long rightPrimaryKey);

	public void destroy();

	public List<L> getLeftBaseModels(
		long rightPrimaryKey, int start, int end,
		OrderByComparator<L> orderByComparator);

	public long[] getLeftPrimaryKeys(long rightPrimaryKey);

	public TableMapper<R, L> getReverseTableMapper();

	public List<R> getRightBaseModels(
		long leftPrimaryKey, int start, int end,
		OrderByComparator<R> orderByComparator);

	public long[] getRightPrimaryKeys(long leftPrimaryKey);

	public boolean matches(String leftColumnName, String rightColumnName);

}