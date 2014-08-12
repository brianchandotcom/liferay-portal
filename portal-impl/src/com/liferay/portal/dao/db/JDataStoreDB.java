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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Alexander Chow
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class JDataStoreDB extends FirebirdDB {

	public static DB getInstance() {
		return _instance;
	}

	@Override
	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = StringUtil.replace(
			template,
			new String[] {"\\'", "\\\"", "\\\\", "\\n", "\\r"},
			new String[] {"''", "\"", "\\", "\n", "\r"});

		return template;
	}

	protected JDataStoreDB() {
		super(TYPE_JDATASTORE);
	}

	@Override
	protected String getServerName() {
		return "jdatastore";
	}

	@Override
	protected String[] getTemplate() {
		return _JDATASTORE;
	}

	private static final String[] _JDATASTORE = {
		"--", "TRUE", "FALSE", "'1970-01-01'", "current_timestamp", " binary",
		" binary", " boolean", " date", " double", " integer", " bigint",
		" long varchar", " long varchar", " varchar", "", "commit"
	};

	private static final JDataStoreDB _instance = new JDataStoreDB();

}