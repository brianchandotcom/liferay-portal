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

package com.liferay.portal.search.web.internal.Interpreter;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.DDMFormValuesReader;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.web.interpreter.SearchResultInterpreter;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true, service = AssetRendererSearchResultInterpreter.class
)
public class AssetRendererSearchResultInterpreter
	implements SearchResultInterpreter {

	@Override
	public String[] getAssetAvailableLanguageIds(Document document)
		throws Exception {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return null;
		}

		return assetRenderer.getAvailableLanguageIds();
	}

	@Override
	public DDMFormValuesReader getAssetDDMFormValuesReader(Document document)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return null;
		}

		return assetRenderer.getDDMFormValuesReader();
	}

	@Override
	public String getAssetDiscussionPath(Document document)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getDiscussionPath();
	}

	@Override
	public AssetEntry getAssetEntry(Document document) throws PortalException {
		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return null;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getAssetEntry(
			document.getString(Field.ENTRY_CLASS_NAME),
			GetterUtil.getLong(document.getLong(Field.ENTRY_CLASS_PK)));
	}

	@Override
	public AssetEntry getAssetEntry(Document document, long entryId)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return null;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getAssetEntry(entryId);
	}

	@Override
	public String getAssetIconCssClass(Document document)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return null;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getIconCssClass();
	}

	@Override
	public AssetRenderer<?> getAssetRenderer(Document document) {
		AssetRendererFactory<?> assetRendererFactory = _getAssetRendererFactory(
			document);

		if (assetRendererFactory == null) {
			return null;
		}

		try {
			return assetRendererFactory.getAssetRenderer(
				GetterUtil.getLong(document.getLong(Field.ENTRY_CLASS_PK)));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return null;
		}
	}

	@Override
	public String getAssetSearchSummary(Document document, Locale locale)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getSearchSummary(locale);
	}

	@Override
	public int getAssetStatus(Document document) throws PortalException {
		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return -1;
		}

		return assetRenderer.getStatus();
	}

	@Override
	public String getAssetSubtypeTitle(Document document, Locale locale)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getSubtypeTitle(locale);
	}

	@Override
	public String getAssetSummary(Document document) throws PortalException {
		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getSummary();
	}

	@Override
	public String getAssetSummary(
			Document document, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getSummary(portletRequest, portletResponse);
	}

	@Override
	public String getAssetThumbnailPath(
			Document document, PortletRequest portletRequest)
		throws Exception {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getThumbnailPath(portletRequest);
	}

	@Override
	public String getAssetTitle(Document document, Locale locale)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getTitle(locale);
	}

	@Override
	public String getAssetType(Document document) throws PortalException {
		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getType();
	}

	@Override
	public String getAssetTypeName(Document document, Locale locale)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getTypeName(locale);
	}

	@Override
	public String getAssetTypeName(
			Document document, Locale locale, long subtypeId)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getTypeName(locale, subtypeId);
	}

	@Override
	public PortletURL getAssetURLAdd(
			long classTypeId, Document document,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return null;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getURLAdd(
			liferayPortletRequest, liferayPortletResponse, classTypeId);
	}

	@Override
	public PortletURL getAssetURLEdit(
			Document document, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return null;
		}

		return assetRenderer.getURLEdit(
			liferayPortletRequest, liferayPortletResponse);
	}

	@Override
	public PortletURL getAssetURLEdit(
			Document document, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState, PortletURL redirectURL)
		throws Exception {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return null;
		}

		return assetRenderer.getURLEdit(
			liferayPortletRequest, liferayPortletResponse, windowState,
			redirectURL);
	}

	@Override
	public PortletURL getAssetURLExport(
			Document document, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return null;
		}

		return assetRenderer.getURLExport(
			liferayPortletRequest, liferayPortletResponse);
	}

	@Override
	public String getAssetURLImagePreview(
			Document document, PortletRequest portletRequest)
		throws Exception {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getURLImagePreview(portletRequest);
	}

	@Override
	public String getAssetUrlTitle(Document document) throws PortalException {
		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getUrlTitle();
	}

	@Override
	public String getAssetUrlTitle(Document document, Locale locale)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getUrlTitle(locale);
	}

	@Override
	public PortletURL getAssetURLView(
			Document document, LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return null;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);
	}

	@Override
	public String getAssetURLViewInContext(
			Document document, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getURLViewInContext(
			liferayPortletRequest, liferayPortletResponse, noSuchEntryRedirect);
	}

	@Override
	public String getAssetUuid(Document document) throws PortalException {
		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		return assetRenderer.getUuid();
	}

	@Override
	public boolean hasAssetViewPermission(
			Document document, PermissionChecker permissionChecker)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return false;
		}

		return assetRenderer.hasViewPermission(permissionChecker);
	}

	@Override
	public boolean isAssetActive(long companyId, Document document)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return false;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.isActive(companyId);
	}

	@Override
	public boolean isAssetCategorizable(Document document)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return false;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.isCategorizable();
	}

	@Override
	public boolean isAssetDeleted(Document document) throws PortalException {
		boolean deleted = false;

		AssetEntry assetEntry = getAssetEntry(document);

		if (assetEntry == null) {
			deleted = true;
		}
		else {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					document.getString(Field.ENTRY_CLASS_NAME));

			if (trashHandler != null) {
				deleted = trashHandler.isInTrash(
					GetterUtil.getLong(document.getLong(Field.ENTRY_CLASS_PK)));
			}
		}

		return deleted;
	}

	@Override
	public boolean isAssetLinkable(Document document) throws PortalException {
		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return false;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.isLinkable();
	}

	@Override
	public boolean isAssetSearchable(Document document) throws PortalException {
		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return false;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.isSearchable();
	}

	@Override
	public boolean isAssetSelectable(Document document) throws PortalException {
		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return false;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.isSelectable();
	}

	@Override
	public boolean isAssetSupportsClassTypes(Document document)
		throws PortalException {

		AssetRenderer<?> assetRenderer = getAssetRenderer(document);

		if (assetRenderer == null) {
			return false;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		return assetRendererFactory.isSupportsClassTypes();
	}

	private AssetRendererFactory<?> _getAssetRendererFactory(
		Document document) {

		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(
				document.getString(Field.ENTRY_CLASS_NAME));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetRendererSearchResultInterpreter.class);

}