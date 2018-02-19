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
import com.liferay.lcs.util.LCSPatcherUtil;
import com.liferay.lcs.util.ResponseMessageUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class SendPatchesCommand implements Command {

	@Override
	public void execute(CommandMessage commandMessage) throws PortalException {
		if (_log.isTraceEnabled()) {
			_log.trace("Executing send patches command");
		}

		if (!LCSPatcherUtil.isConfigured()) {
			if (_log.isWarnEnabled()) {
				_log.warn("Patcher is not configured. Unable to send patches");
			}

			return;
		}

		String[] fixedIssues = LCSPatcherUtil.getFixedIssues();

		String hashCode = null;

		if (commandMessage.getPayload() != null) {
			hashCode = (String)commandMessage.getPayload();
		}

		String[] installedPatches = LCSPatcherUtil.getInstalledPatches();

		Map<String, Object> payload = new HashMap<>();

		StringBundler sb = new StringBundler(installedPatches.length + 1);

		if (installedPatches.length > 0) {
			Arrays.sort(installedPatches);

			for (String patch : installedPatches) {
				sb.append(DigesterUtil.digestHex(Digester.MD5, patch));
			}
		}

		sb.append(
			DigesterUtil.digestHex(
				Digester.MD5, String.valueOf(LCSPatcherUtil.isConfigured())));
		sb.append(
			DigesterUtil.digestHex(
				Digester.MD5,
				String.valueOf(LCSPatcherUtil.getPatchingToolVersion())));

		String installedHashCode = DigesterUtil.digestHex(
			Digester.MD5, sb.toString());

		if (installedHashCode.equals(hashCode)) {
			if (_log.isTraceEnabled()) {
				_log.trace("Installed patches match available patches");
			}

			return;
		}

		payload.put("fixedIssues", ListUtil.fromArray(fixedIssues));
		payload.put("hashCode", installedHashCode);

		Map<String, Integer> patchIdsStatuses = new HashMap<>();

		for (String patch : installedPatches) {
			patchIdsStatuses.put(patch, LCSConstants.PATCHES_INSTALLED);
		}

		payload.put("patchIdsStatuses", patchIdsStatuses);

		ResponseMessage responseMessage =
			ResponseMessageUtil.createResponseMessage(commandMessage, payload);

		try {
			_lcsConnectionManager.sendMessage(responseMessage);
		}
		catch (Exception e) {
			_log.error("Unable to send installed patches statuses", e);
		}
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SendPatchesCommand.class);

	private LCSConnectionManager _lcsConnectionManager;

}