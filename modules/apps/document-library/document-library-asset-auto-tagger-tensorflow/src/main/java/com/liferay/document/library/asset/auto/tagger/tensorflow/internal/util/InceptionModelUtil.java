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

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util;

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderDownloadConfiguration;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.zip.ZipFileUtil;
import com.liferay.portal.util.JarUtil;
import com.liferay.portal.util.PortalInstances;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Paths;

/**
 * @author Alejandro Tardín
 */
public class InceptionModelUtil {

	public static void download(
			TensorFlowImageAssetAutoTagProviderDownloadConfiguration
				tensorFlowImageAssetAutoTagProviderDownloadConfiguration)
		throws Exception {

		if (isDownloaded()) {
			return;
		}

		try {
			_downloadFailed = false;

			_downloadFile(
				_getFileName(),
				tensorFlowImageAssetAutoTagProviderDownloadConfiguration.
					modelDownloadURL());

			_downloadFile(
				_getNativeLibraryFileName(),
				tensorFlowImageAssetAutoTagProviderDownloadConfiguration.
					nativeLibraryDownloadURL());
		}
		catch (Exception exception) {
			_downloadFailed = true;

			throw exception;
		}
	}

	public static byte[] getGraphBytes() throws IOException, PortalException {
		return StreamUtil.toByteArray(
			_getFileInputStream("tensorflow_inception_graph.pb"));
	}

	public static String[] getLabels() throws IOException, PortalException {
		return StringUtil.splitLines(
			StringUtil.read(
				_getFileInputStream("imagenet_comp_graph_label_strings.txt")));
	}

	public static boolean isDownloaded() throws PortalException {
		if (DLStoreUtil.hasFile(
				PortalInstances.getDefaultCompanyId(), CompanyConstants.SYSTEM,
				_getFileName()) &&
			DLStoreUtil.hasFile(
				PortalInstances.getDefaultCompanyId(), CompanyConstants.SYSTEM,
				_getNativeLibraryFileName())) {

			return true;
		}

		return false;
	}

	public static boolean isDownloadFailed() {
		return _downloadFailed;
	}

	private static void _downloadFile(String fileName, String url)
		throws Exception {

		File tempFile = FileUtil.createTempFile();

		JarUtil.downloadAndInstallJar(new URL(url), tempFile.toPath());

		DLStoreUtil.addFile(
			PortalInstances.getDefaultCompanyId(), CompanyConstants.SYSTEM,
			fileName, false, tempFile);
	}

	private static InputStream _getFileInputStream(String fileName)
		throws IOException, PortalException {

		return ZipFileUtil.openInputStream(
			FileUtil.createTempFile(
				DLStoreUtil.getFileAsStream(
					PortalInstances.getDefaultCompanyId(),
					CompanyConstants.SYSTEM, _getFileName())),
			fileName);
	}

	private static String _getFileName() {
		return _getFileName("org.tensorflow.models.inception-5h.jar");
	}

	private static String _getFileName(String fileName) {
		return String.valueOf(
			Paths.get(
				"com.liferay.document.library.asset.auto.tagger.tensorflow",
				fileName));
	}

	private static String _getNativeLibraryFileName() {
		return _getFileName("libtensorflow_jni-1.15.0.jar");
	}

	private static boolean _downloadFailed;

}