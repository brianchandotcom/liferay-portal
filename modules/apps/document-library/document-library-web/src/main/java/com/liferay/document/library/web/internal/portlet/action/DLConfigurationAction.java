/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.web.internal.display.context.DLAdminDisplayContext;
import com.liferay.document.library.web.internal.display.context.DLAdminDisplayContextProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = "javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
	service = ConfigurationAction.class
)
public class DLConfigurationAction
	extends BaseValidateRootFolderConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/document_library/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			DLAdminDisplayContext.class.getName(),
			_dlAdminDisplayContextProvider.getDLAdminDisplayContext(
				httpServletRequest, httpServletResponse));
		httpServletRequest.setAttribute(
			ItemSelector.class.getName(), _itemSelector);

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Override
	protected void postProcess(
			long companyId, PortletRequest portletRequest, Settings settings)
		throws PortalException {

		PortletPreferencesSettings portletPreferencesSettings =
			(PortletPreferencesSettings)settings.getParentSettings();

		PortletPreferences portletPreferences =
			portletPreferencesSettings.getPortletPreferences();

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		long selectedRepositoryId = GetterUtil.getLong(
			portletPreferences.getValue("selectedRepositoryId", null));

		try {
			_setPortletPreferences(
				portletPreferences, rootFolderId, selectedRepositoryId);
		}
		catch (ReadOnlyException readOnlyException) {
			throw new SystemException(readOnlyException);
		}
	}

	@Override
	protected void validate(ActionRequest actionRequest)
		throws PortalException {

		_validateDisplayStyleViews(actionRequest);

		super.validate(actionRequest);
	}

	private void _setPortletPreferences(
			PortletPreferences portletPreferences, long rootFolderId,
			long selectedRepositoryId)
		throws PortalException, ReadOnlyException {

		String rootFolderExternalReferenceCode = StringPool.BLANK;

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = _dlAppLocalService.getFolder(rootFolderId);

			rootFolderExternalReferenceCode = folder.getExternalReferenceCode();
		}

		portletPreferences.setValue(
			"rootFolderExternalReferenceCode", rootFolderExternalReferenceCode);

		Group selectedGroup = null;
		String selectedRepositoryExternalReferenceCode = StringPool.BLANK;

		Repository selectedRepository = _repositoryLocalService.fetchRepository(
			selectedRepositoryId);

		if (selectedRepository == null) {
			selectedGroup = _groupLocalService.getGroup(selectedRepositoryId);
		}
		else {
			selectedGroup = _groupLocalService.getGroup(
				selectedRepository.getGroupId());
			selectedRepositoryExternalReferenceCode =
				selectedRepository.getExternalReferenceCode();
		}

		portletPreferences.setValue(
			"selectedGroupExternalReferenceCode",
			selectedGroup.getExternalReferenceCode());
		portletPreferences.setValue(
			"selectedRepositoryExternalReferenceCode",
			selectedRepositoryExternalReferenceCode);

		portletPreferences.reset("rootFolderId");
		portletPreferences.reset("selectedRepositoryId");
	}

	private void _validateDisplayStyleViews(ActionRequest actionRequest) {
		String displayViews = GetterUtil.getString(
			getParameter(actionRequest, "displayViews"));

		if (Validator.isNull(displayViews)) {
			SessionErrors.add(actionRequest, "displayViewsInvalid");
		}
	}

	@Reference
	private DLAdminDisplayContextProvider _dlAdminDisplayContextProvider;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

}