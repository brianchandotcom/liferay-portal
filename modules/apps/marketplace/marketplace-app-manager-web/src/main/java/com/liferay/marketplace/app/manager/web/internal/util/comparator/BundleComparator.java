/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.app.manager.web.internal.util.comparator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Comparator;
import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

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
		String bundleName1 = _getBundleName(bundle1);
		String bundleName2 = _getBundleName(bundle2);

		int value = bundleName1.compareTo(bundleName2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private String _getBundleName(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getString(headers.get(Constants.BUNDLE_NAME));
	}

	private final boolean _ascending;

}