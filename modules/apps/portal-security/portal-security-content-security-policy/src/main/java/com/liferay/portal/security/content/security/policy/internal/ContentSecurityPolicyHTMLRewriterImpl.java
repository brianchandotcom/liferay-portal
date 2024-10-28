/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyHTMLRewriter;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = ContentSecurityPolicyHTMLRewriter.class)
public class ContentSecurityPolicyHTMLRewriterImpl
	implements ContentSecurityPolicyHTMLRewriter {

	@Override
	public String rewriteInlineEventHandlers(
		String html, String nonce, boolean recursive) {

		StringBundler sb = new StringBundler();

		Document document = Jsoup.parse(html);

		Element bodyElement = document.body();

		boolean containsBodyTag = _containsBodyTag(html);

		if (containsBodyTag) {
			_extractInlineHandlers(bodyElement, sb, recursive);
		}
		else {
			for (Element childElement : bodyElement.children()) {
				_extractInlineHandlers(childElement, sb, recursive);
			}
		}

		if (sb.length() == 0) {
			return html;
		}

		Element element = new Element("script");

		element.attr("nonce", nonce);
		element.html(sb.toString());

		bodyElement.appendChild(element);

		if (containsBodyTag) {
			return bodyElement.outerHtml();
		}

		return bodyElement.html();
	}

	private boolean _containsBodyTag(String html) {
		String trimmedHTML = html.trim();

		String lowerCaseTrimmedHTML = StringUtil.toLowerCase(trimmedHTML);

		return lowerCaseTrimmedHTML.startsWith("<body");
	}

	private void _extractInlineHandlers(
		Element element, StringBundler sb, boolean recursive) {

		String id = element.attr("id");

		List<String> attributesToRemove = new ArrayList<>();

		for (Attribute attribute : element.attributes()) {
			String key = attribute.getKey();

			String lowerCaseKey = StringUtil.toLowerCase(key);

			if (lowerCaseKey.startsWith("on")) {
				if (Objects.equals(element.nodeName(), "body")) {
					sb.append("document.body.");
				}
				else {
					if (Validator.isBlank(id)) {
						id = StringUtil.randomString(8);
					}

					sb.append("document.getElementById('");
					sb.append(id);
					sb.append("').");
				}

				sb.append(key);
				sb.append("=function(event){");
				sb.append(element.attr(key));
				sb.append("};");

				attributesToRemove.add(key);
			}
		}

		if (!Validator.isBlank(id)) {
			element.attr("id", id);
		}

		for (String key : attributesToRemove) {
			element.removeAttr(key);
		}

		if (recursive) {
			for (Element child : element.children()) {
				_extractInlineHandlers(child, sb, true);
			}
		}
	}

}