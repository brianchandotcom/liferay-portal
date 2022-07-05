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

package com.liferay.knowledge.base.web.internal.portlet.action;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"mvc.command.name=/knowledge_base/update_kb_folder"
	},
	service = MVCActionCommand.class
)
public class UpdateKBFolderMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long parentResourceClassNameId = ParamUtil.getLong(
			actionRequest, "parentResourceClassNameId");
		long parentResourcePrimKey = ParamUtil.getLong(
			actionRequest, "parentResourcePrimKey");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			KBFolder.class.getName(), actionRequest);

		if (cmd.equals(Constants.ADD)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(
					KBWebKeys.THEME_DISPLAY);

			_kbFolderService.addKBFolder(
				null, themeDisplay.getScopeGroupId(), parentResourceClassNameId,
				parentResourcePrimKey, name, description, serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			long kbFolderId = ParamUtil.getLong(actionRequest, "kbFolderId");

			_kbFolderService.updateKBFolder(
				parentResourceClassNameId, parentResourcePrimKey, kbFolderId,
				name, description, serviceContext);
		}
	}

	@Reference
	private KBFolderService _kbFolderService;

}