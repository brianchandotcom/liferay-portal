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

package com.liferay.portlet.exportimport.service.impl;

import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationFactory;
import com.liferay.exportimport.kernel.controller.ExportController;
import com.liferay.exportimport.kernel.controller.ExportImportControllerRegistryUtil;
import com.liferay.exportimport.kernel.controller.ImportController;
import com.liferay.exportimport.kernel.exception.ExportImportIOException;
import com.liferay.exportimport.kernel.exception.ExportImportRuntimeException;
import com.liferay.exportimport.kernel.exception.LARFileNameException;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portlet.exportimport.service.base.ExportImportLocalServiceBaseImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class ExportImportLocalServiceImpl
	extends ExportImportLocalServiceBaseImpl {

	@Override
	public File exportLayoutsAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		try {
			ExportController layoutExportController =
				ExportImportControllerRegistryUtil.getExportController(
					Layout.class.getName());

			return layoutExportController.export(exportImportConfiguration);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			ExportImportRuntimeException eire =
				new ExportImportRuntimeException(e.getLocalizedMessage(), e);

			eire.setClassName(ExportImportLocalServiceImpl.class.getName());

			throw eire;
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public File exportLayoutsAsFile(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationFactory.
				buildDefaultLocalPublishingExportImportConfiguration(
					userLocalService.getUser(userId), groupId, 0, privateLayout,
					parameterMap);

		return exportLayoutsAsFile(exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		if (!DLValidatorUtil.isValidName(exportImportConfiguration.getName())) {
			throw new LARFileNameException(exportImportConfiguration.getName());
		}

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.addBackgroundTask(
				userId, exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(),
				BackgroundTaskExecutorNames.
					LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR,
				taskContextMap, new ServiceContext());

		return backgroundTask.getBackgroundTaskId();
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		return exportLayoutsAsFileInBackground(
			userId,
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId));
	}

	@Override
	public File exportPortletInfoAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		try {
			ExportController portletExportController =
				ExportImportControllerRegistryUtil.getExportController(
					Portlet.class.getName());

			return portletExportController.export(exportImportConfiguration);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			ExportImportRuntimeException eire =
				new ExportImportRuntimeException(e.getLocalizedMessage(), e);

			eire.setClassName(ExportImportLocalServiceImpl.class.getName());

			throw eire;
		}
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		String fileName = MapUtil.getString(
			exportImportConfiguration.getSettingsMap(), "fileName");

		if (!DLValidatorUtil.isValidName(fileName)) {
			throw new LARFileNameException(fileName);
		}

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.addBackgroundTask(
				userId, exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(),
				BackgroundTaskExecutorNames.
					PORTLET_EXPORT_BACKGROUND_TASK_EXECUTOR,
				taskContextMap, new ServiceContext());

		return backgroundTask.getBackgroundTaskId();
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		return exportPortletInfoAsFileInBackground(
			userId,
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId));
	}

	@Override
	public void importLayouts(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			ImportController layoutImportController =
				ExportImportControllerRegistryUtil.getImportController(
					Layout.class.getName());

			layoutImportController.importFile(exportImportConfiguration, file);
		}
		catch (PortalException pe) {
			Throwable cause = pe.getCause();

			if (cause instanceof LocaleException) {
				throw (PortalException)cause;
			}

			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			ExportImportRuntimeException eire =
				new ExportImportRuntimeException(e.getLocalizedMessage(), e);

			eire.setClassName(ExportImportLocalServiceImpl.class.getName());

			throw eire;
		}
	}

	@Override
	public void importLayouts(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		File file = null;

		try {
			file = FileUtil.createTempFile("lar");

			FileUtil.write(file, inputStream);

			importLayouts(exportImportConfiguration, file);
		}
		catch (IOException ioe) {
			ExportImportIOException eiioe = new ExportImportIOException(
				ExportImportLocalServiceImpl.class.getName(), ioe);

			if (file != null) {
				eiioe.setFileName(file.getName());
				eiioe.setType(ExportImportIOException.LAYOUT_IMPORT_FILE);
			}
			else {
				eiioe.setType(ExportImportIOException.LAYOUT_IMPORT);
			}

			throw eiioe;
		}
		finally {
			FileUtil.delete(file);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationFactory.
				buildDefaultLocalPublishingExportImportConfiguration(
					userLocalService.getUser(userId), 0, groupId, privateLayout,
					parameterMap);

		importLayouts(exportImportConfiguration, file);
	}

	@Override
	public void importLayoutsDataDeletions(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			ImportController layoutImportController =
				ExportImportControllerRegistryUtil.getImportController(
					Layout.class.getName());

			layoutImportController.importDataDeletions(
				exportImportConfiguration, file);
		}
		catch (PortalException pe) {
			Throwable cause = pe.getCause();

			if (cause instanceof LocaleException) {
				throw (PortalException)cause;
			}

			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			ExportImportRuntimeException eire =
				new ExportImportRuntimeException(e.getLocalizedMessage(), e);

			eire.setClassName(ExportImportLocalServiceImpl.class.getName());

			throw eire;
		}
	}

	@Override
	public long importLayoutsInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration,
			File file)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.addBackgroundTask(
				userId, exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(),
				BackgroundTaskExecutorNames.
					LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR,
				taskContextMap, new ServiceContext());

		backgroundTask.addAttachment(userId, file.getName(), file);

		return backgroundTask.getBackgroundTaskId();
	}

	@Override
	public long importLayoutsInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		File file = null;

		try {
			file = FileUtil.createTempFile("lar");

			FileUtil.write(file, inputStream);

			return importLayoutsInBackground(
				userId, exportImportConfiguration, file);
		}
		catch (IOException ioe) {
			ExportImportIOException eiioe = new ExportImportIOException(
				ExportImportLocalServiceImpl.class.getName(), ioe);

			if (file != null) {
				eiioe.setFileName(file.getName());
				eiioe.setType(ExportImportIOException.LAYOUT_IMPORT_FILE);
			}
			else {
				eiioe.setType(ExportImportIOException.LAYOUT_IMPORT);
			}

			throw eiioe;
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public long importLayoutsInBackground(
			long userId, long exportImportConfigurationId, File file)
		throws PortalException {

		return importPortletInfoInBackground(
			userId,
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId),
			file);
	}

	@Override
	public long importLayoutsInBackground(
			long userId, long exportImportConfigurationId,
			InputStream inputStream)
		throws PortalException {

		return importLayoutsInBackground(
			userId,
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId),
			inputStream);
	}

	@Override
	public void importPortletDataDeletions(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			ImportController portletImportController =
				ExportImportControllerRegistryUtil.getImportController(
					Portlet.class.getName());

			portletImportController.importDataDeletions(
				exportImportConfiguration, file);
		}
		catch (PortalException pe) {
			Throwable cause = pe.getCause();

			if (cause instanceof LocaleException) {
				throw (PortalException)cause;
			}

			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			ExportImportRuntimeException eire =
				new ExportImportRuntimeException(e.getLocalizedMessage(), e);

			eire.setClassName(ExportImportLocalServiceImpl.class.getName());

			throw eire;
		}
	}

	@Override
	public void importPortletInfo(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			ImportController portletImportController =
				ExportImportControllerRegistryUtil.getImportController(
					Portlet.class.getName());

			portletImportController.importFile(exportImportConfiguration, file);
		}
		catch (PortalException pe) {
			Throwable cause = pe.getCause();

			while (true) {
				if (cause == null) {
					break;
				}

				if (cause instanceof LocaleException) {
					throw (PortalException)cause;
				}

				if (cause instanceof PortletDataException) {
					cause = cause.getCause();
				}
				else {
					break;
				}
			}

			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			ExportImportRuntimeException eire =
				new ExportImportRuntimeException(e.getLocalizedMessage(), e);

			eire.setClassName(ExportImportLocalServiceImpl.class.getName());

			throw eire;
		}
	}

	@Override
	public void importPortletInfo(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		File file = null;

		try {
			file = FileUtil.createTempFile("lar");

			FileUtil.write(file, inputStream);

			importPortletInfo(exportImportConfiguration, file);
		}
		catch (IOException ioe) {
			ExportImportIOException eiioe = new ExportImportIOException(
				ExportImportLocalServiceImpl.class.getName(), ioe);

			if (file != null) {
				eiioe.setFileName(file.getName());
				eiioe.setType(ExportImportIOException.PORTLET_IMPORT_FILE);
			}
			else {
				eiioe.setType(ExportImportIOException.PORTLET_IMPORT);
			}

			throw eiioe;
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public long importPortletInfoInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration,
			File file)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.addBackgroundTask(
				userId, exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(),
				BackgroundTaskExecutorNames.
					PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR,
				taskContextMap, new ServiceContext());

		backgroundTask.addAttachment(userId, file.getName(), file);

		return backgroundTask.getBackgroundTaskId();
	}

	@Override
	public long importPortletInfoInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		File file = null;

		try {
			file = FileUtil.createTempFile("lar");

			FileUtil.write(file, inputStream);

			return importPortletInfoInBackground(
				userId, exportImportConfiguration, file);
		}
		catch (IOException ioe) {
			ExportImportIOException eiioe = new ExportImportIOException(
				ExportImportLocalServiceImpl.class.getName(), ioe);

			if (file != null) {
				eiioe.setFileName(file.getName());
				eiioe.setType(ExportImportIOException.PORTLET_IMPORT_FILE);
			}
			else {
				eiioe.setType(ExportImportIOException.PORTLET_IMPORT);
			}

			throw eiioe;
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public long importPortletInfoInBackground(
			long userId, long exportImportConfigurationId, File file)
		throws PortalException {

		return importPortletInfoInBackground(
			userId,
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId),
			file);
	}

	@Override
	public long importPortletInfoInBackground(
			long userId, long exportImportConfigurationId,
			InputStream inputStream)
		throws PortalException {

		return importPortletInfoInBackground(
			userId,
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId),
			inputStream);
	}

	@Override
	public MissingReferences validateImportLayoutsFile(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			ImportController layoutImportController =
				ExportImportControllerRegistryUtil.getImportController(
					Layout.class.getName());

			return layoutImportController.validateFile(
				exportImportConfiguration, file);
		}
		catch (PortalException pe) {
			Throwable cause = pe.getCause();

			if (cause instanceof LocaleException) {
				throw (PortalException)cause;
			}

			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			ExportImportRuntimeException eire =
				new ExportImportRuntimeException(e.getLocalizedMessage(), e);

			eire.setClassName(ExportImportLocalServiceImpl.class.getName());

			throw eire;
		}
	}

	@Override
	public MissingReferences validateImportLayoutsFile(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		File file = null;

		try {
			file = FileUtil.createTempFile("lar");

			FileUtil.write(file, inputStream);

			return validateImportLayoutsFile(exportImportConfiguration, file);
		}
		catch (IOException ioe) {
			ExportImportIOException eiioe = new ExportImportIOException(
				ExportImportLocalServiceImpl.class.getName(), ioe);

			if (file != null) {
				eiioe.setFileName(file.getName());
				eiioe.setType(ExportImportIOException.LAYOUT_VALIDATE_FILE);
			}
			else {
				eiioe.setType(ExportImportIOException.LAYOUT_VALIDATE);
			}

			throw eiioe;
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public MissingReferences validateImportPortletInfo(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			ImportController portletImportController =
				ExportImportControllerRegistryUtil.getImportController(
					Portlet.class.getName());

			return portletImportController.validateFile(
				exportImportConfiguration, file);
		}
		catch (PortalException pe) {
			Throwable cause = pe.getCause();

			if (cause instanceof LocaleException) {
				throw (PortalException)cause;
			}

			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			ExportImportRuntimeException eire =
				new ExportImportRuntimeException(e.getLocalizedMessage(), e);

			eire.setClassName(ExportImportLocalServiceImpl.class.getName());

			throw eire;
		}
	}

	@Override
	public MissingReferences validateImportPortletInfo(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		File file = null;

		try {
			file = FileUtil.createTempFile("lar");

			FileUtil.write(file, inputStream);

			return validateImportPortletInfo(exportImportConfiguration, file);
		}
		catch (IOException ioe) {
			ExportImportIOException eiioe = new ExportImportIOException(
				ExportImportLocalServiceImpl.class.getName(), ioe);

			if (file != null) {
				eiioe.setFileName(file.getName());
				eiioe.setType(ExportImportIOException.PORTLET_VALIDATE_FILE);
			}
			else {
				eiioe.setType(ExportImportIOException.PORTLET_VALIDATE);
			}

			throw eiioe;
		}
		finally {
			FileUtil.delete(file);
		}
	}

}