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

import java.io.IOException;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 * @author Arthur Chan
 */
@ProviderType
public interface CryptoHashVerificationContext {

	public CryptoHashFlavor getCryptoHashFlavor();

	public String getCryptoHashProviderName();

	public Map<String, ?> getCryptoHashProviderProperties();

	public interface Builder extends PepperedBuilder {

		public ContextBuilder setHashFlavor(byte[] serializedHashFlavor)
			throws IOException;

		public ContextBuilder setHashFlavor(CryptoHashFlavor cryptoHashFlavor);

	}

	public interface ContextBuilder {

		public CryptoHashVerificationContext build();

	}

	public interface PepperedBuilder extends SaltedBuilder {

		public SaltedBuilder setPepperId(String pepperId);

	}

	public interface SaltedBuilder extends ContextBuilder {

		public ContextBuilder setSalt(byte[] salt);

	}

}