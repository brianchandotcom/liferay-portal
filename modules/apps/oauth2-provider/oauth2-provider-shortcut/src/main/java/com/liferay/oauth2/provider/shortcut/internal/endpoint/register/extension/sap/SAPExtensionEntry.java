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

package com.liferay.oauth2.provider.shortcut.internal.endpoint.register.extension.sap;

/**
 * @author Tomas Polesovsky
 */
public class SAPExtensionEntry {

	public String getName() {
		return _name;
	}

	public String getSignatures() {
		return _signatures;
	}

	public String getTitle() {
		return _title;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSignatures(String signatures) {
		_signatures = signatures;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _name;
	private String _signatures;
	private String _title;

}