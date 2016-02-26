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

package com.liferay.portal.kernel.template.comparator;

import com.liferay.portal.kernel.template.TemplateHandler;

import java.io.Serializable;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Eduardo Garcia
 */
public class TemplateHandlerComparator
	implements Comparator<TemplateHandler>, Serializable {

	public TemplateHandlerComparator(Locale locale) {
		_locale = locale;
	}

	@Override
	public int compare(
		TemplateHandler templateHandler1, TemplateHandler templateHandler2) {

		String templateHandlerName1 = templateHandler1.getName(_locale);
		String templateHandlerName2 = templateHandler2.getName(_locale);

		return templateHandlerName1.compareTo(templateHandlerName2);
	}

	private final Locale _locale;

}