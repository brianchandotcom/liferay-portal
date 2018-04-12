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

package com.liferay.user.associated.data.web.internal.export.controller;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.web.internal.export.background.task.UADExportBackgroundTaskStatusMessageSender;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = UADApplicationExportController.class)
public class UADApplicationExportController {

	public File export(String applicationName, long userId) throws Exception {
		try {
			_uadExportBackgroundTaskStatusMessageSender.sendStatusMessage(
				"application", applicationName,
				_getApplicationDataCount(applicationName, userId));

			File file = _exportApplicationData(applicationName, userId);

			return file;
		}
		catch (Throwable t) {
			throw t;
		}
	}

	private File _exportApplicationData(String applicationName, long userId)
		throws PortalException {

		ZipWriter zipWriter = _getZipWriter(applicationName, userId);

		for (String uadRegistryKey :
				_getApplicationUADEntityRegistryKeys(applicationName)) {

			UADExporter uadExporter = _uadRegistry.getUADExporter(
				uadRegistryKey);

			File file = uadExporter.exportAll(userId);

			if (file.exists()) {
				try {
					ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
						file);

					List<String> entries = zipReader.getEntries();

					for (String entry : entries) {
						zipWriter.addEntry(
							_getEntryPath(uadRegistryKey, entry),
							zipReader.getEntryAsInputStream(entry));

						_uadExportBackgroundTaskStatusMessageSender.
							sendStatusMessage("entity", uadRegistryKey);
					}
				}
				catch (IOException ioe) {
					throw new PortalException(ioe);
				}
			}
		}

		return zipWriter.getFile();
	}

	private long _getApplicationDataCount(String applicationName, long userId)
		throws PortalException {

		long totalCount = 0;

		for (String uadRegistryKey :
				_getApplicationUADEntityRegistryKeys(applicationName)) {

			UADExporter uadExporter = _uadRegistry.getUADExporter(
				uadRegistryKey);

			totalCount += uadExporter.count(userId);
		}

		return totalCount;
	}

	private Stream<UADEntityDisplay> _getApplicationUADEntityDisplayStream(
		String applicationName) {

		Stream<UADEntityDisplay> uadEntityDisplayStream =
			_uadRegistry.getUADEntityDisplayStream();

		return uadEntityDisplayStream.filter(
			uadEntityDisplay -> applicationName.equals(
				uadEntityDisplay.getApplicationName()));
	}

	private List<String> _getApplicationUADEntityRegistryKeys(
		String applicationName) {

		Stream<UADEntityDisplay> uadEntityDisplayStream =
			_getApplicationUADEntityDisplayStream(applicationName);

		return uadEntityDisplayStream.map(
			UADEntityDisplay::getKey
		).collect(
			Collectors.toList()
		);
	}

	private String _getEntryPath(String uadRegistryKey, String fileName) {
		UADEntityDisplay uadEntityDisplay = _uadRegistry.getUADEntityDisplay(
			uadRegistryKey);

		StringBundler sb = new StringBundler(5);

		sb.append(uadEntityDisplay.getApplicationName());
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(uadEntityDisplay.getTypeName());
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(fileName);

		return sb.toString();
	}

	private ZipWriter _getZipWriter(String applicationName, long userId) {
		User user = _userLocalService.fetchUser(userId);

		StringBundler sb = new StringBundler(8);

		sb.append("UAD");
		sb.append(StringPool.UNDERLINE);

		if (user != null) {
			sb.append(user.getFullName());
		}
		else {
			sb.append(userId);
		}

		sb.append(StringPool.UNDERLINE);
		sb.append(applicationName);
		sb.append(StringPool.UNDERLINE);
		sb.append(Time.getShortTimestamp());
		sb.append(".zip");

		String fileName = sb.toString();

		return ZipWriterFactoryUtil.getZipWriter(
			new File(
				SystemProperties.get(SystemProperties.TMP_DIR) +
					StringPool.SLASH + fileName));
	}

	@Reference
	private UADExportBackgroundTaskStatusMessageSender
		_uadExportBackgroundTaskStatusMessageSender;

	@Reference
	private UADRegistry _uadRegistry;

	@Reference
	private UserLocalService _userLocalService;

}