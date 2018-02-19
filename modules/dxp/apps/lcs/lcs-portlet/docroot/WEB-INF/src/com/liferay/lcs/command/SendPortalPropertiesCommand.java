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

package com.liferay.lcs.command;

import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.ResponseMessage;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.LCSConstants;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.lcs.util.ResponseMessageUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Ivica Cardic
 */
public class SendPortalPropertiesCommand implements Command {

	@Override
	public void execute(CommandMessage commandMessage) throws PortalException {
		if (_log.isTraceEnabled()) {
			_log.trace("Executing send portal properties command");
		}

		Properties portalProperties = getSecurityInsensitivePropertiesKeys();

		StringBundler sb = new StringBundler(portalProperties.size());

		for (Object key : portalProperties.keySet()) {
			sb.append(
				DigesterUtil.digestHex(
					Digester.MD5, (String)portalProperties.get(key)));
		}

		String installedHashCode = DigesterUtil.digestHex(
			Digester.MD5, sb.toString());

		String hashCode = null;

		if (commandMessage.getPayload() != null) {
			hashCode = (String)commandMessage.getPayload();
		}

		if (installedHashCode.equals(hashCode)) {
			return;
		}

		Map<Object, Object> portalPropertiesMap = new TreeMap<>(
			portalProperties);

		Map<String, Object> payload = new HashMap<>();

		payload.put("hashCode", installedHashCode);
		payload.put("portalProperties", portalPropertiesMap);

		ResponseMessage responseMessage =
			ResponseMessageUtil.createResponseMessage(commandMessage, payload);

		try {
			_lcsConnectionManager.sendMessage(responseMessage);
		}
		catch (Exception e) {
			_log.error("Unable to send portal properties", e);
		}
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	protected Properties getSecurityInsensitivePropertiesKeys() {
		String portalPropertiesBlacklist =
			LCSUtil.getPortalPropertiesBlacklist();

		Properties portalProperties = new SortedProperties(
			PropsUtil.getProperties());

		Set<Object> portalPropertiesKeys = portalProperties.keySet();

		Iterator<Object> iterator = portalPropertiesKeys.iterator();

		while (iterator.hasNext()) {
			String portalPropertiesKey = (String)iterator.next();

			if (portalPropertiesKey.endsWith(".password")) {
				if (isSecurityInsensitive(portalPropertiesKey)) {
					continue;
				}

				iterator.remove();

				continue;
			}

			for (String portalPropertiesSecuritySensitiveKey :
					LCSConstants.PORTAL_PROPERTIES_SECURITY_SENSITIVE) {

				if (portalPropertiesKey.startsWith(
						portalPropertiesSecuritySensitiveKey)) {

					iterator.remove();

					break;
				}
			}

			if (StringUtil.contains(
					portalPropertiesBlacklist, portalPropertiesKey)) {

				iterator.remove();
			}
		}

		return portalProperties;
	}

	protected boolean isSecurityInsensitive(String portalPropertiesKey) {
		for (String portalPropertiesSecurityInsensitiveKey :
				LCSConstants.PORTAL_PROPERTIES_SECURITY_INSENSITIVE) {

			if (portalPropertiesKey.startsWith(
					portalPropertiesSecurityInsensitiveKey)) {

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SendPortalPropertiesCommand.class);

	private LCSConnectionManager _lcsConnectionManager;

}