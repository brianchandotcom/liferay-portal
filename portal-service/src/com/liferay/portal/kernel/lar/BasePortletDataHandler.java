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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasePortletDataHandler implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Deleting portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		try {
			return doDeleteData(
				portletDataContext, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
		finally {
			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info("Deleted portlet in " + Time.getDuration(duration));
			}
		}
	}

	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Exporting portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		try {
			return doExportData(
				portletDataContext, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
		finally {
			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info("Exported portlet in " + Time.getDuration(duration));
			}
		}
	}

	public String[] getDataPortletPreferences() {
		return new String[0];
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[0];
	}

	public PortletDataHandlerControl[] getExportMetadataControls() {
		return new PortletDataHandlerControl[0];
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[0];
	}

	public PortletDataHandlerControl[] getImportMetadataControls() {
		return new PortletDataHandlerControl[0];
	}

	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Importing portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		long sourceGroupId = portletDataContext.getSourceGroupId();

		try {
			if (Validator.isXml(data)) {
				Document document = SAXReaderUtil.read(data);

				Element rootElement = document.getRootElement();

				long portletSourceGroupId = GetterUtil.getLong(
					rootElement.attributeValue("group-id"));

				if (portletSourceGroupId != 0) {
					portletDataContext.setSourceGroupId(portletSourceGroupId);
				}
			}

			return doImportData(
				portletDataContext, portletId, portletPreferences, data);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
		finally {
			portletDataContext.setSourceGroupId(sourceGroupId);

			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info("Imported portlet in " + Time.getDuration(duration));
			}
		}
	}

	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	public boolean isAlwaysStaged() {
		return _ALWAYS_STAGED;
	}

	public boolean isDataLocalized() {
		return _DATA_LOCALIZED;
	}

	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return null;
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return null;
	}

	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		return null;
	}

	private static final boolean _ALWAYS_EXPORTABLE = false;

	private static final boolean _ALWAYS_STAGED = false;

	private static final boolean _DATA_LOCALIZED = false;

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = false;

	private static Log _log = LogFactoryUtil.getLog(
		BasePortletDataHandler.class);

}