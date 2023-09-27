/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.internal.config.generator;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class JSConfigGeneratorModule {

	public JSConfigGeneratorModule(
		JSConfigGeneratorPackage jsConfigGeneratorPackage, String moduleId,
		List<String> dependencies, String contextPath) {

		_jsConfigGeneratorPackage = jsConfigGeneratorPackage;
		_moduleId = moduleId;
		_dependencies = dependencies;

		int index = moduleId.indexOf(StringPool.SLASH);

		_name = moduleId.substring(index + 1);

		_url = StringBundler.concat(contextPath, StringPool.SLASH, _name);
	}

	public List<String> getDependencies() {
		return _dependencies;
	}

	/**
	 * Returns the ID of the module.
	 *
	 * For example: 'my-package@1.0.0/path/to/module'
	 *
	 * This is the legacy equivalent of {@link JSModule#getResolvedId()} for new
	 * JS modules, but in this case we don't use "resolved" prefix because
	 * there's no notion of resolved URLs or IDs in legacy modules.
	 *
	 * @return
	 */
	public String getId() {
		return _moduleId;
	}

	public JSConfigGeneratorPackage getJSConfigGeneratorPackage() {
		return _jsConfigGeneratorPackage;
	}

	/**
	 * Returns the name of the module.
	 *
	 * For example: 'path/to/module'
	 *
	 * This is the legacy equivalent of {@link JSModule#getName()} for new JS
	 * modules.
	 *
	 * @review
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns the publicly accessible URL of the module.
	 *
	 * For example: '/o/my-web-context/path/to/module'
	 *
	 * This is the legacy equivalent of {@link JSModule#getResolvedURL()} for
	 * new JS modules, but in this case we don't use "resolved" prefix because
	 * there's no notion of resolved URLs or IDs in legacy modules.
	 *
	 * @review
	 */
	public String getURL() {
		return _url;
	}

	private final List<String> _dependencies;
	private final JSConfigGeneratorPackage _jsConfigGeneratorPackage;
	private final String _moduleId;
	private final String _name;
	private final String _url;

}