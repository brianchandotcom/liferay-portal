/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.portlet.toolbar.contributor;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveManager;
import com.liferay.document.library.portlet.toolbar.contributor.DLPortletToolbarContributorContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = DLPortletToolbarContributorContext.class)
public class DLOpenerOneDriveDLPortletToolbarContributorContext
	implements DLPortletToolbarContributorContext {

	@Override
	public void updatePortletTitleMenuItems(
		List<MenuItem> menuItems, Folder folder, ThemeDisplay themeDisplay,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		try {
			long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			if (folder != null) {
				folderId = folder.getFolderId();
			}

			if (!_dlOpenerOneDriveManager.isConfigured(
					themeDisplay.getCompanyId()) ||
				!ModelResourcePermissionHelper.contains(
					_folderEntryModelResourcePermission,
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroupId(), folderId,
					ActionKeys.ADD_DOCUMENT)) {

				return;
			}

			menuItems.add(
				_createURLMenuItem(
					portletRequest, folder, "onedrive-word",
					DLOpenerMimeTypes.APPLICATION_VND_DOCX, _ICON_NAME_DOCUMENT,
					_ICON_COLOR_DOCUMENT,
					_translate(portletRequest, "create-word-document")));
			menuItems.add(
				_createURLMenuItem(
					portletRequest, folder, "onedrive-powerpoint",
					DLOpenerMimeTypes.APPLICATION_VND_PPTX,
					_ICON_NAME_PRESENTATION, _ICON_COLOR_PRESENTATION,
					_translate(portletRequest, "create-powerpoint-document")));
			menuItems.add(
				_createURLMenuItem(
					portletRequest, folder, "onedrive-excel",
					DLOpenerMimeTypes.APPLICATION_VND_XLSX,
					_ICON_NAME_SPREADSHEET, _ICON_COLOR_SPREADSHEET,
					_translate(portletRequest, "create-excel-document")));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private URLMenuItem _createURLMenuItem(
		PortletRequest portletRequest, Folder folder, String key,
		String contentType, String icon, String iconColor, String modalTitle) {

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setData(
			Collections.singletonMap("file-icon-color", iconColor));
		urlMenuItem.setIcon(icon);
		urlMenuItem.setLabel(_translate(portletRequest, key));
		urlMenuItem.setMethod(HttpMethods.POST);
		urlMenuItem.setURL(
			StringBundler.concat(
				"javascript:",
				_portal.getPortletNamespace(
					_portal.getPortletId(portletRequest)),
				"openCreateOfficeDocument('",
				_getActionURL(portletRequest, folder, contentType),
				StringPool.APOSTROPHE, StringPool.COMMA, StringPool.APOSTROPHE,
				modalTitle, "');"));

		return urlMenuItem;
	}

	private String _getActionURL(
		PortletRequest portletRequest, Folder folder, String contentType) {

		try {
			LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
				portletRequest, _portal.getPortletId(portletRequest),
				PortletRequest.ACTION_PHASE);

			liferayPortletURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/document_library/create_in_office365");
			liferayPortletURL.setParameter(Constants.CMD, Constants.ADD);

			long repositoryId = BeanPropertiesUtil.getLong(
				folder, "repositoryId",
				_portal.getScopeGroupId(portletRequest));

			liferayPortletURL.setParameter(
				"repositoryId", String.valueOf(repositoryId));

			long folderId = BeanPropertiesUtil.getLong(
				folder, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			liferayPortletURL.setParameter(
				"folderId", String.valueOf(folderId));

			liferayPortletURL.setParameter("contentType", contentType);

			return liferayPortletURL.toString();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private String _translate(PortletRequest portletRequest, String key) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_portal.getLocale(portletRequest),
			DLOpenerOneDriveDLPortletToolbarContributorContext.class);

		return _language.get(resourceBundle, key);
	}

	private static final String _ICON_COLOR_DOCUMENT = "6";

	private static final String _ICON_COLOR_PRESENTATION = "4";

	private static final String _ICON_COLOR_SPREADSHEET = "2";

	private static final String _ICON_NAME_DOCUMENT = "document-text";

	private static final String _ICON_NAME_PRESENTATION =
		"document-presentation";

	private static final String _ICON_NAME_SPREADSHEET = "document-table";

	private static final Log _log = LogFactoryUtil.getLog(
		DLOpenerOneDriveDLPortletToolbarContributorContext.class);

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	private ModelResourcePermission<Folder> _folderEntryModelResourcePermission;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}