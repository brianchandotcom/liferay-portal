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

package com.liferay.portlet;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PreferencesValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface PortletPreferencesFactory {

	public void checkControlPanelPortletPreferences(
			ThemeDisplay themeDisplay, Portlet portlet)
		throws PortalException, SystemException;

	public PortletPreferences fromDefaultXML(String xml) throws SystemException;

	public PortalPreferences fromXML(long ownerId, int ownerType, String xml)
		throws SystemException;

	public PortletPreferences fromXML(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #fromXML(long, int, String)}
	 */
	public PortalPreferences fromXML(
			long companyId, long ownerId, int ownerType, String xml)
		throws SystemException;

	public PortletPreferences getLayoutPortletSetup(
			Layout layout, String portletId)
		throws SystemException;

	public PortalPreferences getPortalPreferences(HttpServletRequest request)
		throws SystemException;

	public PortalPreferences getPortalPreferences(
			HttpSession session, long userId, boolean signedIn)
		throws SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getPortalPreferences(HttpSession, long, boolean)}
	 */
	public PortalPreferences getPortalPreferences(
			HttpSession session, long companyId, long userId, boolean signedIn)
		throws SystemException;

	public PortalPreferences getPortalPreferences(long userId, boolean signedIn)
		throws SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getPortalPreferences(long,
	 *             boolean)}
	 */
	public PortalPreferences getPortalPreferences(
			long companyId, long userId, boolean signedIn)
		throws SystemException;

	public PortalPreferences getPortalPreferences(PortletRequest portletRequest)
		throws SystemException;

	public PortletPreferences getPortletPreferences(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException;

	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, Layout selLayout, String portletId)
		throws PortalException, SystemException;

	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException;

	public PortletPreferencesIds getPortletPreferencesIds(
			long scopeGroupId, long userId, Layout layout, String portletId,
			boolean modeEditGuest)
		throws PortalException, SystemException;

	public PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException;

	public PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId,
			String defaultPreferences)
		throws PortalException, SystemException;

	public PortletPreferences getPortletSetup(
			Layout layout, String portletId, String defaultPreferences)
		throws SystemException;

	public PortletPreferences getPortletSetup(
			long scopeGroupId, Layout layout, String portletId,
			String defaultPreferences)
		throws SystemException;

	public PortletPreferences getPortletSetup(PortletRequest portletRequest)
		throws PortalException, SystemException;

	public PortletPreferences getPortletSetup(
			PortletRequest portletRequest, String portletId)
		throws PortalException, SystemException;

	public Map<Long, PortletPreferences> getPortletSetupMap(
			long companyId, long groupId, long ownerId, int ownerType,
			String portletId, boolean privateLayout)
		throws SystemException;

	public PortletPreferences getPreferences(HttpServletRequest request);

	public PreferencesValidator getPreferencesValidator(Portlet portlet);

	public PortletPreferences getStrictLayoutPortletSetup(
			Layout layout, String portletId)
		throws SystemException;

	public PortletPreferences getStrictPortletSetup(
			Layout layout, String portletId)
		throws SystemException;

	public PortletPreferences strictFromXML(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws SystemException;

	public String toXML(PortalPreferences portalPreferences);

	public String toXML(PortletPreferences portletPreferences);

}