/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			new ActionInfo(
				ActionKeys.SUBSCRIBE, KeywordResourceImpl.class,
				"putKeywordSubscribe"),
			"subscribe");
		registerActionInfo(
			new ActionInfo(
				ActionKeys.SUBSCRIBE, KeywordResourceImpl.class,
				"putKeywordUnsubscribe"),
			"unsubscribe");
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