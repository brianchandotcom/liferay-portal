/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.resource;

import javax.ws.rs.core.Response;

/**
 * @author Rafael Praxedes
 */
public interface SCIMUserResource {

	public Response addSCIMUser(String resourceString);

	public Response deleteSCIMUser(String id);

	public Response getSCIMUser(String id);

	public Response getSCIMUsers(int count, int startIndex);

	public Response getSCIMUsers(String resourceString);

	public Response updateSCIMUser(String id, String resourceString);

}