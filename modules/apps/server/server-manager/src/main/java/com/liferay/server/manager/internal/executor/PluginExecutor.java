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

package com.liferay.server.manager.internal.executor;

import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.server.manager.internal.constants.JSONKeys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "server.manager.executor.path=/plugins/plugin",
	service = Executor.class
)
public class PluginExecutor extends BaseExecutor {

	@Override
	public void executeCreate(
			HttpServletRequest httpServletRequest,
			JSONObject responseJSONObject, Queue<String> arguments)
		throws Exception {

		AutoDeploymentContext autoDeploymentContext =
			new AutoDeploymentContext();

		String context = arguments.poll();

		autoDeploymentContext.setContext(context);

		File tempFile = getTempFile(httpServletRequest, responseJSONObject);

		if (tempFile == null) {
			return;
		}

		autoDeploymentContext.setFile(tempFile);

		DeployManagerUtil.deploy(autoDeploymentContext);

		boolean success = FileUtils.deleteQuietly(tempFile.getParentFile());

		if (success) {
			return;
		}

		String message =
			"Unable to remove temp directory " + tempFile.getParentFile();

		_log.error(message);

		responseJSONObject.put(JSONKeys.ERROR, message);

		success = FileUtils.deleteQuietly(tempFile);

		if (success) {
			return;
		}

		message = "Unable to remove temp file " + tempFile;

		_log.error(message);

		responseJSONObject.put(JSONKeys.ERROR, message);
	}

	@Override
	public void executeDelete(
			HttpServletRequest httpServletRequest,
			JSONObject responseJSONObject, Queue<String> arguments)
		throws Exception {

		String context = arguments.poll();

		DeployManagerUtil.undeploy(context);
	}

	@Override
	public void executeRead(
		HttpServletRequest httpServletRequest, JSONObject responseJSONObject,
		Queue<String> arguments) {

		String context = arguments.poll();

		PluginPackage pluginPackage =
			DeployManagerUtil.getInstalledPluginPackage(context);

		boolean installed = true;

		if (pluginPackage == null) {
			installed = false;
		}

		JSONObject pluginPackageJSONObject = JSONUtil.put(
			"installed", installed);

		boolean started = true;

		if (pluginPackage == null) {
			started = false;
		}

		pluginPackageJSONObject.put("started", started);

		List<String> types = new ArrayList<>();

		if (pluginPackage != null) {
			types = pluginPackage.getTypes();
		}

		JSONArray typesJSONArray = JSONFactoryUtil.createJSONArray();

		for (String type : types) {
			typesJSONArray.put(type);
		}

		pluginPackageJSONObject.put("types", typesJSONArray);

		responseJSONObject.put(JSONKeys.OUTPUT, pluginPackageJSONObject);
	}

	@Override
	public void executeUpdate(
			HttpServletRequest httpServletRequest,
			JSONObject responseJSONObject, Queue<String> arguments)
		throws Exception {

		String context = arguments.poll();

		List<File> installedDirs = getInstalledDirectories(context);

		for (File installedDir : installedDirs) {
			if (!installedDir.exists()) {
				responseJSONObject.put(
					JSONKeys.ERROR,
					"Context directory " + installedDir.getAbsolutePath() +
						" does not exist"
				).put(
					JSONKeys.STATUS, 1
				);

				return;
			}
		}

		File tempFile = getTempFile(httpServletRequest, responseJSONObject);

		if (tempFile == null) {
			return;
		}

		for (File deployDirectory : installedDirs) {
			FileUtil.unzip(tempFile, deployDirectory);
		}

		File partialAppDeletePropsFile = new File(
			installedDirs.get(0), "META-INF/liferay-partialapp-delete.props");

		if (!partialAppDeletePropsFile.exists()) {
			return;
		}

		BufferedReader bufferedReader = new BufferedReader(
			new FileReader(partialAppDeletePropsFile));

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			for (File deployDirectory : installedDirs) {
				File staleFile = new File(deployDirectory, line.trim());

				if (!staleFile.exists()) {
					continue;
				}

				boolean success = FileUtils.deleteQuietly(staleFile);

				if (success) {
					continue;
				}

				String message =
					"Unable to delete file " + staleFile.getAbsolutePath();

				_log.error(message);

				responseJSONObject.put(JSONKeys.ERROR, message);
			}
		}

		FileUtils.deleteQuietly(partialAppDeletePropsFile);

		if (_log.isInfoEnabled()) {
			_log.info("Successfully updated " + context);
		}
	}

	protected FileItem getFileItem(HttpServletRequest httpServletRequest)
		throws FileUploadException {

		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

		ServletFileUpload servletFileUpload = new ServletFileUpload(
			diskFileItemFactory);

		List<FileItem> fileItems = servletFileUpload.parseRequest(
			httpServletRequest);

		for (FileItem fileItem : fileItems) {
			if (!fileItem.isFormField()) {
				return fileItem;
			}
		}

		return null;
	}

	protected File getFileItemTempFile(HttpServletRequest httpServletRequest)
		throws Exception {

		FileItem fileItem = getFileItem(httpServletRequest);

		if (fileItem == null) {
			return null;
		}

		File tempDir = new File(
			SystemProperties.get(SystemProperties.TMP_DIR),
			PortalUUIDUtil.generate());

		if (!tempDir.mkdirs()) {
			return null;
		}

		File tempFile = new File(tempDir, fileItem.getName());

		fileItem.write(tempFile);

		return tempFile;
	}

	protected List<File> getInstalledDirectories(final String context)
		throws Exception {

		List<File> installedDirs = new ArrayList<>();

		String installedDirName = DeployManagerUtil.getInstalledDir();

		File installedDir = new File(installedDirName, context);

		if (installedDir.exists()) {
			installedDirs.add(installedDir);
		}
		else {
			File deployWarDir = new File(installedDirName, context + ".war");

			installedDirs.add(deployWarDir);
		}

		if (ServerDetector.isTomcat()) {
			File tempDir = new File(
				SystemProperties.get(SystemProperties.TMP_DIR));

			File[] tempContextDirs = tempDir.listFiles(
				new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						if (name.endsWith("-" + context)) {
							return true;
						}

						return false;
					}

				});

			if (ArrayUtil.isNotEmpty(tempContextDirs)) {
				Arrays.sort(
					tempContextDirs,
					new Comparator<File>() {

						@Override
						public int compare(File file1, File file2) {
							String fileName1 = file1.getName();
							String fileName2 = file2.getName();

							return fileName1.compareTo(fileName2);
						}

					});

				File tempContextDir =
					tempContextDirs[tempContextDirs.length - 1];

				installedDirs.add(tempContextDir);
			}
		}

		return installedDirs;
	}

	protected File getTempFile(
		HttpServletRequest httpServletRequest, JSONObject responseJSONObject) {

		File tempFile = null;

		String message = "Unable to create temp file for uploaded plugin";

		try {
			tempFile = getFileItemTempFile(httpServletRequest);
		}
		catch (Exception e) {
			_log.error(message, e);
		}

		if (tempFile != null) {
			return tempFile;
		}

		responseJSONObject.put(
			JSONKeys.ERROR, message
		).put(
			JSONKeys.STATUS, 1
		);

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(PluginExecutor.class);

}