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

package com.liferay.content.repository.web.internal.portlet.action;

import com.liferay.content.repository.model.ContentRepositoryEntry;
import com.liferay.content.repository.service.ContentRepositoryEntryLocalService;
import com.liferay.content.repository.web.internal.constants.ContentRepositoryPortletKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentRepositoryPortletKeys.CONTENT_REPOSITORY_ADMIN,
		"mvc.command.name=/content_repository_entry/edit"
	},
	service = MVCActionCommand.class
)
public class EditContentRepositoryEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ContentRepositoryEntry.class.getName(), actionRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		long contentRepositoryEntryId = ParamUtil.getLong(
			actionRequest, "contentRepositoryEntryId");

		ContentRepositoryEntry contentRepositoryEntry =
			_contentRepositoryEntryLocalService.getContentRepositoryEntry(
				contentRepositoryEntryId);

		Group group = _groupService.getGroup(
			contentRepositoryEntry.getGroupId());

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name", group.getNameMap());
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "description", group.getDescriptionMap());

		_contentRepositoryEntryLocalService.updateContentRepositoryEntry(
			contentRepositoryEntryId, nameMap, descriptionMap, serviceContext);
	}

	@Reference
	private ContentRepositoryEntryLocalService
		_contentRepositoryEntryLocalService;

	@Reference
	private GroupService _groupService;

}