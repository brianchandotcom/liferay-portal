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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class MarketplaceAppManagerUtil {

	public static String[] getCategories(List<App> apps, List<Bundle> bundles) {
		List<String> categories = new ArrayList<>();

		categories.addAll(getAppCategories(apps));
		categories.addAll(getBundleCategories(bundles));

		ListUtil.distinct(categories);
		ListUtil.sort(categories);

		categories.add(0, "all-categories");

		return ArrayUtil.toStringArray(categories);
	}

	protected static List<String> getAppCategories(List<App> apps) {
		List<String> categories = new ArrayList<>(apps.size());

		for (App app : apps) {
			if (Validator.isNotNull(app.getCategory())) {
				categories.add(app.getCategory());
			}
		}

		return categories;
	}

	protected static List<String> getBundleCategories(List<Bundle> bundles) {
		List<String> categories = new ArrayList<>();

		for (Bundle bundle : bundles) {
			Dictionary<String, String> headers = bundle.getHeaders();

			String[] categoriesArray = StringUtil.split(
				headers.get(BundleConstants.LIFERAY_RELENG_CATEGORY));

			for (String category : categoriesArray) {
				if (Validator.isNotNull(category)) {
					categories.add(category);
				}
			}
		}

		return categories;
	}

}