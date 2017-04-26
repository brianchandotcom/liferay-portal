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
	protected List<SourceCheck> getSourceChecks() throws Exception {
		List<SourceCheck> sourceChecks = new ArrayList<>();

		sourceChecks.add(new XMLBuildFileCheck());
		sourceChecks.add(new XMLCustomSQLFileCheck());
		sourceChecks.add(new XMLDDLStructuresFileCheck());
		sourceChecks.add(new XMLFriendlyURLRoutesFileCheck());
		sourceChecks.add(new XMLHBMFileCheck());
		sourceChecks.add(new XMLLog4jFileCheck());
		sourceChecks.add(new XMLLookAndFeelFileCheck());
		sourceChecks.add(new XMLModelHintsFileCheck());
		sourceChecks.add(new XMLPortletFileCheck());
		sourceChecks.add(new XMLPortletPreferencesFileCheck());
		sourceChecks.add(new XMLPoshiFileCheck());
		sourceChecks.add(new XMLResourceActionsFileCheck());
		sourceChecks.add(new XMLServiceFileCheck());
		sourceChecks.add(new XMLSolrSchemaFileCheck());
		sourceChecks.add(new XMLSpringFileCheck());
		sourceChecks.add(new XMLToggleFileCheck());

		if (portalSource || subrepository) {
			sourceChecks.add(new XMLStrutsConfigFileCheck());
			sourceChecks.add(new XMLTestIgnorableErrorLinesFileCheck());
			sourceChecks.add(new XMLTilesDefsFileCheck());
			sourceChecks.add(new XMLWebFileCheck());
		}

		sourceChecks.add(new XMLWhitespaceCheck());

		sourceChecks.add(new XMLTagAttributesCheck());

		if (portalSource || subrepository) {
			sourceChecks.add(new XMLEmptyLinesCheck());
		}

		return sourceChecks;
	}

	private static final String[] _INCLUDES = new String[] {
		"**/*.action", "**/*.function", "**/*.jrxml", "**/*.macro",
		"**/*.testcase", "**/*.toggle", "**/*.xml"
	};

}