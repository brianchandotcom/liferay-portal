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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import org.hsqldb.lib.StringUtil;

/**
 * @author Seiphon Wang
 */
public class XMLEchoMessageCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".xml")) {
			return content;
		}

		Matcher matcher = _echoMessagePattern.matcher(content);

		Document document = SourceUtil.readXML(content);

		List<Node> echoNodes = document.selectNodes("//*[name() = 'echo']");

		while (matcher.find()) {
			String matchedTag = matcher.group();

			String expectedString = "";

			for (Node echoNode : echoNodes) {
				Element echoElement = (Element)echoNode;

				Attribute messageAttribute = echoElement.attribute("message");

				if (messageAttribute == null) {
					continue;
				}

				Document documentElement = DocumentHelper.parseText(matchedTag);

				Element rootElement = documentElement.getRootElement();

				if (!Objects.equals(echoElement.asXML(), rootElement.asXML())) {
					continue;
				}

				echoElement.remove(messageAttribute);
				echoElement.setText(messageAttribute.getText());

				expectedString = echoElement.asXML();

				break;
			}

			StringBundler sb = new StringBundler(5);

			sb.append("Do not use self-closing tag for attribute 'message' ");
			sb.append("in '<echo>' tag.");

			if (!StringUtil.isEmpty(expectedString)) {
				sb.append(" Please use '");
				sb.append(expectedString);
				sb.append("' instead.");
			}

			addMessage(
				fileName, sb.toString(),
				getLineNumber(content, matcher.start()));
		}

		return content;
	}

	private static final Pattern _echoMessagePattern = Pattern.compile(
		"<echo (.(?!(/>|</)))*?message=.*?/>");

}