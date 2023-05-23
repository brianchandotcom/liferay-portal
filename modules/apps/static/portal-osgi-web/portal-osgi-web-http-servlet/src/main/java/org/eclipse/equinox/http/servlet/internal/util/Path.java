/*******************************************************************************
 * Copyright (c) 2015 Raymond Augé.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 467859
 ******************************************************************************/

package org.eclipse.equinox.http.servlet.internal.util;

/**
 * @author Raymond Augé
 */
public class Path {

	public Path(String path) {
		int index = path.indexOf('?');

		if (index == -1) {
			_requestURI = path;
			_queryString = null;
		}
		else {
			_requestURI = path.substring(0, index);
			_queryString = path.substring(index + 1);
		}

		index = _requestURI.lastIndexOf('.');

		if ((index == -1) || (index < _requestURI.lastIndexOf('/'))) {
			_extension = null;
		}
		else {
			_extension = _requestURI.substring(index + 1);
		}
	}

	public String getRequestURI() {
		return _requestURI;
	}

	public String getQueryString() {
		return _queryString;
	}

	public String getExtension() {
		return _extension;
	}

	private final String _requestURI;
	private final String _queryString;
	private final String _extension;

}