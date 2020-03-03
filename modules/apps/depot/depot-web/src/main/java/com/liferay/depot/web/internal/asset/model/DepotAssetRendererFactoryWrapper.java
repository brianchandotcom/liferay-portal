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
import com.liferay.asset.util.AssetRendererFactoryWrapper;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GroupThreadLocal;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Adolfo Pérez
 */
public class DepotAssetRendererFactoryWrapper<T>
	implements AssetRendererFactoryWrapper<T> {

	public DepotAssetRendererFactoryWrapper(
		AssetRendererFactory assetRendererFactory,
		DepotApplicationController depotApplicationController,
		DepotEntryLocalService depotEntryLocalService,
		GroupLocalService groupLocalService) {

		_assetRendererFactory = assetRendererFactory;
		_depotApplicationController = depotApplicationController;
		_depotEntryLocalService = depotEntryLocalService;
		_groupLocalService = groupLocalService;
	}

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
	public String getClassSimpleName() {
		return getAssetRendererFactory().getClassSimpleName();
	}

	@Override
	public ClassTypeReader getClassTypeReader() {
		if (isSelectable()) {
			return new DepotClassTypeReader(
				_assetRendererFactory.getClassTypeReader(),
				_depotEntryLocalService);
		}

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
	public Class<? extends AssetRendererFactory> getWrappedClass() {
		return getAssetRendererFactory().getClass();
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
		Group group = _getGroup();

		if ((group != null) && (group.getType() == GroupConstants.TYPE_DEPOT) &&
			!_depotApplicationController.isClassNameEnabled(
				getClassName(), group.getGroupId())) {

			return false;
		}

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

	protected AssetRendererFactory getAssetRendererFactory() {
		return _assetRendererFactory;
	}

	private Group _getGroup() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return _groupLocalService.fetchGroup(GroupThreadLocal.getGroupId());
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if (themeDisplay != null) {
			return themeDisplay.getScopeGroup();
		}

		return _groupLocalService.fetchGroup(serviceContext.getScopeGroupId());
	}

	private final AssetRendererFactory _assetRendererFactory;
	private final DepotApplicationController _depotApplicationController;
	private final DepotEntryLocalService _depotEntryLocalService;
	private final GroupLocalService _groupLocalService;

}