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
import com.liferay.dispatch.talend.web.internal.archive.TalendArchive;
import com.liferay.dispatch.talend.web.internal.archive.TalendArchiveParserUtil;
import com.liferay.dispatch.talend.web.internal.archive.exception.TalendArchiveException;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Properties;
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

			SessionErrors.add(actionRequest, TalendArchiveException.class);

			sendRedirect(
				actionRequest, actionResponse,
				ParamUtil.getString(actionRequest, "redirect"));
		}
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

	private String _getJVMOptions(String newJVMOptions, String oldJVMOptions) {
		String[] jvmOptions = newJVMOptions.split("\\s");

		StringBundler sb = new StringBundler((jvmOptions.length * 2) + 1);

		for (String newJVMOption : jvmOptions) {
			if (oldJVMOptions.contains(newJVMOption)) {
				continue;
			}

			sb.append(newJVMOption);

			sb.append(StringPool.SPACE);
		}

		sb.append(oldJVMOptions);

		return sb.toString();
	}

	private boolean _isArchiveException(Exception exception) {
		if (exception instanceof TalendArchiveException ||
			exception instanceof ZipException ||
			(exception.getCause() instanceof ZipException)) {

			return true;
		}

		return false;
	}

	private void _updateDispatchTaskSettings(
			long dispatchTriggerId, InputStream jobArchiveInputStream)
		throws PortalException {

		TalendArchive talendArchive = TalendArchiveParserUtil.parse(
			jobArchiveInputStream);

		if (!talendArchive.hasJVMOptions()) {
			return;
		}

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.getDispatchTrigger(dispatchTriggerId);

		UnicodeProperties dispatchTaskSettingsUnicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		String newJVMOptions = talendArchive.getJVMOptions();

		String oldJVMOptions =
			dispatchTaskSettingsUnicodeProperties.getProperty("JAVA_OPTS");

		if (Validator.isNotNull(oldJVMOptions)) {
			newJVMOptions = _getJVMOptions(newJVMOptions, oldJVMOptions);
		}

		dispatchTaskSettingsUnicodeProperties.put("JAVA_OPTS", newJVMOptions);

		Properties contextProperties = talendArchive.getContextProperties();

		for (String propertyName : contextProperties.stringPropertyNames()) {
			if (dispatchTaskSettingsUnicodeProperties.containsKey(
					propertyName)) {

				continue;
			}

			dispatchTaskSettingsUnicodeProperties.put(
				propertyName,
				contextProperties.getProperty(propertyName) +
					" (Automatic Copy)");
		}

		_dispatchTriggerLocalService.updateDispatchTrigger(
			dispatchTriggerId, dispatchTaskSettingsUnicodeProperties,
			dispatchTrigger.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditDispatchTalendJobArchiveMVCActionCommand.class);

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private Portal _portal;

}