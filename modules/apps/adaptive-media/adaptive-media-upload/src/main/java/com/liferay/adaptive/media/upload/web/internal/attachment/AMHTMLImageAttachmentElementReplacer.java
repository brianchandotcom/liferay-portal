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

package com.liferay.adaptive.media.upload.web.internal.attachment;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.upload.AttachmentElementReplacer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"format=html", "html.tag.name=img", "service.ranking:Integer=2"
	},
	service = AttachmentElementReplacer.class
)
public class AMHTMLImageAttachmentElementReplacer
	implements AttachmentElementReplacer {

	public AMHTMLImageAttachmentElementReplacer() {
	}

	@Override
	public String replace(String originalElement, FileEntry fileEntry) {
		Element imageElement = _parseImgTag(
			_defaultAttachmentElementReplacer.replace(
				originalElement, fileEntry));

		imageElement.attr(
			"data-fileentryid", String.valueOf(fileEntry.getFileEntryId()));

		return imageElement.toString();
	}

	protected AMHTMLImageAttachmentElementReplacer(
		AttachmentElementReplacer defaultAttachmentElementReplacer) {

		_defaultAttachmentElementReplacer = defaultAttachmentElementReplacer;
	}

	private Element _parseImgTag(String originalImgTag) {
		Document document = Jsoup.parseBodyFragment(originalImgTag);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);
		outputSettings.syntax(Document.OutputSettings.Syntax.xml);

		document.outputSettings(outputSettings);

		Element element = document.body();

		return element.child(0);
	}

	@Reference(
		target = "(&(format=html)(html.tag.name=img)(!(component.name=com.liferay.adaptive.media.upload.web.internal.attachment.AMHTMLImageAttachmentElementReplacer)))"
	)
	private AttachmentElementReplacer _defaultAttachmentElementReplacer;

}