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

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.util.DLValidatorUtil;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException;
import com.liferay.portlet.exportimport.LARFileNameException;
import com.liferay.portlet.exportimport.backgroundtask.LayoutExportBackgroundTaskExecutor;
import com.liferay.portlet.exportimport.backgroundtask.LayoutImportBackgroundTaskExecutor;
import com.liferay.portlet.exportimport.backgroundtask.PortletExportBackgroundTaskExecutor;
import com.liferay.portlet.exportimport.backgroundtask.PortletImportBackgroundTaskExecutor;
import com.liferay.portlet.exportimport.lar.LayoutExporter;
import com.liferay.portlet.exportimport.lar.LayoutImporter;
import com.liferay.portlet.exportimport.lar.MissingReferences;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.PortletExporter;
import com.liferay.portlet.exportimport.lar.PortletImporter;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
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
			LayoutExporter layoutExporter = LayoutExporter.getInstance();

			return layoutExporter.exportLayoutsAsFile(
				exportImportConfiguration);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		if (!DLValidatorUtil.isValidName(exportImportConfiguration.getName())) {
			throw new LARFileNameException(exportImportConfiguration.getName());
		}

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(Constants.CMD, Constants.EXPORT);
		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		BackgroundTask backgroundTask =
			backgroundTaskLocalService.addBackgroundTask(
				userId, exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(), null,
				LayoutExportBackgroundTaskExecutor.class, taskContextMap,
				new ServiceContext());

		return backgroundTask.getBackgroundTaskId();
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		return exportLayoutsAsFileInBackground(
			userId, exportImportConfiguration);
	}

	@Override
	public File exportPortletInfoAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		try {
			PortletExporter portletExporter = PortletExporter.getInstance();

			return portletExporter.exportPortletInfoAsFile(
				exportImportConfiguration);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		String fileName = MapUtil.getString(settingsMap, "fileName");

		if (!DLValidatorUtil.isValidName(fileName)) {
			throw new LARFileNameException(fileName);
		}

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(Constants.CMD, Constants.EXPORT);
		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		BackgroundTask backgroundTask =
			backgroundTaskLocalService.addBackgroundTask(
				userId, exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(), null,
				PortletExportBackgroundTaskExecutor.class, taskContextMap,
				new ServiceContext());

		return backgroundTask.getBackgroundTaskId();
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		return exportPortletInfoAsFileInBackground(
			userId, exportImportConfiguration);
	}

	@Override
	public void importLayouts(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			LayoutImporter layoutImporter = LayoutImporter.getInstance();

			layoutImporter.importLayouts(exportImportConfiguration, file);
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
			throw new SystemException(e);
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
			throw new SystemException(ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public void importLayoutsDataDeletions(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			LayoutImporter layoutImporter = LayoutImporter.getInstance();

			layoutImporter.importLayoutsDataDeletions(
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
			throw new SystemException(e);
		}
	}

	@Override
	public long importLayoutsInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration,
			File file)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(Constants.CMD, Constants.IMPORT);
		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		BackgroundTask backgroundTask =
			backgroundTaskLocalService.addBackgroundTask(
				userId, exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(), null,
				LayoutImportBackgroundTaskExecutor.class, taskContextMap,
				new ServiceContext());

		backgroundTaskLocalService.addBackgroundTaskAttachment(
			userId, backgroundTask.getBackgroundTaskId(), file.getName(), file);

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
			throw new SystemException(ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public long importLayoutsInBackground(
			long userId, long exportImportConfigurationId, File file)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		return importPortletInfoInBackground(
			userId, exportImportConfiguration, file);
	}

	@Override
	public long importLayoutsInBackground(
			long userId, long exportImportConfigurationId,
			InputStream inputStream)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		return importLayoutsInBackground(
			userId, exportImportConfiguration, inputStream);
	}

	@Override
	public void importPortletDataDeletions(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			PortletImporter portletImporter = PortletImporter.getInstance();

			portletImporter.importPortletDataDeletions(
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
			throw new SystemException(e);
		}
	}

	@Override
	public void importPortletInfo(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			PortletImporter portletImporter = PortletImporter.getInstance();

			portletImporter.importPortletInfo(exportImportConfiguration, file);
		}
		catch (PortalException pe) {
			Throwable cause = pe.getCause();

			while (true) {
				if (cause == null) {
					break;
				}

				if ((cause instanceof LocaleException) ||
					(cause instanceof
						StructureDuplicateStructureKeyException)) {

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
			throw new SystemException(e);
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
			throw new SystemException(ioe);
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

		taskContextMap.put(Constants.CMD, Constants.IMPORT);
		taskContextMap.put(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		BackgroundTask backgroundTask =
			backgroundTaskLocalService.addBackgroundTask(
				userId, exportImportConfiguration.getGroupId(),
				exportImportConfiguration.getName(), null,
				PortletImportBackgroundTaskExecutor.class, taskContextMap,
				new ServiceContext());

		backgroundTaskLocalService.addBackgroundTaskAttachment(
			userId, backgroundTask.getBackgroundTaskId(), file.getName(), file);

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
			throw new SystemException(ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public long importPortletInfoInBackground(
			long userId, long exportImportConfigurationId, File file)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		return importPortletInfoInBackground(
			userId, exportImportConfiguration, file);
	}

	@Override
	public long importPortletInfoInBackground(
			long userId, long exportImportConfigurationId,
			InputStream inputStream)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		return importPortletInfoInBackground(
			userId, exportImportConfiguration, inputStream);
	}

	@Override
	public MissingReferences validateImportLayoutsFile(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		try {
			LayoutImporter layoutImporter = LayoutImporter.getInstance();

			return layoutImporter.validateFile(exportImportConfiguration, file);
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
			throw new SystemException(e);
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
			throw new SystemException(ioe);
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
			PortletImporter portletImporter = PortletImporter.getInstance();

			return portletImporter.validateFile(
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
			throw new SystemException(e);
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
			throw new SystemException(ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

}