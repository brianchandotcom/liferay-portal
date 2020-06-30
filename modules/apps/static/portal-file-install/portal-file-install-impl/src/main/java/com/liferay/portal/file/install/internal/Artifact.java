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

package com.liferay.portal.file.install.internal;

import com.liferay.portal.file.install.listener.ArtifactListener;

import java.io.File;

import java.net.URL;

/**
 * @author Matthew Tambara
 */
public class Artifact {

	public long getBundleId() {
		return _bundleId;
	}

	public long getChecksum() {
		return _checksum;
	}

	public File getJaredDirectory() {
		return _jaredDirectory;
	}

	public URL getJaredUrl() {
		return _jaredUrl;
	}

	public ArtifactListener getListener() {
		return _artifactListener;
	}

	public File getPath() {
		return _path;
	}

	public URL getTransformedUrl() {
		return _transformedURL;
	}

	public void setBundleId(long bundleId) {
		_bundleId = bundleId;
	}

	public void setChecksum(long checksum) {
		_checksum = checksum;
	}

	public void setJaredDirectory(File jaredDirectory) {
		_jaredDirectory = jaredDirectory;
	}

	public void setJaredUrl(URL jaredUrl) {
		_jaredUrl = jaredUrl;
	}

	public void setListener(ArtifactListener listener) {
		_artifactListener = listener;
	}

	public void setPath(File path) {
		_path = path;
	}

	public void setTransformedUrl(URL transformedUrl) {
		_transformedURL = transformedUrl;
	}

	private ArtifactListener _artifactListener;
	private long _bundleId = -1;
	private long _checksum;
	private File _jaredDirectory;
	private URL _jaredUrl;
	private File _path;
	private URL _transformedURL;

}