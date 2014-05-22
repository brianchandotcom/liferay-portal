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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.NoSuchClassTypeException;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
public abstract class BaseAssetRendererFactory implements AssetRendererFactory {

	@Override
	public AssetEntry getAssetEntry(long assetEntryId)
		throws PortalException, SystemException {

		return AssetEntryLocalServiceUtil.getEntry(assetEntryId);
	}

	@Override
	public AssetEntry getAssetEntry(String className, long classPK)
		throws PortalException, SystemException {

		return AssetEntryLocalServiceUtil.getEntry(className, classPK);
	}

	@Override
	public AssetRenderer getAssetRenderer(long classPK)
		throws PortalException, SystemException {

		return getAssetRenderer(classPK, TYPE_LATEST_APPROVED);
	}

	@Override
	@SuppressWarnings("unused")
	public AssetRenderer getAssetRenderer(long groupId, String urlTitle)
		throws PortalException, SystemException {

		return null;
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(_className);
	}

	@Deprecated
	@Override
	public Tuple getClassTypeFieldName(
			long classTypeId, String fieldName, Locale locale)
		throws Exception {

		ClassTypeReader classTypeReader = getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(classTypeId, locale);

		List<ClassTypeField> classTypeFields = classType.getClassTypeFields();

		for (ClassTypeField classTypeField : classTypeFields) {
			if (fieldName.equals(classTypeField.getName())) {
				return toTuple(classTypeField);
			}
		}

		return null;
	}

	@Deprecated
	@Override
	public List<Tuple> getClassTypeFieldNames(
			long classTypeId, Locale locale, int start, int end)
		throws Exception {

		ClassTypeReader classTypeReader = getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(classTypeId, locale);

		List<ClassTypeField> classTypeFields = classType.getClassTypeFields(
			start, end);

		List<Tuple> tuples = new ArrayList<Tuple>(classTypeFields.size());

		for (ClassTypeField classTypeField : classTypeFields) {
			tuples.add(toTuple(classTypeField));
		}

		return tuples;
	}

	@Deprecated
	@Override
	public int getClassTypeFieldNamesCount(long classTypeId, Locale locale)
		throws Exception {

		ClassTypeReader classTypeReader = getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(classTypeId, locale);

		return classType.getClassTypeFieldsCount();
	}

	@Override
	public ClassTypeReader getClassTypeReader() {
		return new NullClassTypeReader();
	}

	@Deprecated
	@Override
	public Map<Long, String> getClassTypes(long[] groupId, Locale locale)
		throws Exception {

		ClassTypeReader classTypeReader = getClassTypeReader();

		List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(
			groupId, locale);

		Map<Long, String> classTypesMap = new HashMap<Long, String>();

		for (ClassType classType : classTypes) {
			classTypesMap.put(classType.getClassTypeId(), classType.getName());
		}

		return classTypesMap;
	}

	@Override
	public String getIconCssClass() {
		return null;
	}

	@Override
	public String getIconPath(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getIconPath(themeDisplay);
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public String getTypeName(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getTypeName(Locale)}
	 */
	@Deprecated
	@Override
	public String getTypeName(Locale locale, boolean hasSubtypes) {
		return getTypeName(locale);
	}

	@Override
	public String getTypeName(Locale locale, long subtypeId) {
		return getTypeName(locale);
	}

	@Override
	@SuppressWarnings("unused")
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException, SystemException {

		return null;
	}

	@Override
	@SuppressWarnings("unused")
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws PortalException, SystemException {

		return null;
	}

	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		return false;
	}

	@Deprecated
	@Override
	public boolean hasClassTypeFieldNames(long classTypeId, Locale locale)
		throws Exception {

		ClassTypeReader classTypeReader = getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(classTypeId, locale);

		return classType.getClassTypeFieldsCount() > 0;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return _PERMISSION;
	}

	@Override
	public boolean isActive(long companyId) {
		if (Validator.isNull(getPortletId())) {
			return true;
		}

		Portlet portlet = null;

		try {
			portlet = PortletLocalServiceUtil.getPortletById(
				companyId, getPortletId());
		}
		catch (SystemException se) {
			portlet = PortletLocalServiceUtil.getPortletById(getPortletId());
		}

		if (portlet == null) {
			return false;
		}

		return portlet.isActive();
	}

	@Override
	public boolean isCategorizable() {
		return _categorizable;
	}

	@Override
	public boolean isLinkable() {
		return _linkable;
	}

	@Override
	public boolean isSelectable() {
		return _selectable;
	}

	@Override
	public boolean isSupportsClassTypes() {
		return _supportsClassTypes;
	}

	@Override
	public void setClassName(String className) {
		_className = className;
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	protected long getControlPanelPlid(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId());
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/page.png";
	}

	protected void setCategorizable(boolean categorizable) {
		_categorizable = categorizable;
	}

	protected void setLinkable(boolean linkable) {
		_linkable = linkable;
	}

	protected void setSelectable(boolean selectable) {
		_selectable = selectable;
	}

	protected void setSupportsClassTypes(boolean supportsClassTypes) {
		_supportsClassTypes = supportsClassTypes;
	}

	protected Tuple toTuple(ClassTypeField classTypeField) {
		return new Tuple(
			classTypeField.getLabel(), classTypeField.getName(),
			classTypeField.getType(), classTypeField.getClassTypeId());
	}

	private static final boolean _PERMISSION = true;

	private static final class NullClassTypeReader implements ClassTypeReader {

		@Override
		public List<ClassType> getAvailableClassTypes(
			long[] groupIds, Locale locale) {

			return Collections.emptyList();
		}

		@Override
		public ClassType getClassType(long classTypeId, Locale locale)
			throws PortalException, SystemException {

			throw new NoSuchClassTypeException();
		}

	}

	private boolean _categorizable = true;
	private String _className;
	private boolean _linkable;
	private String _portletId;
	private boolean _selectable = true;
	private boolean _supportsClassTypes;

}