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

package com.liferay.site.initializer.liferay.learn.extra.gcp.function;

import com.liferay.petra.string.StringBundler;

import java.io.InputStream;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Allen Ziegenfus
 */
public class Main {

	public static void main(String[] arguments) throws Exception {
		Properties properties = new Properties();

		try (InputStream inputStream = Main.class.getResourceAsStream(
				"dependencies/application.properties")) {

			properties.load(inputStream);
		}

		Main main = new Main(
			properties.getProperty("liferay.client.id"),
			properties.getProperty("liferay.client.secret"),
			properties.getProperty("liferay.url"));

		main.uploadToLiferay();
	}

	public Main(
		String liferayClientId, String liferayClientSecret, String liferayURL) {

		_liferayClientId = liferayClientId;
		_liferayClientSecret = liferayClientSecret;
		_liferayURL = liferayURL;

		_logger = Logger.getLogger(Main.class.getName());

		try {
			LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);

			FileHandler fileHandler = new FileHandler(
				StringBundler.concat(
					localDateTime.getYear(), "-", localDateTime.getMonthValue(),
					"-", localDateTime.getDayOfMonth(), ".log"),
				true);

			fileHandler.setFormatter(new SimpleFormatter());

			_logger.addHandler(fileHandler);
		}
		catch (Exception exception) {
			_logger.log(Level.SEVERE, exception.getMessage(), exception);
		}
	}

	public void uploadToLiferay() throws Exception {
	}

	private final String _liferayClientId;
	private final String _liferayClientSecret;
	private final String _liferayURL;
	private final Logger _logger;

}