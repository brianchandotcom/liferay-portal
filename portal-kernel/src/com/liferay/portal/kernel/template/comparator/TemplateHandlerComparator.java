/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template.comparator;

import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.io.Serializable;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Eduardo García
 */
public class TemplateHandlerComparator
	implements Comparator<TemplateHandler>, Serializable {

	public TemplateHandlerComparator(Locale locale) {
		_locale = locale;

		_collator = CollatorUtil.getInstance(locale);
	}

	@Override
	public int compare(
		TemplateHandler templateHandler1, TemplateHandler templateHandler2) {

		String templateHandlerName1 = templateHandler1.getName(_locale);
		String templateHandlerName2 = templateHandler2.getName(_locale);

		return _collator.compare(templateHandlerName1, templateHandlerName2);
	}

	private final Collator _collator;
	private final Locale _locale;

}