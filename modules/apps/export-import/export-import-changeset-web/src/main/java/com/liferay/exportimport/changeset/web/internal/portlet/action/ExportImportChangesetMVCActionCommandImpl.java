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

package com.liferay.exportimport.changeset.web.internal.portlet.action;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.ChangesetManager;
import com.liferay.exportimport.changeset.constants.ChangesetConstants;
import com.liferay.exportimport.changeset.constants.ChangesetPortletKeys;
import com.liferay.exportimport.changeset.exception.ExportImportEntityException;
import com.liferay.exportimport.changeset.portlet.action.ExportImportChangesetMVCActionCommand;
import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.exportimport.kernel.staging.StagingURLHelper;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.http.LayoutServiceHttp;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ChangesetPortletKeys.CHANGESET,
		"mvc.command.name=/export_import_changeset/export_import_changeset"
	},
	service = {
		ExportImportChangesetMVCActionCommand.class, MVCActionCommand.class
	}
)
public class ExportImportChangesetMVCActionCommandImpl
	extends BaseMVCActionCommand
	implements ExportImportChangesetMVCActionCommand {

	@Override
	public void processExportAction(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Changeset changeset)
		throws Exception {

		_changesetManager.addChangeset(changeset);

		_processExportAndPublishAction(
			actionRequest, actionResponse, Constants.EXPORT,
			changeset.getUuid());
	}

	@Override
	public void processPublishAction(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Changeset changeset)
		throws Exception {

		_changesetManager.addChangeset(changeset);

		_processExportAndPublishAction(
			actionRequest, actionResponse, Constants.PUBLISH,
			changeset.getUuid());
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, "cmd");

		if (cmd.equals(Constants.EXPORT) || cmd.equals(Constants.PUBLISH) ||
			cmd.equals(ChangesetConstants.PUBLISH_CHANGESET)) {

			_processExportAndPublishAction(
				actionRequest, actionResponse, cmd, null);
		}
		else {
			SessionErrors.add(
				actionRequest, ExportImportEntityException.class,
				new ExportImportEntityException(
					ExportImportEntityException.TYPE_INVALID_COMMAND));
		}
	}

	private void _processExportAndPublishAction(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String cmd, String changesetUuid)
		throws Exception {

		if (Validator.isNotNull(actionRequest.getParameter("changesetUuid"))) {
			changesetUuid = ParamUtil.getString(actionRequest, "changesetUuid");
		}
		else if (Validator.isNull(changesetUuid)) {
			SessionErrors.add(
				actionRequest, ExportImportEntityException.class,
				new ExportImportEntityException(
					ExportImportEntityException.TYPE_NO_DATA_FOUND));

			return;
		}

		Map<String, String[]> parameterMap =
			_exportImportConfigurationParameterMapFactory.buildParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put("changesetUuid", new String[] {changesetUuid});

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletId = MapUtil.getString(
			actionRequest.getParameterMap(), "portletId");

		if (Validator.isNull(portletId)) {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			portletId = portletDisplay.getId();
		}

		long backgroundTaskId = 0;

		if (cmd.equals(Constants.EXPORT)) {
			Portlet portlet = _portletLocalService.getPortletById(portletId);

			Map<String, Serializable> settingsMap =
				_exportImportConfigurationSettingsMapFactory.
					buildExportPortletSettingsMap(
						themeDisplay.getUser(), themeDisplay.getPlid(),
						themeDisplay.getScopeGroupId(),
						ChangesetPortletKeys.CHANGESET, parameterMap,
						_exportImportHelper.getPortletExportFileName(portlet));

			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						themeDisplay.getUserId(), portletId,
						ExportImportConfigurationConstants.TYPE_EXPORT_PORTLET,
						settingsMap);

			backgroundTaskId =
				_exportImportLocalService.exportPortletInfoAsFileInBackground(
					themeDisplay.getUserId(), exportImportConfiguration);
		}
		else if (cmd.equals(Constants.PUBLISH) ||
				 cmd.equals(ChangesetConstants.PUBLISH_CHANGESET)) {

			Group scopeGroup = _groupLocalService.fetchGroup(
				ParamUtil.getLong(actionRequest, "groupId"));

			if (scopeGroup == null) {
				scopeGroup = themeDisplay.getScopeGroup();
			}

			if (!scopeGroup.isStagingGroup() &&
				!scopeGroup.isStagedRemotely()) {

				SessionErrors.add(
					actionRequest, ExportImportEntityException.class,
					new ExportImportEntityException(
						ExportImportEntityException.TYPE_GROUP_NOT_STAGED));

				return;
			}

			if (!scopeGroup.isStagedPortlet(portletId)) {
				SessionErrors.add(
					actionRequest, ExportImportEntityException.class,
					new ExportImportEntityException(
						ExportImportEntityException.TYPE_PORTLET_NOT_STAGED));

				return;
			}

			int type =
				ExportImportConfigurationConstants.TYPE_PUBLISH_PORTLET_LOCAL;

			if (scopeGroup.isStagedRemotely()) {
				type =
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_PORTLET_REMOTE;
			}

			long liveGroupId = 0;
			long targetPlid = themeDisplay.getPlid();

			if (scopeGroup.isStagingGroup()) {
				liveGroupId = scopeGroup.getLiveGroupId();
			}
			else if (scopeGroup.isStagedRemotely()) {
				liveGroupId = scopeGroup.getRemoteLiveGroupId();

				PermissionChecker permissionChecker =
					PermissionThreadLocal.getPermissionChecker();

				User user = permissionChecker.getUser();

				Group stagingGroup = scopeGroup;

				if (scopeGroup.isLayout()) {
					stagingGroup = scopeGroup.getParentGroup();
				}

				HttpPrincipal httpPrincipal = new HttpPrincipal(
					_stagingURLHelper.buildRemoteURL(
						stagingGroup.getTypeSettingsProperties()),
					user.getLogin(), user.getPassword(),
					user.isPasswordEncrypted());

				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader =
					currentThread.getContextClassLoader();

				try {
					currentThread.setContextClassLoader(
						PortalClassLoaderUtil.getClassLoader());

					targetPlid = LayoutServiceHttp.getControlPanelLayoutPlid(
						httpPrincipal);
				}
				finally {
					currentThread.setContextClassLoader(contextClassLoader);
				}
			}

			Map<String, Serializable> settingsMap =
				_exportImportConfigurationSettingsMapFactory.
					buildPublishPortletSettingsMap(
						themeDisplay.getUser(), scopeGroup.getGroupId(),
						themeDisplay.getPlid(), liveGroupId, targetPlid,
						ChangesetPortletKeys.CHANGESET, parameterMap);

			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						themeDisplay.getUserId(), portletId, type, settingsMap);

			backgroundTaskId = _staging.publishPortlet(
				themeDisplay.getUserId(), exportImportConfiguration);
		}

		_sendRedirect(actionRequest, actionResponse, backgroundTaskId);
	}

	private void _sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			long backgroundTaskId)
		throws Exception {

		actionRequest.setAttribute(
			WebKeys.REDIRECT,
			PortletURLBuilder.createRenderURL(
				_portal.getLiferayPortletResponse(actionResponse),
				ExportImportPortletKeys.EXPORT_IMPORT
			).setMVCPath(
				"/view_export_import.jsp"
			).setBackURL(
				actionRequest.getParameter("backURL")
			).setParameter(
				"backgroundTaskId", backgroundTaskId
			).buildString());

		hideDefaultSuccessMessage(actionRequest);

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private ChangesetManager _changesetManager;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportConfigurationParameterMapFactory
		_exportImportConfigurationParameterMapFactory;

	@Reference
	private ExportImportConfigurationSettingsMapFactory
		_exportImportConfigurationSettingsMapFactory;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private Staging _staging;

	@Reference
	private StagingURLHelper _stagingURLHelper;

}