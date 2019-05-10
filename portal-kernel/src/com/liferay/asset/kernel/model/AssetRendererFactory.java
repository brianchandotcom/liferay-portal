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
import com.liferay.portal.kernel.util.Tuple;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
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

	/**
	 * @deprecated As of Wilberforce (7.0.x), see {@link
	 *             com.liferay.portlet.asset.model.ClassTypeReader}
	 */
	@Deprecated
	public Tuple getClassTypeFieldName(
			long classTypeId, String fieldName, Locale locale)
		throws Exception;

	/**
	 * @deprecated As of Wilberforce (7.0.x), see {@link
	 *             com.liferay.portlet.asset.model.ClassTypeReader}
	 */
	@Deprecated
	public List<Tuple> getClassTypeFieldNames(
			long classTypeId, Locale locale, int start, int end)
		throws Exception;

	/**
	 * @deprecated As of Wilberforce (7.0.x), see {@link
	 *             com.liferay.portlet.asset.model.ClassTypeReader}
	 */
	@Deprecated
	public int getClassTypeFieldNamesCount(long classTypeId, Locale locale)
		throws Exception;

	public ClassTypeReader getClassTypeReader();

	/**
	 * @deprecated As of Wilberforce (7.0.x), see {@link
	 *             com.liferay.portlet.asset.model.ClassTypeReader}
	 */
	@Deprecated
	public Map<Long, String> getClassTypes(long[] groupIds, Locale locale)
		throws Exception;

	public String getIconCssClass();

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public String getIconPath(PortletRequest portletRequest);

	public String getPortletId();

	public String getSubtypeTitle(Locale locale);

	public String getType();

	public String getTypeName(Locale locale);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getTypeName(Locale)}
	 */
	@Deprecated
	public String getTypeName(Locale locale, boolean hasSubtypes);

	public String getTypeName(Locale locale, long subtypeId);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getURLAdd(LiferayPortletRequest, LiferayPortletResponse,
	 *             long)}
	 */
	@Deprecated
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException;

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

	/**
	 * @deprecated As of Wilberforce (7.0.x), see {@link
	 *             com.liferay.portlet.asset.model.ClassTypeReader}
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

	public boolean isSearchable();

	public boolean isSelectable();

	public boolean isSupportsClassTypes();

	public void setClassName(String className);

	public void setPortletId(String portletId);

}