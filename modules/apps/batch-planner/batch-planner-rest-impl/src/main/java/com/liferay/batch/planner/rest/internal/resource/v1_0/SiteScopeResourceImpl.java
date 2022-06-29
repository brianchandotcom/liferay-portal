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

package com.liferay.batch.planner.rest.internal.resource.v1_0;

import com.liferay.batch.planner.rest.dto.v1_0.SiteScope;
import com.liferay.batch.planner.rest.internal.vulcan.yaml.openapi.OpenAPIYAMLProvider;
import com.liferay.batch.planner.rest.resource.v1_0.SiteScopeResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.OpenAPIUtil;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Matija Petanjek
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/site-scope.properties",
	scope = ServiceScope.PROTOTYPE, service = SiteScopeResource.class
)
public class SiteScopeResourceImpl extends BaseSiteScopeResourceImpl {

	@Override
	public Page<SiteScope> getPlanInternalClassNameSiteScopesPage(
			String internalClassName, Boolean export)
		throws Exception {

		List<String> entityScopes = null;

		OpenAPIYAML openAPIYAML = _openAPIYAMLProvider.getOpenAPIYAML(
			internalClassName);

		String simpleInternalClassName = internalClassName.substring(
			internalClassName.lastIndexOf(StringPool.PERIOD) + 1);

		if (GetterUtil.getBoolean(export)) {
			entityScopes = OpenAPIUtil.getReadEntityScopes(
				simpleInternalClassName, openAPIYAML);
		}
		else {
			entityScopes = OpenAPIUtil.getCreateEntityScopes(
				simpleInternalClassName, openAPIYAML);
		}

		return Page.of(_getSiteScopes(entityScopes));
	}

	private List<SiteScope> _getSiteScopes(List<String> entityScopes)
		throws Exception {

		List<SiteScope> siteScopes = new ArrayList<>();

		if (entityScopes.contains("site")) {
			for (Group group : _groupService.getUserSitesGroups()) {
				if (Objects.equals(group.getDescriptiveName(), "Global")) {
					continue;
				}

				siteScopes.add(
					new SiteScope() {
						{
							label = group.getDescriptiveName();
							value = group.getGroupId();
						}
					});
			}
		}

		return siteScopes;
	}

	@Reference
	private GroupService _groupService;

	@Reference
	private OpenAPIYAMLProvider _openAPIYAMLProvider;

}