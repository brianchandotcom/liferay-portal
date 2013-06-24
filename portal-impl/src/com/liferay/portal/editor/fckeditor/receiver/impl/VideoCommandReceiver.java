/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.VideoProcessorUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Juan Gonzalez
 * @author Roberto Díaz
 */
public class VideoCommandReceiver extends DocumentCommandReceiver {

	@Override
	protected String fileUpload(
		CommandArgument commandArgument, String fileName,
		InputStream inputStream, String contentType, long size) {

		if (!XugglerUtil.isEnabled()) {
			return "210";
		}

		return super.fileUpload(
			commandArgument, fileName, inputStream, contentType, size);
	}

	@Override
	protected List<FileEntry> getFileEntries(Folder folder) throws Exception {
		List<FileEntry> fileEntries = null;

		String[] videoMimeTypes = getVideoMimeTypes();

		if (videoMimeTypes != null) {
			fileEntries = DLAppServiceUtil.getFileEntries(
				folder.getRepositoryId(), folder.getFolderId(), videoMimeTypes);
		}
		else {
			fileEntries = DLAppServiceUtil.getFileEntries(
				folder.getRepositoryId(), folder.getFolderId());
		}

		List<FileEntry> generatedVideoEntries = new ArrayList<FileEntry>();

		for (FileEntry fileEntry : fileEntries) {
			generatedVideoEntries.add(fileEntry);
		}

		return generatedVideoEntries;
	}

	private String[] getVideoMimeTypes() {
		Set<String> videoMimesSet = VideoProcessorUtil.getVideoMimeTypes();

		if (videoMimesSet == null) {
			return null;
		}

		return ArrayUtil.toStringArray(videoMimesSet.toArray());
	}

}