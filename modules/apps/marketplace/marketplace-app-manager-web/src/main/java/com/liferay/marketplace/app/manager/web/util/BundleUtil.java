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
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class BundleUtil {

	public static void filterBundles(List<Bundle> bundles, int state) {
		Iterator<Bundle> iterator = bundles.iterator();

		while (iterator.hasNext()) {
			Bundle bundle = iterator.next();

			if ((state > 0) && (bundle.getState() != state)) {
				iterator.remove();
			}
		}
	}

	public static boolean isFragment(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String fragmentHost = headers.get(BundleConstants.FRAGMENT_HOST);

		if (Validator.isNotNull(fragmentHost)) {
			return true;
		}
		else {
			return false;
		}
	}

}