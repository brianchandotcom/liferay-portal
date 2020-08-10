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
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Eric Chin
 */
@Component(
	configurationPid = "com.liferay.commerce.google.merchant.internal.sftp.SftpConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = SftpUploader.class
)
public class SftpUploader {

	@Activate
	public void activate(Map<String, Object> properties) {
		_sftpConfiguration = ConfigurableUtil.createConfigurable(
			SftpConfiguration.class, properties);
	}

	public void upload(String fileName, String fileContent) throws Exception {
		ChannelSftp channelSftp = null;
		Session jschSession = null;

		try {
			String host = _sftpConfiguration.host();
			String password = _sftpConfiguration.password();
			int port = _sftpConfiguration.port();
			String username = _sftpConfiguration.username();

			JSch jsch = new JSch();

			FingerprintHostKeyRepository fingerprintHostKeyRepository =
				new FingerprintHostKeyRepository(
					jsch, _sftpConfiguration.fingerprint());

			jsch.setHostKeyRepository(fingerprintHostKeyRepository);

			jschSession = jsch.getSession(username, host);

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

	private SftpConfiguration _sftpConfiguration;

}