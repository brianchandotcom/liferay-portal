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

package com.liferay.portal.diff;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.diff.DiffHtml;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Reader;

import java.util.Locale;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.outerj.daisy.diff.HtmlCleaner;
import org.outerj.daisy.diff.XslFilter;
import org.outerj.daisy.diff.html.HTMLDiffer;
import org.outerj.daisy.diff.html.HtmlSaxDiffOutput;
import org.outerj.daisy.diff.html.TextNodeComparator;
import org.outerj.daisy.diff.html.dom.DomTreeBuilder;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.AttributesImpl;

/**
 * This class can compare two different versions of HTML code. It detects
 * changes to an entire HTML page such as removal or addition of characters or
 * images.
 *
 * @author Julio Camarero
 */
public class DiffHtmlImpl implements DiffHtml {

	/**
	 * This is a diff method with default values.
	 *
	 * @param  source the source text
	 * @param  target the modified version of the source text
	 * @return a string containing the HTML code of the source text showing the
	 *         differences with the target text
	 * @throws Exception if an exception occurred
	 */
	@Override
	public String diff(Reader source, Reader target) throws Exception {
		if (source == null) {
			throw new NullPointerException("Source is null");
		}

		if (target == null) {
			throw new NullPointerException("Target is null");
		}

		InputSource oldSource = new InputSource(source);
		InputSource newSource = new InputSource(target);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			DiffHtmlImpl.class.getClassLoader());

		try {
			SAXTransformerFactory saxTransformerFactory =
				(SAXTransformerFactory)TransformerFactory.newInstance();

			TransformerHandler tranformHandler =
				saxTransformerFactory.newTransformerHandler();

			tranformHandler.setResult(new StreamResult(unsyncStringWriter));

			XslFilter xslFilter = new XslFilter();

			ContentHandler contentHandler = xslFilter.xsl(
				tranformHandler,
				"com/liferay/portal/util/dependencies/diff_html.xsl");

			HtmlCleaner htmlCleaner = new HtmlCleaner();

			DomTreeBuilder oldDomTreeBuilder = new DomTreeBuilder();

			htmlCleaner.cleanAndParse(oldSource, oldDomTreeBuilder);

			Locale locale = LocaleUtil.getDefault();

			TextNodeComparator leftTextNodeComparator = new TextNodeComparator(
				oldDomTreeBuilder, locale);

			DomTreeBuilder newDomTreeBuilder = new DomTreeBuilder();

			htmlCleaner.cleanAndParse(newSource, newDomTreeBuilder);

			TextNodeComparator rightTextNodeComparator = new TextNodeComparator(
				newDomTreeBuilder, locale);

			contentHandler.startDocument();

			contentHandler.startElement(
				StringPool.BLANK, _DIFF_REPORT, _DIFF_REPORT,
				new AttributesImpl());

			contentHandler.startElement(
				StringPool.BLANK, _DIFF, _DIFF, new AttributesImpl());

			HtmlSaxDiffOutput htmlSaxDiffOutput = new HtmlSaxDiffOutput(
				contentHandler, _DIFF);

			HTMLDiffer htmlDiffer = new HTMLDiffer(htmlSaxDiffOutput);

			htmlDiffer.diff(leftTextNodeComparator, rightTextNodeComparator);

			contentHandler.endElement(StringPool.BLANK, _DIFF, _DIFF);

			contentHandler.endElement(
				StringPool.BLANK, _DIFF_REPORT, _DIFF_REPORT);

			contentHandler.endDocument();

			unsyncStringWriter.flush();

			String string = unsyncStringWriter.toString();

			if (string.startsWith("<?xml")) {
				int index = string.indexOf("?>");

				string = string.substring(index + 2);
			}

			return string;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public String replaceStyles(String html) {
		return StringUtil.replace(
			html,
			new String[] {
				"changeType=\"diff-added-image\"",
				"changeType=\"diff-changed-image\"",
				"changeType=\"diff-removed-image\"",
				"class=\"diff-html-added\"", "class=\"diff-html-changed\"",
				"class=\"diff-html-removed\""
			},
			new String[] {
				"style=\"border: 10px solid #CFC;\"",
				"style=\"border: 10px solid blue;\"",
				"style=\"border: 10px solid #FDC6C6;\"",
				"style=\"background-color: #CFC;\"",
				"style=\"border-bottom: 2px dotted blue;\"",
				"style=\"background-color: #FDC6C6; text-decoration: " +
					"line-through;\""
			});
	}

	private static final String _DIFF = "diff";

	private static final String _DIFF_REPORT = "diffreport";

}