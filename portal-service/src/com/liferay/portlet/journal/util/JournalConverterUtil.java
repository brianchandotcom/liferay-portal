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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 * @author Bruno Basto
 * @author Leonardo Barros
 */
public class JournalConverterUtil {

	public static String getDDMXSD(String journalXSD) throws Exception {
		return _getJournalConverterManager().getDDMXSD(
			journalXSD, LocaleUtil.getSiteDefault());
	}

	public static String getDDMXSD(String journalXSD, Locale defaultLocale)
		throws Exception {

		return _getJournalConverterManager().getDDMXSD(
			journalXSD, defaultLocale);
	}

	private static JournalConverterManager _getJournalConverterManager() {
		JournalConverterManager manager =
			_instance._serviceTracker.getService();

		if (manager != null) {
			return manager;
		}
		else {
			if (_dummyImpl == null) {
				_dummyImpl = new DummyJournalConverterManagerImpl();
			}

			return _dummyImpl;
		}
	}

	private JournalConverterUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(JournalConverterManager.class);

		_serviceTracker.open();
	}

	private static final JournalConverterUtil _instance =
		new JournalConverterUtil();

	private static DummyJournalConverterManagerImpl _dummyImpl;

	private final
		ServiceTracker<JournalConverterManager,
			JournalConverterManager> _serviceTracker;

}