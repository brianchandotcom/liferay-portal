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

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.importer.MasterLayoutsImporter;
import com.liferay.layout.page.template.importer.MasterLayoutsImporterResultEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template/import_master_layout"
	},
	service = MVCActionCommand.class
)
public class ImportMasterLayoutsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String successMessage = LanguageUtil.get(
			_portal.getHttpServletRequest(actionRequest),
			"the-files-were-imported-correctly");

		SessionMessages.add(actionRequest, "requestProcessed", successMessage);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		File file = uploadPortletRequest.getFile("file");

		boolean overwrite = ParamUtil.getBoolean(
			actionRequest, "overwrite", true);

		try {
			List<MasterLayoutsImporterResultEntry>
				masterLayoutsImporterResultEntries =
					_masterLayoutsImporter.importFile(
						themeDisplay.getUserId(),
						themeDisplay.getScopeGroupId(), file, overwrite);

			if (ListUtil.isEmpty(masterLayoutsImporterResultEntries)) {
				return;
			}

			Stream<MasterLayoutsImporterResultEntry> stream =
				masterLayoutsImporterResultEntries.stream();

			List<MasterLayoutsImporterResultEntry>
				notImportedMasterLayoutsImporterResultEntries = stream.filter(
					masterLayoutsImporterResultEntry ->
						masterLayoutsImporterResultEntry.getStatus() !=
							MasterLayoutsImporterResultEntry.Status.IMPORTED
				).collect(
					Collectors.toList()
				);

			if (ListUtil.isNotEmpty(
					notImportedMasterLayoutsImporterResultEntries)) {

				SessionMessages.add(
					actionRequest,
					"notImportedMasterLayoutsImporterResultEntries",
					notImportedMasterLayoutsImporterResultEntries);
			}
			else {
				SessionMessages.add(actionRequest, "success");
			}
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass(), exception);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private MasterLayoutsImporter _masterLayoutsImporter;

	@Reference
	private Portal _portal;

}