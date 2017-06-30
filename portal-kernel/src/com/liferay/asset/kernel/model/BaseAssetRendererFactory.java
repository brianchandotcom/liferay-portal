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

import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 */
public abstract class BaseAssetRendererFactory<T>
	implements AssetRendererFactory<T> {

	@Override
	public AssetEntry getAssetEntry(long assetEntryId) throws PortalException {
		return AssetEntryLocalServiceUtil.getEntry(assetEntryId);
	}

	@Override
	public AssetEntry getAssetEntry(String className, long classPK)
		throws PortalException {

		return AssetEntryLocalServiceUtil.getEntry(className, classPK);
	}

	@Override
	public AssetRenderer<T> getAssetRenderer(long classPK)
		throws PortalException {

		return getAssetRenderer(classPK, TYPE_LATEST_APPROVED);
	}

	@Override
	@SuppressWarnings("unused")
	public AssetRenderer<T> getAssetRenderer(long groupId, String urlTitle)
		throws PortalException {

		return null;
	}

	@Override
	public AssetRenderer<T> getAssetRenderer(T entry, int type)
		throws PortalException {

		return null;
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(getClassName());
	}

	/**
	 * @deprecated As of 7.0.0
	 */
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

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public List<Tuple> getClassTypeFieldNames(
			long classTypeId, Locale locale, int start, int end)
		throws Exception {

		ClassTypeReader classTypeReader = getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(classTypeId, locale);

		List<ClassTypeField> classTypeFields = classType.getClassTypeFields(
			start, end);

		List<Tuple> tuples = new ArrayList<>(classTypeFields.size());

		for (ClassTypeField classTypeField : classTypeFields) {
			tuples.add(toTuple(classTypeField));
		}

		return tuples;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
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

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public Map<Long, String> getClassTypes(long[] groupIds, Locale locale)
		throws Exception {

		ClassTypeReader classTypeReader = getClassTypeReader();

		List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(
			groupIds, locale);

		Map<Long, String> classTypesMap = new HashMap<>();

		for (ClassType classType : classTypes) {
			classTypesMap.put(classType.getClassTypeId(), classType.getName());
		}

		return classTypesMap;
	}

	@Override
	public String getIconCssClass() {
		return null;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String getIconPath(PortletRequest portletRequest) {
		return StringPool.BLANK;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public String getSubtypeTitle(Locale locale) {
		return LanguageUtil.get(locale, "subtype");
	}

	@Override
	public String getTypeName(Locale locale) {
		String modelResourceNamePrefix =
			ResourceActionsUtil.getModelResourceNamePrefix();

		String key = modelResourceNamePrefix.concat(getClassName());

		String value = LanguageUtil.get(locale, key, null);

		if (value == null) {
			PortletBag portletBag = PortletBagPool.get(getPortletId());

			ResourceBundle resourceBundle = portletBag.getResourceBundle(
				locale);

			if (resourceBundle != null) {
				value = ResourceBundleUtil.getString(resourceBundle, key);
			}
		}

		if (value == null) {
			value = getClassName();
		}

		return value;
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

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		return getURLAdd(liferayPortletRequest, liferayPortletResponse, 0);
	}

	@Override
	@SuppressWarnings("unused")
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classTypeId)
		throws PortalException {

		return null;
	}

	@Override
	@SuppressWarnings("unused")
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

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public boolean hasClassTypeFieldNames(long classTypeId, Locale locale)
		throws Exception {

		ClassTypeReader classTypeReader = getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(classTypeId, locale);

		if (classType.getClassTypeFieldsCount() > 0) {
			return true;
		}

		return false;
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

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, getPortletId());

		if (portlet == null) {
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
	public boolean isSearchable() {
		return _searchable;
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
		throws PortalException {

		return PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId());
	}

	protected Group getGroup(LiferayPortletRequest liferayPortletRequest) {
		return (Group)liferayPortletRequest.getAttribute(
			WebKeys.ASSET_RENDERER_FACTORY_GROUP);
	}

	protected void setCategorizable(boolean categorizable) {
		_categorizable = categorizable;
	}

	protected void setLinkable(boolean linkable) {
		_linkable = linkable;
	}

	protected void setSearchable(boolean searchable) {
		_searchable = searchable;
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

	private boolean _categorizable = true;
	private String _className;
	private boolean _linkable;
	private String _portletId;
	private boolean _searchable;
	private boolean _selectable = true;
	private boolean _supportsClassTypes;

}