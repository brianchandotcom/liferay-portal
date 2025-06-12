/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class ViewSpaceFilesSectionDisplayContext
	extends ViewFilesSectionDisplayContext {

	public ViewSpaceFilesSectionDisplayContext(
		DepotEntryLocalService depotEntryLocalService, long groupId,
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionService objectDefinitionService,
		ObjectDefinitionSettingLocalService objectDefinitionSettingLocalService,
		Portal portal) {

		super(
			depotEntryLocalService, groupLocalService, httpServletRequest,
			language, objectDefinitionService,
			objectDefinitionSettingLocalService, portal);

		this.groupId = groupId;
		this.portal = portal;
	}

	@Override
	protected String getFilterByGroupString() {
		return String.format("groupIds/any(g:g eq %s) and ", groupId);
	}

	@Override
	protected void initEmptyState() {
		emptyStateDescriptionKey = "create-and-manage-files-within-this-space";
		emptyStateImage = "/states/cms_empty_state_files.svg";
		emptyStateTitleKey = "no-files-yet";
	}

	protected final long groupId;
	protected final Portal portal;

}