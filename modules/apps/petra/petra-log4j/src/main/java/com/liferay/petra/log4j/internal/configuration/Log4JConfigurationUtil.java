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

package com.liferay.petra.log4j.internal.configuration;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;

import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Hai Yu
 */
public class Log4JConfigurationUtil {

	public static void configureLog4JXml(String xml) {
		DOMConfigurator domConfigurator = new DOMConfigurator();

		domConfigurator.doConfigure(
			new UnsyncStringReader(xml), LogManager.getLoggerRepository());
	}

}