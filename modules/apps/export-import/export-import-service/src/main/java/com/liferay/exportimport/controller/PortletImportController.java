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

package com.liferay.exportimport.controller;

import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_FAILED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_STARTED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_SUCCEEDED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS;

import com.liferay.exportimport.lar.DeletionSystemEventImporter;
import com.liferay.exportimport.lar.LayoutCache;
import com.liferay.exportimport.lar.PermissionImporter;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessorRegistryUtil;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.PortletIdException;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.PortletItemLocalService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.PortletPreferencesLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetLinkLocalService;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.util.ExpandoConverterUtil;
import com.liferay.portlet.exportimport.LARFileException;
import com.liferay.portlet.exportimport.LARTypeException;
import com.liferay.portlet.exportimport.LayoutImportException;
import com.liferay.portlet.exportimport.MissingReferenceException;
import com.liferay.portlet.exportimport.controller.ExportImportController;
import com.liferay.portlet.exportimport.controller.ImportController;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.ExportImportThreadLocal;
import com.liferay.portlet.exportimport.lar.ManifestSummary;
import com.liferay.portlet.exportimport.lar.MissingReference;
import com.liferay.portlet.exportimport.lar.MissingReferences;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataContextFactoryUtil;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.portlet.exportimport.lar.UserIdStrategy;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleManager;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.staging.MergeLayoutPrototypesThreadLocal;

import java.io.File;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Zsigmond Rab
 * @author Douglas Wong
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portal.model.Portlet"},
	service = {ExportImportController.class, PortletImportController.class}
)
public class PortletImportController implements ImportController {

	@Override
	public void importDataDeletions(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws Exception {

		ZipReader zipReader = null;

		try {

			// LAR validation

			ExportImportThreadLocal.setPortletDataDeletionImportInProcess(true);

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			Map<String, String[]> parameterMap =
				(Map<String, String[]>)settingsMap.get("parameterMap");
			String portletId = MapUtil.getString(settingsMap, "portletId");
			long targetPlid = MapUtil.getLong(settingsMap, "targetPlid");
			long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

			Layout layout = _layoutLocalService.getLayout(targetPlid);

			zipReader = ZipReaderFactoryUtil.getZipReader(file);

			validateFile(
				layout.getCompanyId(), targetGroupId, portletId, zipReader);

			PortletDataContext portletDataContext = getPortletDataContext(
				exportImportConfiguration, file);

			boolean deletePortletData = MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);

			// Portlet data deletion

			if (deletePortletData) {
				if (_log.isDebugEnabled()) {
					_log.debug("Deleting portlet data");
				}

				deletePortletData(portletDataContext);
			}

			// Deletion system events

			populateDeletionStagedModelTypes(portletDataContext);

			_deletionSystemEventImporter.importDeletionSystemEvents(
				portletDataContext);
		}
		finally {
			ExportImportThreadLocal.setPortletDataDeletionImportInProcess(
				false);

			if (zipReader != null) {
				zipReader.close();
			}
		}
	}

