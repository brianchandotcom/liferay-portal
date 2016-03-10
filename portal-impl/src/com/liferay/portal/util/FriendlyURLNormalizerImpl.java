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

package com.liferay.portal.util;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.Normalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@DoPrivileged
public class FriendlyURLNormalizerImpl implements FriendlyURLNormalizer {

	@Override
	public String normalize(String friendlyURL) {
		return normalize(friendlyURL, _friendlyURLPattern);
	}

	@Override
	public String normalize(String friendlyURL, Pattern friendlyURLPattern) {
		if (Validator.isNull(friendlyURL)) {
			return friendlyURL;
		}

		friendlyURL = StringUtil.toLowerCase(friendlyURL);
		friendlyURL = Normalizer.normalizeToAscii(friendlyURL);

		Matcher matcher = friendlyURLPattern.matcher(friendlyURL);

		friendlyURL = matcher.replaceAll(StringPool.DASH);

		matcher = _friendlyURLHyphenPattern.matcher(friendlyURL);

		friendlyURL = matcher.replaceAll(StringPool.DASH);

		return friendlyURL;
	}

	@Override
	public String normalizeWithPeriodsAndSlashes(String friendlyURL) {
		return normalize(friendlyURL, _friendlyURLPatternWithPeriodsAndSlashes);
	}

	private static final Pattern _friendlyURLHyphenPattern = Pattern.compile(
		"-{2,}");
	private static final Pattern _friendlyURLPattern = Pattern.compile(
		"[^a-z0-9./_-]");
	private static final Pattern _friendlyURLPatternWithPeriodsAndSlashes =
		Pattern.compile("[^a-z0-9_-]");

}