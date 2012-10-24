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

package com.liferay.portal.kernel.dao.db;

/**
 * @author James Lefeu
 * @author Peter Shin
 */
public class IndexMetadata extends Index {

	public IndexMetadata(
		String indexName, String tableName, boolean unique,
		String specification, String createSQL, String dropSQL) {

		super(indexName, tableName, unique);

		_specification = specification;
		_createSQL = createSQL;
		_dropSQL = dropSQL;
	}

	public String getCreateSQL() {
		return _createSQL;
	}

	public String getDropSQL() {
		return _dropSQL;
	}

	public String getSpecification() {
		return _specification;
	}

	private String _createSQL;
	private String _dropSQL;
	private String _specification;

}