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

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplate;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasePortletDataHandler implements PortletDataHandler {

	@Override
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

	@Override
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
			portletDataContext.addDeletionSystemEventStagedModelTypes(
				getDeletionSystemEventStagedModelTypes());

			for (PortletDataHandlerControl portletDataHandlerControl :
					getExportControls()) {

				addUncheckedModelAdditionCount(
					portletDataContext, portletDataHandlerControl);
			}

			if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
				PortletDataContext clonePortletDataContext =
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext);

				prepareManifestSummary(
					clonePortletDataContext, portletPreferences);

				PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
					"portlet", portletId,
					clonePortletDataContext.getManifestSummary());
			}

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

	@Override
	public DataLevel getDataLevel() {
		return _dataLevel;
	}

	@Override
	public String[] getDataPortletPreferences() {
		return _dataPortletPreferences;
	}

	@Override
	public StagedModelType[] getDeletionSystemEventStagedModelTypes() {
		return _deletionSystemEventStagedModelTypes;
	}

	@Override
	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet,
			boolean privateLayout)
		throws Exception {

		return getExportConfigurationControls(
			companyId, groupId, portlet, -1, privateLayout);
	}

	@Override
	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet, long plid,
			boolean privateLayout)
		throws Exception {

		List<PortletDataHandlerBoolean> configurationControls =
			new ArrayList<PortletDataHandlerBoolean>();

		// Setup

		if ((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portlet, false)
				> 0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP,
				portlet.getRootPortletId(), false) > 0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				portlet.getRootPortletId(), false) > 0)) {

				configurationControls.add(
					new PortletDataHandlerBoolean(
						null, PortletDataHandlerKeys.PORTLET_SETUP, "setup",
						true, false, null, null, null));
		}

		// Archived setups

		if (PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				-1, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED,
				portlet.getRootPortletId(), false) > 0) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
					"archived-setups", true, false, null, null, null)
			);
		}

		// User preferences

		if ((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				-1, PortletKeys.PREFS_OWNER_TYPE_USER, plid, portlet, false)
				> 0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				groupId, PortletKeys.PREFS_OWNER_TYPE_USER,
				PortletKeys.PREFS_PLID_SHARED, portlet, false) > 0)) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
					"user-preferences", true, false, null, null, null));
		}

		return configurationControls.toArray(
			new PortletDataHandlerBoolean[configurationControls.size()]);
	}

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return _exportControls;
	}

	@Override
	public PortletDataHandlerControl[] getExportMetadataControls() {
		return _exportMetadataControls;
	}

	@Override
	public long getExportModelCount(ManifestSummary manifestSummary) {
		return getExportModelCount(manifestSummary, getExportControls());
	}

	@Override
	public PortletDataHandlerControl[] getImportConfigurationControls(
		Portlet portlet, ManifestSummary manifestSummary) {

		String[] configurationPortletOptions =
			manifestSummary.getConfigurationPortletOptions(
				portlet.getRootPortletId());

		return getImportConfigurationControls(configurationPortletOptions);
	}

	@Override
	public PortletDataHandlerControl[] getImportConfigurationControls(
		String[] configurationPortletOptions) {

		List<PortletDataHandlerBoolean> configurationControls =
			new ArrayList<PortletDataHandlerBoolean>();

		// Setup

		if (ArrayUtil.contains(configurationPortletOptions, "setup")) {
			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_SETUP, "setup", true,
					false, null, null, null));
		}

		// Archived setups

		if (ArrayUtil.contains(
				configurationPortletOptions, "archived-setups")) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
					"archived-setups", true, false, null, null, null));
		}

		// User preferences

		if (ArrayUtil.contains(
				configurationPortletOptions, "user-preferences")) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
					"user-preferences", true, false, null, null, null));
		}

		return configurationControls.toArray(
			new PortletDataHandlerBoolean[configurationControls.size()]);
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return _importControls;
	}

	@Override
	public PortletDataHandlerControl[] getImportMetadataControls() {
		return _importMetadataControls;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
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
				addImportDataRootElement(portletDataContext, data);
			}

			portletDataContext.addDeletionSystemEventStagedModelTypes(
				getDeletionSystemEventStagedModelTypes());

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

	@Override
	public boolean isDataLocalized() {
		return _dataLocalized;
	}

	@Override
	public boolean isDataPortalLevel() {
		return _dataLevel.equals(DataLevel.PORTAL);
	}

	@Override
	public boolean isDataPortletInstanceLevel() {
		return _dataLevel.equals(DataLevel.PORTLET_INSTANCE);
	}

	@Override
	public boolean isDataSiteLevel() {
		return _dataLevel.equals(DataLevel.SITE);
	}

	@Override
	public boolean isDisplayPortlet() {
		if (isDataPortletInstanceLevel() &&
			!ArrayUtil.isEmpty(getDataPortletPreferences())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return _publishToLiveByDefault;
	}

	@Override
	public boolean isSupportsDataStrategyCopyAsNew() {
		return _supportsDataStrategyCopyAsNew;
	}

	@Override
	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws PortletDataException {

		prepareManifestSummary(portletDataContext, null);
	}

	@Override
	public void prepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			doPrepareManifestSummary(portletDataContext, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		String displayStyle = getDisplayTemplate(
			portletDataContext, portletId, portletPreferences);

		if (Validator.isNotNull(displayStyle) &&
			displayStyle.startsWith(
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX)) {

			long displayStyleGroupId = getDisplayTemplateGroupId(
				portletDataContext, portletId, portletPreferences);

			long previousScopeGroupId = portletDataContext.getScopeGroupId();

			if (displayStyleGroupId != portletDataContext.getScopeGroupId()) {
				portletDataContext.setScopeGroupId(displayStyleGroupId);
			}

			DDMTemplate ddmTemplate =
				PortletDisplayTemplateUtil.fetchDDMTemplate(
					portletDataContext.getGroupId(), displayStyle);

			if (ddmTemplate != null) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, portletId, ddmTemplate);
			}

			portletDataContext.setScopeGroupId(previousScopeGroupId);
		}

		try {
			return doProcessExportPortletPreferences(
				portletDataContext, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			String displayStyle = getDisplayTemplate(
				portletDataContext, portletId, portletPreferences);

			if (Validator.isNotNull(displayStyle) &&
				displayStyle.startsWith(
					PortletDisplayTemplate.DISPLAY_STYLE_PREFIX)) {

				DDMTemplate ddmTemplate = null;

				long displayStyleGroupId = getDisplayTemplateGroupId(
					portletDataContext, portletId, portletPreferences);

				if (displayStyleGroupId ==
						portletDataContext.getCompanyGroupId()) {

					ddmTemplate = PortletDisplayTemplateUtil.fetchDDMTemplate(
						portletDataContext.getCompanyGroupId(), displayStyle);
				}
				else if (displayStyleGroupId ==
							portletDataContext.getSourceGroupId()) {

					ddmTemplate = PortletDisplayTemplateUtil.fetchDDMTemplate(
						portletDataContext.getScopeGroupId(), displayStyle);
				}
				else {
					ddmTemplate = PortletDisplayTemplateUtil.fetchDDMTemplate(
						displayStyleGroupId, displayStyle);
				}

				long importedDisplayStyleGroupId =
					portletDataContext.getScopeGroupId();

				if (ddmTemplate == null) {
					String ddmTemplateUuid =
						PortletDisplayTemplateUtil.getDDMTemplateUuid(
							displayStyle);

					Element ddmTemplateElement =
						portletDataContext.getImportDataElement(
							DDMTemplate.class.getSimpleName(), "uuid",
							ddmTemplateUuid);

					String ddmTemplatePath = ddmTemplateElement.attributeValue(
						"path");

					ddmTemplate =
						(DDMTemplate)portletDataContext.getZipEntryAsObject(
							ddmTemplatePath);

					if (ddmTemplate != null) {
						StagedModelDataHandlerUtil.importStagedModel(
							portletDataContext, ddmTemplate);
					}
				}
				else {
					importedDisplayStyleGroupId = ddmTemplate.getGroupId();
				}

				portletPreferences.setValue(
					"displayStyleGroupId",
					String.valueOf(importedDisplayStyleGroupId));
			}
			else {
				portletPreferences.setValue(
					"displayStyleGroupId", StringPool.BLANK);
			}

			return doProcessImportPortletPreferences(
				portletDataContext, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	protected Element addExportDataRootElement(
		PortletDataContext portletDataContext) {

		Document document = SAXReaderUtil.createDocument();

		Class<?> clazz = getClass();

		Element rootElement = document.addElement(clazz.getSimpleName());

		portletDataContext.setExportDataRootElement(rootElement);

		return rootElement;
	}

	protected Element addImportDataRootElement(
			PortletDataContext portletDataContext, String data)
		throws DocumentException {

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		portletDataContext.setImportDataRootElement(rootElement);

		long groupId = GetterUtil.getLong(
			rootElement.attributeValue("group-id"));

		if (groupId != 0) {
			portletDataContext.setSourceGroupId(groupId);
		}

		return rootElement;
	}

	protected void addUncheckedModelAdditionCount(
		PortletDataContext portletDataContext,
		PortletDataHandlerControl portletDataHandlerControl) {

		if (!(portletDataHandlerControl instanceof PortletDataHandlerBoolean)) {
			return;
		}

		PortletDataHandlerBoolean portletDataHandlerBoolean =
			(PortletDataHandlerBoolean)portletDataHandlerControl;

		PortletDataHandlerControl[] childPortletDataHandlerControls =
			portletDataHandlerBoolean.getChildren();

		if (childPortletDataHandlerControls != null) {
			for (PortletDataHandlerControl childPortletDataHandlerControl :
					childPortletDataHandlerControls) {

				addUncheckedModelAdditionCount(
					portletDataContext, childPortletDataHandlerControl);
			}
		}

		if (Validator.isNull(portletDataHandlerControl.getClassName())) {
			return;
		}

		boolean checkedControl = GetterUtil.getBoolean(
			portletDataContext.getBooleanParameter(
				portletDataHandlerControl.getNamespace(),
				portletDataHandlerControl.getControlName(), false));

		if (!checkedControl) {
			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			String manifestSummaryKey = ManifestSummary.getManifestSummaryKey(
				portletDataHandlerControl.getClassName(),
				portletDataHandlerBoolean.getReferrerClassName());

			manifestSummary.addModelAdditionCount(manifestSummaryKey, 0);
		}
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
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {
	}

	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return portletPreferences;
	}

	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return portletPreferences;
	}

	protected String getDisplayTemplate(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if (Validator.isNotNull(portlet.getTemplateHandlerClass())) {
				return portletPreferences.getValue("displayStyle", null);
			}
		}
		catch (Exception e) {
		}

		return null;
	}

	protected long getDisplayTemplateGroupId(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if (Validator.isNotNull(portlet.getTemplateHandlerClass())) {
				return GetterUtil.getLong(
					portletPreferences.getValue("displayStyleGroupId", null));
			}
		}
		catch (Exception e) {
		}

		return 0;
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

	protected long getExportModelCount(
		ManifestSummary manifestSummary,
		PortletDataHandlerControl[] portletDataHandlerControls) {

		long totalModelCount = -1;

		for (PortletDataHandlerControl portletDataHandlerControl :
				portletDataHandlerControls) {

			long modelCount = manifestSummary.getModelAdditionCount(
				portletDataHandlerControl.getClassName(),
				portletDataHandlerControl.getReferrerClassName());

			if (portletDataHandlerControl
					instanceof PortletDataHandlerBoolean) {

				PortletDataHandlerBoolean portletDataHandlerBoolean =
					(PortletDataHandlerBoolean)portletDataHandlerControl;

				PortletDataHandlerControl[] childPortletDataHandlerControls =
					portletDataHandlerBoolean.getChildren();

				if (childPortletDataHandlerControls != null) {
					long childModelCount = getExportModelCount(
						manifestSummary, childPortletDataHandlerControls);

					if (childModelCount != -1) {
						if (modelCount == -1) {
							modelCount = childModelCount;
						}
						else {
							modelCount += childModelCount;
						}
					}
				}
			}

			if (modelCount == -1) {
				continue;
			}

			if (totalModelCount == -1) {
				totalModelCount = modelCount;
			}
			else {
				totalModelCount += modelCount;
			}
		}

		return totalModelCount;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	protected void setAlwaysExportable(boolean alwaysExportable) {
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	protected void setAlwaysStaged(boolean alwaysStaged) {
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

	protected void setDeletionSystemEventStagedModelTypes(
		StagedModelType... deletionSystemEventStagedModelTypes) {

		_deletionSystemEventStagedModelTypes =
			deletionSystemEventStagedModelTypes;
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

	protected void setSupportsDataStrategyCopyAsNew(
		boolean supportsDataStrategyCopyAsNew) {

		_supportsDataStrategyCopyAsNew = supportsDataStrategyCopyAsNew;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BasePortletDataHandler.class);

	private DataLevel _dataLevel = DataLevel.SITE;
	private boolean _dataLocalized;
	private String[] _dataPortletPreferences = StringPool.EMPTY_ARRAY;
	private StagedModelType[] _deletionSystemEventStagedModelTypes =
		new StagedModelType[0];
	private PortletDataHandlerControl[] _exportControls =
		new PortletDataHandlerControl[0];
	private PortletDataHandlerControl[] _exportMetadataControls =
		new PortletDataHandlerControl[0];
	private PortletDataHandlerControl[] _importControls =
		new PortletDataHandlerControl[0];
	private PortletDataHandlerControl[] _importMetadataControls =
		new PortletDataHandlerControl[0];
	private String _portletId;
	private boolean _publishToLiveByDefault;
	private boolean _supportsDataStrategyCopyAsNew = true;

}