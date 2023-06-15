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

package com.liferay.headless.admin.taxonomy.internal.dto.v1_0.action;

import com.liferay.headless.admin.taxonomy.internal.dto.v1_0.action.metadata.TaxonomyCategoryDTOActionMetadataProvider;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.portal.vulcan.action.ActionInfo;
import com.liferay.portal.vulcan.action.DTOActionProvider;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.UriInfoUtil;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		"dto.class.name=com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory"
	},
	service = DTOActionProvider.class
)
@Generated("")
public class TaxonomyCategoryDTOActionProvider implements DTOActionProvider {

	@Override
	public Map<String, Map<String, String>> getActions(
		long groupId, long primaryKey, UriInfo uriInfo, long userId) {

		Map<String, Map<String, String>> actions = new HashMap<>();

		TaxonomyCategoryDTOActionMetadataProvider
			taxonomyCategoryDTOActionMetadataProvider =
				new TaxonomyCategoryDTOActionMetadataProvider();

		for (String actionName :
				taxonomyCategoryDTOActionMetadataProvider.getActionNames()) {

			ActionInfo actionInfo =
				taxonomyCategoryDTOActionMetadataProvider.getActionInfo(
					actionName);

			if ((actionInfo == null) || (actionInfo.getActionKey() == null) ||
				(actionInfo.getResourceMethodName() == null)) {

				continue;
			}

			actions.put(
				actionName,
				ActionUtil.addAction(
					actionInfo.getActionKey(), actionInfo.getResourceClass(),
					primaryKey, actionInfo.getResourceMethodName(),
					_scopeChecker, userId,
					taxonomyCategoryDTOActionMetadataProvider.
						getPermissionName(),
					groupId,
					() -> UriInfoUtil.getBaseUriBuilder(
						"headless-admin-taxonomy", uriInfo),
					uriInfo));
		}

		return actions;
	}

	@Override
	public Map<String, ActionInfo> getActionInfos() throws Exception {
		Map<String, ActionInfo> actionInfos = new HashMap<>();

		TaxonomyCategoryDTOActionMetadataProvider
			taxonomyCategoryDTOActionMetadataProvider =
				new TaxonomyCategoryDTOActionMetadataProvider();

		for (String actionName :
				taxonomyCategoryDTOActionMetadataProvider.getActionNames()) {

			actionInfos.put(
				actionName,
				taxonomyCategoryDTOActionMetadataProvider.getActionInfo(
					actionName));
		}

		return actionInfos;
	}

	@Reference
	private ScopeChecker _scopeChecker;

}