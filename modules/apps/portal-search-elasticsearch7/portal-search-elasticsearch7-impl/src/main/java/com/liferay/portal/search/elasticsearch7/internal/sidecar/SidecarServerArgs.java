/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import java.io.Serializable;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

import java.util.Map;

import org.elasticsearch.common.hash.MessageDigests;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.settings.KeyStoreWrapper;

/**
 * See org.elasticsearch.bootstrap.ServerArgs
 *
 * @author Dante Wang
 */
public class SidecarServerArgs implements Serializable {

	public SidecarServerArgs(
		String configDir, boolean daemonize, String logsDir, String pidFile,
		boolean quiet, Map<String, Serializable> settings) {

		_configDir = configDir;
		_daemonize = daemonize;
		_logsDir = logsDir;
		_pidFile = pidFile;
		_quiet = quiet;
		_settings = settings;
	}

	public void writeTo(StreamOutput streamOutput) throws Exception {
		streamOutput.writeBoolean(_daemonize);
		streamOutput.writeBoolean(_quiet);
		streamOutput.writeOptionalString(_pidFile);

		streamOutput.writeString(KeyStoreWrapper.class.getName());

		try (KeyStoreWrapper keyStoreWrapper = KeyStoreWrapper.create()) {
			streamOutput.writeInt(keyStoreWrapper.getFormatVersion());
			streamOutput.writeBoolean(keyStoreWrapper.hasPassword());
			streamOutput.writeBoolean(false);
			streamOutput.writeVInt(1);
			streamOutput.writeString(KeyStoreWrapper.SEED_SETTING.getKey());

			ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(
				ElasticsearchServerUtil.class.getSimpleName());

			byte[] bytes = byteBuffer.array();

			MessageDigest messageDigest = MessageDigests.sha256();

			streamOutput.writeByteArray(bytes);
			streamOutput.writeByteArray(messageDigest.digest(bytes));
			streamOutput.writeBoolean(false);
		}

		streamOutput.writeVInt(_settings.size());

		for (Map.Entry<String, Serializable> entry : _settings.entrySet()) {
			streamOutput.writeString(entry.getKey());
			streamOutput.writeGenericValue(entry.getValue());
		}

		streamOutput.writeString(_configDir);
		streamOutput.writeString(_logsDir);

		streamOutput.flush();
	}

	private final String _configDir;
	private final boolean _daemonize;
	private final String _logsDir;
	private final String _pidFile;
	private final boolean _quiet;
	private final Map<String, Serializable> _settings;

}