/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.resource;

import javax.ws.rs.core.Response;

/**
 * @author Rafael Praxedes
 */
public interface UserResource {

	public Response createUser(String resourceString);

	public Response deleteUser(String id);

	public Response getUser(String id);

	public Response listUsers(int count, int startIndex);

	public Response searchUser(String resourceString);

	public Response updateUser(String id, String resourceString);

}