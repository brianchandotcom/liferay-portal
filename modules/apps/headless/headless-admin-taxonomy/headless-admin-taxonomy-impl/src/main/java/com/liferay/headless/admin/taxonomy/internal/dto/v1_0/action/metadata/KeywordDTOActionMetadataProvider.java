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

package com.liferay.headless.admin.taxonomy.internal.dto.v1_0.action.metadata;

import com.liferay.headless.admin.taxonomy.internal.resource.v1_0.KeywordResourceImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.vulcan.dto.action.ActionInfo;
import com.liferay.portlet.asset.service.permission.AssetTagsPermission;

/**
 * @author Carlos Correa
 */
public class KeywordDTOActionMetadataProvider
	extends BaseKeywordDTOActionMetadataProvider {

	public KeywordDTOActionMetadataProvider() {
		registerActionInfo(
			"subscribe",
			new ActionInfo(
				ActionKeys.SUBSCRIBE, KeywordResourceImpl.class,
				"putKeywordSubscribe"));
		registerActionInfo(
			"unsubscribe",
			new ActionInfo(
				ActionKeys.SUBSCRIBE, KeywordResourceImpl.class,
				"putKeywordUnsubscribe"));
	}

	@Override
	public String getPermissionName() {
		return AssetTagsPermission.RESOURCE_NAME;
	}

	@Override
	protected String getDeleteActionName() {
		return ActionKeys.MANAGE_TAG;
	}

	@Override
	protected String getGetActionName() {
		return ActionKeys.MANAGE_TAG;
	}

	@Override
	protected String getReplaceActionName() {
		return ActionKeys.MANAGE_TAG;
	}

	@Override
	protected String getUpdateResourceMethodName() {
		return null;
	}

}