	@Override
	public void importFile(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws Exception {

		PortletDataContext portletDataContext = null;

		try {
			ExportImportThreadLocal.setPortletImportInProcess(true);

			portletDataContext = getPortletDataContext(
				exportImportConfiguration, file);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_IMPORT_STARTED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			long userId = MapUtil.getLong(settingsMap, "userId");

			doImportPortletInfo(portletDataContext, userId);

			ExportImportThreadLocal.setPortletImportInProcess(false);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_IMPORT_SUCCEEDED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext),
				userId);
		}
		catch (Throwable t) {
			ExportImportThreadLocal.setPortletImportInProcess(false);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_IMPORT_FAILED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext),
				t);

			throw t;
		}
	}

	public String importPortletData(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, Element portletDataElement)
		throws Exception {

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(),
			portletDataContext.getPortletId());

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not import portlet data for " +
						portletDataContext.getPortletId() +
							" because the portlet does not exist");
			}

			return null;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if ((portletDataHandler == null) ||
			portletDataHandler.isDataPortletInstanceLevel()) {

			if (_log.isDebugEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("Do not import portlet data for ");
				sb.append(portletDataContext.getPortletId());
				sb.append(" because the portlet does not have a portlet data ");
				sb.append("handler");

				_log.debug(sb.toString());
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Importing data for " + portletDataContext.getPortletId());
		}

		PortletPreferencesImpl portletPreferencesImpl = null;

		if (portletPreferences != null) {
			portletPreferencesImpl =
				(PortletPreferencesImpl)
					PortletPreferencesFactoryUtil.fromDefaultXML(
						portletPreferences.getPreferences());
		}

		String portletData = portletDataContext.getZipEntryAsString(
			portletDataElement.attributeValue("path"));

		if (Validator.isNull(portletData)) {
			return null;
		}

		portletPreferencesImpl =
			(PortletPreferencesImpl)portletDataHandler.importData(
				portletDataContext, portletDataContext.getPortletId(),
				portletPreferencesImpl, portletData);

		if (portletPreferencesImpl == null) {
			return null;
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferencesImpl);
	}

	@Override
	public MissingReferences validateFile(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws Exception {

		ZipReader zipReader = null;

		try {
			ExportImportThreadLocal.setPortletValidationInProcess(true);

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			String portletId = MapUtil.getString(settingsMap, "portletId");
			long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
			long targetPlid = MapUtil.getLong(settingsMap, "targetPlid");

			Layout layout = _layoutLocalService.getLayout(targetPlid);

			zipReader = ZipReaderFactoryUtil.getZipReader(file);

			validateFile(
				layout.getCompanyId(), targetGroupId, portletId, zipReader);

			PortletDataContext portletDataContext = getPortletDataContext(
				exportImportConfiguration, file);

			MissingReferences missingReferences =
				ExportImportHelperUtil.validateMissingReferences(
					portletDataContext);

			Map<String, MissingReference> dependencyMissingReferences =
				missingReferences.getDependencyMissingReferences();

			if (!dependencyMissingReferences.isEmpty()) {
				throw new MissingReferenceException(missingReferences);
			}

			return missingReferences;
		}
		finally {
			ExportImportThreadLocal.setPortletValidationInProcess(false);

			if (zipReader != null) {
				zipReader.close();
			}
		}
	}

	protected void deletePortletData(PortletDataContext portletDataContext)
		throws Exception {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		PortletPreferences portletPreferences =
			PortletPreferencesUtil.fetchByO_O_P_P(
				ownerId, ownerType, portletDataContext.getPlid(),
				portletDataContext.getPortletId());

		if (portletPreferences == null) {
			portletPreferences =
				new com.liferay.portal.model.impl.PortletPreferencesImpl();
		}

		String xml = deletePortletData(portletDataContext, portletPreferences);

		if (xml != null) {
			_portletPreferencesLocalService.updatePreferences(
				ownerId, ownerType, portletDataContext.getPlid(),
				portletDataContext.getPortletId(), xml);
		}
	}

	protected String deletePortletData(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());

		if (!group.isStagedPortlet(portletDataContext.getPortletId())) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not delete portlet data for " +
						portletDataContext.getPortletId() +
							" because the portlet is not staged");
			}

			return null;
		}

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(),
			portletDataContext.getPortletId());

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not delete portlet data for " +
						portletDataContext.getPortletId() +
							" because the portlet does not exist");
			}

			return null;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
			if (_log.isDebugEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("Do not delete portlet data for ");
				sb.append(portletDataContext.getPortletId());
				sb.append(" because the portlet does not have a ");
				sb.append("PortletDataHandler");

				_log.debug(sb.toString());
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Deleting data for " + portletDataContext.getPortletId());
		}

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)
				PortletPreferencesFactoryUtil.fromDefaultXML(
					portletPreferences.getPreferences());

		try {
			portletPreferencesImpl =
				(PortletPreferencesImpl)portletDataHandler.deleteData(
					portletDataContext, portletDataContext.getPortletId(),
					portletPreferencesImpl);
		}
		finally {
			portletDataContext.setGroupId(portletDataContext.getScopeGroupId());
		}

		if (portletPreferencesImpl == null) {
			return null;
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferencesImpl);
	}

	protected void doImportPortletInfo(
			PortletDataContext portletDataContext, long userId)
		throws Exception {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		boolean importPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();

			serviceContext.setCompanyId(portletDataContext.getCompanyId());
			serviceContext.setSignedIn(false);
			serviceContext.setUserId(userId);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		// LAR validation

		validateFile(
			portletDataContext.getCompanyId(), portletDataContext.getGroupId(),
			portletDataContext.getPortletId(),
			portletDataContext.getZipReader());

		// Source and target group id

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		groupIds.put(
			portletDataContext.getSourceGroupId(),
			portletDataContext.getGroupId());

		// Manifest

		ManifestSummary manifestSummary =
			ExportImportHelperUtil.getManifestSummary(portletDataContext);

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"portlet", portletDataContext.getPortletId(), manifestSummary);
		}

		portletDataContext.setManifestSummary(manifestSummary);

		// Read expando tables, locks and permissions to make them
		// available to the data handlers through the portlet data context

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element portletElement = null;

		try {
			portletElement = rootElement.element("portlet");

			Document portletDocument = SAXReaderUtil.read(
				portletDataContext.getZipEntryAsString(
					portletElement.attributeValue("path")));

			portletElement = portletDocument.getRootElement();
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}

		LayoutCache layoutCache = new LayoutCache();

		if (importPermissions) {
			_permissionImporter.checkRoles(
				layoutCache, portletDataContext.getCompanyId(),
				portletDataContext.getGroupId(), userId, portletElement);

			_permissionImporter.readPortletDataPermissions(portletDataContext);
		}

		readExpandoTables(portletDataContext);
		readLocks(portletDataContext);

		Element portletDataElement = portletElement.element("portlet-data");

		Map<String, Boolean> importPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				portletDataContext.getCompanyId(),
				portletDataContext.getPortletId(), parameterMap,
				portletDataElement, manifestSummary);

		Layout layout = _layoutLocalService.getLayout(
			portletDataContext.getPlid());

		try {

			// Portlet preferences

			importPortletPreferences(
				portletDataContext, layout.getCompanyId(),
				portletDataContext.getGroupId(), layout, portletElement, true,
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_SETUP),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));

			// Portlet data

			if (importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA)) {

				if (_log.isDebugEnabled()) {
					_log.debug("Importing portlet data");
				}

				importPortletData(portletDataContext, portletDataElement);
			}
		}
		finally {
			resetPortletScope(
				portletDataContext, portletDataContext.getGroupId());
		}

		// Portlet permissions

		if (importPermissions) {
			if (_log.isDebugEnabled()) {
				_log.debug("Importing portlet permissions");
			}

			_permissionImporter.importPortletPermissions(
				layoutCache, portletDataContext.getCompanyId(),
				portletDataContext.getGroupId(), userId, layout, portletElement,
				portletDataContext.getPortletId());

			if (userId > 0) {
				Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					User.class);

				User user = _userLocalService.fetchUser(userId);

				indexer.reindex(user);
			}
		}

		// Asset links

		if (_log.isDebugEnabled()) {
			_log.debug("Importing asset links");
		}

		readAssetLinks(portletDataContext);

		// Deletion system events

		_deletionSystemEventImporter.importDeletionSystemEvents(
			portletDataContext);

		if (_log.isInfoEnabled()) {
			_log.info("Importing portlet takes " + stopWatch.getTime() + " ms");
		}

		// Service portlet preferences

		boolean importPortletSetup = importPortletControlsMap.get(
			PortletDataHandlerKeys.PORTLET_SETUP);

		if (importPortletSetup) {
			try {
				List<Element> serviceElements = rootElement.elements("service");

				for (Element serviceElement : serviceElements) {
					Document serviceDocument = SAXReaderUtil.read(
						portletDataContext.getZipEntryAsString(
							serviceElement.attributeValue("path")));

					importServicePortletPreferences(
						portletDataContext, serviceDocument.getRootElement());
				}
			}
			catch (DocumentException de) {
				throw new SystemException(de);
			}
		}

		ZipReader zipReader = portletDataContext.getZipReader();

		zipReader.close();
	}

	protected PortletDataContext getPortletDataContext(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");
		String portletId = MapUtil.getString(settingsMap, "portletId");
		long targetPlid = MapUtil.getLong(settingsMap, "targetPlid");
		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
		long userId = MapUtil.getLong(settingsMap, "userId");

		Layout layout = _layoutLocalService.getLayout(targetPlid);

		String userIdStrategyString = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		UserIdStrategy userIdStrategy =
			ExportImportHelperUtil.getUserIdStrategy(
				userId, userIdStrategyString);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				layout.getCompanyId(), targetGroupId, parameterMap,
				userIdStrategy, zipReader);

		portletDataContext.setOldPlid(targetPlid);
		portletDataContext.setPlid(targetPlid);
		portletDataContext.setPortletId(portletId);
		portletDataContext.setPrivateLayout(layout.isPrivateLayout());

		return portletDataContext;
	}

	protected PortletPreferences getPortletPreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String serviceName)
		throws PortalException {

		PortletPreferences portletPreferences = null;

		try {
			if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) ||
				(ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) ||
				(ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP)) {

				portletPreferences =
					_portletPreferencesLocalService.getPortletPreferences(
						ownerId, ownerType, LayoutConstants.DEFAULT_PLID,
						serviceName);
			}
			else {
				portletPreferences =
					_portletPreferencesLocalService.getPortletPreferences(
						ownerId, ownerType, plid, serviceName);
			}
		}
		catch (NoSuchPortletPreferencesException nsppe) {
			portletPreferences =
				_portletPreferencesLocalService.addPortletPreferences(
					companyId, ownerId, ownerType, plid, serviceName, null,
					null);
		}

		return portletPreferences;
	}

	protected int getProcessFlag() {
		if (ExportImportThreadLocal.isPortletStagingInProcess()) {
			return PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS;
		}

		return PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS;
	}

	protected void importPortletData(
			PortletDataContext portletDataContext, Element portletDataElement)
		throws Exception {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		PortletPreferences portletPreferences =
			PortletPreferencesUtil.fetchByO_O_P_P(
				ownerId, ownerType, portletDataContext.getPlid(),
				portletDataContext.getPortletId());

		if (portletPreferences == null) {
			portletPreferences =
				new com.liferay.portal.model.impl.PortletPreferencesImpl();
		}

		String xml = importPortletData(
			portletDataContext, portletPreferences, portletDataElement);

		if (Validator.isNotNull(xml)) {
			_portletPreferencesLocalService.updatePreferences(
				ownerId, ownerType, portletDataContext.getPlid(),
				portletDataContext.getPortletId(), xml);
		}
	}

	protected void importPortletPreferences(
			PortletDataContext portletDataContext, long companyId, long groupId,
			Layout layout, Element parentElement, boolean preserveScopeLayoutId,
			boolean importPortletArchivedSetups, boolean importPortletData,
			boolean importPortletSetup, boolean importPortletUserPreferences)
		throws Exception {

		long plid = LayoutConstants.DEFAULT_PLID;
		String scopeType = StringPool.BLANK;
		String scopeLayoutUuid = StringPool.BLANK;

		if (layout != null) {
			plid = layout.getPlid();

			if (preserveScopeLayoutId) {
				javax.portlet.PortletPreferences jxPortletPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						layout, portletDataContext.getPortletId());

				scopeType = GetterUtil.getString(
					jxPortletPreferences.getValue("lfrScopeType", null));
				scopeLayoutUuid = GetterUtil.getString(
					jxPortletPreferences.getValue("lfrScopeLayoutUuid", null));

				portletDataContext.setScopeType(scopeType);
				portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
			}
		}

		List<Element> portletPreferencesElements = parentElement.elements(
			"portlet-preferences");

		for (Element portletPreferencesElement : portletPreferencesElements) {
			String path = portletPreferencesElement.attributeValue("path");

			if (portletDataContext.isPathNotProcessed(path)) {
				String xml = null;

				Element element = null;

				try {
					xml = portletDataContext.getZipEntryAsString(path);

					Document preferencesDocument = SAXReaderUtil.read(xml);

					element = preferencesDocument.getRootElement();
				}
				catch (DocumentException de) {
					throw new SystemException(de);
				}

				long ownerId = GetterUtil.getLong(
					element.attributeValue("owner-id"));
				int ownerType = GetterUtil.getInteger(
					element.attributeValue("owner-type"));

				if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) ||
					!importPortletSetup) {

					continue;
				}

				if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) &&
					!importPortletArchivedSetups) {

					continue;
				}

				if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) &&
					(ownerId != PortletKeys.PREFS_OWNER_ID_DEFAULT) &&
					!importPortletUserPreferences) {

					continue;
				}

				long curPlid = plid;
				String curPortletId = portletDataContext.getPortletId();

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
					curPlid = PortletKeys.PREFS_PLID_SHARED;
					curPortletId = portletDataContext.getRootPortletId();
					ownerId = portletDataContext.getScopeGroupId();
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
					String userUuid = element.attributeValue(
						"archive-user-uuid");

					long userId = portletDataContext.getUserId(userUuid);

					String name = element.attributeValue("archive-name");

					curPortletId = portletDataContext.getRootPortletId();

					PortletItem portletItem =
						_portletItemLocalService.updatePortletItem(
							userId, groupId, name, curPortletId,
							PortletPreferences.class.getName());

					curPlid = LayoutConstants.DEFAULT_PLID;
					ownerId = portletItem.getPortletItemId();
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
					String userUuid = element.attributeValue("user-uuid");

					ownerId = portletDataContext.getUserId(userUuid);
				}

				boolean defaultUser = GetterUtil.getBoolean(
					element.attributeValue("default-user"));

				if (defaultUser) {
					ownerId = _userLocalService.getDefaultUserId(companyId);
				}

				javax.portlet.PortletPreferences jxPortletPreferences =
					PortletPreferencesFactoryUtil.fromXML(
						companyId, ownerId, ownerType, curPlid, curPortletId,
						xml);

				Element importDataRootElement =
					portletDataContext.getImportDataRootElement();

				try {
					Element preferenceDataElement =
						portletPreferencesElement.element("preference-data");

					if (preferenceDataElement != null) {
						portletDataContext.setImportDataRootElement(
							preferenceDataElement);
					}

					Portlet portlet = _portletLocalService.getPortletById(
						portletDataContext.getCompanyId(), curPortletId);

					ExportImportPortletPreferencesProcessor
						exportImportPortletPreferencesProcessor =
							ExportImportPortletPreferencesProcessorRegistryUtil.
								getExportImportPortletPreferencesProcessor(
									portlet.getRootPortletId());

					if (exportImportPortletPreferencesProcessor != null) {
						List<Capability> importCapabilities =
							exportImportPortletPreferencesProcessor.
								getImportCapabilities();

						if (ListUtil.isNotEmpty(importCapabilities)) {
							for (Capability importCapability :
									importCapabilities) {

								importCapability.process(
									portletDataContext, jxPortletPreferences);
							}
						}

						exportImportPortletPreferencesProcessor.
							processImportPortletPreferences(
								portletDataContext, jxPortletPreferences);
					}
					else {
						PortletDataHandler portletDataHandler =
							portlet.getPortletDataHandlerInstance();

						jxPortletPreferences =
							portletDataHandler.processImportPortletPreferences(
								portletDataContext, curPortletId,
								jxPortletPreferences);
					}
				}
				finally {
					portletDataContext.setImportDataRootElement(
						importDataRootElement);
				}

				updatePortletPreferences(
					portletDataContext, ownerId, ownerType, curPlid,
					curPortletId,
					PortletPreferencesFactoryUtil.toXML(jxPortletPreferences),
					importPortletData);
			}
		}

		if (preserveScopeLayoutId && (layout != null)) {
			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletDataContext.getPortletId());

			try {
				jxPortletPreferences.setValue("lfrScopeType", scopeType);
				jxPortletPreferences.setValue(
					"lfrScopeLayoutUuid", scopeLayoutUuid);

				jxPortletPreferences.store();
			}
			finally {
				portletDataContext.setScopeType(scopeType);
				portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
			}
		}
	}

	protected void importServicePortletPreferences(
			PortletDataContext portletDataContext, Element serviceElement)
		throws PortalException {

		long ownerId = GetterUtil.getLong(
			serviceElement.attributeValue("owner-id"));
		int ownerType = GetterUtil.getInteger(
			serviceElement.attributeValue("owner-type"));
		String serviceName = serviceElement.attributeValue("service-name");

		PortletPreferences portletPreferences = getPortletPreferences(
			portletDataContext.getCompanyId(), ownerId, ownerType,
			LayoutConstants.DEFAULT_PLID, serviceName);

		for (Attribute attribute : serviceElement.attributes()) {
			serviceElement.remove(attribute);
		}

		String xml = serviceElement.asXML();

		portletPreferences.setPreferences(xml);

		_portletPreferencesLocalService.updatePortletPreferences(
			portletPreferences);
	}

	protected void populateDeletionStagedModelTypes(
			PortletDataContext portletDataContext)
		throws Exception {

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(),
			portletDataContext.getPortletId());

		if ((portlet == null) || !portlet.isActive() ||
			portlet.isUndeployedPortlet()) {

			return;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
			return;
		}

		portletDataContext.addDeletionSystemEventStagedModelTypes(
			portletDataHandler.getDeletionSystemEventStagedModelTypes());
	}

	protected void readAssetLinks(PortletDataContext portletDataContext)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			ExportImportPathUtil.getSourceRootPath(portletDataContext) +
				"/links.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> assetLinkGroupElements = rootElement.elements(
			"asset-link-group");

		for (Element assetLinkGroupElement : assetLinkGroupElements) {
			String sourceUuid = assetLinkGroupElement.attributeValue(
				"source-uuid");

			AssetEntry sourceAssetEntry = _assetEntryLocalService.fetchEntry(
				portletDataContext.getScopeGroupId(), sourceUuid);

			if (sourceAssetEntry == null) {
				sourceAssetEntry = _assetEntryLocalService.fetchEntry(
					portletDataContext.getCompanyGroupId(), sourceUuid);
			}

			if (sourceAssetEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find asset entry with uuid " + sourceUuid);
				}

				continue;
			}

			List<Element> assetLinksElements = assetLinkGroupElement.elements(
				"asset-link");

			for (Element assetLinkElement : assetLinksElements) {
				String path = assetLinkElement.attributeValue("path");

				if (!portletDataContext.isPathNotProcessed(path)) {
					continue;
				}

				String targetUuid = assetLinkElement.attributeValue(
					"target-uuid");

				AssetEntry targetAssetEntry =
					_assetEntryLocalService.fetchEntry(
						portletDataContext.getScopeGroupId(), targetUuid);

				if (targetAssetEntry == null) {
					targetAssetEntry = _assetEntryLocalService.fetchEntry(
						portletDataContext.getCompanyGroupId(), targetUuid);
				}

				if (targetAssetEntry == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to find asset entry with uuid " +
								targetUuid);
					}

					continue;
				}

				AssetLink assetLink =
					(AssetLink)portletDataContext.getZipEntryAsObject(path);

				long userId = portletDataContext.getUserId(
					assetLink.getUserUuid());

				_assetLinkLocalService.updateLink(
					userId, sourceAssetEntry.getEntryId(),
					targetAssetEntry.getEntryId(), assetLink.getType(),
					assetLink.getWeight());
			}
		}
	}

	protected void readExpandoTables(PortletDataContext portletDataContext)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			ExportImportPathUtil.getSourceRootPath(portletDataContext) +
				"/expando-tables.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> expandoTableElements = rootElement.elements(
			"expando-table");

		for (Element expandoTableElement : expandoTableElements) {
			String className = expandoTableElement.attributeValue("class-name");

			ExpandoTable expandoTable = null;

			try {
				expandoTable = _expandoTableLocalService.getDefaultTable(
					portletDataContext.getCompanyId(), className);
			}
			catch (NoSuchTableException nste) {
				expandoTable = _expandoTableLocalService.addDefaultTable(
					portletDataContext.getCompanyId(), className);
			}

			List<Element> expandoColumnElements = expandoTableElement.elements(
				"expando-column");

			for (Element expandoColumnElement : expandoColumnElements) {
				long columnId = GetterUtil.getLong(
					expandoColumnElement.attributeValue("column-id"));
				String name = expandoColumnElement.attributeValue("name");
				int type = GetterUtil.getInteger(
					expandoColumnElement.attributeValue("type"));
				String defaultData = expandoColumnElement.elementText(
					"default-data");
				String typeSettings = expandoColumnElement.elementText(
					"type-settings");

				Serializable defaultDataObject =
					ExpandoConverterUtil.getAttributeFromString(
						type, defaultData);

				ExpandoColumn expandoColumn =
					_expandoColumnLocalService.getColumn(
						expandoTable.getTableId(), name);

				if (expandoColumn != null) {
					_expandoColumnLocalService.updateColumn(
						expandoColumn.getColumnId(), name, type,
						defaultDataObject);
				}
				else {
					expandoColumn = _expandoColumnLocalService.addColumn(
						expandoTable.getTableId(), name, type,
						defaultDataObject);
				}

				_expandoColumnLocalService.updateTypeSettings(
					expandoColumn.getColumnId(), typeSettings);

				portletDataContext.importPermissions(
					ExpandoColumn.class, columnId, expandoColumn.getColumnId());
			}
		}
	}

	protected void readLocks(PortletDataContext portletDataContext)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			ExportImportPathUtil.getSourceRootPath(portletDataContext) +
				"/locks.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> assetElements = rootElement.elements("asset");

		for (Element assetElement : assetElements) {
			String path = assetElement.attributeValue("path");
			String className = assetElement.attributeValue("class-name");
			String key = assetElement.attributeValue("key");

			Lock lock = (Lock)portletDataContext.getZipEntryAsObject(path);

			if (lock != null) {
				portletDataContext.addLocks(className, key, lock);
			}
		}
	}

	protected void resetPortletScope(
		PortletDataContext portletDataContext, long groupId) {

		portletDataContext.setScopeGroupId(groupId);
		portletDataContext.setScopeLayoutUuid(StringPool.BLANK);
		portletDataContext.setScopeType(StringPool.BLANK);
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetLinkLocalService(
		AssetLinkLocalService assetLinkLocalService) {

		_assetLinkLocalService = assetLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoColumnLocalService(
		ExpandoColumnLocalService expandoColumnLocalService) {

		_expandoColumnLocalService = expandoColumnLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoTableLocalService(
		ExpandoTableLocalService expandoTableLocalService) {

		_expandoTableLocalService = expandoTableLocalService;
	}

	@Reference(unbind = "-")
	protected void setExportImportLifecycleManager(
		ExportImportLifecycleManager exportImportLifecycleManager) {

		_exportImportLifecycleManager = exportImportLifecycleManager;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletItemLocalService(
		PortletItemLocalService portletItemLocalService) {

		_portletItemLocalService = portletItemLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void updatePortletPreferences(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			long plid, String portletId, String xml, boolean importData)
		throws Exception {

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(), portletId);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not update portlet preferences for " + portletId +
						" because the portlet does not exist");
			}

			return;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (importData || !MergeLayoutPrototypesThreadLocal.isInProgress()) {
			_portletPreferencesLocalService.updatePreferences(
				ownerId, ownerType, plid, portletId, xml);

			return;
		}

		// Portlet preferences to be updated only when importing data

		String[] dataPortletPreferences =
			portletDataHandler.getDataPortletPreferences();

		// Current portlet preferences

		javax.portlet.PortletPreferences portletPreferences =
			_portletPreferencesLocalService.getPreferences(
				portletDataContext.getCompanyId(), ownerId, ownerType, plid,
				portletId);

		// New portlet preferences

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				portletDataContext.getCompanyId(), ownerId, ownerType, plid,
				portletId, xml);

		Enumeration<String> enu = jxPortletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			String scopeLayoutUuid = portletDataContext.getScopeLayoutUuid();
			String scopeType = portletDataContext.getScopeType();

			if (!ArrayUtil.contains(dataPortletPreferences, name) ||
				(Validator.isNull(scopeLayoutUuid) &&
				 scopeType.equals("company"))) {

				String[] values = jxPortletPreferences.getValues(name, null);

				portletPreferences.setValues(name, values);
			}
		}

		_portletPreferencesLocalService.updatePreferences(
			ownerId, ownerType, plid, portletId, portletPreferences);
	}

	protected void validateFile(
			long companyId, long groupId, String portletId, ZipReader zipReader)
		throws Exception {

		// XML

		String xml = zipReader.getEntryAsString("/manifest.xml");

		if (xml == null) {
			throw new LARFileException("manifest.xml not found in the LAR");
		}

		Element rootElement = null;

		try {
			Document document = SAXReaderUtil.read(xml);

			rootElement = document.getRootElement();
		}
		catch (Exception e) {
			throw new LARFileException(e);
		}

		// Build compatibility

		int buildNumber = ReleaseInfo.getBuildNumber();

		Element headerElement = rootElement.element("header");

		int importBuildNumber = GetterUtil.getInteger(
			headerElement.attributeValue("build-number"));

		if (buildNumber != importBuildNumber) {
			throw new LayoutImportException(
				"LAR build number " + importBuildNumber + " does not match " +
					"portal build number " + buildNumber);
		}

		// Type

		String larType = headerElement.attributeValue("type");

		if (!larType.equals("portlet")) {
			throw new LARTypeException(larType);
		}

		// Portlet compatibility

		String rootPortletId = headerElement.attributeValue("root-portlet-id");

		if (!PortletConstants.getRootPortletId(portletId).equals(
				rootPortletId)) {

			throw new PortletIdException("Invalid portlet id " + rootPortletId);
		}

		// Available locales

		Portlet portlet = _portletLocalService.getPortletById(
			companyId, portletId);

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler.isDataLocalized()) {
			List<Locale> sourceAvailableLocales = Arrays.asList(
				LocaleUtil.fromLanguageIds(
					StringUtil.split(
						headerElement.attributeValue("available-locales"))));

			for (Locale sourceAvailableLocale : sourceAvailableLocales) {
				if (!LanguageUtil.isAvailableLocale(
						PortalUtil.getSiteGroupId(groupId),
						sourceAvailableLocale)) {

					LocaleException le = new LocaleException(
						LocaleException.TYPE_EXPORT_IMPORT,
						"Locale " + sourceAvailableLocale + " is not " +
							"available in company " + companyId);

					le.setSourceAvailableLocales(sourceAvailableLocales);
					le.setTargetAvailableLocales(
						LanguageUtil.getAvailableLocales(
							PortalUtil.getSiteGroupId(groupId)));

					throw le;
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletImportController.class);

	private AssetEntryLocalService _assetEntryLocalService;
	private AssetLinkLocalService _assetLinkLocalService;
	private final DeletionSystemEventImporter _deletionSystemEventImporter =
		DeletionSystemEventImporter.getInstance();
	private ExpandoColumnLocalService _expandoColumnLocalService;
	private ExpandoTableLocalService _expandoTableLocalService;
	private ExportImportLifecycleManager _exportImportLifecycleManager;
	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private final PermissionImporter _permissionImporter =
		PermissionImporter.getInstance();
	private PortletItemLocalService _portletItemLocalService;
	private PortletLocalService _portletLocalService;
	private PortletPreferencesLocalService _portletPreferencesLocalService;
	private UserLocalService _userLocalService;

}