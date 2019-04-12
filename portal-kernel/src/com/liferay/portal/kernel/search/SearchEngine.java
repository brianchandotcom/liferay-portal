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

package com.liferay.portal.kernel.search;

/**
 * @author Bruno Farache
 * @author Michael C. Han
 */
public interface SearchEngine {

	public String backup(long companyId, String backupName)
		throws SearchException;

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	public BooleanQueryFactory getBooleanQueryFactory();

	public IndexSearcher getIndexSearcher();

	public IndexWriter getIndexWriter();

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	public TermQueryFactory getTermQueryFactory();

	public String getVendor();

	public void initialize(long companyId);

	public void removeBackup(long companyId, String backupName)
		throws SearchException;

	public void removeCompany(long companyId);

	public void restore(long companyId, String backupName)
		throws SearchException;

}