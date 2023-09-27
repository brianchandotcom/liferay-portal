/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.google.merchant.internal.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import com.liferay.commerce.google.merchant.internal.constants.CommerceGoogleMerchantConstants;
import com.liferay.commerce.google.merchant.internal.jsch.FingerprintHostKeyRepository;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Eric Chin
 */
@Component(
	configurationPid = "com.liferay.commerce.google.merchant.internal.sftp.SftpConfiguration",
	service = SftpUploader.class
)
public class SftpUploader {

	public void upload(String fileName, String fileContent) throws Exception {
		ChannelSftp channelSftp = null;
		Session jschSession = null;

		try {
			String host = _sftpConfiguration.host();
			String password = _sftpConfiguration.password();
			int port = _sftpConfiguration.port();
			String username = _sftpConfiguration.username();

			JSch jSch = new JSch();

			FingerprintHostKeyRepository fingerprintHostKeyRepository =
				new FingerprintHostKeyRepository(
					jSch, _sftpConfiguration.fingerprint());

			jSch.setHostKeyRepository(fingerprintHostKeyRepository);

			jschSession = jSch.getSession(username, host);

			jschSession.setPassword(password);
			jschSession.setPort(port);

			jschSession.connect();

			channelSftp = (ChannelSftp)jschSession.openChannel(
				CommerceGoogleMerchantConstants.JSCH_CHANNEL_SFTP);

			channelSftp.connect();

			InputStream inputStream = new ByteArrayInputStream(
				fileContent.getBytes(StandardCharsets.UTF_8));

			channelSftp.put(inputStream, fileName);
		}
		finally {
			if (channelSftp != null) {
				channelSftp.disconnect();
				channelSftp.exit();
			}

			if (jschSession != null) {
				jschSession.disconnect();
			}
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_sftpConfiguration = ConfigurableUtil.createConfigurable(
			SftpConfiguration.class, properties);
	}

	private SftpConfiguration _sftpConfiguration;

}