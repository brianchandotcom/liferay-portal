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

package com.liferay.lcs.util;

import com.liferay.lcs.exception.InitializationException;
import com.liferay.lcs.rest.client.LCSClusterNode;
import com.liferay.lcs.rest.client.LCSClusterNodeClient;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class KeyGeneratorImpl implements KeyGenerator {

	@Override
	public void clearCache() {
		_key = null;
	}

	@Override
	public String getKey() {
		return getKey(true);
	}

	public synchronized String getKey(boolean create) {
		if (_key != null) {
			return _key;
		}

		if (!create) {
			return null;
		}

		_key = DigesterUtil.digestHex(Digester.MD5, getLcsServerId());

		try {
			if (!isValid(_key)) {
				_key = DigesterUtil.digestHex(Digester.MD5, getLcsServerId());

				if (!isValid(_key)) {
					_key = DigesterUtil.digestHex(
						Digester.MD5, getLcsServerId(true));
				}
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage(), e);
			}

			_key = null;

			return DigesterUtil.digestHex(Digester.MD5, getLcsServerId());
		}

		return _key;
	}

	public void setLCSClusterNodeClient(
		LCSClusterNodeClient lcsClusterNodeClient) {

		_lcsClusterNodeClient = lcsClusterNodeClient;
	}

	protected byte[] generateLcsServerId() {
		try {
			Properties lcsServerIdProperties = new Properties();

			lcsServerIdProperties.put(
				"hostName", LicenseManagerUtil.getHostName());
			lcsServerIdProperties.put(
				"ipAddresses", StringUtil.merge(getIpAddresses()));
			lcsServerIdProperties.put(
				"macAddresses", StringUtil.merge(getMacAddresses()));
			lcsServerIdProperties.put(
				"salt", String.valueOf(UUID.randomUUID()));

			String lcsServerIdPropertiesString = PropertiesUtil.toString(
				lcsServerIdProperties);

			String lcsServerIdPropertiesBase64String = Base64.encode(
				lcsServerIdPropertiesString.getBytes(StringPool.UTF8));

			return lcsServerIdPropertiesBase64String.getBytes(StringPool.UTF8);
		}
		catch (IOException ioe) {
			throw new InitializationException(ioe);
		}
	}

	protected String getHostName(String lcsServerIdPropertiesString) {
		Matcher matcher = _hostNamePattern.matcher(lcsServerIdPropertiesString);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return StringPool.BLANK;
	}

	protected Set<String> getIpAddresses() {
		Set<String> ipAddresses = new HashSet<>();

		try {
			List<NetworkInterface> networkInterfaces = Collections.list(
				NetworkInterface.getNetworkInterfaces());

			for (NetworkInterface networkInterface : networkInterfaces) {
				List<InetAddress> inetAddresses = Collections.list(
					networkInterface.getInetAddresses());

				for (InetAddress inetAddress : inetAddresses) {
					if (inetAddress.isLinkLocalAddress() ||
						inetAddress.isLoopbackAddress() ||
						!(inetAddress instanceof Inet4Address)) {

						continue;
					}

					ipAddresses.add(inetAddress.getHostAddress());
				}
			}
		}
		catch (SocketException se) {
			_log.error("Unable to read local server's IP addresses", se);
		}

		return ipAddresses;
	}

	protected Set<String> getIpAddresses(String lcsServerIdPropertiesString) {
		Matcher ipAddressesMatcher = _ipAddressesPattern.matcher(
			lcsServerIdPropertiesString);

		Set<String> ipAddresses = new HashSet<>();

		if (!ipAddressesMatcher.find()) {
			return ipAddresses;
		}

		Matcher ipAddressMatcher = _ipAddressPattern.matcher(
			ipAddressesMatcher.group(1));

		while (ipAddressMatcher.find()) {
			String ipAddress = ipAddressMatcher.group(2);

			if ((ipAddress != null) && !ipAddress.equals(StringPool.BLANK)) {
				ipAddresses.add(ipAddress);
			}
		}

		return ipAddresses;
	}

	protected String getLcsServerId() {
		return getLcsServerId(false);
	}

	protected String getLcsServerId(boolean forceGenerate) {
		byte[] lcsServerIdBytes = null;

		try {
			File lcsServerIdFile = new File(
				_getLicenseRepositoryDir() + "/server/lcsServerId");

			if (forceGenerate || !lcsServerIdFile.exists()) {
				lcsServerIdBytes = generateLcsServerId();

				writeLcsServerProperties(lcsServerIdBytes);

				return Arrays.toString(lcsServerIdBytes);
			}
			else {
				lcsServerIdBytes = FileUtil.getBytes(lcsServerIdFile);
			}

			if (hasLCSServerIdParametersChanged(lcsServerIdBytes)) {
				lcsServerIdBytes = generateLcsServerId();

				writeLcsServerProperties(lcsServerIdBytes);
			}

			return Arrays.toString(lcsServerIdBytes);
		}
		catch (IOException ioe) {
			throw new InitializationException.FileSystemAccessException(
				PropsKeys.LIFERAY_HOME + "/data/license/server/lcsServerId",
				ioe);
		}
	}

	protected Set<String> getMacAddresses() {
		Set<String> macAddresses = new HashSet<>();

		try {
			List<NetworkInterface> networkInterfaces = Collections.list(
				NetworkInterface.getNetworkInterfaces());

			for (NetworkInterface networkInterface : networkInterfaces) {
				try {
					byte[] hardwareAddress =
						networkInterface.getHardwareAddress();

					if (ArrayUtil.isEmpty(hardwareAddress)) {
						continue;
					}

					StringBuilder sb = new StringBuilder(
						(hardwareAddress.length * 3) - 1);

					String hexString = StringUtil.bytesToHexString(
						hardwareAddress);

					for (int i = 0; i < hexString.length(); i += 2) {
						if (i != 0) {
							sb.append(CharPool.COLON);
						}

						sb.append(Character.toLowerCase(hexString.charAt(i)));
						sb.append(
							Character.toLowerCase(hexString.charAt(i + 1)));
					}

					macAddresses.add(sb.toString());
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to read hardware addresses");
					}
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to read local server's MAC addresses", e);
		}

		return macAddresses;
	}

	protected Set<String> getMacAddresses(String lcsServerIdPropertiesString) {
		Matcher macAddressesMatcher = _macAddressesPattern.matcher(
			lcsServerIdPropertiesString);

		Set<String> macAddresses = new HashSet<>();

		if (!macAddressesMatcher.find()) {
			return macAddresses;
		}

		Matcher macAddressMatcher = _macAddressPattern.matcher(
			macAddressesMatcher.group(1));

		while (macAddressMatcher.find()) {
			String macAddress = macAddressMatcher.group(2);

			if ((macAddress != null) && !macAddress.equals(StringPool.BLANK)) {
				macAddresses.add(macAddress);
			}
		}

		return macAddresses;
	}

	protected boolean hasLCSServerIdParametersChanged(byte[] lcsServerIdBytes)
		throws IOException {

		int count = 0;

		String curHostName = LicenseManagerUtil.getHostName();

		String lcsServerIdPropertiesString = new String(
			Base64.decode(new String(lcsServerIdBytes, StringPool.UTF8)),
			StringPool.UTF8);

		String serverHostName = getHostName(lcsServerIdPropertiesString);

		if (!curHostName.equals(serverHostName)) {
			count++;
		}

		Set<String> curMacAddresses = getMacAddresses();
		Set<String> serverMacAddresses = getMacAddresses(
			lcsServerIdPropertiesString);

		if (!curMacAddresses.equals(serverMacAddresses)) {
			count++;
		}

		Set<String> curIpAddresses = getIpAddresses();
		Set<String> serverIpAddresses = getIpAddresses(
			lcsServerIdPropertiesString);

		if (!curIpAddresses.equals(serverIpAddresses)) {
			count++;
		}

		if (count < 2) {
			return false;
		}

		return true;
	}

	protected boolean isValid(String key) {
		LCSClusterNode lcsClusterNode =
			_lcsClusterNodeClient.fetchLCSClusterNode(key);

		if ((lcsClusterNode == null) || lcsClusterNode.isArchived()) {
			return false;
		}

		return true;
	}

	protected void writeLcsServerProperties(byte[] lcsServerIdBytes)
		throws IOException {

		File lcsServerIdFile = new File(
			_LICENSE_REPOSITORY_DIR + "/server/lcsServerId");

		FileUtil.write(lcsServerIdFile, lcsServerIdBytes);
	}

	private static String _getLicenseRepositoryDir() {
		return _LICENSE_REPOSITORY_DIR;
	}

	private static final String _LICENSE_REPOSITORY_DIR =
		PropsUtil.get(PropsKeys.LIFERAY_HOME) + "/data/license";

	private static final Log _log = LogFactoryUtil.getLog(
		KeyGeneratorImpl.class);

	private static final Pattern _hostNamePattern = Pattern.compile(
		"hostName=(.*)(\\s?)");
	private static final Pattern _ipAddressesPattern = Pattern.compile(
		"ipAddresses=(.*)(\\s?)");
	private static final Pattern _ipAddressPattern = Pattern.compile(
		"(([^,]*),?)");
	private static final Pattern _macAddressesPattern = Pattern.compile(
		"macAddresses=(.*)(\\s?)");
	private static final Pattern _macAddressPattern = Pattern.compile(
		"(([^,]*),?)");

	private String _key;
	private LCSClusterNodeClient _lcsClusterNodeClient;

}