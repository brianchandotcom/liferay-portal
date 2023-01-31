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

package com.liferay.asset.test.util.asset.renderer.factory;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gustavo Lima
 */
@Component(
	property = {"company.id=1", "service.ranking:Integer=" + Integer.MAX_VALUE},
	service = AssetRendererFactory.class
)
public class TestAssetRendererObjectDefinitionFactory
	implements AssetRendererFactory<TestAssetRendererModelObjectDefinition> {

	@Override
	public AssetEntry getAssetEntry(long assetEntryId) throws PortalException {
		return null;
	}

	@Override
	public AssetEntry getAssetEntry(String classNameId, long classPK)
		throws PortalException {

		return null;
	}

	@Override
	public AssetRenderer<TestAssetRendererModelObjectDefinition>
			getAssetRenderer(long classPK)
		throws PortalException {

		return null;
	}

	@Override
	public AssetRenderer<TestAssetRendererModelObjectDefinition>
			getAssetRenderer(long classPK, int type)
		throws PortalException {

		return null;
	}

	@Override
	public AssetRenderer<TestAssetRendererModelObjectDefinition>
			getAssetRenderer(long groupId, String urlTitle)
		throws PortalException {

		return null;
	}

	@Override
	public AssetRenderer<TestAssetRendererModelObjectDefinition>
			getAssetRenderer(
				TestAssetRendererModelObjectDefinition entry, int type)
		throws PortalException {

		return null;
	}

	@Override
	public String getClassName() {
		return "com.liferay.object.model.ObjectDefinition#12345";
	}

	@Override
	public long getClassNameId() {
		return 1234568756;
	}

	@Override
	public ClassTypeReader getClassTypeReader() {
		return null;
	}

	@Override
	public String getIconCssClass() {
		return null;
	}

	@Override
	public String getPortletId() {
		return null;
	}

	@Override
	public String getSubtypeTitle(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getType() {
		return "com.liferay.object.model.ObjectDefinition#12345";
	}

	@Override
	public String getTypeName(Locale locale) {
		return null;
	}

	@Override
	public String getTypeName(Locale locale, long subtypeId) {
		return null;
	}

	@Override
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classTypeId)
		throws PortalException {

		return null;
	}

	@Override
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws PortalException {

		return null;
	}

	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		return false;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception {

		return false;
	}

	@Override
	public boolean isActive(long companyId) {
		if ((companyId == 1) || (companyId == 2)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isCategorizable() {
		return false;
	}

	@Override
	public boolean isLinkable() {
		return false;
	}

	@Override
	public boolean isSearchable() {
		return false;
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	@Override
	public boolean isSupportsClassTypes() {
		return false;
	}

	@Override
	public void setClassName(String className) {
	}

	@Override
	public void setPortletId(String portletId) {
	}

}