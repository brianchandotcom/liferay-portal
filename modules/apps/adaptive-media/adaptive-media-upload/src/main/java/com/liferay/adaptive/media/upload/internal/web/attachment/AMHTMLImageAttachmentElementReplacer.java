/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.upload.internal.web.attachment;

import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
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
			AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID,
			String.valueOf(fileEntry.getFileEntryId()));

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
		target = "(&(format=html)(html.tag.name=img)(!(component.name=com.liferay.adaptive.media.upload.internal.web.attachment.AMHTMLImageAttachmentElementReplacer)))"
	)
	private AttachmentElementReplacer _defaultAttachmentElementReplacer;

}