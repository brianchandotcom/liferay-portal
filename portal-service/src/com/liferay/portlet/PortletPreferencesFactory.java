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

package com.liferay.portlet;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
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
		throws PortalException;

	public PortletPreferences fromDefaultXML(String xml);

	public PortalPreferences fromXML(long ownerId, int ownerType, String xml);

	public PortletPreferences fromXML(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String xml);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #fromXML(long, int, String)}
	 */
	@Deprecated
	public PortalPreferences fromXML(
		long companyId, long ownerId, int ownerType, String xml);

	public PortletPreferences getLayoutPortletSetup(
		Layout layout, String portletId);

	public PortalPreferences getPortalPreferences(HttpServletRequest request);

	public PortalPreferences getPortalPreferences(
		HttpSession session, long userId, boolean signedIn);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getPortalPreferences(HttpSession, long, boolean)}
	 */
	@Deprecated
	public PortalPreferences getPortalPreferences(
		HttpSession session, long companyId, long userId, boolean signedIn);

	public PortalPreferences getPortalPreferences(
		long userId, boolean signedIn);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getPortalPreferences(long,
	 *             boolean)}
	 */
	@Deprecated
	public PortalPreferences getPortalPreferences(
		long companyId, long userId, boolean signedIn);

	public PortalPreferences getPortalPreferences(
		PortletRequest portletRequest);

	public PortletPreferences getPortletPreferences(
			HttpServletRequest request, String portletId)
		throws PortalException;

	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, Layout selLayout, String portletId)
		throws PortalException;

	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, String portletId)
		throws PortalException;

	public PortletPreferencesIds getPortletPreferencesIds(
			long scopeGroupId, long userId, Layout layout, String portletId,
			boolean modeEditGuest)
		throws PortalException;

	public PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId)
		throws PortalException;

	public PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId,
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

	public PortletPreferences getPreferences(HttpServletRequest request);

	public PreferencesValidator getPreferencesValidator(Portlet portlet);

	public PortletPreferences getStrictLayoutPortletSetup(
		Layout layout, String portletId);

	public PortletPreferences getStrictPortletSetup(
		Layout layout, String portletId);

	public String toXML(PortalPreferences portalPreferences);

	public String toXML(PortletPreferences portletPreferences);

}