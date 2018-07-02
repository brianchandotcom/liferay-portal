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

package com.liferay.journal.kernel.util;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Locale;

/**
 * @author     Marcellus Tavares
 * @author     Bruno Basto
 * @author     Leonardo Barros
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class JournalConverterManagerUtil {

	public static String getDDMXSD(String journalXSD, Locale defaultLocale)
		throws Exception {

		return _journalConverterManager.getDDMXSD(journalXSD, defaultLocale);
	}

	private static volatile JournalConverterManager _journalConverterManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			JournalConverterManager.class, JournalConverterManagerUtil.class,
			"_journalConverterManager", false);

}