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

package com.liferay.marketplace.app.manager.web.util.comparator;

import com.liferay.marketplace.app.manager.web.constants.BundleConstants;
import com.liferay.marketplace.app.manager.web.util.AppDisplay;
import com.liferay.marketplace.app.manager.web.util.ModuleGroupDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Comparator;
import java.util.Dictionary;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class MarketplaceAppManagerComparator implements Comparator {

	public MarketplaceAppManagerComparator(String orderByType) {
		_orderByType = orderByType;

		if (!orderByType.equals("asc")) {
			_ascending = false;
		}
		else {
			_ascending = true;
		}
	}

	@Override
	public int compare(Object object1, Object object2) {
		int value = compareClass(object1, object2);

		if (value == 0) {
			value = compareTitle(object1, object2);
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	protected int compareClass(Object object1, Object object2) {
		int value1 = getClassValue(object1);
		int value2 = getClassValue(object2);

		if (value1 < value2) {
			return -1;
		}

		if (value1 > value2) {
			return 1;
		}
		else {
			return 0;
		}
	}

	protected int compareTitle(Object object1, Object object2) {
		String title1 = getTitle(object1);
		String title2 = getTitle(object2);

		return title1.compareToIgnoreCase(title2);
	}

	protected int getClassValue(Object object) {
		if (object instanceof AppDisplay) {
			AppDisplay appDisplay = (AppDisplay)object;

			if (appDisplay.hasModuleGroups()) {
				return 1;
			}
			else {
				return 2;
			}
		}
		else if (object instanceof ModuleGroupDisplay) {
			return 2;
		}
		else if (object instanceof Bundle) {
			return 3;
		}

		return 0;
	}

	protected String getTitle(Object object) {
		if (object instanceof AppDisplay) {
			AppDisplay appDisplay = (AppDisplay)object;

			return appDisplay.getTitle();
		}
		else if (object instanceof Bundle) {
			Bundle bundle = (Bundle)object;

			Dictionary<String, String> headers = bundle.getHeaders();

			return GetterUtil.getString(
				headers.get(BundleConstants.BUNDLE_NAME));
		}
		else if (object instanceof ModuleGroupDisplay) {
			ModuleGroupDisplay moduleGroupDisplay = (ModuleGroupDisplay)object;

			return moduleGroupDisplay.getTitle();
		}

		return StringPool.BLANK;
	}

	private final boolean _ascending;
	private final String _orderByType;

}