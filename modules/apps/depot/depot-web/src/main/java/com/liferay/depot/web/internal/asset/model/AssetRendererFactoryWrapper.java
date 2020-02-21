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

package com.liferay.depot.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Adolfo Pérez
 */
public abstract class AssetRendererFactoryWrapper<T>
	implements AssetRendererFactory<T> {

	@Override
	public AssetEntry getAssetEntry(long assetEntryId) throws PortalException {
		return getAssetRendererFactory().getAssetEntry(assetEntryId);
	}

	@Override
	public AssetEntry getAssetEntry(String classNameId, long classPK)
		throws PortalException {

		return getAssetRendererFactory().getAssetEntry(classNameId, classPK);
	}

	@Override
	public AssetRenderer<T> getAssetRenderer(long classPK)
		throws PortalException {

		return getAssetRendererFactory().getAssetRenderer(classPK);
	}

	@Override
	public AssetRenderer<T> getAssetRenderer(long classPK, int type)
		throws PortalException {

		return getAssetRendererFactory().getAssetRenderer(classPK, type);
	}

	@Override
	public AssetRenderer<T> getAssetRenderer(long groupId, String urlTitle)
		throws PortalException {

		return getAssetRendererFactory().getAssetRenderer(groupId, urlTitle);
	}

	@Override
	public AssetRenderer<T> getAssetRenderer(T entry, int type)
		throws PortalException {

		return getAssetRendererFactory().getAssetRenderer(entry, type);
	}

	@Override
	public String getClassName() {
		return getAssetRendererFactory().getClassName();
	}

	@Override
	public long getClassNameId() {
		return getAssetRendererFactory().getClassNameId();
	}

	@Override
	public ClassTypeReader getClassTypeReader() {
		return getAssetRendererFactory().getClassTypeReader();
	}

	@Override
	public String getIconCssClass() {
		return getAssetRendererFactory().getIconCssClass();
	}

	@Override
	public String getPortletId() {
		return getAssetRendererFactory().getPortletId();
	}

	@Override
	public String getSubtypeTitle(Locale locale) {
		return getAssetRendererFactory().getSubtypeTitle(locale);
	}

	@Override
	public String getType() {
		return getAssetRendererFactory().getType();
	}

	@Override
	public String getTypeName(Locale locale) {
		return getAssetRendererFactory().getTypeName(locale);
	}

	@Override
	public String getTypeName(Locale locale, long subtypeId) {
		return getAssetRendererFactory().getTypeName(locale, subtypeId);
	}

	@Override
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classTypeId)
		throws PortalException {

		return getAssetRendererFactory().getURLAdd(
			liferayPortletRequest, liferayPortletResponse, classTypeId);
	}

	@Override
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws PortalException {

		return getAssetRendererFactory().getURLView(
			liferayPortletResponse, windowState);
	}

	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		return getAssetRendererFactory().hasAddPermission(
			permissionChecker, groupId, classTypeId);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception {

		return getAssetRendererFactory().hasPermission(
			permissionChecker, entryClassPK, actionId);
	}

	@Override
	public boolean isActive(long companyId) {
		return getAssetRendererFactory().isActive(companyId);
	}

	@Override
	public boolean isCategorizable() {
		return getAssetRendererFactory().isCategorizable();
	}

	@Override
	public boolean isLinkable() {
		return getAssetRendererFactory().isLinkable();
	}

	@Override
	public boolean isSearchable() {
		return getAssetRendererFactory().isSearchable();
	}

	@Override
	public boolean isSelectable() {
		return getAssetRendererFactory().isSelectable();
	}

	@Override
	public boolean isSupportsClassTypes() {
		return getAssetRendererFactory().isSupportsClassTypes();
	}

	@Override
	public void setClassName(String className) {
		getAssetRendererFactory().setClassName(className);
	}

	@Override
	public void setPortletId(String portletId) {
		getAssetRendererFactory().setPortletId(portletId);
	}

	protected abstract AssetRendererFactory<T> getAssetRendererFactory();

}