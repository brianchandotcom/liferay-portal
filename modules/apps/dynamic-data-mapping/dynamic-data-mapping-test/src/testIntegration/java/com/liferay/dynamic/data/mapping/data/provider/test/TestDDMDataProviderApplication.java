/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.test;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

/**
 * @author Carolina Barbosa
 */
public class TestDDMDataProviderApplication extends Application {

	@GET
	@Path("/get-full-name")
	public Response getFullName(@QueryParam("name") String[] name) {
		return Response.ok(
			JSONUtil.put(
				"fullName", StringUtil.merge(name, StringPool.SPACE)
			).toString()
		).build();
	}

	@Override
	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

}