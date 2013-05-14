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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.xml.StAXReaderUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PreferencesValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Minhchau Dang
 * @author Raymond Augé
 */
@DoPrivileged
public class PortletPreferencesFactoryImpl
	implements PortletPreferencesFactory {

	@Override
	public PortletPreferences fromDefaultXML(String xml)
		throws SystemException {

		Map<String, Preference> preferencesMap =
			new HashMap<String, Preference>();

		populateMap(xml, preferencesMap);

		return new PortletPreferencesImpl(xml, preferencesMap);
	}

	@Override
	public PortletPreferencesImpl fromXML(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws SystemException {

		try {
			Map<String, Preference> preferencesMap =
				new HashMap<String, Preference>();

			populateMap(xml, preferencesMap);

			return new PortletPreferencesImpl(
				companyId, ownerId, ownerType, plid, portletId, xml,
				preferencesMap);
		}
		catch (SystemException se) {
			throw se;
		}
	}

	@Override
	public PortalPreferencesImpl fromXML(
			long companyId, long ownerId, int ownerType, String xml)
		throws SystemException {

		try {
			Map<String, Preference> preferencesMap =
				new HashMap<String, Preference>();

			populateMap(xml, preferencesMap);

			return new PortalPreferencesImpl(
				companyId, ownerId, ownerType, xml, preferencesMap, false);
		}
		catch (SystemException se) {
			throw se;
		}
	}

	@Override
	public PortletPreferences getLayoutPortletSetup(
			Layout layout, String portletId)
		throws SystemException {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		if (PortletConstants.hasUserId(portletId)) {
			ownerId = PortletConstants.getUserId(portletId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		return PortletPreferencesLocalServiceUtil.getPreferences(
			layout.getCompanyId(), ownerId, ownerType, layout.getPlid(),
			portletId);
	}

	@Override
	public PortalPreferences getPortalPreferences(HttpServletRequest request)
		throws SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getPortalPreferences(
			request.getSession(), themeDisplay.getCompanyId(),
			themeDisplay.getUserId(), themeDisplay.isSignedIn());
	}

	@Override
	public PortalPreferences getPortalPreferences(
			HttpSession session, long companyId, long userId, boolean signedIn)
		throws SystemException {

		long ownerId = userId;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;

		PortalPreferences portalPreferences = null;

		if (signedIn) {
			PortalPreferencesWrapper portalPreferencesWrapper =
				(PortalPreferencesWrapper)
					PortalPreferencesLocalServiceUtil.getPreferences(
						companyId, ownerId, ownerType);

			portalPreferences =
				portalPreferencesWrapper.getPortalPreferencesImpl();
		}
		else {
			if (session != null) {
				portalPreferences = (PortalPreferences)session.getAttribute(
					WebKeys.PORTAL_PREFERENCES);
			}

			if (portalPreferences == null) {
				PortalPreferencesWrapper portalPreferencesWrapper =
					(PortalPreferencesWrapper)
						PortalPreferencesLocalServiceUtil.getPreferences(
							companyId, ownerId, ownerType);

				PortalPreferencesImpl portalPreferencesImpl =
					portalPreferencesWrapper.getPortalPreferencesImpl();

				portalPreferences =
					(PortalPreferences)portalPreferencesImpl.clone();

				if (session != null) {
					session.setAttribute(
						WebKeys.PORTAL_PREFERENCES, portalPreferences);
				}
			}
		}

		portalPreferences.setSignedIn(signedIn);
		portalPreferences.setUserId(userId);

		return portalPreferences;
	}

	@Override
	public PortalPreferences getPortalPreferences(
			long companyId, long userId, boolean signedIn)
		throws SystemException {

		return getPortalPreferences(null, companyId, userId, signedIn);
	}

	@Override
	public PortalPreferences getPortalPreferences(PortletRequest portletRequest)
		throws SystemException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getPortalPreferences(request);
	}

	@Override
	public PortletPreferences getPortletPreferences(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		PortletPreferencesIds portletPreferencesIds = getPortletPreferencesIds(
			request, portletId);

		return PortletPreferencesLocalServiceUtil.getPreferences(
			portletPreferencesIds);
	}

	@Override
	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, Layout layout, String portletId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = PortalUtil.getScopeGroupId(
			request, portletId, true);
		long userId = PortalUtil.getUserId(request);
		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		boolean modeEditGuest = false;

		String portletMode = ParamUtil.getString(request, "p_p_mode");

		if (portletMode.equals(LiferayPortletMode.EDIT_GUEST.toString()) ||
			((layoutTypePortlet != null) &&
			 layoutTypePortlet.hasModeEditGuestPortletId(portletId))) {

			modeEditGuest = true;
		}

		return getPortletPreferencesIds(
			scopeGroupId, userId, layout, portletId, modeEditGuest);
	}

	@Override
	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		return getPortletPreferencesIds(request, layout, portletId);
	}

	@Override
	public PortletPreferencesIds getPortletPreferencesIds(
			long scopeGroupId, long userId, Layout layout, String portletId,
			boolean modeEditGuest)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		String originalPortletId = portletId;

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			layout.getCompanyId(), portletId);

		long ownerId = 0;
		int ownerType = 0;
		long plid = 0;

		if (modeEditGuest) {
			boolean hasUpdateLayoutPermission = LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.UPDATE);

			if (!layout.isPrivateLayout() && hasUpdateLayoutPermission) {
			}
			else {

				// Only users with the correct permissions can update guest
				// preferences

				throw new PrincipalException();
			}
		}

		if (PortletConstants.hasUserId(originalPortletId) &&
			(PortletConstants.getUserId(originalPortletId) == userId)) {

			ownerId = userId;
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
			plid = layout.getPlid();
		}
		else if (portlet.isPreferencesCompanyWide()) {
			ownerId = layout.getCompanyId();
			ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
			plid = PortletKeys.PREFS_PLID_SHARED;
			portletId = PortletConstants.getRootPortletId(portletId);
		}
		else {
			if (portlet.isPreferencesUniquePerLayout()) {
				ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
				ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
				plid = layout.getPlid();

				if (portlet.isPreferencesOwnedByGroup()) {
				}
				else {
					if ((userId <= 0) || modeEditGuest) {
						userId = UserLocalServiceUtil.getDefaultUserId(
							layout.getCompanyId());
					}

					ownerId = userId;
					ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
				}
			}
			else {
				plid = PortletKeys.PREFS_PLID_SHARED;

				if (portlet.isPreferencesOwnedByGroup()) {
					ownerId = scopeGroupId;
					ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
					portletId = PortletConstants.getRootPortletId(portletId);
				}
				else {
					if ((userId <= 0) || modeEditGuest) {
						userId = UserLocalServiceUtil.getDefaultUserId(
							layout.getCompanyId());
					}

					ownerId = userId;
					ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
				}
			}
		}

		return new PortletPreferencesIds(
			layout.getCompanyId(), ownerId, ownerType, plid, portletId);
	}

	@Override
	public PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		return getPortletSetup(request, portletId, null);
	}

	@Override
	public PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId,
			String defaultPreferences)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = PortalUtil.getScopeGroupId(
			request, portletId, true);

		return getPortletSetup(
			scopeGroupId, themeDisplay.getLayout(), portletId,
			defaultPreferences);
	}

	@Override
	public PortletPreferences getPortletSetup(
			Layout layout, String portletId, String defaultPreferences)
		throws SystemException {

		return getPortletSetup(
			LayoutConstants.DEFAULT_PLID, layout, portletId,
			defaultPreferences);
	}

	@Override
	public PortletPreferences getPortletSetup(
			long scopeGroupId, Layout layout, String portletId,
			String defaultPreferences)
		throws SystemException {

		return getPortletSetup(
			scopeGroupId, layout, portletId, defaultPreferences, false);
	}

	@Override
	public PortletPreferences getPortletSetup(PortletRequest portletRequest)
		throws PortalException, SystemException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		String portletId = PortalUtil.getPortletId(portletRequest);

		return getPortletSetup(request, portletId);
	}

	@Override
	public PortletPreferences getPortletSetup(
			PortletRequest portletRequest, String portletId)
		throws PortalException, SystemException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getPortletSetup(request, portletId);
	}

	@Override
	public Map<Long, PortletPreferences> getPortletSetupMap(
			long companyId, long groupId, long ownerId, int ownerType,
			String portletId, boolean privateLayout)
		throws SystemException {

		Map<Long, PortletPreferences> portletSetupMap =
			new HashMap<Long, PortletPreferences>();

		List<com.liferay.portal.model.PortletPreferences>
			portletPreferencesList =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					companyId, groupId, ownerId, ownerType, portletId,
					privateLayout);

		for (com.liferay.portal.model.PortletPreferences portletPreferences :
				portletPreferencesList) {

			PortletPreferences portletSetup =
				PortletPreferencesLocalServiceUtil.getPreferences(
					companyId, ownerId, ownerType, portletPreferences.getPlid(),
					portletId);

			portletSetupMap.put(portletPreferences.getPlid(), portletSetup);
		}

		return portletSetupMap;
	}

	@Override
	public PortletPreferences getPreferences(HttpServletRequest request) {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletPreferences portletPreferences = null;

		if (portletRequest != null) {
			PortletPreferencesWrapper portletPreferencesWrapper =
				(PortletPreferencesWrapper)portletRequest.getPreferences();

			portletPreferences =
				portletPreferencesWrapper.getPortletPreferencesImpl();
		}

		return portletPreferences;
	}

	@Override
	public PreferencesValidator getPreferencesValidator(Portlet portlet) {
		return PortalUtil.getPreferencesValidator(portlet);
	}

	@Override
	public PortletPreferences getStrictLayoutPortletSetup(
			Layout layout, String portletId)
		throws SystemException {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		if (PortletConstants.hasUserId(portletId)) {
			ownerId = PortletConstants.getUserId(portletId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		return PortletPreferencesLocalServiceUtil.getStrictPreferences(
			layout.getCompanyId(), ownerId, ownerType, layout.getPlid(),
			portletId);
	}

	@Override
	public PortletPreferences getStrictPortletSetup(
			Layout layout, String portletId)
		throws SystemException {

		return getPortletSetup(
			LayoutConstants.DEFAULT_PLID, layout, portletId, StringPool.BLANK,
			true);
	}

	@Override
	public String toXML(PortalPreferences portalPreferences) {
		PortalPreferencesImpl portalPreferencesImpl =
			(PortalPreferencesImpl)portalPreferences;

		return portalPreferencesImpl.toXML();
	}

	@Override
	public String toXML(PortletPreferences portletPreferences) {
		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)portletPreferences;

		return portletPreferencesImpl.toXML();
	}

	protected PortletPreferences getPortletSetup(
			long scopeGroupId, Layout layout, String portletId,
			String defaultPreferences, boolean strictMode)
		throws SystemException {

		String originalPortletId = portletId;

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			layout.getCompanyId(), portletId);

		boolean uniquePerLayout = false;
		boolean uniquePerGroup = false;

		if (portlet.isPreferencesCompanyWide()) {
			portletId = PortletConstants.getRootPortletId(portletId);
		}
		else {
			if (portlet.isPreferencesUniquePerLayout()) {
				uniquePerLayout = true;

				if (portlet.isPreferencesOwnedByGroup()) {
					uniquePerGroup = true;
				}
			}
			else {
				if (portlet.isPreferencesOwnedByGroup()) {
					uniquePerGroup = true;
					portletId = PortletConstants.getRootPortletId(portletId);
				}
			}
		}

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
		long plid = layout.getPlid();

		Group group = GroupLocalServiceUtil.fetchGroup(scopeGroupId);

		if ((group != null) && group.isLayout()) {
			plid = group.getClassPK();
		}

		if (PortletConstants.hasUserId(originalPortletId)) {
			ownerId = PortletConstants.getUserId(originalPortletId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}
		else if (!uniquePerLayout) {
			plid = PortletKeys.PREFS_PLID_SHARED;

			if (uniquePerGroup) {
				if (scopeGroupId > LayoutConstants.DEFAULT_PLID) {
					ownerId = scopeGroupId;
				}
				else {
					ownerId = layout.getGroupId();
				}

				ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			}
			else {
				ownerId = layout.getCompanyId();
				ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
			}
		}

		if (strictMode) {
			return PortletPreferencesLocalServiceUtil.getStrictPreferences(
				layout.getCompanyId(), ownerId, ownerType, plid, portletId);
		}

		return PortletPreferencesLocalServiceUtil.getPreferences(
			layout.getCompanyId(), ownerId, ownerType, plid, portletId,
			defaultPreferences);
	}

	protected void populateMap(
			String xml, Map<String, Preference> preferencesMap)
		throws SystemException {

		if (Validator.isNull(xml)) {
			return;
		}

		XMLEventReader xmlEventReader = null;

		try {
			XMLInputFactory xmlInputFactory =
				StAXReaderUtil.getXMLInputFactory();

			xmlEventReader = xmlInputFactory.createXMLEventReader(
				new UnsyncStringReader(xml));

			while (xmlEventReader.hasNext()) {
				XMLEvent xmlEvent = xmlEventReader.nextEvent();

				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();

					String elementName = startElement.getName().getLocalPart();

					if (elementName.equals("preference")) {
						Preference preference = readPreference(xmlEventReader);

						preferencesMap.put(preference.getName(), preference);
					}
				}
			}
		}
		catch (XMLStreamException xse) {
			throw new SystemException(xse);
		}
		finally {
			if (xmlEventReader != null) {
				try {
					xmlEventReader.close();
				}
				catch (XMLStreamException xse) {
				}
			}
		}
	}

	protected Preference readPreference(XMLEventReader xmlEventReader)
		throws XMLStreamException {

		String name = null;
		List<String> values = new ArrayList<String>();
		boolean readOnly = false;

		while (xmlEventReader.hasNext()) {
			XMLEvent xmlEvent = xmlEventReader.nextEvent();

			if (xmlEvent.isStartElement()) {
				StartElement startElement = xmlEvent.asStartElement();

				String elementName = startElement.getName().getLocalPart();

				if (elementName.equals("name")) {
					name = StAXReaderUtil.read(xmlEventReader);
				}
				else if (elementName.equals("value")) {
					String value = StAXReaderUtil.read(xmlEventReader);

					values.add(value);
				}
				else if (elementName.equals("read-only")) {
					String value = StAXReaderUtil.read(xmlEventReader);

					readOnly = GetterUtil.getBoolean(value);
				}
			}
			else if (xmlEvent.isEndElement()) {
				EndElement endElement = xmlEvent.asEndElement();

				String elementName = endElement.getName().getLocalPart();

				if (elementName.equals("preference")) {
					break;
				}
			}
		}

		return new Preference(
			name, values.toArray(new String[values.size()]), readOnly);
	}

}