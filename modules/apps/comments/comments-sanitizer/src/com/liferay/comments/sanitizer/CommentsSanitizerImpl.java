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

package com.liferay.comments.sanitizer;

import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class CommentsSanitizerImpl implements Sanitizer {

	public CommentsSanitizerImpl() {
		_commentsAllowedContent = new CommentsAllowedContent(
			PropsValues.DISCUSSION_COMMENTS_ALLOWED_CONTENT);
	}

	@Override
	public byte[] sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException {

		return bytes;
	}

	@Override
	public void sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes,
			InputStream inputStream, OutputStream outputStream,
			Map<String, Object> options)
		throws SanitizerException {
	}

	@Override
	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException {

		if (MapUtil.isEmpty(options)) {
			return s;
		}

		boolean discussion = GetterUtil.getBoolean(options.get("discussion"));

		if (!discussion || !contentType.equals("text/html")) {
			return s;
		}

		return sanitize(s);
	}

	protected String sanitize(String html) {
		HtmlPolicyBuilder htmlPolicyBuilder = new HtmlPolicyBuilder();

		htmlPolicyBuilder.allowStandardUrlProtocols();

		Map<String, String[]> allowedContentElementAttributes =
			_commentsAllowedContent.getAllowedContentElementAttributes();

		for (String allowedContentElement :
				allowedContentElementAttributes.keySet()) {

			String[] allowedContentAttributes =
				allowedContentElementAttributes.get(allowedContentElement);

			if (allowedContentAttributes != null) {
				htmlPolicyBuilder.allowAttributes(allowedContentAttributes).
					onElements(allowedContentElement);
			}

			htmlPolicyBuilder.allowElements(allowedContentElement);
		}

		PolicyFactory policyFactory = htmlPolicyBuilder.toFactory();

		return policyFactory.sanitize(html);
	}

	private final CommentsAllowedContent _commentsAllowedContent;

}