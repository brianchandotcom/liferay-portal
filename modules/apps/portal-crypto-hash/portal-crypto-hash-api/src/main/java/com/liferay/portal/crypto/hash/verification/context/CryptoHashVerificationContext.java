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

	public interface Builder {

		public CryptoHashVerificationContext build();

		/**
		 * Set Hash flavor to this builder.
		 *
		 * @param serializedHashFlavor the serialized HashFlavor. A hashFlavor contains all flavors including pepper and salt.
		 * @return the builder object itself.
		 * @throws IllegalArgumentException if any other setters were invoked before.
		 * @throws IOException if failed to parse the serialized HashFlavor.
		 */
		public Builder setHashFlavor(byte[] serializedHashFlavor)
			throws IllegalArgumentException, IOException;

		/**
		 * Set Hash flavor to this builder.
		 *
		 * @param HashFlavor the HashFlavor. A hashFlavor contains all flavors including pepper and salt.
		 * @return the builder object itself.
		 * @throws IllegalArgumentException if any other setters were invoked before.
		 */
		public Builder setHashFlavor(CryptoHashFlavor cryptoHashFlavor)
			throws IllegalArgumentException;

		/**
		 * Set pepperId to this builder.
		 *
		 * @param pepperId the pepperId. A pepperId is the ID to the bytes of pepper that is stored in some storage.
		 * @return the builder object itself.
		 * @throws IllegalArgumentException if setHashFlavor() or setSalt() was invoked before.
		 */
		public Builder setPepperId(String pepperId)
			throws IllegalArgumentException;

		/**
		 * Set salt to this builder.
		 *
		 * @param salt the salt.
		 * @return the builder object itself.
		 * @throws IllegalArgumentException if setHashFlavor() was invoked before.
		 */
		public Builder setSalt(byte[] salt) throws IllegalArgumentException;

	}

}