/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.resource.v1_0;

import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.scim.rest.dto.v1_0.Group;
import com.liferay.scim.rest.internal.manager.GroupResourceManagerImpl;
import com.liferay.scim.rest.internal.manager.UserManagerImpl;
import com.liferay.scim.rest.resource.v1_0.GroupResource;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import org.wso2.charon3.core.extensions.UserManager;
import org.wso2.charon3.core.protocol.SCIMResponse;
import org.wso2.charon3.core.protocol.endpoints.GroupResourceManager;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/group.properties",
	scope = ServiceScope.PROTOTYPE, service = GroupResource.class
)
public class GroupResourceImpl extends BaseGroupResourceImpl {

	@Override
	public Response deleteV2Group(String id) throws Exception {
		return _buildResponse(_groupResourceManager.delete(id, _userManager));
	}

	@Override
	public Object getV2GroupById(String id) throws Exception {
		return _buildResponse(
			_groupResourceManager.get(id, _userManager, null, null));
	}

	@Override
	public Object getV2Groups(Integer count, Integer startIndex)
		throws Exception {

		return _buildResponse(
			_groupResourceManager.listWithGET(
				_userManager, null, startIndex, count, null, null, null, null,
				null));
	}

	@Override
	public Response postV2Group(Group group) throws Exception {
		return _buildResponse(
			_groupResourceManager.create(
				group.toString(), _userManager, null, null));
	}

	@Override
	public Response putV2Group(String id, Group group) throws Exception {
		return _buildResponse(
			_groupResourceManager.updateWithPUT(
				id, group.toString(), _userManager, null, null));
	}

	@Activate
	protected void activate() {
		_userManager = new UserManagerImpl(
			_classNameLocalService, _companyLocalService, _configurationAdmin,
			_expandoColumnLocalService, _expandoTableLocalService,
			_expandoValueLocalService, _searcher, _searchRequestBuilderFactory,
			_userGroupLocalService, _userGroupService, _userLocalService,
			_userService);
	}

	private Response _buildResponse(SCIMResponse scimResponse) {
		Response.ResponseBuilder responseBuilder = Response.status(
			scimResponse.getResponseStatus());

		if (scimResponse.getResponseMessage() != null) {
			responseBuilder.entity(scimResponse.getResponseMessage());
		}

		Map<String, String> map = scimResponse.getHeaderParamMap();

		if (MapUtil.isNotEmpty(map)) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				responseBuilder.header(entry.getKey(), entry.getValue());
			}
		}

		return responseBuilder.build();
	}

	private static final GroupResourceManager _groupResourceManager =
		new GroupResourceManagerImpl();

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserGroupService _userGroupService;

	@Reference
	private UserLocalService _userLocalService;

	private UserManager _userManager;

	@Reference
	private UserService _userService;

}