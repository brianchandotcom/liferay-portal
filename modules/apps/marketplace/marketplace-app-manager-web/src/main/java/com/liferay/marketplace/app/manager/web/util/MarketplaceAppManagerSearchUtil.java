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

package com.liferay.marketplace.app.manager.web.util;

import com.liferay.marketplace.app.manager.web.constants.BundleConstants;
import com.liferay.marketplace.app.manager.web.constants.BundleStateConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class MarketplaceAppManagerSearchUtil {

	public static List<Object> getResults(
		List<Bundle> bundles, String keywords) {

		List<Object> results = new ArrayList<>();

		String keywordsRegex = getKeywordsRegex(keywords);

		// App display

		List<AppDisplay> appDisplays = AppDisplayFactoryUtil.getAppDisplays(
			bundles, StringPool.BLANK, BundleStateConstants.ANY);

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

		// Module group display

		List<ModuleGroupDisplay> moduleGroupDisplays = new ArrayList<>();

		for (AppDisplay appDisplay : appDisplays) {
			if (appDisplay.hasModuleGroups()) {
				moduleGroupDisplays.addAll(appDisplay.getModuleGroupDisplays());
			}
		}

		for (ModuleGroupDisplay moduleGroupDisplay : moduleGroupDisplays) {
			if (hasModuleGroupDisplayKeywordsMatch(
					moduleGroupDisplay, keywordsRegex)) {

				results.add(moduleGroupDisplay);
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
		else {
			return false;
		}
	}

	protected static String getKeywordsRegex(String keywords) {
		keywords = StringUtil.replace(
			keywords, StringPool.SPACE, StringPool.PIPE);

		return
			StringPool.OPEN_PARENTHESIS + keywords +
				StringPool.CLOSE_PARENTHESIS;
	}

	protected static boolean hasAppDisplayKeywordsMatch(
		AppDisplay appDisplay, String keywordsRegex) {

		if (containsMatches(keywordsRegex, appDisplay.getTitle()) ||
			containsMatches(keywordsRegex, appDisplay.getDescription())) {

			return true;
		}
		else {
			return false;
		}
	}

	protected static boolean hasBundleKeywordsMatch(
		Bundle bundle, String keywordsRegex) {

		if (containsMatches(keywordsRegex, bundle.getSymbolicName())) {
			return true;
		}

		Dictionary<String, String> headers = bundle.getHeaders();

		String bundleDescription = headers.get(
			BundleConstants.BUNDLE_DESCRIPTION);

		if (containsMatches(keywordsRegex, bundleDescription)) {
			return true;
		}

		String bundleName = headers.get(BundleConstants.BUNDLE_NAME);

		if (containsMatches(keywordsRegex, bundleName)) {
			return true;
		}

		return false;
	}

	protected static boolean hasModuleGroupDisplayKeywordsMatch(
		ModuleGroupDisplay moduleGroupDisplay, String keywordsRegex) {

		if (containsMatches(keywordsRegex, moduleGroupDisplay.getTitle()) ||
			containsMatches(
				keywordsRegex, moduleGroupDisplay.getDescription())) {

			return true;
		}
		else {
			return false;
		}
	}

}