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
package com.liferay.portlet.portalsettings.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Enumeration;

import javax.portlet.PortletPreferences;

/**
 * This DataHandler class is used to import and export Portal Settings
 *
 * @author kamesh
 *
 */
public class PortalSettingsDataHandlerImpl extends BasePortletDataHandler {

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.kernel.lar.BasePortletDataHandler#getExportControls()
	 */
	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] { _company_preferences };
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.kernel.lar.BasePortletDataHandler#getImportControls()
	 */
	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] { _company_preferences };
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.kernel.lar.BasePortletDataHandler#isAlwaysExportable()
	 */
	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.kernel.lar.BasePortletDataHandler#isPublishToLiveByDefault
	 * ()
	 */
	@Override
	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.kernel.lar.BasePortletDataHandler#doExportData(com
	 * .liferay.portal.kernel.lar.PortletDataContext, java.lang.String,
	 * javax.portlet.PortletPreferences)
	 */
	@Override
	protected String doExportData(PortletDataContext portletDataContext,
			String portletId, PortletPreferences portletPreferences)
			throws Exception {
		_log.info("Exporting Data - PortletPreferences : "
				+ portletPreferences.getMap());
		Document document = SAXReaderUtil.createDocument();
		long companyId = portletDataContext.getCompanyId();
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
		long ownerId = companyId;
		PortletPreferences companyPreferences = PortalPreferencesLocalServiceUtil
				.getPreferences(companyId, ownerId, ownerType);
		_log.info("Data to be exported : " + companyPreferences);
		Element rootElement = document.addElement("portal-preferences-data");
		rootElement.addAttribute("owner-id", String.valueOf(companyId));
		rootElement.addAttribute("owner-type", String.valueOf(ownerType));
		Enumeration<String> prefNames = portletPreferences.getNames();
		while (prefNames.hasMoreElements()) {
			Element preferencesElement = rootElement.addElement("preferences");
			String prefName = prefNames.nextElement();
			String prefValue = companyPreferences.getValue(prefName, "");
			preferencesElement.addAttribute(prefName, prefValue);
		}

		return document.formattedString();
	}

	protected String getSettingsPath(PortletDataContext portletDataContext,
			String prefName) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext
				.getPortletPath(PortletKeys.PORTAL_SETTINGS));
		sb.append("/preferences/");
		sb.append(prefName);
		sb.append(".xml");

		return sb.toString();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see
	 * com.liferay.portal.kernel.lar.BasePortletDataHandler#doImportData(com
	 * .liferay.portal.kernel.lar.PortletDataContext, java.lang.String,
	 * javax.portlet.PortletPreferences, java.lang.String)
	 */
	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
			throws Exception {
		_log.info("Importing Data");
		return super.doImportData(portletDataContext, portletId,
				portletPreferences, data);
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "portal_preferences";

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static PortletDataHandlerBoolean _company_preferences = new PortletDataHandlerBoolean(
			_NAMESPACE, "company_preferences", true, true);

	private static Log _log = LogFactoryUtil
			.getLog(PortalSettingsDataHandlerImpl.class);

}