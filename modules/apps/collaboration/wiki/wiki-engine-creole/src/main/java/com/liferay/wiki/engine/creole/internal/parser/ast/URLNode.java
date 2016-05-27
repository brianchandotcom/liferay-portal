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

package com.liferay.wiki.engine.creole.internal.parser.ast;

import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.engine.creole.internal.util.WikiEngineCreoleComponentProvider;

/**
 * @author Miguel Pastor
 */
public abstract class URLNode extends ASTNode {

	public URLNode() {
		initSupportedProtocols();
	}

	public URLNode(int token) {
		super(token);

		initSupportedProtocols();
	}

	public URLNode(int token, String link) {
		super(token);

		_link = link;

		initSupportedProtocols();
	}

	public URLNode(String link) {
		_link = link;

		initSupportedProtocols();
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

	protected void initSupportedProtocols() {
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