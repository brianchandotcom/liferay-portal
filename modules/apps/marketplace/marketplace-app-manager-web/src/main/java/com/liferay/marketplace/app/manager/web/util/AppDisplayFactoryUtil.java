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
import com.liferay.marketplace.model.App;
import com.liferay.marketplace.model.Module;
import com.liferay.marketplace.service.AppLocalService;
import com.liferay.marketplace.service.ModuleLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 */
@Component
public class AppDisplayFactoryUtil {

	public static AppDisplay getAppDisplay(
		List<Bundle> bundles, String appName) {

		AppDisplay appDisplay = new AppDisplay(appName);

		if (appName.equals(AppDisplay.APP_NAME_UNCATEGORIZED)) {
			appName = null;
		}

		for (Bundle bundle : bundles) {
			Dictionary<String, String> headers = bundle.getHeaders();

			String curAppTitle = headers.get(
				BundleConstants.LIFERAY_RELENG_APP_TITLE);

			if (Validator.isNotNull(appName) && !appName.equals(curAppTitle)) {
				continue;
			}
			else if (curAppTitle != null) {
				continue;
			}

			appDisplay.addBundle(bundle);
		}

		return appDisplay;
	}

	public static List<AppDisplay> getAppDisplays(
		List<Bundle> bundles, String category, int state) {

		BundlesMap bundlesMap = new BundlesMap(bundles.size());

		bundlesMap.load(bundles);

		List<AppDisplay> appDisplays = new ArrayList<>();

		appDisplays.addAll(buildMarketplaceAppDisplays(bundlesMap, category));
		appDisplays.addAll(buildPortalAppDisplays(bundlesMap, category));

		filterAppDisplays(appDisplays, state);

		return ListUtil.sort(appDisplays);
	}

	protected static List<AppDisplay> buildMarketplaceAppDisplays(
		BundlesMap bundlesMap, String category) {

		List<AppDisplay> appDisplays = new ArrayList<>();

		List<App> apps = null;

		if (Validator.isNotNull(category)) {
			apps = _appLocalService.getApps(category);
		}
		else {
			apps = _appLocalService.getApps(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}

		for (App app : apps) {
			AppDisplay appDisplay = new AppDisplay(app.getTitle());

			List<Module> modules = _moduleLocalService.getModules(
				app.getAppId());

			for (Module module : modules) {
				Bundle bundle = bundlesMap.removeBundle(module);

				if (bundle != null) {
					appDisplay.addBundle(bundle);
				}
			}

			appDisplays.add(appDisplay);
		}

		return appDisplays;
	}

	protected static List<AppDisplay> buildPortalAppDisplays(
		BundlesMap bundlesMap, String category) {

		Collection<Bundle> bundles = bundlesMap.values();

		Map<String, AppDisplay> appDisplaysMap = new HashMap<>();

		for (Bundle bundle : bundles) {
			Dictionary<String, String> headers = bundle.getHeaders();

			String appTitle = headers.get(
				BundleConstants.LIFERAY_RELENG_APP_TITLE);

			if (appTitle == null) {
				appTitle = AppDisplay.APP_NAME_UNCATEGORIZED;
			}

			String[] categories = StringUtil.split(
				headers.get(BundleConstants.LIFERAY_RELENG_CATEGORY));

			if (!ArrayUtil.contains(categories, category)) {
				continue;
			}

			AppDisplay appDisplay = appDisplaysMap.get(appTitle);

			if (appDisplay == null) {
				appDisplay = new AppDisplay(appTitle);

				appDisplaysMap.put(appTitle, appDisplay);
			}

			appDisplay.addBundle(bundle);
		}

		return ListUtil.fromMapValues(appDisplaysMap);
	}

	protected static void filterAppDisplays(
		List<AppDisplay> appDisplays, int state) {

		Iterator<AppDisplay> itr = appDisplays.iterator();

		while (itr.hasNext()) {
			AppDisplay appDisplay = itr.next();

			if (appDisplay.getState() != state) {
				itr.remove();
			}
		}
	}

	@Reference
	protected void setAppLocalService(AppLocalService appLocalService) {
		_appLocalService = appLocalService;
	}

	@Reference
	protected void setModuleLocalService(
		ModuleLocalService moduleLocalService) {

		_moduleLocalService = moduleLocalService;
	}

	private static AppLocalService _appLocalService;
	private static ModuleLocalService _moduleLocalService;

}