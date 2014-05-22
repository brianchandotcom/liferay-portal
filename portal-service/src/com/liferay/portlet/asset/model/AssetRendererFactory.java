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
import javax.portlet.WindowState;

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

	/**
	 * @deprecated As of 7.0.0, see {@link com.liferay.portlet.asset.model.ClassTypeReader}
	 */
	@Deprecated
	public Tuple getClassTypeFieldName(
			long classTypeId, String fieldName, Locale locale)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, see {@link com.liferay.portlet.asset.model.ClassTypeReader}
	 */
	@Deprecated
	public List<Tuple> getClassTypeFieldNames(
			long classTypeId, Locale locale, int start, int end)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, see {@link com.liferay.portlet.asset.model.ClassTypeReader}
	 */
	@Deprecated
	public int getClassTypeFieldNamesCount(long classTypeId, Locale locale)
		throws Exception;

	public ClassTypeReader getClassTypeReader();

	/**
	 * @deprecated As of 7.0.0, see {@link com.liferay.portlet.asset.model.ClassTypeReader}
	 */
	@Deprecated
	public Map<Long, String> getClassTypes(long[] groupIds, Locale locale)
		throws Exception;

	public String getIconCssClass();

	public String getIconPath(PortletRequest portletRequest);

	public String getPortletId();

	public String getType();

	public String getTypeName(Locale locale);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getTypeName(Locale)}
	 */
	@Deprecated
	public String getTypeName(Locale locale, boolean hasSubtypes);

	public String getTypeName(Locale locale, long subtypeId);

	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException, SystemException;

	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws PortalException, SystemException;

	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, see {@link com.liferay.portlet.asset.model.ClassTypeReader}
	 */
	@Deprecated
	public boolean hasClassTypeFieldNames(long classTypeId, Locale locale)
		throws Exception;

	public boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception;

	public boolean isActive(long companyId);

	public boolean isCategorizable();

	public boolean isLinkable();

	public boolean isSelectable();

	public boolean isSupportsClassTypes();

	public void setClassName(String className);

	public void setPortletId(String portletId);

}