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

package com.liferay.upload.web.internal;

import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.upload.AttachmentElementReplacer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jürgen Kappler
 */
@Component(
	property = {"format=html", "html.tag.name=img"},
	service = AttachmentElementReplacer.class
)
public class HTMLImageAttachmentElementReplacer
	implements AttachmentElementReplacer {

	@Override
	public String replace(String originalImgHtmlElement, FileEntry fileEntry) {
		String fileEntryURL = _portletFileRepository.getPortletFileEntryURL(
			null, fileEntry, StringPool.BLANK);

		Element image = _parseImgTag(originalImgHtmlElement);

		image.attr("src", fileEntryURL);
		image.removeAttr(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);

		return image.toString();
	}

	@Reference(unbind = "-")
	protected void setPortletFileRepository(
		PortletFileRepository portletFileRepository) {

		_portletFileRepository = portletFileRepository;
	}

	private Element _parseImgTag(String originalImgTag) {
		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);
		outputSettings.syntax(Document.OutputSettings.Syntax.xml);

		Document document = Jsoup.parseBodyFragment(originalImgTag);

		document.outputSettings(outputSettings);

		return document.body().child(0);
	}

	private PortletFileRepository _portletFileRepository;

}