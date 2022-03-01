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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

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

		List<String> matchedTags = new ArrayList<>();

		while (matcher.find()) {
			matchedTags.add(matcher.group());
		}

		if (ListUtil.isEmpty(matchedTags)) {
			return content;
		}

		Document document = SourceUtil.readXML(content);

		List<Node> echoNodes = document.selectNodes("//*[name() = 'echo']");

		for (Node echoNode : echoNodes) {
			Element echoElement = (Element)echoNode;

			Attribute messageAttribute = echoElement.attribute("message");

			if (messageAttribute == null) {
				continue;
			}

			for (String matchedTag : matchedTags) {
				Document documentElement = DocumentHelper.parseText(matchedTag);

				Element rootElement = documentElement.getRootElement();

				if (!Objects.equals(echoElement.asXML(), rootElement.asXML())) {
					continue;
				}

				echoElement.remove(messageAttribute);
				echoElement.setText(messageAttribute.getText());

				content = StringUtil.replace(
					content, matchedTag, echoElement.asXML());

				break;
			}
		}

		return content;
	}

	private static final Pattern _echoMessagePattern = Pattern.compile(
		"<echo (.(?!(/>|</)))*?message=.*?/>");

}