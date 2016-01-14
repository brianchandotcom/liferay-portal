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

package com.liferay.portlet.asset.bundle.assetrendererfactoryregistryutil;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.ClassTypeReader;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestAssetRendererFactory
	implements AssetRendererFactory<TestAssetRendererModel> {

	@Override
	public AssetEntry getAssetEntry(long assetEntryId) {
		return null;
	}

	@Override
	public AssetEntry getAssetEntry(String classNameId, long classPK) {
		return null;
	}

	@Override
	public AssetRenderer<TestAssetRendererModel> getAssetRenderer(
		long classPK) {

		return null;
	}

	@Override
	public AssetRenderer<TestAssetRendererModel> getAssetRenderer(
		long classPK, int type) {

		return null;
	}

	@Override
	public AssetRenderer<TestAssetRendererModel> getAssetRenderer(
		long groupId, String urlTitle) {

		return null;
	}

	@Override
	public String getClassName() {
		return TestAssetRendererFactory.class.getName();
	}

	@Override
	public long getClassNameId() {
		return 1234567890;
	}

	@Deprecated
	@Override
	public Tuple getClassTypeFieldName(
		long classTypeId, String fieldName, Locale locale) {

		return null;
	}

	@Deprecated
	@Override
	public List<Tuple> getClassTypeFieldNames(
		long classTypeId, Locale locale, int start, int end) {

		return null;
	}

	@Deprecated
	@Override
	public int getClassTypeFieldNamesCount(long classTypeId, Locale locale) {
		return 0;
	}

	@Override
	public ClassTypeReader getClassTypeReader() {
		return null;
	}

	@Deprecated
	@Override
	public Map<Long, String> getClassTypes(long[] groupIds, Locale locale) {
		return null;
	}

	@Override
	public String getIconCssClass() {
		return null;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public String getIconPath(PortletRequest portletRequest) {
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
		return TestAssetRendererFactory.class.getName();
	}

	@Override
	public String getTypeName(Locale locale) {
		return null;
	}

	@Deprecated
	@Override
	public String getTypeName(Locale locale, boolean hasSubtypes) {
		return null;
	}

	@Override
	public String getTypeName(Locale locale, long subtypeId) {
		return null;
	}

	@Deprecated
	@Override
	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		return null;
	}

	@Override
	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, long classTypeId) {

		return null;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) {

		return null;
	}

	@Override
	public boolean hasAddPermission(
		PermissionChecker permissionChecker, long groupId, long classTypeId) {

		return false;
	}

	@Deprecated
	@Override
	public boolean hasClassTypeFieldNames(long classTypeId, Locale locale) {
		return false;
	}

	@Override
	public boolean hasPermission(
		PermissionChecker permissionChecker, long entryClassPK,
		String actionId) {

		return false;
	}

	@Override
	public boolean isActive(long companyId) {
		if (companyId == 1) {
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
	public boolean isListable(long classPK) {
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