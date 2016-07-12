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

package com.liferay.portal.security.antisamy.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.BaseSanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class AntiSamySanitizerImpl extends BaseSanitizer {

	public AntiSamySanitizerImpl(
		String[] blacklist, URL url, String[] whitelist) {

		try (InputStream inputstream = url.openStream()) {
			_policy = Policy.getInstance(inputstream);
		}
		catch (Exception e) {
			throw new IllegalStateException("Unable to initialize policy", e);
		}

		if (blacklist != null) {
			for (String blacklistItem : blacklist) {
				blacklistItem = blacklistItem.trim();

				if (!blacklistItem.isEmpty()) {
					_blacklist.add(blacklistItem);
				}
			}
		}

		if (whitelist != null) {
			for (String whitelistItem : whitelist) {
				whitelistItem = whitelistItem.trim();

				if (!whitelistItem.isEmpty()) {
					_whitelist.add(whitelistItem);
				}
			}
		}
	}

	@Override
	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String content,
			Map<String, Object> options)
		throws SanitizerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Sanitizing " + className + "#" + classPK);
		}

		if (Validator.isNull(content)) {
			return content;
		}

		if (Validator.isNull(contentType) ||
			!contentType.equals(ContentTypes.TEXT_HTML)) {

			return content;
		}

		if (isWhitelisted(className, classPK)) {
			return content;
		}

		try {
			AntiSamy antiSamy = new AntiSamy();

			CleanResults cleanResults = antiSamy.scan(content, _policy);

			return cleanResults.getCleanHTML();
		}
		catch (Exception e) {
			_log.error("Unable to sanitize input", e);

			throw new SanitizerException(e);
		}
	}

	protected boolean isWhitelisted(String className, long classPK) {
		String classNameAndClassPK = className + StringPool.POUND + classPK;

		for (String blacklistItem : _blacklist) {
			if (classNameAndClassPK.startsWith(blacklistItem)) {
				return false;
			}
		}

		for (String whitelistItem : _whitelist) {
			if (classNameAndClassPK.startsWith(whitelistItem)) {
				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AntiSamySanitizerImpl.class);

	private final List<String> _blacklist = new ArrayList<>();
	private final Policy _policy;
	private final List<String> _whitelist = new ArrayList<>();

}