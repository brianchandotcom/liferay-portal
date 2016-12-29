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

package com.liferay.gradle.plugins.defaults.internal.util.copy;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.util.Validator;

import java.io.IOException;

import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.file.FileCopyDetails;

/**
 * @author Andrea Di Giorgi
 */
public class RenameDependencyAction implements Action<FileCopyDetails> {

	public RenameDependencyAction(boolean keepVersion) {
		_keepVersion = keepVersion;
	}

	@Override
	public void execute(FileCopyDetails fileCopyDetails) {
		String fileName = fileCopyDetails.getName();

		if (!fileName.endsWith(".jar")) {
			return;
		}

		int pos = _getVersionStart(fileName);

		if ((_keepVersion == (pos < fileName.length())) &&
			(fileName.lastIndexOf('.', pos) != -1)) {

			return;
		}

		try (JarFile jarFile = new JarFile(fileCopyDetails.getFile())) {
			fileName = _getFileName(jarFile);

			fileCopyDetails.setName(fileName);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	private static int _getVersionStart(String name) {
		Matcher matcher = _versionStartPattern.matcher(name);

		if (!matcher.find()) {
			return name.length();
		}

		return matcher.start();
	}

	private String _getFileName(JarFile jarFile) throws IOException {
		Manifest manifest = jarFile.getManifest();

		if (manifest == null) {
			throw new GradleException(
				"Unable to rename " + jarFile.getName() +
					", as it does not contain a manifest");
		}

		Attributes attributes = manifest.getMainAttributes();

		String fileName = attributes.getValue(Constants.BUNDLE_SYMBOLICNAME);

		if (Validator.isNotNull(fileName)) {
			int pos = fileName.indexOf(';');

			if (pos != -1) {
				fileName = fileName.substring(0, pos);
			}
		}

		if (Validator.isNull(fileName) || (fileName.indexOf('.') == -1) ||
			(_getVersionStart(fileName) < fileName.length())) {

			throw new GradleException(
				"Unable to rename " + jarFile.getName() +
					", as its manifest does not contain a valid '" +
						Constants.BUNDLE_SYMBOLICNAME + "' header");
		}

		if (_keepVersion) {
			String version = attributes.getValue(Constants.BUNDLE_VERSION);

			if (Validator.isNull(version)) {
				throw new GradleException(
					"Unable to rename " + jarFile.getName() +
						", as its manifest does not contain a valid '" +
							Constants.BUNDLE_VERSION + "' header");
			}

			fileName += "-" + version;
		}

		fileName += ".jar";

		return fileName;
	}

	private static final Pattern _versionStartPattern = Pattern.compile(
		"\\w-\\d.*");

	private final boolean _keepVersion;

}