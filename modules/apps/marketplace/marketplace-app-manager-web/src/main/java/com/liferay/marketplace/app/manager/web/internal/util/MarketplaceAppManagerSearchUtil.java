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

package com.liferay.marketplace.app.manager.web.internal.util;

import com.liferay.marketplace.app.manager.web.internal.constants.BundleStateConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

/**
 * @author Ryan Park
 */
public class MarketplaceAppManagerSearchUtil {

	public static List<Object> getResults(
		List<Bundle> bundles, String keywords, Locale locale) {

		List<Object> results = new ArrayList<>();

		String keywordsRegex = getKeywordsRegex(keywords);

		// App display

		List<AppDisplay> appDisplays = AppDisplayFactoryUtil.getAppDisplays(
			bundles, StringPool.BLANK, BundleStateConstants.ANY, locale);

		for (AppDisplay appDisplay : appDisplays) {
			if (hasAppDisplayKeywordsMatch(appDisplay, keywordsRegex)) {
				results.add(appDisplay);
			}
		}

		// Bundle

		for (Bundle bundle : bundles) {
			if (hasBundleKeywordsMatch(bundle, keywordsRegex)) {
				results.add(bundle);
			}
		}

		return results;
	}

	protected static boolean containsMatches(String regex, String string) {
		if (string == null) {
			return false;
		}

		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(string);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	protected static String getKeywordsRegex(String keywords) {
		keywords = StringUtil.replace(
			keywords,
			new String[] {
				StringPool.SPACE, StringPool.APOSTROPHE, StringPool.QUOTE
			},
			new String[] {StringPool.PIPE, StringPool.BLANK, StringPool.BLANK});

		return StringPool.OPEN_PARENTHESIS + keywords +
			StringPool.CLOSE_PARENTHESIS;
	}

	protected static boolean hasAppDisplayKeywordsMatch(
		AppDisplay appDisplay, String keywordsRegex) {

		if (containsMatches(keywordsRegex, appDisplay.getDisplayTitle()) ||
			containsMatches(keywordsRegex, appDisplay.getDescription())) {

			return true;
		}

		return false;
	}

	protected static boolean hasBundleKeywordsMatch(
		Bundle bundle, String keywordsRegex) {

		if (containsMatches(keywordsRegex, bundle.getSymbolicName())) {
			return true;
		}

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String bundleDescription = headers.get(Constants.BUNDLE_DESCRIPTION);

		if (containsMatches(keywordsRegex, bundleDescription)) {
			return true;
		}

		String bundleName = headers.get(Constants.BUNDLE_NAME);

		if (containsMatches(keywordsRegex, bundleName)) {
			return true;
		}

		return false;
	}

}