/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 */
public interface AssetRendererFactory {

	public static final int TYPE_LATEST = 0;

	public static final int TYPE_LATEST_APPROVED = 1;

	public AssetEntry getAssetEntry(long assetEntryId)
		throws PortalException, SystemException;

	public AssetEntry getAssetEntry(String classNameId, long classPK)
		throws PortalException, SystemException;

	public AssetRenderer getAssetRenderer(long classPK)
		throws PortalException, SystemException;

	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException, SystemException;

	public AssetRenderer getAssetRenderer(long groupId, String urlTitle)
		throws PortalException, SystemException;

	public String getClassName();

	public long getClassNameId();

	public List<Tuple> getClassTypeFieldNames(
			long classTypeId, Locale locale, int start, int end)
		throws Exception;

	public int getClassTypeFieldNamesCount(long classTypeId, Locale locale)
		throws Exception;

	public Map<Long, String> getClassTypes(long[] groupIds, Locale locale)
		throws Exception;

	public String getIconPath(PortletRequest portletRequest);

	public String getPortletId();

	public String getType();

	public String getTypeName(Locale locale, boolean hasSubtypes);

	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException, SystemException;

	public boolean hasClassTypeFieldNames(long classTypeId, Locale locale)
		throws Exception;

	public boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception;

	public boolean isCategorizable();

	public boolean isLinkable();

	public boolean isSelectable();

	public void setClassName(String className);

	public void setPortletId(String portletId);

}