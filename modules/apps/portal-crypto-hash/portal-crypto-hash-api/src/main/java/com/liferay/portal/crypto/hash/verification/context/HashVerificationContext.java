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

package com.liferay.portal.crypto.hash.verification.context;

import com.liferay.portal.crypto.hash.flavor.CryptoHashFlavor;
import com.liferay.portal.crypto.hash.provider.context.CryptoHashProviderContext;

import java.io.IOException;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 * @author Arthur Chan
 */
@ProviderType
public interface CryptoHashVerificationContext
	extends CryptoHashProviderContext {

	public CryptoHashFlavor getCryptoHashFlavor();

	public interface Builder extends PepperedBuilder {

		public CryptoHashVerificationContext build(
				String cryptoHashProviderName,
				Map<String, ?> cryptoHashProviderProperties,
				byte[] serializedHashFlavor)
			throws IOException;

		public CryptoHashVerificationContext build(
			String cryptoHashProviderName,
			Map<String, ?> cryptoHashProviderProperties,
			CryptoHashFlavor cryptoHashFlavor);

	}

	public interface CryptoHashVerificationContextBuilder {

		public CryptoHashVerificationContext build(
			String cryptoHashProviderName,
			Map<String, ?> cryptoHashProviderProperties);

	}

	public interface PepperedBuilder extends SaltedBuilder {

		public SaltedBuilder pepperId(String pepperId);

	}

	public interface SaltedBuilder
		extends CryptoHashVerificationContextBuilder {

		public Builder salt(byte[] salt);

	}

}