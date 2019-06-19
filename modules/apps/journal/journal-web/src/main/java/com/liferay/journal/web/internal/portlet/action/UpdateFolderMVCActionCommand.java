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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/update_folder"
	},
	service = MVCActionCommand.class
)
public class UpdateFolderMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		long parentFolderId = ParamUtil.getLong(
			actionRequest, "parentFolderId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalFolder.class.getName(), actionRequest);

		long[] ddmStructureIds = StringUtil.split(
			ParamUtil.getString(
				actionRequest, "ddmStructuresSearchContainerPrimaryKeys"),
			0L);
		int restrinctionType = ParamUtil.getInteger(
			actionRequest, "restrictionType");
		boolean mergeWithParentFolder = ParamUtil.getBoolean(
			actionRequest, "mergeWithParentFolder");

		_journalFolderService.updateFolder(
			serviceContext.getScopeGroupId(), folderId, parentFolderId, name,
			description, ddmStructureIds, restrinctionType,
			mergeWithParentFolder, serviceContext);
	}

	@Reference
	private JournalFolderService _journalFolderService;

}