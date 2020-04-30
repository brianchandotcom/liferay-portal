/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with work for additional information
 * regarding copyright ownership.  The ASF licenses file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.felix.fileinstall.internal;

import java.io.File;

import java.net.URL;

import org.apache.felix.fileinstall.ArtifactListener;

/**
 * An artifact that has been dropped into one watched directory.
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

	public File getTransformed() {
		return _transformed;
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

	public void setTransformed(File transformed) {
		_transformed = transformed;
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
	private File _transformed;
	private URL _transformedURL;

}
/* @generated */