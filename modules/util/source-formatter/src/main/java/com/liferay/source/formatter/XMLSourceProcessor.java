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

import com.liferay.source.formatter.checks.SourceCheck;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class XMLSourceProcessor extends BaseSourceProcessor {

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
	protected List<SourceCheck> getSourceChecks() {
		return _sourceChecks;
	}

	@Override
	protected void populateSourceChecks() throws Exception {
		_sourceChecks.add(
			new XMLBuildFileCheck(sourceFormatterArgs.getBaseDirName()));
		_sourceChecks.add(new XMLCustomSQLFileCheck());
		_sourceChecks.add(new XMLDDLStructuresFileCheck());
		_sourceChecks.add(new XMLFriendlyURLRoutesFileCheck());
		_sourceChecks.add(new XMLHBMFileCheck());
		_sourceChecks.add(new XMLLog4jFileCheck());
		_sourceChecks.add(new XMLLookAndFeelFileCheck());
		_sourceChecks.add(new XMLModelHintsFileCheck());
		_sourceChecks.add(new XMLPortletFileCheck(portalSource, subrepository));
		_sourceChecks.add(new XMLPortletPreferencesFileCheck());
		_sourceChecks.add(new XMLPoshiFileCheck());
		_sourceChecks.add(new XMLResourceActionsFileCheck());
		_sourceChecks.add(
			new XMLServiceFileCheck(
				portalSource, subrepository,
				getContent("sql/portal-tables.sql", PORTAL_MAX_DIR_LEVEL),
				getPluginsInsideModulesDirectoryNames()));
		_sourceChecks.add(new XMLSolrSchemaFileCheck());
		_sourceChecks.add(new XMLSpringFileCheck());
		_sourceChecks.add(new XMLToggleFileCheck());

		if (portalSource || subrepository) {
			_sourceChecks.add(new XMLStrutsConfigFileCheck());
			_sourceChecks.add(new XMLTestIgnorableErrorLinesFileCheck());
			_sourceChecks.add(new XMLTilesDefsFileCheck());
			_sourceChecks.add(
				new XMLWebFileCheck(sourceFormatterArgs.getBaseDirName()));
		}

		_sourceChecks.add(
			new XMLWhitespaceCheck(sourceFormatterArgs.getBaseDirName()));

		_sourceChecks.add(new XMLTagAttributesCheck());

		if (portalSource || subrepository) {
			_sourceChecks.add(
				new XMLEmptyLinesCheck(sourceFormatterArgs.getBaseDirName()));
		}
	}

	private static final String[] _INCLUDES = new String[] {
		"**/*.action", "**/*.function", "**/*.jrxml", "**/*.macro",
		"**/*.testcase", "**/*.toggle", "**/*.xml"
	};

	private final List<SourceCheck> _sourceChecks = new ArrayList<>();

}