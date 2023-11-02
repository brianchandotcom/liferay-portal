/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.resource.v1_0;

import com.liferay.scim.rest.dto.v1_0.User;
import com.liferay.scim.rest.resource.handler.UserResourceHandler;
import com.liferay.scim.rest.resource.v1_0.UserResource;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user.properties",
	scope = ServiceScope.PROTOTYPE, service = UserResource.class
)
public class UserResourceImpl extends BaseUserResourceImpl {

	@Override
	public Object getV2User(Integer count, Integer startIndex)
		throws Exception {

		return _userResourceHandler.getV2User(count, startIndex);
	}

	@Override
	public Object getV2UserById(String id) throws Exception {
		return _userResourceHandler.getV2UserById(id);
	}

	@Override
	public Response postV2User(User user) throws Exception {
		return _userResourceHandler.postV2User(user);
	}

	@Override
	public Response putV2User(String id, User user) throws Exception {
		return _userResourceHandler.putV2User(id, user);
	}

	@Reference
	private UserResourceHandler _userResourceHandler;

}