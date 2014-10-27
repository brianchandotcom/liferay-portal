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

package com.liferay.wiki.parsers.creole.ast;

import com.liferay.wiki.configuration.WikiPropsValues;

/**
 * @author Miguel Pastor
 */
public abstract class URLNode extends ASTNode {

	public URLNode() {
	}

	public URLNode(int token) {
		super(token);
	}

	public URLNode(int token, String link) {
		this(token);

		_link = link;
	}

	public URLNode(String link) {
		_link = link;
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

	private String _link;
	private String[] _supportedProtocols =
		WikiPropsValues.PARSERS_CREOLE_SUPPORTED_PROTOCOLS;

}