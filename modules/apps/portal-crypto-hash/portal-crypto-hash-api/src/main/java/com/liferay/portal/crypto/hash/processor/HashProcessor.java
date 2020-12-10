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

package com.liferay.portal.crypto.hash.processor;

import com.liferay.portal.crypto.hash.generation.context.CryptoHashGenerationContext;
import com.liferay.portal.crypto.hash.generation.response.CryptoHashGenerationResponse;
import com.liferay.portal.crypto.hash.verification.context.CryptoHashVerificationContext;

import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
@ProviderType
public interface CryptoHashProcessor {

	public CryptoHashGenerationContext.Builder
		createCryptoHashGenerationContextBuilder(
			String cryptoHashProviderName,
			Map<String, ?> cryptoHashProviderProperties);

	public CryptoHashVerificationContext.Builder
		createCryptoHashVerificationContextBuilder(
			String cryptoHashProviderName,
			Map<String, ?> cryptoHashProviderProperties);

	public CryptoHashGenerationResponse generate(
			byte[] input,
			CryptoHashGenerationContext cryptoHashGenerationContext)
		throws Exception;

	public Set<String> getAvailableCryptoHashProviderNames();

	public boolean verify(
			byte[] input, byte[] hash,
			CryptoHashVerificationContext... cryptoHashVerificationContexts)
		throws Exception;

}