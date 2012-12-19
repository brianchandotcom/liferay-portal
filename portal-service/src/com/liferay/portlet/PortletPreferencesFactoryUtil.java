/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;

import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PreferencesValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletPreferencesFactoryUtil {

	public static PortletPreferences fromDefaultXML(String xml)
		throws SystemException {

		return getPortletPreferencesFactory().fromDefaultXML(xml);
	}

	public static PortletPreferences fromXML(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws SystemException {

		return getPortletPreferencesFactory().fromXML(
			companyId, ownerId, ownerType, plid, portletId, xml);
	}

	public static PortalPreferences fromXML(
			long companyId, long ownerId, int ownerType, String xml)
		throws SystemException {

		return getPortletPreferencesFactory().fromXML(
			companyId, ownerId, ownerType, xml);
	}

	public static PortletPreferences getLayoutPortletSetup(
			Layout layout, String portletId)
		throws SystemException {

		return getPortletPreferencesFactory().getLayoutPortletSetup(
			layout, portletId);
	}

	public static PortalPreferences getPortalPreferences(
			HttpServletRequest request)
		throws SystemException {

		return getPortletPreferencesFactory().getPortalPreferences(request);
	}

	public static PortalPreferences getPortalPreferences(
			HttpSession session, long companyId, long userId, boolean signedIn)
		throws SystemException {

		return getPortletPreferencesFactory().getPortalPreferences(
			session, companyId, userId, signedIn);
	}

	public static PortalPreferences getPortalPreferences(
			long companyId, long userId, boolean signedIn)
		throws SystemException {

		return getPortletPreferencesFactory().getPortalPreferences(
			companyId, userId, signedIn);
	}

	public static PortalPreferences getPortalPreferences(
			PortletRequest portletRequest)
		throws SystemException {

		return getPortletPreferencesFactory().getPortalPreferences(
			portletRequest);
	}

	public static PortletPreferences getPortletPreferences(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		return getPortletPreferencesFactory().getPortletPreferences(
			request, portletId);
	}

	public static PortletPreferencesFactory getPortletPreferencesFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletPreferencesFactoryUtil.class);

		return _portletPreferencesFactory;
	}

	public static PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, Layout selLayout, String portletId)
		throws PortalException, SystemException {

		return getPortletPreferencesFactory().getPortletPreferencesIds(
			request, selLayout, portletId);
	}

	public static PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		return getPortletPreferencesFactory().getPortletPreferencesIds(
			request, portletId);
	}

	public static PortletPreferencesIds getPortletPreferencesIds(
			long scopeGroupId, long userId, Layout layout, String portletId,
			boolean modeEditGuest)
		throws PortalException, SystemException {

		return getPortletPreferencesFactory().getPortletPreferencesIds(
			scopeGroupId, userId, layout, portletId, modeEditGuest);
	}

	public static PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		return getPortletPreferencesFactory().getPortletSetup(
			request, portletId);
	}

	public static PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId,
			String defaultPreferences)
		throws PortalException, SystemException {

		return getPortletPreferencesFactory().getPortletSetup(
			request, portletId, defaultPreferences);
	}

	public static PortletPreferences getPortletSetup(
			Layout layout, String portletId, String defaultPreferences)
		throws SystemException {

		return getPortletPreferencesFactory().getPortletSetup(
			layout, portletId, defaultPreferences);
	}

	public static PortletPreferences getPortletSetup(
			long scopeGroupId, Layout layout, String portletId,
			String defaultPreferences)
		throws SystemException {

		return getPortletPreferencesFactory().getPortletSetup(
			scopeGroupId, layout, portletId, defaultPreferences);
	}

	public static PortletPreferences getPortletSetup(
			PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getPortletPreferencesFactory().getPortletSetup(portletRequest);
	}

	public static PortletPreferences getPortletSetup(
			PortletRequest portletRequest, String portletId)
		throws PortalException, SystemException {

		return getPortletPreferencesFactory().getPortletSetup(
			portletRequest, portletId);
	}

	public static Map<Long, PortletPreferences> getPortletSetupMap(
			long companyId, long groupId, long ownerId, int ownerType,
			String portletId, boolean privateLayout)
		throws SystemException {

		return getPortletPreferencesFactory().getPortletSetupMap(
			companyId, groupId, ownerId, ownerType, portletId, privateLayout);
	}

	public static PortletPreferences getPreferences(
		HttpServletRequest request) {

		return getPortletPreferencesFactory().getPreferences(request);
	}

	public static PreferencesValidator getPreferencesValidator(
		Portlet portlet) {

		return getPortletPreferencesFactory().getPreferencesValidator(portlet);
	}

	public static PortletPreferences getStrictLayoutPortletSetup(
			Layout layout, String portletId)
		throws SystemException {

		return getPortletPreferencesFactory().getStrictLayoutPortletSetup(
			layout, portletId);
	}

	public static PortletPreferences getStrictPortletSetup(
			Layout layout, String portletId)
		throws SystemException {

		return getPortletPreferencesFactory().getStrictPortletSetup(
			layout, portletId);
	}

	public static String toXML(PortalPreferences portalPreferences) {
		return getPortletPreferencesFactory().toXML(portalPreferences);
	}

	public static String toXML(PortletPreferences portletPreferences) {
		return getPortletPreferencesFactory().toXML(portletPreferences);
	}

	public void setPortletPreferencesFactory(
		PortletPreferencesFactory portletPreferencesFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletPreferencesFactory = portletPreferencesFactory;
	}

	private static PortletPreferencesFactory _portletPreferencesFactory;

}