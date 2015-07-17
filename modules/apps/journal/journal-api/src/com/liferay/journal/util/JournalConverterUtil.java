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

package com.liferay.journal.util;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 * @author Bruno Basto
 */
public class JournalConverterUtil {

	public static String getContent(DDMStructure ddmStructure, Fields ddmFields)
		throws Exception {

		return getJournalConverter().getContent(ddmStructure, ddmFields);
	}

	public static Fields getDDMFields(
			DDMStructure ddmStructure, Document document)
		throws Exception {

		return getJournalConverter().getDDMFields(ddmStructure, document);
	}

	public static Fields getDDMFields(DDMStructure ddmStructure, String content)
		throws Exception {

		return getJournalConverter().getDDMFields(ddmStructure, content);
	}

	public static String getDDMXSD(String journalXSD) throws Exception {
		return getJournalConverter().getDDMXSD(journalXSD);
	}

	public static String getDDMXSD(String journalXSD, Locale defaultLocale)
		throws Exception {

		return getJournalConverter().getDDMXSD(journalXSD, defaultLocale);
	}

	public static JournalConverter getJournalConverter() {
		return _instance._serviceTracker.getService();
	}

	public static String getJournalXSD(String ddmXSD) throws Exception {
		return getJournalConverter().getJournalXSD(ddmXSD);
	}

	private JournalConverterUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(JournalConverter.class);

		_serviceTracker.open();
	}

	private static final JournalConverterUtil _instance =
		new JournalConverterUtil();

	private final ServiceTracker<JournalConverter, JournalConverter>
		_serviceTracker;

}