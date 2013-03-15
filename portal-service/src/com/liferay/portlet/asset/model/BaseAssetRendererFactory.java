/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 */
public abstract class BaseAssetRendererFactory implements AssetRendererFactory {

	public AssetEntry getAssetEntry(long assetEntryId)
		throws PortalException, SystemException {

		return AssetEntryLocalServiceUtil.getEntry(assetEntryId);
	}

	public AssetEntry getAssetEntry(String className, long classPK)
		throws PortalException, SystemException {

		return AssetEntryLocalServiceUtil.getEntry(className, classPK);
	}

	public AssetRenderer getAssetRenderer(long classPK)
		throws PortalException, SystemException {

		return getAssetRenderer(classPK, TYPE_LATEST_APPROVED);
	}

	@SuppressWarnings("unused")
	public AssetRenderer getAssetRenderer(long groupId, String urlTitle)
		throws PortalException, SystemException {

		return null;
	}

	public long getClassNameId() {
		return PortalUtil.getClassNameId(_className);
	}

	public Map<String, Map<String, String>> getClassTypeFieldNames(
			long classTypeId, Locale locale)
		throws Exception {

		return null;
	}

	public Map<Long, String> getClassTypes(long[] groupId, Locale locale)
		throws Exception {

		return null;
	}

	public String getIconPath(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getIconPath(themeDisplay);
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getTypeName(Locale locale, boolean hasSubtypes) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@SuppressWarnings("unused")
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException, SystemException {

		return null;
	}

	public boolean hasClassTypeFieldNames(long classTypeId, Locale locale)
		throws Exception {

		Map<String, Map<String, String>> classTypeFieldNames =
			getClassTypeFieldNames(classTypeId, locale);

		if ((classTypeFieldNames == null) ||
			(classTypeFieldNames.size() <= 0)) {

			return false;
		}

		return true;
	}

	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return _PERMISSION;
	}

	public boolean isCategorizable() {
		return true;
	}

	public boolean isLinkable() {
		return _LINKABLE;
	}

	public boolean isSelectable() {
		return _SELECTABLE;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	protected Map<String, Map<String, String>> filterDDMStructureFields(
			DDMStructure ddmStructure, Locale locale)
		throws Exception {

		Map<String, Map<String, String>> filterFields =
			new HashMap<String, Map<String, String>>();

		Map<String, Map<String, String>> fieldsMap = ddmStructure.getFieldsMap(
			LocaleUtil.toLanguageId(locale));

		for (Map<String, String> fieldMap : fieldsMap.values()) {
			String indexType = fieldMap.get("indexType");
			boolean isPrivate = GetterUtil.getBoolean(fieldMap.get("private"));

			if (Validator.isNull(indexType) || isPrivate) {
				continue;
			}

			String name = fieldMap.get("name");

			String encodeFieldName = DDMIndexerUtil.encodeName(
				ddmStructure.getStructureId(), name, locale);

			filterFields.put(encodeFieldName, fieldMap);
		}

		return filterFields;
	}

	protected long getControlPanelPlid(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId());
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/page.png";
	}

	private static final boolean _LINKABLE = false;

	private static final boolean _PERMISSION = true;

	private static final boolean _SELECTABLE = true;

	private String _className;
	private String _portletId;

}