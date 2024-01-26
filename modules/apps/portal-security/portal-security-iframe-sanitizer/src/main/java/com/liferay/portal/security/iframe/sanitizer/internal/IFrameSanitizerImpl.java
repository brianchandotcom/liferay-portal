/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.iframe.sanitizer.internal;

import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.util.ContentTypes;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(service = Sanitizer.class)
public class IFrameSanitizerImpl implements Sanitizer {

	@Override
	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String content,
			Map<String, Object> options)
		throws SanitizerException {

		Document document = _getDocument(content);

		for (Element iframe : document.getElementsByTag("iframe")) {
			iframe.remove();
		}

		if (contentType.equals(ContentTypes.TEXT_HTML)) {
			return document.html();
		}

		return document.text();
	}

	private Document _getDocument(String content) {
		Document document = Jsoup.parseBodyFragment(content);

		document.outputSettings(
			new Document.OutputSettings() {
				{
					prettyPrint(false);
				}
			});

		return document;
	}

}