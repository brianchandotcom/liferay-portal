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
public class ActionComparator implements Comparator<String>, Serializable {

	public ActionComparator(Locale locale) {
		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(String action1, String action2) {
		action1 = ResourceActionsUtil.getAction(_locale, action1);
		action2 = ResourceActionsUtil.getAction(_locale, action2);

		return _collator.compare(action1, action2);
	}

	private final Collator _collator;
	private final Locale _locale;

}