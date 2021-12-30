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

package com.liferay.marketplace.app.manager.web.internal.util.comparator;

import com.liferay.marketplace.app.manager.web.internal.util.AppDisplay;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Comparator;
import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

/**
 * @author Ryan Park
 */
public class MarketplaceAppManagerComparator implements Comparator<Object> {

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
		int value = _compareClass(object1, object2);

		if (value == 0) {
			value = _compareTitle(object1, object2);
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private int _compareClass(Object object1, Object object2) {
		int value1 = _getClassValue(object1);
		int value2 = _getClassValue(object2);

		if (value1 < value2) {
			return -1;
		}

		if (value1 > value2) {
			return 1;
		}

		return 0;
	}

	private int _compareTitle(Object object1, Object object2) {
		String title1 = _getTitle(object1);
		String title2 = _getTitle(object2);

		return title1.compareToIgnoreCase(title2);
	}

	private int _getClassValue(Object object) {
		if (object instanceof AppDisplay) {
			return 2;
		}
		else if (object instanceof Bundle) {
			return 3;
		}

		return 0;
	}

	private String _getTitle(Object object) {
		if (object instanceof AppDisplay) {
			AppDisplay appDisplay = (AppDisplay)object;

			return appDisplay.getDisplayTitle();
		}
		else if (object instanceof Bundle) {
			Bundle bundle = (Bundle)object;

			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			return GetterUtil.getString(headers.get(Constants.BUNDLE_NAME));
		}

		return StringPool.BLANK;
	}

	private final boolean _ascending;
	private final String _orderByType;

}