/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Organization {

	public Organization(long organizationId, String name, String path) {
		_organizationId = organizationId;
		_name = name;
		_path = path;
	}

	public String getName() {
		return _name;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public String getPath() {
		return _path;
	}

	private final String _name;
	private final long _organizationId;
	private final String _path;

}