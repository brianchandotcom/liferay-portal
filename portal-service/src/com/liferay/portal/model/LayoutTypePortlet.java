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

package com.liferay.portal.model;

import com.liferay.portlet.PortalPreferences;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public interface LayoutTypePortlet extends LayoutType {

	public void addModeAboutPortletId(String portletId);

	public void addModeConfigPortletId(String portletId);

	public void addModeEditDefaultsPortletId(String portletId);

	public void addModeEditGuestPortletId(String portletId);

	public void addModeEditPortletId(String portletId);

	public void addModeHelpPortletId(String portletId);

	public void addModePreviewPortletId(String portletId);

	public void addModePrintPortletId(String portletId);

	public String addPortletId(long userId, String portletId);

	public String addPortletId(
		long userId, String portletId, boolean checkPermission);

	public String addPortletId(
		long userId, String portletId, String columnId, int columnPos);

	public String addPortletId(
		long userId, String portletId, String columnId, int columnPos,
		boolean checkPermission);

	public void addPortletIds(
		long userId, String[] portletIds, boolean checkPermission);

	public void addPortletIds(
		long userId, String[] portletIds, String columnId,
		boolean checkPermission);

	public void addStateMaxPortletId(String portletId);

	public void addStateMinPortletId(String portletId);

	public List<Portlet> addStaticPortlets(
		List<Portlet> portlets, List<Portlet> startPortlets,
		List<Portlet> endPortlets);

	public List<Portlet> getAllPortlets();

	public List<Portlet> getAllPortlets(boolean includeSystem);

	public List<Portlet> getAllPortlets(String columnId);

	public List<Portlet> getEmbeddedPortlets();

	public List<Portlet> getExplicitlyAddedPortlets();

	public Layout getLayoutSetPrototypeLayout();

	public String getLayoutSetPrototypeLayoutProperty(String key);

	public LayoutTemplate getLayoutTemplate();

	public String getLayoutTemplateId();

	public String getModeAbout();

	public String getModeConfig();

	public String getModeEdit();

	public String getModeEditDefaults();

	public String getModeEditGuest();

	public String getModeHelp();

	public String getModePreview();

	public String getModePrint();

	public int getNumOfColumns();

	public PortalPreferences getPortalPreferences();

	public List<String> getPortletIds();

	public List<Portlet> getPortlets();

	public String getStateMax();

	public String getStateMaxPortletId();

	public String getStateMin();

	public boolean hasDefaultScopePortletId(long groupId, String portletId);

	public boolean hasModeAboutPortletId(String portletId);

	public boolean hasModeConfigPortletId(String portletId);

	public boolean hasModeEditDefaultsPortletId(String portletId);

	public boolean hasModeEditGuestPortletId(String portletId);

	public boolean hasModeEditPortletId(String portletId);

	public boolean hasModeHelpPortletId(String portletId);

	public boolean hasModePreviewPortletId(String portletId);

	public boolean hasModePrintPortletId(String portletId);

	public boolean hasModeViewPortletId(String portletId);

	public boolean hasPortletId(String portletId);

	public boolean hasPortletId(String portletId, boolean strict);

	public boolean hasStateMax();

	public boolean hasStateMaxPortletId(String portletId);

	public boolean hasStateMin();

	public boolean hasStateMinPortletId(String portletId);

	public boolean hasStateNormalPortletId(String portletId);

	public boolean hasUpdatePermission();

	public boolean isCacheable();

	public boolean isColumnCustomizable(String columnId);

	public boolean isColumnDisabled(String columnId);

	public boolean isCustomizable();

	public boolean isCustomizedView();

	public boolean isDefaultUpdated();

	public boolean isPortletCustomizable(String portletId);

	public boolean isPortletEmbedded(String portletId);

	public void movePortletId(
		long userId, String portletId, String columnId, int columnPos);

	public void removeModeAboutPortletId(String portletId);

	public void removeModeConfigPortletId(String portletId);

	public void removeModeEditDefaultsPortletId(String portletId);

	public void removeModeEditGuestPortletId(String portletId);

	public void removeModeEditPortletId(String portletId);

	public void removeModeHelpPortletId(String portletId);

	public void removeModePreviewPortletId(String portletId);

	public void removeModePrintPortletId(String portletId);

	public void removeModesPortletId(String portletId);

	public void removeNestedColumns(String portletNamespace);

	public void removePortletId(long userId, String portletId);

	public void removePortletId(
		long userId, String portletId, boolean modeAndState);

	public void removeStateMaxPortletId(String portletId);

	public void removeStateMinPortletId(String portletId);

	public void removeStatesPortletId(String portletId);

	public void reorganizePortlets(
		List<String> newColumns, List<String> oldColumns);

	public void resetModes();

	public void resetStates();

	public void resetUserPreferences();

	public void setCustomizedView(boolean customizedView);

	public void setLayoutTemplateId(long userId, String newLayoutTemplateId);

	public void setLayoutTemplateId(
		long userId, String newLayoutTemplateId, boolean checkPermission);

	public void setModeAbout(String modeAbout);

	public void setModeConfig(String modeConfig);

	public void setModeEdit(String modeEdit);

	public void setModeEditDefaults(String modeEditDefaults);

	public void setModeEditGuest(String modeEditGuest);

	public void setModeHelp(String modeHelp);

	public void setModePreview(String modePreview);

	public void setModePrint(String modePrint);

	public void setPortalPreferences(PortalPreferences portalPreferences);

	public void setStateMax(String stateMax);

	public void setStateMin(String stateMin);

	public void setUpdatePermission(boolean updatePermission);

}