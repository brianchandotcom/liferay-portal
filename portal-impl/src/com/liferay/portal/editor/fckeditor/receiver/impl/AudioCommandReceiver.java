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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.AudioProcessorUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Juan Gonzalez
 */
public class AudioCommandReceiver extends DocumentCommandReceiver {

	@Override
	protected String fileUpload(
		CommandArgument commandArgument, String fileName,
		InputStream inputStream, String contentType, long size) {

		if (!XugglerUtil.isEnabled()) {
			return String.valueOf(
				ServletResponseConstants.SC_AUDIO_PREVIEW_DISABLED_EXCEPTION);
		}

		return super.fileUpload(
			commandArgument, fileName, inputStream, contentType, size);
	}

	@Override
	protected Element getFileElement(
			CommandArgument commandArgument, Element fileElement,
			FileEntry fileEntry)
		throws Exception {

		fileElement = super.getFileElement(
			commandArgument, fileElement, fileEntry);

		if (!AudioProcessorUtil.hasAudio(fileEntry.getFileVersion())) {
			fileElement.setAttribute(
				"errorMessage",
				LanguageUtil.get(
					commandArgument.getLocale(),
					"the-audio-preview-is-not-yet-ready.-please-try-again-" +
						"later"));
		}

		return fileElement;
	}

	@Override
	protected List<Element> getFileElements(
			CommandArgument commandArgument, Document document, Folder folder)
		throws Exception {

		List<Element> fileElements = new ArrayList<Element>();

		List<FileEntry> fileEntries = null;

		String[] audioMimeTypes = getAudioMimeTypes();

		if (audioMimeTypes != null) {
			fileEntries = DLAppServiceUtil.getFileEntries(
				folder.getRepositoryId(), folder.getFolderId(), audioMimeTypes);
		}
		else {
			fileEntries = DLAppServiceUtil.getFileEntries(
				folder.getRepositoryId(), folder.getFolderId());
		}

		for (FileEntry fileEntry : fileEntries) {
			Element fileElement = document.createElement("File");

			fileElement = getFileElement(
				commandArgument, fileElement, fileEntry);

			fileElements.add(fileElement);
		}

		return fileElements;
	}

	private String[] getAudioMimeTypes() {
		Set<String> audioMimeTypes = AudioProcessorUtil.getAudioMimeTypes();

		if (audioMimeTypes == null) {
			return null;
		}

		return ArrayUtil.toStringArray(audioMimeTypes.toArray());
	}

}