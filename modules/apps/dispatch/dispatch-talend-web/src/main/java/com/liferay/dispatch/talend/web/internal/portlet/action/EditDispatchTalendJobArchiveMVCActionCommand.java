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

package com.liferay.dispatch.talend.web.internal.portlet.action;

import com.liferay.dispatch.constants.DispatchPortletKeys;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.dispatch.talend.archive.TalendArchiveParserUtil;
import com.liferay.dispatch.talend.archive.exception.TalendArchiveException;
import com.liferay.expando.kernel.exception.DuplicateColumnNameException;
import com.liferay.expando.kernel.exception.DuplicateTableNameException;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.zip.ZipException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(
	property = {
		"javax.portlet.name=" + DispatchPortletKeys.DISPATCH,
		"mvc.command.name=/dispatch_talend/edit_dispatch_talend_job_archive"
	},
	service = MVCActionCommand.class
)
public class EditDispatchTalendJobArchiveMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_checkPermission(actionRequest);
			_setupExpando(
				_companyLocalService.getCompanyIdByUserId(
					_portal.getUserId(actionRequest)));

			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(actionRequest);

			long dispatchTriggerId = ParamUtil.getLong(
				uploadPortletRequest, "dispatchTriggerId");

			File jobArchiveFile = FileUtil.createTempFile(
				uploadPortletRequest.getFileAsStream("jobArchive"));

			try (FileInputStream fileInputStream = new FileInputStream(
					jobArchiveFile)) {

				_updateDispatchTaskSettings(dispatchTriggerId, fileInputStream);

				_dispatchFileRepository.addFileEntry(
					_portal.getUserId(actionRequest), dispatchTriggerId,
					uploadPortletRequest.getFileName("jobArchive"),
					uploadPortletRequest.getSize("jobArchive"),
					uploadPortletRequest.getContentType("jobArchive"),
					new FileInputStream(jobArchiveFile));

				_expandoValueLocalService.addValue(
					_companyLocalService.getCompanyIdByUserId(
						_portal.getUserId(actionRequest)),
					DispatchTrigger.class.getName(), "DispatchArchiveFile",
					"fileName", _portal.getUserId(actionRequest),
					uploadPortletRequest.getFileName("jobArchive"));

				_expandoValueLocalService.addValue(
					_companyLocalService.getCompanyIdByUserId(
						_portal.getUserId(actionRequest)),
					DispatchTrigger.class.getName(), "DispatchArchiveFile",
					"dispatchTriggerId", _portal.getUserId(actionRequest),
					String.valueOf(dispatchTriggerId));
			}
			finally {
				FileUtil.delete(jobArchiveFile);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			if (!_isArchiveException(exception)) {
				return;
			}

			SessionErrors.add(actionRequest, exception.getClass());

			sendRedirect(
				actionRequest, actionResponse,
				ParamUtil.getString(actionRequest, "redirect"));
		}
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoColumnLocalService(
		ExpandoColumnLocalService expandoColumnLocalService) {

		_expandoColumnLocalService = expandoColumnLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoTableLocalService(
		ExpandoTableLocalService expandoTableLocalService) {

		_expandoTableLocalService = expandoTableLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {

		_expandoValueLocalService = expandoValueLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private void _checkPermission(ActionRequest actionRequest)
		throws PrincipalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException();
		}
	}

	private boolean _isArchiveException(Exception exception) {
		if (exception instanceof TalendArchiveException ||
			exception instanceof ZipException ||
			(exception.getCause() instanceof ZipException)) {

			return true;
		}

		return false;
	}

	private void _setupExpando(long companyId) throws Exception {
		ExpandoTable table;

		try {
			table = _expandoTableLocalService.addTable(
				companyId, DispatchTrigger.class.getName(),
				"DispatchArchiveFile");
		}
		catch (DuplicateTableNameException duplicateTableNameException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					duplicateTableNameException, duplicateTableNameException);
			}

			table = _expandoTableLocalService.getTable(
				companyId, DispatchTrigger.class.getName(),
				"DispatchArchiveFile");
		}

		try {
			_expandoColumnLocalService.addColumn(
				table.getTableId(), "fileName", ExpandoColumnConstants.STRING);
			_expandoColumnLocalService.addColumn(
				table.getTableId(), "dispatchTriggerId",
				ExpandoColumnConstants.STRING);
		}
		catch (DuplicateColumnNameException duplicateColumnNameException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					duplicateColumnNameException, duplicateColumnNameException);
			}
		}
	}

	private void _updateDispatchTaskSettings(
			long dispatchTriggerId, InputStream jobArchiveInputStream)
		throws PortalException {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.getDispatchTrigger(dispatchTriggerId);

		UnicodeProperties dispatchTaskSettingsUnicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		TalendArchiveParserUtil.updateUnicodeProperties(
			jobArchiveInputStream, dispatchTaskSettingsUnicodeProperties);

		_dispatchTriggerLocalService.updateDispatchTrigger(
			dispatchTriggerId, dispatchTaskSettingsUnicodeProperties,
			dispatchTrigger.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditDispatchTalendJobArchiveMVCActionCommand.class);

	private CompanyLocalService _companyLocalService;

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	private ExpandoColumnLocalService _expandoColumnLocalService;
	private ExpandoTableLocalService _expandoTableLocalService;
	private ExpandoValueLocalService _expandoValueLocalService;

	@Reference
	private Portal _portal;

	private UserLocalService _userLocalService;

}