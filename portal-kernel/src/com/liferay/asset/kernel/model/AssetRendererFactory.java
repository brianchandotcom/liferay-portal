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

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 */
@ProviderType
public interface AssetRendererFactory<T> {

	public static final int TYPE_LATEST = 0;

	public static final int TYPE_LATEST_APPROVED = 1;

	public AssetEntry getAssetEntry(long assetEntryId) throws PortalException;

	public AssetEntry getAssetEntry(String classNameId, long classPK)
		throws PortalException;

	public AssetRenderer<T> getAssetRenderer(long classPK)
		throws PortalException;

	public AssetRenderer<T> getAssetRenderer(long classPK, int type)
		throws PortalException;

	public AssetRenderer<T> getAssetRenderer(long groupId, String urlTitle)
		throws PortalException;

	public AssetRenderer<T> getAssetRenderer(T entry, int type)
		throws PortalException;

	public String getClassName();

	public long getClassNameId();

	public ClassTypeReader getClassTypeReader();

	public String getIconCssClass();

	public String getPortletId();

	public String getSubtypeTitle(Locale locale);

	public String getType();

	public String getTypeName(Locale locale);

	public String getTypeName(Locale locale, long subtypeId);

	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classTypeId)
		throws PortalException;

	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws PortalException;

	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception;

	public boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception;

	public boolean isActive(long companyId);

	public boolean isCategorizable();

	public boolean isLinkable();

	public boolean isSearchable();

	public boolean isSelectable();

	public boolean isSupportsClassTypes();

	public void setClassName(String className);

	public void setPortletId(String portletId);

}