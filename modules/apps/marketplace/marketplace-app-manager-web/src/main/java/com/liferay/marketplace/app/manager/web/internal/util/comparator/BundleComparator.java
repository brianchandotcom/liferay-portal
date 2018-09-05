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

import com.liferay.marketplace.app.manager.web.internal.constants.BundleConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Comparator;
import java.util.Dictionary;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class BundleComparator implements Comparator<Bundle> {

	public BundleComparator(String orderByType) {
		if (!orderByType.equals("asc")) {
			_ascending = false;
		}
		else {
			_ascending = true;
		}
	}

	@Override
	public int compare(Bundle bundle1, Bundle bundle2) {
		String bundle1Name = getBundleName(bundle1);
		String bundle2Name = getBundleName(bundle2);

		int value = bundle1Name.compareTo(bundle2Name);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	protected String getBundleName(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getString(headers.get(BundleConstants.BUNDLE_NAME));
	}

	private final boolean _ascending;

}