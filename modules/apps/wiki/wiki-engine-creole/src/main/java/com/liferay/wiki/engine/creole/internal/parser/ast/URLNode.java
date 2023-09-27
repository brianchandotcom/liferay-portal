/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.engine.creole.internal.parser.ast;

import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.engine.creole.internal.util.WikiEngineCreoleComponentProvider;

/**
 * @author Miguel Pastor
 */
public abstract class URLNode extends ASTNode {

	public URLNode() {
		_initSupportedProtocols();
	}

	public URLNode(int token) {
		super(token);

		_initSupportedProtocols();
	}

	public URLNode(int token, String link) {
		super(token);

		_link = link;

		_initSupportedProtocols();
	}

	public URLNode(String link) {
		_link = link;

		_initSupportedProtocols();
	}

	public String getLink() {
		return _link;
	}

	public String[] getSupportedProtocols() {
		return _supportedProtocols;
	}

	public boolean isAbsoluteLink() {
		for (String supportedProtocol : getSupportedProtocols()) {
			if (_link.startsWith(supportedProtocol)) {
				return true;
			}
		}

		return false;
	}

	public void setLink(String link) {
		_link = link;
	}

	public void setSupportedProtocols(String[] supportedProtocols) {
		_supportedProtocols = supportedProtocols;
	}

	private void _initSupportedProtocols() {
		WikiEngineCreoleComponentProvider wikiEngineCreoleComponentProvider =
			WikiEngineCreoleComponentProvider.
				getWikiEngineCreoleComponentProvider();

		WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
			wikiEngineCreoleComponentProvider.
				getWikiGroupServiceConfiguration();

		_supportedProtocols =
			wikiGroupServiceConfiguration.parsersCreoleSupportedProtocols();
	}

	private String _link;
	private String[] _supportedProtocols;

}