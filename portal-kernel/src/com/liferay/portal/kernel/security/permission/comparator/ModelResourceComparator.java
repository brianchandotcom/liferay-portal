/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.comparator;

import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.io.Serializable;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class ModelResourceComparator
	implements Comparator<String>, Serializable {

	public ModelResourceComparator(Locale locale) {
		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(String resource1, String resource2) {
		resource1 = ResourceActionsUtil.getModelResource(_locale, resource1);
		resource2 = ResourceActionsUtil.getModelResource(_locale, resource2);

		return _collator.compare(resource1, resource2);
	}

	private final Collator _collator;
	private final Locale _locale;

}