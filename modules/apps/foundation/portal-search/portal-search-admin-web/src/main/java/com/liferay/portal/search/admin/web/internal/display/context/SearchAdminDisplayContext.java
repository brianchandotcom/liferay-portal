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

package com.liferay.portal.search.admin.web.internal.display.context;

/**
 * @author Adam Brandizzi
 */
public class SearchAdminDisplayContext {

	public String getStatusString() {
		return _statusString;
	}

	public boolean isMissingSearchEngine() {
		return _missingSearchEngine;
	}

	public void setMissingSearchEngine(boolean missingSearchEngine) {
		_missingSearchEngine = missingSearchEngine;
	}

	public void setStatusString(String statusString) {
		_statusString = statusString;
	}

	private boolean _missingSearchEngine;
	private String _statusString;

}