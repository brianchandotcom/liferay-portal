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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class AntiSamySanitizerImpl implements Sanitizer {

	public AntiSamySanitizerImpl(
		String[] blacklist, URL url, String[] whitelist) {

		try (InputStream inputStream = url.openStream()) {
			_policy = Policy.getInstance(inputStream);
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Unable to initialize policy", exception);
		}

		if (blacklist != null) {
			for (String blacklistItem : blacklist) {
				blacklistItem = blacklistItem.trim();

				if (!blacklistItem.isEmpty()) {
					blacklistItem = stripTrailingStar(blacklistItem);

					_blacklist.add(blacklistItem);
				}
			}
		}

		if (whitelist != null) {
			for (String whitelistItem : whitelist) {
				whitelistItem = whitelistItem.trim();

				if (!whitelistItem.isEmpty()) {
					whitelistItem = stripTrailingStar(whitelistItem);

					_whitelist.add(whitelistItem);
				}
			}
		}
	}

	public void addPolicy(String className, URL url) {
		try (InputStream inputStream = url.openStream()) {
			Policy policy = Policy.getInstance(inputStream);

			_policies.put(className, policy);
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Unable to initialize policy", exception);
		}
	}

	public void removePolicy(String className) {
		_policies.remove(className);
	}

	@Override
	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String content,
			Map<String, Object> options)
		throws SanitizerException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat("Sanitizing ", className, "#", classPK));
		}

		if (Validator.isNull(content) || Validator.isNull(contentType) ||
			!contentType.equals(ContentTypes.TEXT_HTML) ||
			isWhitelisted(className, classPK)) {

			return content;
		}

		AntiSamy antiSamy = new AntiSamy();

		try {
			if (isConfigured(className, classPK)) {
				Policy policy = _policies.get(className);

				CleanResults cleanResults = antiSamy.scan(
					content, policy, AntiSamy.SAX);

				return cleanResults.getCleanHTML();
			}

			CleanResults cleanResults = antiSamy.scan(content, _policy);

			return cleanResults.getCleanHTML();
		}
		catch (Exception exception) {
			_log.error("Unable to sanitize input", exception);

			throw new SanitizerException(exception);
		}
	}

	protected boolean isConfigured(String className, long classPK) {
		String classNameAndClassPK = className + StringPool.POUND + classPK;

		for (String policyClassName : _policies.keySet()) {
			if (classNameAndClassPK.startsWith(policyClassName)) {
				return true;
			}
		}

		return false;
	}

	protected boolean isWhitelisted(String className, long classPK) {
		String classNameAndClassPK = className + StringPool.POUND + classPK;

		for (String blacklistItem : _blacklist) {
			if (blacklistItem.equals(StringPool.STAR) ||
				classNameAndClassPK.startsWith(blacklistItem)) {

				return false;
			}
		}

		for (String whitelistItem : _whitelist) {
			if (whitelistItem.equals(StringPool.STAR) ||
				classNameAndClassPK.startsWith(whitelistItem)) {

				return true;
			}
		}

		return false;
	}

	protected String stripTrailingStar(String item) {
		if (item.equals(StringPool.STAR)) {
			return item;
		}

		char c = item.charAt(item.length() - 1);

		if (c == CharPool.STAR) {
			return item.substring(0, item.length() - 1);
		}

		return item;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AntiSamySanitizerImpl.class);

	private final List<String> _blacklist = new ArrayList<>();
	private final Map<String, Policy> _policies = new HashMap<>();
	private final Policy _policy;
	private final List<String> _whitelist = new ArrayList<>();

}