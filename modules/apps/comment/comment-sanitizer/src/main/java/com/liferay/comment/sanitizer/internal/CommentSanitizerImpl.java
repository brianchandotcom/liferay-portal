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

package com.liferay.comment.sanitizer.internal;

import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = Sanitizer.class)
public class CommentSanitizerImpl implements Sanitizer {

	public CommentSanitizerImpl() {
		_commentAllowedContent = new CommentAllowedContent(
			PropsValues.DISCUSSION_COMMENTS_ALLOWED_CONTENT);
	}

	@Override
	public String sanitize(
		long companyId, long groupId, long userId, String className,
		long classPK, String contentType, String[] modes, String content,
		Map<String, Object> options) {

		if (MapUtil.isEmpty(options)) {
			return content;
		}

		boolean discussion = GetterUtil.getBoolean(options.get("discussion"));

		if (!discussion || !contentType.equals(ContentTypes.TEXT_HTML)) {
			return content;
		}

		return sanitize(content);
	}

	protected String sanitize(String html) {
		HtmlPolicyBuilder htmlPolicyBuilder = new HtmlPolicyBuilder();

		htmlPolicyBuilder.allowStandardUrlProtocols();

		Map<String, String[]> attributeNames =
			_commentAllowedContent.getAttributeNames();

		for (Map.Entry<String, String[]> entry : attributeNames.entrySet()) {
			String elementName = entry.getKey();
			String[] attributesNames = entry.getValue();

			if (attributesNames != null) {
				HtmlPolicyBuilder.AttributeBuilder attributeBuilder =
					htmlPolicyBuilder.allowAttributes(attributesNames);

				attributeBuilder.onElements(elementName);
			}

			htmlPolicyBuilder.allowElements(elementName);
		}

		PolicyFactory policyFactory = htmlPolicyBuilder.toFactory();

		return policyFactory.sanitize(html);
	}

	private final CommentAllowedContent _commentAllowedContent;

}