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

package com.liferay.adaptive.media.journal.web.internal.exportimport.content.processor;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, service = AMJournalArticleContentHTMLReplacer.class
)
public class AMJournalArticleContentHTMLReplacer {

	public String replace(String content, Replace replace) throws Exception {
		try {
			Document document = SAXReaderUtil.read(content);

			XPath xPath = SAXReaderUtil.createXPath(
				"//dynamic-element[@type='text_area']");

			List<Node> ddmJournalArticleNodes = xPath.selectNodes(document);

			for (Node ddmJournalArticleNode : ddmJournalArticleNodes) {
				Element ddmJournalArticleElement =
					(Element)ddmJournalArticleNode;

				List<Element> dynamicContentElements =
					ddmJournalArticleElement.elements("dynamic-content");

				for (Element dynamicContentElement : dynamicContentElements) {
					String replacedHtml = replace.apply(
						dynamicContentElement.getStringValue());

					dynamicContentElement.clearContent();

					dynamicContentElement.addCDATA(replacedHtml);
				}
			}

			return document.asXML();
		}
		catch (DocumentException documentException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid content:\n" + content);
			}

			return content;
		}
	}

	@FunctionalInterface
	public interface Replace extends UnsafeFunction<String, String, Exception> {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMJournalArticleContentHTMLReplacer.class);

}