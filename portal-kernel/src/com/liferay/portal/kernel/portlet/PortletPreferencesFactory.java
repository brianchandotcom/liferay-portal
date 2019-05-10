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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PreferencesValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface PortletPreferencesFactory {

	public void checkControlPanelPortletPreferences(
			ThemeDisplay themeDisplay, Portlet portlet)
		throws PortalException;

	public PortletPreferences fromDefaultXML(String xml);

	public PortalPreferences fromXML(long ownerId, int ownerType, String xml);

	public PortletPreferences fromXML(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String xml);

	public PortletPreferences getExistingPortletSetup(
			Layout layout, String portletId)
		throws PortalException;

	public PortletPreferences getExistingPortletSetup(
			PortletRequest portletRequest)
		throws PortalException;

	public PortletPreferences getLayoutPortletSetup(
		Layout layout, String portletId);

	public PortletPreferences getLayoutPortletSetup(
		Layout layout, String portletId, String defaultPreferences);

	public PortletPreferences getLayoutPortletSetup(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String defaultPreferences);

	public PortalPreferences getPortalPreferences(
		HttpServletRequest httpServletRequest);

	public PortalPreferences getPortalPreferences(
		HttpSession session, long userId, boolean signedIn);

	public PortalPreferences getPortalPreferences(
		long userId, boolean signedIn);

	public PortalPreferences getPortalPreferences(
		PortletRequest portletRequest);

	public PortletPreferences getPortletPreferences(
			HttpServletRequest httpServletRequest, String portletId)
		throws PortalException;

	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest httpServletRequest, Layout selLayout,
			String portletId)
		throws PortalException;

	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest httpServletRequest, String portletId)
		throws PortalException;

	public PortletPreferencesIds getPortletPreferencesIds(
			long scopeGroupId, long userId, Layout layout, String portletId,
			boolean modeEditGuest)
		throws PortalException;

	public PortletPreferencesIds getPortletPreferencesIds(
		long companyId, long siteGroupId, long plid, String portletId,
		String settingsScope);

	public PortletPreferences getPortletSetup(
			HttpServletRequest httpServletRequest, String portletId)
		throws PortalException;

	public PortletPreferences getPortletSetup(
			HttpServletRequest httpServletRequest, String portletId,
			String defaultPreferences)
		throws PortalException;

	public PortletPreferences getPortletSetup(
		Layout layout, String portletId, String defaultPreferences);

	public PortletPreferences getPortletSetup(
		long scopeGroupId, Layout layout, String portletId,
		String defaultPreferences);

	public PortletPreferences getPortletSetup(PortletRequest portletRequest)
		throws PortalException;

	public PortletPreferences getPortletSetup(
			PortletRequest portletRequest, String portletId)
		throws PortalException;

	public Map<Long, PortletPreferences> getPortletSetupMap(
		long companyId, long groupId, long ownerId, int ownerType,
		String portletId, boolean privateLayout);

	public PortletPreferences getPreferences(
		HttpServletRequest httpServletRequest);

	public PreferencesValidator getPreferencesValidator(Portlet portlet);

	public PortletPreferences getStrictLayoutPortletSetup(
		Layout layout, String portletId);

	public PortletPreferences getStrictPortletSetup(
		Layout layout, String portletId);

	public PortletPreferences getStrictPortletSetup(
		long companyId, long groupId, String portletId);

	public PortletPreferences strictFromXML(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String xml);

	public String toXML(PortalPreferences portalPreferences);

	public String toXML(PortletPreferences portletPreferences);

}