/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.language.override.exception.PLOEntryImportException;
import com.liferay.portal.language.override.service.PLOEntryService;
import com.liferay.portal.language.override.web.internal.constants.PLOPortletKeys;

import java.io.File;

import java.nio.file.Files;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"javax.portlet.name=" + PLOPortletKeys.PORTAL_LANGUAGE_OVERRIDE,
		"mvc.command.name=/portal_language_override/import_translations"
	},
	service = MVCActionCommand.class
)
public class ImportTranslationsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		_importTranslations(
			actionRequest, uploadPortletRequest.getFile("file"),
			ParamUtil.getString(actionRequest, "languageId"));

		if (!SessionErrors.isEmpty(actionRequest)) {
			actionResponse.setRenderParameter(
				"mvcPath", "/configuration/icon/import_translations.jsp");
		}
		else {
			sendRedirect(actionRequest, actionResponse);
		}
	}

	private void _importTranslations(
		ActionRequest actionRequest, File file, String languageId) {

		if ((file == null) || !file.exists()) {
			SessionErrors.add(actionRequest, "fileEmpty");

			return;
		}

		if (!Objects.equals(
				FileUtil.getExtension(file.getName()), "properties")) {

			SessionErrors.add(actionRequest, "fileExtensionInvalid");

			return;
		}

		try {
			_ploEntryService.importPLOEntries(
				Files.newInputStream(file.toPath()), languageId);
		}
		catch (PLOEntryImportException.InvalidPropertiesFile
					ploEntryImportException) {

			SessionErrors.add(
				actionRequest, "fileInvalid", ploEntryImportException);
		}
		catch (PLOEntryImportException.InvalidTranslations
					ploEntryImportException) {

			for (Exception exception :
					ploEntryImportException.getExceptions()) {

				SessionErrors.add(
					actionRequest, exception.getClass(), exception);
			}
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass(), exception);
		}
	}

	@Reference
	private PLOEntryService _ploEntryService;

	@Reference
	private Portal _portal;

}