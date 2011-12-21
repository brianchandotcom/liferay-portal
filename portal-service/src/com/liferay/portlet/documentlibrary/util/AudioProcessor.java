/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public interface AudioProcessor {

	public static final String PREVIEW_TYPE = "mp3";

	public void generateAudio(FileVersion fileVersion) throws Exception;

	public Set<String> getAudioMimeTypes();

	public InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception;

	public long getPreviewFileSize(FileVersion fileVersion)
		throws Exception;

	public boolean hasAudio(FileVersion fileVersion);

	public boolean isAudioSupported(FileVersion fileVersion);

	public boolean isAudioSupported(String mimeType);

	public boolean isSupported(String mimeType);

	public void trigger(FileVersion fileVersion);

}