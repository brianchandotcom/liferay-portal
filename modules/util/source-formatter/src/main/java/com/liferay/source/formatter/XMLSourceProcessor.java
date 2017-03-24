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

package com.liferay.source.formatter;

import com.liferay.source.formatter.checks.FileCheck;
import com.liferay.source.formatter.checks.XMLBuildFileCheck;
import com.liferay.source.formatter.checks.XMLCustomSQLFileCheck;
import com.liferay.source.formatter.checks.XMLDDLStructuresFileCheck;
import com.liferay.source.formatter.checks.XMLEmptyLinesCheck;
import com.liferay.source.formatter.checks.XMLFriendlyURLRoutesFileCheck;
import com.liferay.source.formatter.checks.XMLHBMFileCheck;
import com.liferay.source.formatter.checks.XMLLog4jFileCheck;
import com.liferay.source.formatter.checks.XMLLookAndFeelFileCheck;
import com.liferay.source.formatter.checks.XMLModelHintsFileCheck;
import com.liferay.source.formatter.checks.XMLPortletFileCheck;
import com.liferay.source.formatter.checks.XMLPortletPreferencesFileCheck;
import com.liferay.source.formatter.checks.XMLPoshiFileCheck;
import com.liferay.source.formatter.checks.XMLResourceActionsFileCheck;
import com.liferay.source.formatter.checks.XMLServiceFileCheck;
import com.liferay.source.formatter.checks.XMLSolrSchemaFileCheck;
import com.liferay.source.formatter.checks.XMLSpringFileCheck;
import com.liferay.source.formatter.checks.XMLStrutsConfigFileCheck;
import com.liferay.source.formatter.checks.XMLTagAttributesCheck;
import com.liferay.source.formatter.checks.XMLTestIgnorableErrorLinesFileCheck;
import com.liferay.source.formatter.checks.XMLTilesDefsFileCheck;
import com.liferay.source.formatter.checks.XMLToggleFileCheck;
import com.liferay.source.formatter.checks.XMLWebFileCheck;
import com.liferay.source.formatter.checks.XMLWhitespaceCheck;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class XMLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		return content;
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = new String[] {
			"**/.bnd/**", "**/.idea/**", "**/.ivy/**", "**/bin/**",
			"**/javadocs-*.xml", "**/logs/**", "**/portal-impl/**/*.action",
			"**/portal-impl/**/*.function", "**/portal-impl/**/*.macro",
			"**/portal-impl/**/*.testcase", "**/src/test/**",
			"**/test-classes/unit/**", "**/test-results/**", "**/test/unit/**",
			"**/tools/node**"
		};

		return getFileNames(excludes, getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<FileCheck> getFileChecks() {
		return _fileChecks;
	}

	@Override
	protected void populateFileChecks() throws Exception {
		_fileChecks.add(
			new XMLBuildFileCheck(sourceFormatterArgs.getBaseDirName()));
		_fileChecks.add(new XMLCustomSQLFileCheck());
		_fileChecks.add(new XMLDDLStructuresFileCheck());
		_fileChecks.add(new XMLFriendlyURLRoutesFileCheck());
		_fileChecks.add(new XMLHBMFileCheck());
		_fileChecks.add(new XMLLog4jFileCheck());
		_fileChecks.add(new XMLLookAndFeelFileCheck());
		_fileChecks.add(new XMLModelHintsFileCheck());
		_fileChecks.add(
			new XMLPortletFileCheck(
				getExcludes(_NUMERICAL_PORTLET_NAME_ELEMENT_EXCLUDES),
				portalSource, subrepository));
		_fileChecks.add(new XMLPortletPreferencesFileCheck());
		_fileChecks.add(new XMLPoshiFileCheck());
		_fileChecks.add(new XMLResourceActionsFileCheck());
		_fileChecks.add(
			new XMLServiceFileCheck(
				getExcludes(_SERVICE_FINDER_COLUMN_SORT_EXCLUDES), portalSource,
				subrepository,
				getContent("sql/portal-tables.sql", PORTAL_MAX_DIR_LEVEL),
				getPluginsInsideModulesDirectoryNames()));
		_fileChecks.add(new XMLSolrSchemaFileCheck());
		_fileChecks.add(new XMLSpringFileCheck());
		_fileChecks.add(new XMLToggleFileCheck());

		if (portalSource || subrepository) {
			_fileChecks.add(new XMLStrutsConfigFileCheck());
			_fileChecks.add(new XMLTestIgnorableErrorLinesFileCheck());
			_fileChecks.add(new XMLTilesDefsFileCheck());
			_fileChecks.add(
				new XMLWebFileCheck(sourceFormatterArgs.getBaseDirName()));
		}

		_fileChecks.add(
			new XMLWhitespaceCheck(sourceFormatterArgs.getBaseDirName()));

		_fileChecks.add(new XMLTagAttributesCheck());

		if (portalSource || subrepository) {
			_fileChecks.add(
				new XMLEmptyLinesCheck(sourceFormatterArgs.getBaseDirName()));
		}
	}

	private static final String[] _INCLUDES = new String[] {
		"**/*.action", "**/*.function", "**/*.jrxml", "**/*.macro",
		"**/*.testcase", "**/*.toggle", "**/*.xml"
	};

	private static final String _NUMERICAL_PORTLET_NAME_ELEMENT_EXCLUDES =
		"numerical.portlet.name.element.excludes";

	private static final String _SERVICE_FINDER_COLUMN_SORT_EXCLUDES =
		"service.finder.column.sort.excludes";

	private final List<FileCheck> _fileChecks = new ArrayList<>();

}