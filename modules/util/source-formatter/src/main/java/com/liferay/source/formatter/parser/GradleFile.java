/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.parser;

import java.util.Set;

/**
 * @author Peter Shin
 */
public class GradleFile {

	public GradleFile(
		Set<String> applyPlugins, String bodyBlock, String buildScriptBlock,
		String content, String extScriptBlock, String fileName,
		String importsBlock, String initializeBlock, String pluginsScriptBlock,
		Set<String> properties, Set<String> tasks) {

		_applyPlugins = applyPlugins;
		_bodyBlock = bodyBlock;
		_buildScriptBlock = buildScriptBlock;
		_content = content;
		_extScriptBlock = extScriptBlock;
		_fileName = fileName;
		_importsBlock = importsBlock;
		_initializeBlock = initializeBlock;
		_pluginsScriptBlock = pluginsScriptBlock;
		_properties = properties;
		_tasks = tasks;
	}

	public Set<String> getApplyPlugins() {
		return _applyPlugins;
	}

	public String getBodyBlock() {
		return _bodyBlock;
	}

	public String getBuildScriptBlock() {
		return _buildScriptBlock;
	}

	public String getContent() {
		return _content;
	}

	public String getExtScriptBlock() {
		return _extScriptBlock;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getImportsBlock() {
		return _importsBlock;
	}

	public String getInitializeBlock() {
		return _initializeBlock;
	}

	public String getPluginsScriptBlock() {
		return _pluginsScriptBlock;
	}

	public Set<String> getProperties() {
		return _properties;
	}

	public Set<String> getTasks() {
		return _tasks;
	}

	private final Set<String> _applyPlugins;
	private final String _bodyBlock;
	private final String _buildScriptBlock;
	private final String _content;
	private final String _extScriptBlock;
	private final String _fileName;
	private final String _importsBlock;
	private final String _initializeBlock;
	private final String _pluginsScriptBlock;
	private final Set<String> _properties;
	private final Set<String> _tasks;

}