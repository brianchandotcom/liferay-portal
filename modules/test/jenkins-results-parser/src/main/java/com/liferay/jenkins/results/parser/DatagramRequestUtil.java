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

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.Properties;

/**
 * @author Leslie Wong
 */
public class DatagramRequestUtil {

	public static void send(String message) {
		try (DatagramSocket datagramSocket = new DatagramSocket()) {
			System.out.println("Message payload: " + message);

			Properties buildProperties = null;

			try {
				buildProperties = JenkinsResultsParserUtil.getBuildProperties();
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to get build.properties", ioe);
			}

			String metricsHostName = buildProperties.getProperty(
				"build.metrics.host.name");

			InetAddress inetAddress = InetAddress.getByName(metricsHostName);

			int metricsHostPort = Integer.valueOf(
				buildProperties.getProperty("build.metrics.host.port"));

			DatagramPacket datagramPacket = new DatagramPacket(
				message.getBytes(), message.length(), inetAddress,
				metricsHostPort);

			datagramSocket.connect(inetAddress, metricsHostPort);

			datagramSocket.send(datagramPacket);
		}
		catch (IOException ioe) {
			System.out.println("Unable to send payload:\n" + ioe.getMessage());
		}
	}

}