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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;

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

	public DataLevel getDataLevel() {
		return _dataLevel;
	}

	public String[] getDataPortletPreferences() {
		return _dataPortletPreferences;
	}

	public PortletDataHandlerControl[] getExportControls() {
		return _exportControls;
	}

	public PortletDataHandlerControl[] getExportMetadataControls() {
		return _exportMetadataControls;
	}

	public PortletDataHandlerControl[] getImportControls() {
		return _importControls;
	}

	public PortletDataHandlerControl[] getImportMetadataControls() {
		return _importMetadataControls;
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

				portletDataContext.setImportDataRootElement(rootElement);

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
		return _alwaysExportable;
	}

	public boolean isAlwaysStaged() {
		return _alwaysStaged;
	}

	public boolean isDataLocalized() {
		return _dataLocalized;
	}

	public boolean isDataPortalLevel() {
		return _dataLevel.equals(DataLevel.PORTAL);
	}

	public boolean isDataPortletInstanceLevel() {
		return _dataLevel.equals(DataLevel.PORTLET_INSTANCE);
	}

	public boolean isDataSiteLevel() {
		return _dataLevel.equals(DataLevel.SITE);
	}

	public boolean isPublishToLiveByDefault() {
		return _publishToLiveByDefault;
	}

	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws PortletDataException {

		try {
			doPrepareManifestSummary(portletDataContext);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected Element addExportDataRootElement(
		PortletDataContext portletDataContext) {

		Document document = SAXReaderUtil.createDocument();

		Class<?> clazz = getClass();

		Element rootElement = document.addElement(clazz.getSimpleName());

		portletDataContext.setExportDataRootElement(rootElement);

		return rootElement;
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return portletPreferences;
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

	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {
	}

	protected String getExportDataRootElementString(Element rootElement) {
		if (rootElement == null) {
			return StringPool.BLANK;
		}

		try {
			Document document = rootElement.getDocument();

			return document.formattedString();
		}
		catch (IOException ioe) {
			return StringPool.BLANK;
		}
	}

	protected void setAlwaysExportable(boolean alwaysExportable) {
		_alwaysExportable = alwaysExportable;
	}

	protected void setAlwaysStaged(boolean alwaysStaged) {
		_alwaysStaged = alwaysStaged;
	}

	protected void setDataLevel(DataLevel dataLevel) {
		_dataLevel = dataLevel;
	}

	protected void setDataLocalized(boolean dataLocalized) {
		_dataLocalized = dataLocalized;
	}

	protected void setDataPortletPreferences(String... dataPortletPreferences) {
		_dataPortletPreferences = dataPortletPreferences;
	}

	protected void setExportControls(
		PortletDataHandlerControl... exportControls) {

		_exportControls = exportControls;

		setImportControls(exportControls);
	}

	protected void setExportMetadataControls(
		PortletDataHandlerControl... exportMetadataControls) {

		_exportMetadataControls = exportMetadataControls;

		setImportMetadataControls(exportMetadataControls);
	}

	protected void setImportControls(
		PortletDataHandlerControl... importControls) {

		_importControls = importControls;
	}

	protected void setImportMetadataControls(
		PortletDataHandlerControl... importMetadataControls) {

		_importMetadataControls = importMetadataControls;
	}

	protected void setPublishToLiveByDefault(boolean publishToLiveByDefault) {
		_publishToLiveByDefault = publishToLiveByDefault;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BasePortletDataHandler.class);

	private boolean _alwaysExportable;
	private boolean _alwaysStaged;
	private DataLevel _dataLevel = DataLevel.SITE;
	private boolean _dataLocalized;
	private String[] _dataPortletPreferences = new String[0];
	private PortletDataHandlerControl[] _exportControls =
		new PortletDataHandlerControl[0];
	private PortletDataHandlerControl[] _exportMetadataControls =
		new PortletDataHandlerControl[0];
	private PortletDataHandlerControl[] _importControls =
		new PortletDataHandlerControl[0];
	private PortletDataHandlerControl[] _importMetadataControls =
		new PortletDataHandlerControl[0];
	private boolean _publishToLiveByDefault;

}