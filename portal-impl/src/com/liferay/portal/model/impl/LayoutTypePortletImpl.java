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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CustomizedPages;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.Theme;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.PluginSettingLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.util.JS;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Berentey Zsolt
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
public class LayoutTypePortletImpl
	extends LayoutTypeImpl implements LayoutTypePortlet {

	public static String generateInstanceId() {
		return StringUtil.randomString(12);
	}

	public LayoutTypePortletImpl(Layout layout) {
		super(layout);

		if (_nestedPortletsNamespace == null) {
			_nestedPortletsNamespace = PortalUtil.getPortletNamespace(
				PortletKeys.NESTED_PORTLETS);
		}

		_layoutSetPrototypeLayout = SitesUtil.getLayoutSetPrototypeLayout(
			layout);
	}

	@Override
	public void addModeAboutPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeAbout(StringUtil.add(getModeAbout(), portletId));
	}

	@Override
	public void addModeConfigPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeConfig(StringUtil.add(getModeConfig(), portletId));
	}

	@Override
	public void addModeEditDefaultsPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeEditDefaults(StringUtil.add(getModeEditDefaults(), portletId));
	}

	@Override
	public void addModeEditGuestPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeEditGuest(StringUtil.add(getModeEditGuest(), portletId));
	}

	@Override
	public void addModeEditPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeEdit(StringUtil.add(getModeEdit(), portletId));
	}

	@Override
	public void addModeHelpPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModeHelp(StringUtil.add(getModeHelp(), portletId));
	}

	@Override
	public void addModePreviewPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModePreview(StringUtil.add(getModePreview(), portletId));
	}

	@Override
	public void addModePrintPortletId(String portletId) {
		removeModesPortletId(portletId);
		setModePrint(StringUtil.add(getModePrint(), portletId));
	}

	@Override
	public String addPortletId(long userId, String portletId)
		throws PortalException {

		return addPortletId(userId, portletId, true);
	}

	@Override
	public String addPortletId(
			long userId, String portletId, boolean checkPermission)
		throws PortalException {

		return addPortletId(userId, portletId, null, -1, checkPermission);
	}

	@Override
	public String addPortletId(
			long userId, String portletId, String columnId, int columnPos)
		throws PortalException {

		return addPortletId(userId, portletId, columnId, columnPos, true);
	}

	@Override
	public String addPortletId(
			long userId, String portletId, String columnId, int columnPos,
			boolean checkPermission)
		throws PortalException {

		return addPortletId(
			userId, portletId, columnId, columnPos, checkPermission, false);
	}

	@Override
	public void addPortletIds(
			long userId, String[] portletIds, boolean checkPermission)
		throws PortalException {

		for (String portletId : portletIds) {
			addPortletId(userId, portletId, checkPermission);
		}
	}

	@Override
	public void addPortletIds(
			long userId, String[] portletIds, String columnId,
			boolean checkPermission)
		throws PortalException {

		for (String portletId : portletIds) {
			addPortletId(userId, portletId, columnId, -1, checkPermission);
		}
	}

	@Override
	public void addStateMaxPortletId(String portletId) {
		removeStatesPortletId(portletId);
		//setStateMax(StringUtil.add(getStateMax(), portletId));
		setStateMax(StringUtil.add(StringPool.BLANK, portletId));
	}

	@Override
	public void addStateMinPortletId(String portletId) {
		removeStateMaxPortletId(portletId);
		setStateMin(StringUtil.add(getStateMin(), portletId));
	}

	@Override
	public List<Portlet> addStaticPortlets(
		List<Portlet> portlets, List<Portlet> startPortlets,
		List<Portlet> endPortlets) {

		// Return the original array of portlets if no static portlets are
		// specified

		if (startPortlets == null) {
			startPortlets = new ArrayList<Portlet>();
		}

		if (endPortlets == null) {
			endPortlets = new ArrayList<Portlet>();
		}

		if (startPortlets.isEmpty() && endPortlets.isEmpty()) {
			return portlets;
		}

		// New array of portlets that contain the static portlets

		List<Portlet> list = new ArrayList<Portlet>(
			portlets.size() + startPortlets.size() + endPortlets.size());

		if (!startPortlets.isEmpty()) {
			list.addAll(startPortlets);
		}

		for (int i = 0; i < portlets.size(); i++) {
			Portlet portlet = portlets.get(i);

			// Add the portlet if and only if it is not also a static portlet

			if (!startPortlets.contains(portlet) &&
				!endPortlets.contains(portlet)) {

				list.add(portlet);
			}
		}

		if (!endPortlets.isEmpty()) {
			list.addAll(endPortlets);
		}

		return list;
	}

	@Override
	public List<Portlet> getAllPortlets() throws PortalException {
		List<Portlet> portlets = new ArrayList<Portlet>();

		List<String> columns = getColumns();

		for (int i = 0; i < columns.size(); i++) {
			String columnId = columns.get(i);

			portlets.addAll(getAllPortlets(columnId));
		}

		List<Portlet> staticPortlets = getStaticPortlets(
			PropsKeys.LAYOUT_STATIC_PORTLETS_ALL);

		List<Portlet> embeddedPortlets = getEmbeddedPortlets(
			portlets, staticPortlets);

		return addStaticPortlets(portlets, staticPortlets, embeddedPortlets);
	}

	@Override
	public List<Portlet> getAllPortlets(boolean includeSystem)
		throws PortalException {

		List<Portlet> filteredPortlets = new ArrayList<Portlet>();

		List<Portlet> portlets = getAllPortlets();

		for (Portlet portlet : portlets) {
			if (portlet.isSystem() && !includeSystem) {
				continue;
			}

			filteredPortlets.add(portlet);
		}

		return filteredPortlets;
	}

	@Override
	public List<Portlet> getAllPortlets(String columnId)
		throws PortalException {

		String columnValue = getColumnValue(columnId);

		String[] portletIds = StringUtil.split(columnValue);

		List<Portlet> portlets = new ArrayList<Portlet>(portletIds.length);

		for (String portletId : portletIds) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getCompanyId(), portletId);

			if ((portlet != null) && !portlet.isUndeployedPortlet()) {
				portlets.add(portlet);
			}
		}

		List<Portlet> startPortlets = getStaticPortlets(
			PropsKeys.LAYOUT_STATIC_PORTLETS_START + columnId);

		List<Portlet> endPortlets = getStaticPortlets(
			PropsKeys.LAYOUT_STATIC_PORTLETS_END + columnId);

		return addStaticPortlets(portlets, startPortlets, endPortlets);
	}

	@Override
	public Layout getLayoutSetPrototypeLayout() {
		return _layoutSetPrototypeLayout;
	}

	@Override
	public String getLayoutSetPrototypeLayoutProperty(String key) {
		if (_layoutSetPrototypeLayout == null) {
			return StringPool.BLANK;
		}

		UnicodeProperties typeSettingsProperties =
			_layoutSetPrototypeLayout.getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key);
	}

	@Override
	public LayoutTemplate getLayoutTemplate() {
		String themeId = getThemeId();

		LayoutTemplate layoutTemplate =
			LayoutTemplateLocalServiceUtil.getLayoutTemplate(
				getLayoutTemplateId(), false, themeId);

		if (layoutTemplate == null) {
			layoutTemplate = new LayoutTemplateImpl(
				StringPool.BLANK, StringPool.BLANK);

			List<String> columns = new ArrayList<String>();

			for (int i = 1; i <= 10; i++) {
				columns.add(LayoutTypePortletConstants.COLUMN_PREFIX + i);
			}

			layoutTemplate.setColumns(columns);
		}

		return layoutTemplate;
	}

	@Override
	public String getLayoutTemplateId() {
		return GetterUtil.getString(
			getTypeSettingsProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));
	}

	@Override
	public String getModeAbout() {
		return getTypeSettingsProperty(LayoutTypePortletConstants.MODE_ABOUT);
	}

	@Override
	public String getModeConfig() {
		return getTypeSettingsProperty(LayoutTypePortletConstants.MODE_CONFIG);
	}

	@Override
	public String getModeEdit() {
		return getTypeSettingsProperty(LayoutTypePortletConstants.MODE_EDIT);
	}

	@Override
	public String getModeEditDefaults() {
		return getTypeSettingsProperty(
			LayoutTypePortletConstants.MODE_EDIT_DEFAULTS);
	}

	@Override
	public String getModeEditGuest() {
		return getTypeSettingsProperty(
			LayoutTypePortletConstants.MODE_EDIT_GUEST);
	}

	@Override
	public String getModeHelp() {
		return getTypeSettingsProperty(LayoutTypePortletConstants.MODE_HELP);
	}

	@Override
	public String getModePreview() {
		return getTypeSettingsProperty(LayoutTypePortletConstants.MODE_PREVIEW);
	}

	@Override
	public String getModePrint() {
		return getTypeSettingsProperty(LayoutTypePortletConstants.MODE_PRINT);
	}

	@Override
	public int getNumOfColumns() {
		return getLayoutTemplate().getColumns().size();
	}

	@Override
	public PortalPreferences getPortalPreferences() {
		return _portalPreferences;
	}

	@Override
	public List<String> getPortletIds() {
		List<String> portletIds = new ArrayList<String>();

		List<String> columns = getColumns();

		for (int i = 0; i < columns.size(); i++) {
			String columnId = columns.get(i);

			String columnValue = getColumnValue(columnId);

			portletIds.addAll(
				ListUtil.fromArray(StringUtil.split(columnValue)));
		}

		return portletIds;
	}

	@Override
	public List<Portlet> getPortlets() {
		List<String> portletIds = getPortletIds();

		List<Portlet> portlets = new ArrayList<Portlet>(portletIds.size());

		for (int i = 0; i < portletIds.size(); i++) {
			String portletId = portletIds.get(i);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getCompanyId(), portletId);

			if (portlet != null) {
				portlets.add(portlet);
			}
		}

		return portlets;
	}

	@Override
	public String getStateMax() {
		return getTypeSettingsProperty(LayoutTypePortletConstants.STATE_MAX);
	}

	@Override
	public String getStateMaxPortletId() {
		String[] stateMax = StringUtil.split(getStateMax());

		if (stateMax.length > 0) {
			return stateMax[0];
		}
		else {
			return StringPool.BLANK;
		}
	}

	@Override
	public String getStateMin() {
		return getTypeSettingsProperty(LayoutTypePortletConstants.STATE_MIN);
	}

	@Override
	public boolean hasDefaultScopePortletId(long groupId, String portletId)
		throws PortalException {

		if (hasPortletId(portletId)) {
			long scopeGroupId = PortalUtil.getScopeGroupId(
				getLayout(), portletId);

			if (groupId == scopeGroupId) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasModeAboutPortletId(String portletId) {
		return StringUtil.contains(getModeAbout(), portletId);
	}

	@Override
	public boolean hasModeConfigPortletId(String portletId) {
		return StringUtil.contains(getModeConfig(), portletId);
	}

	@Override
	public boolean hasModeEditDefaultsPortletId(String portletId) {
		return StringUtil.contains(getModeEditDefaults(), portletId);
	}

	@Override
	public boolean hasModeEditGuestPortletId(String portletId) {
		return StringUtil.contains(getModeEditGuest(), portletId);
	}

	@Override
	public boolean hasModeEditPortletId(String portletId) {
		return StringUtil.contains(getModeEdit(), portletId);
	}

	@Override
	public boolean hasModeHelpPortletId(String portletId) {
		return StringUtil.contains(getModeHelp(), portletId);
	}

	@Override
	public boolean hasModePreviewPortletId(String portletId) {
		return StringUtil.contains(getModePreview(), portletId);
	}

	@Override
	public boolean hasModePrintPortletId(String portletId) {
		return StringUtil.contains(getModePrint(), portletId);
	}

	@Override
	public boolean hasModeViewPortletId(String portletId) {
		if (hasModeAboutPortletId(portletId) ||
			hasModeConfigPortletId(portletId) ||
			hasModeEditPortletId(portletId) ||
			hasModeEditDefaultsPortletId(portletId) ||
			hasModeEditGuestPortletId(portletId) ||
			hasModeHelpPortletId(portletId) ||
			hasModePreviewPortletId(portletId) ||
			hasModePrintPortletId(portletId)) {

			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean hasPortletId(String portletId) throws PortalException {
		return hasPortletId(portletId, false);
	}

	@Override
	public boolean hasPortletId(String portletId, boolean strict)
		throws PortalException {

		List<String> columns = getColumns();

		for (String columnId : columns) {
			if (hasNonstaticPortletId(columnId, portletId)) {
				return true;
			}

			if (hasStaticPortletId(columnId, portletId)) {
				return true;
			}
		}

		Layout layout = getLayout();

		if (layout.isTypeControlPanel()) {
			return false;
		}

		if (isCustomizable() && isCustomizedView()) {
			LayoutTypePortletImpl defaultLayoutTypePortletImpl =
				getDefaultLayoutTypePortletImpl();

			if (defaultLayoutTypePortletImpl.hasNonstaticPortletId(portletId)) {
				return false;
			}
		}

		if (!strict &&
			((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId) > 0) ||
			 (PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_USER, layout.getPlid(),
				portletId) > 0))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean hasStateMax() {
		String[] stateMax = StringUtil.split(getStateMax());

		if (stateMax.length > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasStateMaxPortletId(String portletId) {
		if (StringUtil.contains(getStateMax(), portletId)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasStateMin() {
		String[] stateMin = StringUtil.split(getStateMin());

		if (stateMin.length > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasStateMinPortletId(String portletId) {
		if (StringUtil.contains(getStateMin(), portletId)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasStateNormalPortletId(String portletId) {
		if (hasStateMaxPortletId(portletId) ||
			hasStateMinPortletId(portletId)) {

			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean hasUpdatePermission() {
		return _updatePermission;
	}

	@Override
	public boolean isCacheable() throws PortalException {
		for (String columnId : getColumns()) {
			for (Portlet portlet : getAllPortlets(columnId)) {
				Portlet rootPortlet = portlet.getRootPortlet();

				if (!rootPortlet.isLayoutCacheable()) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean isColumnCustomizable(String columnId) {
		if (!isLayoutSetPrototype()) {
			String customizableString = getTypeSettingsProperty(
				CustomizedPages.namespaceColumnId(columnId));

			boolean customizable = GetterUtil.getBoolean(customizableString);

			if (!customizable && hasUserPreferences()) {
				String columnValue = _portalPreferences.getValue(
					CustomizedPages.namespacePlid(getPlid()), columnId,
					StringPool.NULL);

				if (!Validator.equals(columnValue, StringPool.NULL)) {
					setUserPreference(columnId, null);
				}
			}

			return customizable;
		}

		return false;
	}

	@Override
	public boolean isColumnDisabled(String columnId) {
		if ((isCustomizedView() && !isColumnCustomizable(columnId)) ||
			(!isCustomizedView() && !hasUpdatePermission())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isCustomizable() {
		for (String columnId : getColumns()) {
			if (isColumnCustomizable(columnId)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isCustomizedView() {
		return _customizedView;
	}

	@Override
	public boolean isDefaultUpdated() {
		if (!isCustomizedView() || !hasUserPreferences()) {
			return false;
		}

		String preferencesModifiedDateString = _portalPreferences.getValue(
			CustomizedPages.namespacePlid(getPlid()), _MODIFIED_DATE,
			_NULL_DATE);

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			PropsValues.INDEX_DATE_FORMAT_PATTERN);

		try {
			Date preferencesModifiedDate = dateFormat.parse(
				preferencesModifiedDateString);

			Layout layout = getLayout();

			String propertiesModifiedDateString =
				layout.getTypeSettingsProperty(_MODIFIED_DATE, _NULL_DATE);

			Date propertiesModifiedDate = dateFormat.parse(
				propertiesModifiedDateString);

			return propertiesModifiedDate.after(preferencesModifiedDate);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	public boolean isPortletCustomizable(String portletId) {
		return isColumnCustomizable(getColumn(portletId));
	}

	@Override
	public void movePortletId(
			long userId, String portletId, String columnId, int columnPos)
		throws PortalException {

		if (!hasPortletId(portletId)) {
			return;
		}

		_enablePortletLayoutListener = false;

		try {
			removePortletId(userId, portletId, false);
			addPortletId(userId, portletId, columnId, columnPos, false, true);
		}
		finally {
			_enablePortletLayoutListener = true;
		}

		Layout layout = getLayout();

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				layout.getCompanyId(), portletId);

			if (portlet != null) {
				PortletLayoutListener portletLayoutListener =
					portlet.getPortletLayoutListenerInstance();

				if (portletLayoutListener != null) {
					portletLayoutListener.onMoveInLayout(
						portletId, layout.getPlid());
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to fire portlet layout listener event", e);
		}
	}

	@Override
	public void removeModeAboutPortletId(String portletId) {
		setModeAbout(StringUtil.removeFromList(getModeAbout(), portletId));
	}

	@Override
	public void removeModeConfigPortletId(String portletId) {
		setModeConfig(StringUtil.removeFromList(getModeConfig(), portletId));
	}

	@Override
	public void removeModeEditDefaultsPortletId(String portletId) {
		setModeEditDefaults(
			StringUtil.removeFromList(getModeEditDefaults(), portletId));
	}

	@Override
	public void removeModeEditGuestPortletId(String portletId) {
		setModeEditGuest(
			StringUtil.removeFromList(getModeEditGuest(), portletId));
	}

	@Override
	public void removeModeEditPortletId(String portletId) {
		setModeEdit(StringUtil.removeFromList(getModeEdit(), portletId));
	}

	@Override
	public void removeModeHelpPortletId(String portletId) {
		setModeHelp(StringUtil.removeFromList(getModeHelp(), portletId));
	}

	@Override
	public void removeModePreviewPortletId(String portletId) {
		setModePreview(StringUtil.removeFromList(getModePreview(), portletId));
	}

	@Override
	public void removeModePrintPortletId(String portletId) {
		setModePrint(StringUtil.removeFromList(getModePrint(), portletId));
	}

	@Override
	public void removeModesPortletId(String portletId) {
		removeModeAboutPortletId(portletId);
		removeModeConfigPortletId(portletId);
		removeModeEditPortletId(portletId);
		removeModeEditDefaultsPortletId(portletId);
		removeModeEditGuestPortletId(portletId);
		removeModeHelpPortletId(portletId);
		removeModePreviewPortletId(portletId);
		removeModePrintPortletId(portletId);
	}

	@Override
	public void removeNestedColumns(String portletNamespace) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		UnicodeProperties newTypeSettingsProperties = new UnicodeProperties();

		for (Map.Entry<String, String> entry :
				typeSettingsProperties.entrySet()) {

			String key = entry.getKey();

			if (!key.startsWith(portletNamespace)) {
				newTypeSettingsProperties.setProperty(key, entry.getValue());
			}
		}

		Layout layout = getLayout();

		layout.setTypeSettingsProperties(newTypeSettingsProperties);

		String nestedColumnIds = GetterUtil.getString(
			getTypeSettingsProperty(
				LayoutTypePortletConstants.NESTED_COLUMN_IDS));

		String[] nestedColumnIdsArray = ArrayUtil.removeByPrefix(
			StringUtil.split(nestedColumnIds), portletNamespace);

		setTypeSettingsProperty(
			LayoutTypePortletConstants.NESTED_COLUMN_IDS,
			StringUtil.merge(nestedColumnIdsArray));
	}

	@Override
	public void removePortletId(long userId, String portletId) {
		removePortletId(userId, portletId, true);
	}

	@Override
	public void removePortletId(
		long userId, String portletId, boolean cleanUp) {

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getCompanyId(), portletId);

			if (portlet == null) {
				_log.error(
					"Portlet " + portletId +
						" cannot be removed because it is not registered");

				return;
			}

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (!LayoutPermissionUtil.contains(
					permissionChecker, getLayout(), ActionKeys.UPDATE) &&
				!isCustomizable()) {

				return;
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			return;
		}

		List<String> columns = getColumns();

		for (int i = 0; i < columns.size(); i++) {
			String columnId = columns.get(i);

			if (isCustomizable() && isColumnDisabled(columnId)) {
				continue;
			}

			String columnValue = StringPool.BLANK;

			if (hasUserPreferences()) {
				columnValue = getUserPreference(columnId);
			}
			else {
				columnValue = getTypeSettingsProperty(columnId);
			}

			columnValue = StringUtil.removeFromList(columnValue, portletId);

			if (hasUserPreferences()) {
				setUserPreference(columnId, columnValue);
			}
			else {
				setTypeSettingsProperty(columnId, columnValue);
			}
		}

		if (cleanUp) {
			try {
				onRemoveFromLayout(new String[] {portletId});
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Override
	public void removeStateMaxPortletId(String portletId) {
		setStateMax(StringUtil.removeFromList(getStateMax(), portletId));
	}

	@Override
	public void removeStateMinPortletId(String portletId) {
		setStateMin(StringUtil.removeFromList(getStateMin(), portletId));
	}

	@Override
	public void removeStatesPortletId(String portletId) {
		removeStateMaxPortletId(portletId);
		removeStateMinPortletId(portletId);
	}

	@Override
	public void reorganizePortlets(
		List<String> newColumns, List<String> oldColumns) {

		String lastNewColumnId = newColumns.get(newColumns.size() - 1);
		String lastNewColumnValue = getTypeSettingsProperty(lastNewColumnId);

		for (String oldColumnId : oldColumns) {
			if (!newColumns.contains(oldColumnId)) {
				String oldColumnValue = getTypeSettingsProperties().remove(
					oldColumnId);

				String[] portletIds = StringUtil.split(oldColumnValue);

				for (String portletId : portletIds) {
					lastNewColumnValue = StringUtil.add(
						lastNewColumnValue, portletId);
				}
			}
		}

		setTypeSettingsProperty(lastNewColumnId, lastNewColumnValue);
	}

	@Override
	public void resetModes() {
		setModeAbout(StringPool.BLANK);
		setModeConfig(StringPool.BLANK);
		setModeEdit(StringPool.BLANK);
		setModeEditDefaults(StringPool.BLANK);
		setModeEditGuest(StringPool.BLANK);
		setModeHelp(StringPool.BLANK);
		setModePreview(StringPool.BLANK);
		setModePrint(StringPool.BLANK);
	}

	@Override
	public void resetStates() {
		setStateMax(StringPool.BLANK);
		setStateMin(StringPool.BLANK);
	}

	@Override
	public void resetUserPreferences() {
		if (!hasUserPreferences()) {
			return;
		}

		long plid = getPlid();

		List<String> customPortletIds = new UniqueList<String>();

		for (String columnId : getColumns()) {
			String value = _portalPreferences.getValue(
				CustomizedPages.namespacePlid(plid), columnId);

			for (String customPortletId : StringUtil.split(value)) {
				customPortletIds.add(customPortletId);
			}
		}

		try {
			onRemoveFromLayout(
				customPortletIds.toArray(new String[customPortletIds.size()]));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_portalPreferences.resetValues(CustomizedPages.namespacePlid(plid));

		_portalPreferences.setValue(
			CustomizedPages.namespacePlid(plid), _MODIFIED_DATE,
			_dateFormat.format(new Date()));
	}

	@Override
	public void setCustomizedView(boolean customizedView) {
		_customizedView = customizedView;
	}

	@Override
	public void setLayoutTemplateId(long userId, String newLayoutTemplateId) {
		setLayoutTemplateId(userId, newLayoutTemplateId, true);
	}

	@Override
	public void setLayoutTemplateId(
		long userId, String newLayoutTemplateId, boolean checkPermission) {

		if (checkPermission &&
			!PluginSettingLocalServiceUtil.hasPermission(
				userId, newLayoutTemplateId, Plugin.TYPE_LAYOUT_TEMPLATE)) {

			return;
		}

		LayoutTemplate oldLayoutTemplate = getLayoutTemplate();

		String themeId = getThemeId();

		LayoutTemplate newLayoutTemplate =
			LayoutTemplateLocalServiceUtil.getLayoutTemplate(
				newLayoutTemplateId, false, themeId);

		if (newLayoutTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find layout template " + newLayoutTemplateId);
			}

			return;
		}

		setTypeSettingsProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, newLayoutTemplateId);

		List<String> oldColumns = oldLayoutTemplate.getColumns();
		List<String> newColumns = newLayoutTemplate.getColumns();

		reorganizePortlets(newColumns, oldColumns);
	}

	@Override
	public void setModeAbout(String modeAbout) {
		setTypeSettingsProperty(
			LayoutTypePortletConstants.MODE_ABOUT, modeAbout);
	}

	@Override
	public void setModeConfig(String modeConfig) {
		setTypeSettingsProperty(
			LayoutTypePortletConstants.MODE_CONFIG, modeConfig);
	}

	@Override
	public void setModeEdit(String modeEdit) {
		setTypeSettingsProperty(LayoutTypePortletConstants.MODE_EDIT, modeEdit);
	}

	@Override
	public void setModeEditDefaults(String modeEditDefaults) {
		setTypeSettingsProperty(
			LayoutTypePortletConstants.MODE_EDIT_DEFAULTS, modeEditDefaults);
	}

	@Override
	public void setModeEditGuest(String modeEditGuest) {
		setTypeSettingsProperty(
			LayoutTypePortletConstants.MODE_EDIT_GUEST, modeEditGuest);
	}

	@Override
	public void setModeHelp(String modeHelp) {
		setTypeSettingsProperty(LayoutTypePortletConstants.MODE_HELP, modeHelp);
	}

	@Override
	public void setModePreview(String modePreview) {
		setTypeSettingsProperty(
			LayoutTypePortletConstants.MODE_PREVIEW, modePreview);
	}

	@Override
	public void setModePrint(String modePrint) {
		setTypeSettingsProperty(
			LayoutTypePortletConstants.MODE_PRINT, modePrint);
	}

	@Override
	public void setPortalPreferences(PortalPreferences portalPreferences) {
		_portalPreferences = portalPreferences;
	}

	@Override
	public void setStateMax(String stateMax) {
		setTypeSettingsProperty(LayoutTypePortletConstants.STATE_MAX, stateMax);
	}

	@Override
	public void setStateMin(String stateMin) {
		setTypeSettingsProperty(LayoutTypePortletConstants.STATE_MIN, stateMin);
	}

	@Override
	public void setUpdatePermission(boolean updatePermission) {
		_updatePermission = updatePermission;
	}

	protected void addNestedColumn(String columnId) {
		String nestedColumnIds = getTypeSettingsProperty(
			LayoutTypePortletConstants.NESTED_COLUMN_IDS, StringPool.BLANK);

		if (!nestedColumnIds.contains(columnId)) {
			nestedColumnIds = StringUtil.add(nestedColumnIds, columnId);

			setTypeSettingsProperty(
				LayoutTypePortletConstants.NESTED_COLUMN_IDS, nestedColumnIds);
		}
	}

	protected String addPortletId(
			long userId, String portletId, String columnId, int columnPos,
			boolean checkPermission, boolean strictHasPortlet)
		throws PortalException {

		portletId = JS.getSafeName(portletId);

		Layout layout = getLayout();

		Portlet portlet = null;

		try {
			portlet = PortletLocalServiceUtil.getPortletById(
				layout.getCompanyId(), portletId);

			if (portlet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Portlet " + portletId +
							" cannot be added because it is not registered");
				}

				return null;
			}

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (checkPermission &&
				!PortletPermissionUtil.contains(
					permissionChecker, layout, portlet,
					ActionKeys.ADD_TO_PAGE)) {

				return null;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (portlet.isSystem()) {
			return null;
		}

		if (portlet.isInstanceable() &&
			!PortletConstants.hasInstanceId(portletId)) {

			portletId = PortletConstants.assemblePortletId(
				portletId, generateInstanceId());
		}

		if (hasPortletId(portletId, strictHasPortlet)) {
			return null;
		}

		if (columnId == null) {
			LayoutTemplate layoutTemplate = getLayoutTemplate();

			List<String> columns = layoutTemplate.getColumns();

			if (!columns.isEmpty()) {
				columnId = columns.get(0);
			}
		}

		if (columnId == null) {
			return null;
		}

		if (isCustomizable()) {
			if (isColumnDisabled(columnId)) {
				return null;
			}

			if ((PortletConstants.hasInstanceId(portletId) ||
				 portlet.isPreferencesUniquePerLayout()) &&
				hasUserPreferences()) {

				portletId = PortletConstants.assemblePortletId(
					portletId, userId);
			}
		}

		String columnValue = StringPool.BLANK;

		if (hasUserPreferences()) {
			columnValue = getUserPreference(columnId);
		}
		else {
			columnValue = getTypeSettingsProperty(columnId);
		}

		if ((columnValue == null) &&
			columnId.startsWith(_nestedPortletsNamespace)) {

			addNestedColumn(columnId);
		}

		if (columnPos >= 0) {
			List<String> portletIds = ListUtil.fromArray(
				StringUtil.split(columnValue));

			if (columnPos <= portletIds.size()) {
				portletIds.add(columnPos, portletId);
			}
			else {
				portletIds.add(portletId);
			}

			columnValue = StringUtil.merge(portletIds);
		}
		else {
			columnValue = StringUtil.add(columnValue, portletId);
		}

		if (hasUserPreferences()) {
			setUserPreference(columnId, columnValue);
		}
		else {
			setTypeSettingsProperty(columnId, columnValue);
		}

		try {
			if (_enablePortletLayoutListener) {
				PortletLayoutListener portletLayoutListener =
					portlet.getPortletLayoutListenerInstance();

				if (portletLayoutListener != null) {
					portletLayoutListener.onAddToLayout(
						portletId, layout.getPlid());
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to fire portlet layout listener event", e);
		}

		return portletId;
	}

	protected void copyPreferences(
		long userId, String sourcePortletId, String targetPortletId) {

		Layout layout = getLayout();

		try {
			PortletPreferencesIds portletPreferencesIds =
				PortletPreferencesFactoryUtil.getPortletPreferencesIds(
					layout.getGroupId(), 0, layout, sourcePortletId, false);

			javax.portlet.PortletPreferences sourcePortletPreferences =
				PortletPreferencesLocalServiceUtil.getStrictPreferences(
					portletPreferencesIds);

			portletPreferencesIds =
				PortletPreferencesFactoryUtil.getPortletPreferencesIds(
					layout.getGroupId(), userId, layout, targetPortletId,
					false);

			PortletPreferencesLocalServiceUtil.updatePreferences(
				portletPreferencesIds.getOwnerId(),
				portletPreferencesIds.getOwnerType(),
				portletPreferencesIds.getPlid(),
				portletPreferencesIds.getPortletId(), sourcePortletPreferences);
		}
		catch (Exception e) {
		}
	}

	protected String getColumn(String portletId) {
		String portletIdColumnId = StringPool.BLANK;

		List<String> columnIds = getColumns();

		for (String columnId : columnIds) {
			String columnValue = getColumnValue(columnId);

			String[] portletIds = StringUtil.split(columnValue);

			for (String columnPortletId : portletIds) {
				if (columnPortletId.equals(portletId)) {
					return columnId;
				}

				if (Validator.isNull(portletIdColumnId) &&
					PortletConstants.hasIdenticalRootPortletId(
						columnPortletId, portletId)) {

					portletIdColumnId = columnId;
				}
			}
		}

		return portletIdColumnId;
	}

	protected List<String> getColumns() {
		List<String> columns = new ArrayList<String>();

		Layout layout = getLayout();

		if (layout.isTypePortlet()) {
			LayoutTemplate layoutTemplate = getLayoutTemplate();

			columns.addAll(layoutTemplate.getColumns());

			columns.addAll(getNestedColumns());
		}
		else if (layout.isTypePanel()) {
			columns.add("panelSelectedPortlets");
		}

		return columns;
	}

	protected String getColumnValue(String columnId) {
		if (hasUserPreferences() && isCustomizable() &&
			!isColumnDisabled(columnId)) {

			return getUserPreference(columnId);
		}

		return getTypeSettingsProperty(columnId);
	}

	protected long getCompanyId() {
		Layout layout = getLayout();

		return layout.getCompanyId();
	}

	protected LayoutTypePortletImpl getDefaultLayoutTypePortletImpl() {
		if (!isCustomizedView()) {
			return this;
		}

		LayoutTypePortletImpl defaultLayoutTypePortletImpl =
			new LayoutTypePortletImpl(getLayout());

		defaultLayoutTypePortletImpl._embeddedPortlets = _embeddedPortlets;
		defaultLayoutTypePortletImpl._layoutSetPrototypeLayout =
			_layoutSetPrototypeLayout;
		defaultLayoutTypePortletImpl._updatePermission = _updatePermission;

		return defaultLayoutTypePortletImpl;
	}

	protected List<Portlet> getEmbeddedPortlets(
		List<Portlet> columnPortlets, List<Portlet> staticPortlets) {

		if (_embeddedPortlets != null) {
			return _embeddedPortlets;
		}

		List<Portlet> portlets = new ArrayList<Portlet>();

		Layout layout = getLayout();

		List<PortletPreferences> portletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());

		if (isCustomizable() && hasUserPreferences()) {
			portletPreferences = ListUtil.copy(portletPreferences);

			portletPreferences.addAll(
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					_portalPreferences.getUserId(),
					PortletKeys.PREFS_OWNER_TYPE_USER, layout.getPlid()));
		}

		for (PortletPreferences portletPreference : portletPreferences) {
			String portletId = portletPreference.getPortletId();

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getCompanyId(), portletId);

			if (Validator.isNull(portletId) ||
				columnPortlets.contains(portlet) ||
				staticPortlets.contains(portlet) || !portlet.isReady() ||
				portlet.isUndeployedPortlet() || !portlet.isActive()) {

				continue;
			}

			if (portlet != null) {
				Portlet embeddedPortlet = portlet;

				if (portlet.isInstanceable()) {

					// Instanceable portlets do not need to be cloned because
					// they are already cloned. See the method getPortletById in
					// the class PortletLocalServiceImpl and how it references
					// the method getClonedInstance in the class PortletImpl.

				}
				else {
					embeddedPortlet = (Portlet)embeddedPortlet.clone();
				}

				// We set embedded portlets as static on order to avoid adding
				// the close and/or move icons.

				embeddedPortlet.setStatic(true);

				portlets.add(embeddedPortlet);
			}
		}

		_embeddedPortlets = portlets;

		return _embeddedPortlets;
	}

	protected List<String> getNestedColumns() {
		String nestedColumnIds = getTypeSettingsProperty(
			LayoutTypePortletConstants.NESTED_COLUMN_IDS);

		return ListUtil.fromArray(StringUtil.split(nestedColumnIds));
	}

	protected long getPlid() {
		Layout layout = getLayout();

		return layout.getPlid();
	}

	protected String[] getStaticPortletIds(String position)
		throws PortalException {

		Layout layout = getLayout();

		String selector1 = StringPool.BLANK;

		Group group = layout.getGroup();

		if (group.isUser()) {
			selector1 = LayoutTypePortletConstants.STATIC_PORTLET_USER_SELECTOR;
		}
		else if (group.isOrganization()) {
			selector1 =
				LayoutTypePortletConstants.STATIC_PORTLET_ORGANIZATION_SELECTOR;
		}
		else if (group.isRegularSite()) {
			selector1 =
				LayoutTypePortletConstants.STATIC_PORTLET_REGULAR_SITE_SELECTOR;
		}

		String selector2 = layout.getFriendlyURL();

		String[] portletIds = PropsUtil.getArray(
			position, new Filter(selector1, selector2));

		for (int i = 0; i < portletIds.length; i++) {
			portletIds[i] = JS.getSafeName(portletIds[i]);
		}

		return portletIds;
	}

	protected List<Portlet> getStaticPortlets(String position)
		throws PortalException {

		String[] portletIds = getStaticPortletIds(position);

		List<Portlet> portlets = new ArrayList<Portlet>();

		for (String portletId : portletIds) {
			if (Validator.isNull(portletId) ||
				hasNonstaticPortletId(portletId)) {

				continue;
			}

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getCompanyId(), portletId);

			if (portlet != null) {
				Portlet staticPortlet = portlet;

				if (portlet.isInstanceable()) {

					// Instanceable portlets do not need to be cloned because
					// they are already cloned. See the method getPortletById in
					// the class PortletLocalServiceImpl and how it references
					// the method getClonedInstance in the class PortletImpl.

				}
				else {
					staticPortlet = (Portlet)staticPortlet.clone();
				}

				staticPortlet.setStatic(true);

				if (position.startsWith("layout.static.portlets.start")) {
					staticPortlet.setStaticStart(true);
				}

				portlets.add(staticPortlet);
			}
		}

		return portlets;
	}

	protected String getThemeId() {
		String themeId = null;

		try {
			Layout layout = getLayout();

			Theme theme = layout.getTheme();

			if (theme != null) {
				themeId = theme.getThemeId();
			}
			else {
				themeId = layout.getThemeId();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return themeId;
	}

	protected String getUserPreference(String key) {
		String value = StringPool.BLANK;

		if (!hasUserPreferences()) {
			return value;
		}

		value = _portalPreferences.getValue(
			CustomizedPages.namespacePlid(getPlid()), key, StringPool.NULL);

		if (!value.equals(StringPool.NULL)) {
			return value;
		}

		value = getTypeSettingsProperty(key);

		if (Validator.isNull(value)) {
			return value;
		}

		List<String> newPortletIds = new ArrayList<String>();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		String[] portletIds = StringUtil.split(value);

		for (String portletId : portletIds) {
			try {
				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);

				if (!PortletPermissionUtil.contains(
						permissionChecker, rootPortletId,
						ActionKeys.ADD_TO_PAGE)) {

					continue;
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			String newPortletId = null;

			boolean preferencesUniquePerLayout = false;

			try {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					getCompanyId(), portletId);

				preferencesUniquePerLayout =
					portlet.isPreferencesUniquePerLayout();
			}
			catch (SystemException se) {
				_log.error(se, se);
			}

			if (PortletConstants.hasInstanceId(portletId) ||
				preferencesUniquePerLayout) {

				String instanceId = null;

				if (PortletConstants.hasInstanceId(portletId)) {
					instanceId = generateInstanceId();
				}

				newPortletId = PortletConstants.assemblePortletId(
					portletId, _portalPreferences.getUserId(), instanceId);

				copyPreferences(
					_portalPreferences.getUserId(), portletId, newPortletId);
			}
			else {
				newPortletId = portletId;
			}

			newPortletIds.add(newPortletId);
		}

		value = StringUtil.merge(newPortletIds);

		setUserPreference(key, value);

		return value;
	}

	protected boolean hasNonstaticPortletId(String portletId) {
		LayoutTemplate layoutTemplate = getLayoutTemplate();

		List<String> columns = layoutTemplate.getColumns();

		for (int i = 0; i < columns.size(); i++) {
			String columnId = columns.get(i);

			if (hasNonstaticPortletId(columnId, portletId)) {
				return true;
			}
		}

		return false;
	}

	protected boolean hasNonstaticPortletId(String columnId, String portletId) {
		String columnValue = getColumnValue(columnId);

		String[] columnValues = StringUtil.split(columnValue);

		for (String nonstaticPortletId : columnValues) {
			if (nonstaticPortletId.equals(portletId) ||
				PortletConstants.getRootPortletId(
					nonstaticPortletId).equals(portletId)) {

				return true;
			}
		}

		return false;
	}

	protected boolean hasStaticPortletId(String columnId, String portletId)
		throws PortalException {

		String[] staticPortletIdsStart = getStaticPortletIds(
			PropsKeys.LAYOUT_STATIC_PORTLETS_START + columnId);

		String[] staticPortletIdsEnd = getStaticPortletIds(
			PropsKeys.LAYOUT_STATIC_PORTLETS_END + columnId);

		for (String staticPortletId : staticPortletIdsStart) {
			if (staticPortletId.equals(portletId) ||
				PortletConstants.getRootPortletId(
					staticPortletId).equals(portletId)) {

				return true;
			}
		}

		for (String staticPortletId : staticPortletIdsEnd) {
			if (staticPortletId.equals(portletId) ||
				PortletConstants.getRootPortletId(
					staticPortletId).equals(portletId)) {

				return true;
			}
		}

		return false;
	}

	protected boolean hasUserPreferences() {
		if (_portalPreferences != null) {
			return true;
		}

		return false;
	}

	protected boolean isLayoutSetPrototype() {
		try {
			Layout layout = getLayout();

			LayoutSet layoutSet = layout.getLayoutSet();

			Group group = layoutSet.getGroup();

			return group.isLayoutSetPrototype();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	protected void onRemoveFromLayout(String[] portletIds) {
		List<String> portletIdList = new UniqueList<String>();

		for (String portletId : portletIds) {
			removeModesPortletId(portletId);
			removeStatesPortletId(portletId);

			portletIdList.add(portletId);

			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			if (rootPortletId.equals(PortletKeys.NESTED_PORTLETS)) {
				String portletNamespace = PortalUtil.getPortletNamespace(
					portletId);

				UnicodeProperties typeSettingsProperties =
					getTypeSettingsProperties();

				for (Map.Entry<String, String> entry :
						typeSettingsProperties.entrySet()) {

					String key = entry.getKey();

					if (!key.startsWith(portletNamespace)) {
						continue;
					}

					String nestedPortletIds = entry.getValue();

					for (String nestedPortletId :
							StringUtil.split(nestedPortletIds)) {

						removeModesPortletId(nestedPortletId);
						removeStatesPortletId(nestedPortletId);

						portletIdList.add(nestedPortletId);
					}
				}

				removeNestedColumns(portletNamespace);
			}
		}

		try {
			PortletLocalServiceUtil.deletePortlets(
				getCompanyId(),
				portletIdList.toArray(new String[portletIdList.size()]),
				getPlid());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
	}

	protected void setUserPreference(String key, String value) {
		if (!hasUserPreferences()) {
			return;
		}

		_portalPreferences.setValue(
			CustomizedPages.namespacePlid(getPlid()), key, value);

		_portalPreferences.setValue(
			CustomizedPages.namespacePlid(getPlid()), _MODIFIED_DATE,
			_dateFormat.format(new Date()));
	}

	private static final String _MODIFIED_DATE = "modifiedDate";

	private static final String _NULL_DATE = "00000000000000";

	private static Log _log = LogFactoryUtil.getLog(
		LayoutTypePortletImpl.class);

	private static String _nestedPortletsNamespace;

	private boolean _customizedView;
	private Format _dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
		PropsValues.INDEX_DATE_FORMAT_PATTERN);
	private List<Portlet> _embeddedPortlets;
	private boolean _enablePortletLayoutListener = true;
	private Layout _layoutSetPrototypeLayout;
	private PortalPreferences _portalPreferences;
	private boolean _updatePermission;

}