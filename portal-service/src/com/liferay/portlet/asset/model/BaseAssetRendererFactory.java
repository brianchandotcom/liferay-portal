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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

	public List<Tuple> getClassTypeFieldNames(
			long classTypeId, Locale locale, int start, int end)
		throws Exception {

		return Collections.emptyList();
	}

	public int getClassTypeFieldNamesCount(long classTypeId, Locale locale)
		throws Exception {

		return 0;
	}

	public Map<Long, String> getClassTypes(long[] groupId, Locale locale)
		throws Exception {

		return Collections.emptyMap();
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

		List<Tuple> classTypeFieldNames = getClassTypeFieldNames(
			classTypeId, locale, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return !classTypeFieldNames.isEmpty();
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

	protected long getControlPanelPlid(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId());
	}

	protected List<Tuple> getDDMStructureFieldNames(
			DDMStructure ddmStructure, Locale locale)
		throws Exception {

		List<Tuple> fields = new ArrayList<Tuple>();

		Map<String, Map<String, String>> fieldsMap = ddmStructure.getFieldsMap(
			LocaleUtil.toLanguageId(locale));

		for (Map<String, String> fieldMap : fieldsMap.values()) {
			String indexType = fieldMap.get("indexType");
			boolean privateField = GetterUtil.getBoolean(
				fieldMap.get("private"));

			String type = fieldMap.get("type");

			if (Validator.isNull(indexType) || privateField ||
				!ArrayUtil.contains(_SELECTABLE_DDM_STRUCTURE_FIELDS, type)) {

				continue;
			}

			String label = fieldMap.get("label");
			String name = fieldMap.get("name");

			fields.add(
				new Tuple(label, name, type, ddmStructure.getStructureId()));
		}

		return fields;
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/page.png";
	}

	private static final boolean _LINKABLE = false;

	private static final boolean _PERMISSION = true;

	private static final boolean _SELECTABLE = true;

	private static final String[] _SELECTABLE_DDM_STRUCTURE_FIELDS = {
		"checkbox", "ddm-date", "ddm-decimal", "ddm-integer", "ddm-number",
		"radio", "select", "text"
	};

	private String _className;
	private String _portletId;

}