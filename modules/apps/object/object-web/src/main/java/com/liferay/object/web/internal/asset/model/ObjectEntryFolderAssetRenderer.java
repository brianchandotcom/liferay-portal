/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.asset.model;

import com.liferay.asset.kernel.model.BaseAssetRenderer;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mikel Lorza
 */
public class ObjectEntryFolderAssetRenderer
	extends BaseAssetRenderer<ObjectEntryFolder> {

	public ObjectEntryFolderAssetRenderer(
		ObjectEntryFolder objectEntryFolder,
		ObjectEntryFolderAssetRendererFactory
			objectEntryFolderAssetRendererFactory) {

		_objectEntryFolder = objectEntryFolder;
		_objectEntryFolderAssetRendererFactory =
			objectEntryFolderAssetRendererFactory;
	}

	@Override
	public ObjectEntryFolder getAssetObject() {
		return _objectEntryFolder;
	}

	@Override
	public String getClassName() {
		return ObjectEntryFolder.class.getName();
	}

	@Override
	public long getClassPK() {
		return _objectEntryFolder.getObjectEntryFolderId();
	}

	@Override
	public long getGroupId() {
		return _objectEntryFolder.getGroupId();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _objectEntryFolder.getLabel(locale);
	}

	@Override
	public long getUserId() {
		return _objectEntryFolder.getUserId();
	}

	@Override
	public String getUserName() {
		return _objectEntryFolder.getUserName();
	}

	@Override
	public String getUuid() {
		return _objectEntryFolder.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return _hasPermission(permissionChecker, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return _hasPermission(permissionChecker, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String template) {

		return false;
	}

	@Override
	public boolean isDisplayable() {
		return false;
	}

	private boolean _hasPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		try {
			return _objectEntryFolderAssetRendererFactory.hasPermission(
				permissionChecker, _objectEntryFolder.getObjectEntryFolderId(),
				actionId);
		}
		catch (PortalException | RuntimeException exception) {
			throw exception;
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private final ObjectEntryFolder _objectEntryFolder;
	private final ObjectEntryFolderAssetRendererFactory
		_objectEntryFolderAssetRendererFactory;

}