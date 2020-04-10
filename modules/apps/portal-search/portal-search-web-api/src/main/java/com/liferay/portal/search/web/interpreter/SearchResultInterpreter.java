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

package com.liferay.portal.search.web.interpreter;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.DDMFormValuesReader;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.search.document.Document;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Wade Cao
 */
@ProviderType
public interface SearchResultInterpreter {

	public String[] getAssetAvailableLanguageIds(Document document)
		throws Exception;

	public DDMFormValuesReader getAssetDDMFormValuesReader(Document document)
		throws PortalException;

	public String getAssetDiscussionPath(Document document)
		throws PortalException;

	public AssetEntry getAssetEntry(Document document) throws PortalException;

	public AssetEntry getAssetEntry(Document document, long entryId)
		throws PortalException;

	public String getAssetIconCssClass(Document document)
		throws PortalException;

	public AssetRenderer<?> getAssetRenderer(Document document)
		throws PortalException;

	public String getAssetSearchSummary(Document document, Locale locale)
		throws PortalException;

	public int getAssetStatus(Document document) throws PortalException;

	public String getAssetSubtypeTitle(Document document, Locale locale)
		throws PortalException;

	public String getAssetSummary(Document document) throws PortalException;

	public String getAssetSummary(
			Document document, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws PortalException;

	public String getAssetThumbnailPath(
			Document document, PortletRequest portletRequest)
		throws Exception;

	public String getAssetTitle(Document document, Locale locale)
		throws PortalException;

	public String getAssetType(Document document) throws PortalException;

	public String getAssetTypeName(Document document, Locale locale)
		throws PortalException;

	public String getAssetTypeName(
			Document document, Locale locale, long subtypeId)
		throws PortalException;

	public PortletURL getAssetURLAdd(
			long classTypeId, Document document,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException;

	public PortletURL getAssetURLEdit(
			Document document, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public PortletURL getAssetURLEdit(
			Document document, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState, PortletURL redirectURL)
		throws Exception;

	public PortletURL getAssetURLExport(
			Document document, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public String getAssetURLImagePreview(
			Document document, PortletRequest portletRequest)
		throws Exception;

	public String getAssetUrlTitle(Document document) throws PortalException;

	public String getAssetUrlTitle(Document document, Locale locale)
		throws PortalException;

	public PortletURL getAssetURLView(
			Document document, LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws PortalException;

	public String getAssetURLViewInContext(
			Document document, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception;

	public String getAssetUuid(Document document) throws PortalException;

	public boolean hasAssetViewPermission(
			Document document, PermissionChecker permissionChecker)
		throws PortalException;

	public boolean isAssetActive(long companyId, Document document)
		throws PortalException;

	public boolean isAssetCategorizable(Document document)
		throws PortalException;

	public boolean isAssetDeleted(Document document) throws PortalException;

	public boolean isAssetLinkable(Document document) throws PortalException;

	public boolean isAssetSearchable(Document document) throws PortalException;

	public boolean isAssetSelectable(Document document) throws PortalException;

	public boolean isAssetSupportsClassTypes(Document document)
		throws PortalException;

}