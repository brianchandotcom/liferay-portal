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

package com.liferay.lcs.security.impl;

import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.Message;
import com.liferay.lcs.security.DigitalSignature;
import com.liferay.lcs.security.KeyStoreAdvisor;
import com.liferay.lcs.security.KeyStoreFactory;

import java.security.KeyStore;
import java.security.Signature;
import java.security.cert.Certificate;

import java.util.Map;

import javax.xml.bind.DatatypeConverter;

/**
 * Provides digital signing and verification methods. The LCS system is the
 * signer and uses a private key to produce the digital signature. The client
 * uses a public key to verify the digital signature.
 *
 * @author  Igor Beslic
 * @author  Ivica Cardic
 * @version 2.1.1
 * @since   LCS 0.1
 */
public class DigitalSignatureImpl implements DigitalSignature {

	public DigitalSignatureImpl() {
		_keyStorePassword = "_k3y#5t0r3-p45S";
	}

	/**
	 * Sets the algorithm provider. The algorithm provider must have the signing
	 * algorithm.
	 *
	 * @param algorithmProvider the algorithm provider
	 * @see   #setSigningAlgorithm(String)
	 * @since LCS 0.1
	 */
	public void setAlgorithmProvider(String algorithmProvider) {
		_algorithmProvider = algorithmProvider;
	}

	/**
	 * Sets the key name.
	 *
	 * @param keyName the alias defined for key store key entry
	 * @since LCS 0.1
	 */
	public void setKeyName(String keyName) {
		_keyAlias = keyName;
	}

	/**
	 * Sets the key store password.
	 *
	 * @param keyStorePassword the key store password
	 * @since LCS 0.1
	 */
	public void setKeyStorePassword(String keyStorePassword) {
		_keyStorePassword = keyStorePassword;
	}

	/**
	 * Sets the key store path.
	 *
	 * @param keyStorePath the key store path
	 * @since LCS 0.1
	 */
	public void setKeyStorePath(String keyStorePath) {
		_keyStorePath = keyStorePath;
	}

	/**
	 * Sets the key store type.
	 *
	 * @param keyStoreType the key store type
	 * @since LCS 0.1
	 */
	public void setKeyStoreType(String keyStoreType) {
		_keyStoreType = keyStoreType;
	}

	/**
	 * Sets the signing algorithm.
	 *
	 * @param signingAlgorithm the signing algorithm
	 * @since LCS 0.1
	 */
	public void setSigningAlgorithm(String signingAlgorithm) {
		_signingAlgorithm = signingAlgorithm;
	}

	@Override
	public void signMessage(int buildNumber, Message message) {
		try {
			doSignMessage(buildNumber, message);
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to sign message", e);
		}
	}

	/**
	 * Signs the message, if possible.
	 *
	 * <p>
	 * If possible, this method produces a digital signature and stores it in
	 * the message's values map using the key {@link Message#KEY_SIGNATURE} .
	 * </p>
	 *
	 * @param message the message
	 * @since LCS 0.1
	 */
	@Override
	public void signMessage(Message message) {
		try {
			doSignMessage(0, message);
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to sign message", e);
		}
	}

	@Override
	public boolean verifyMessage(int buildNumber, Message message) {
		if (!(message instanceof CommandMessage)) {
			return true;
		}

		try {
			return doVerifyMessage(buildNumber, (CommandMessage)message);
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to verify message", e);
		}
	}

	/**
	 * Returns <code>true</code> if the message's digital signature is valid and
	 * it's a command message.
	 *
	 * @param  message the message
	 * @return <code>true</code> if the message's digital signature is valid and
	 *         it's a command message; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	@Override
	public boolean verifyMessage(Message message) {
		if (!(message instanceof CommandMessage)) {
			return true;
		}

		try {
			return doVerifyMessage(0, (CommandMessage)message);
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to verify message", e);
		}
	}

	/**
	 * Signs the command message. If the message is not an instance of
	 * <code>CommandMessage</code>, the message is not signed.
	 *
	 * @param  message the message
	 * @throws Exception if the entry does not contain the information needed to
	 *         recover the key (e.g. wrong password) or if an exception occurred
	 * @since  LCS 0.1
	 */
	protected void doSignMessage(int buildNumber, Message message)
		throws Exception {

		if (!(message instanceof CommandMessage)) {
			return;
		}

		String keyAlias = _keyStoreAdvisor.getKeyAlias(
			buildNumber, _keyAlias, getKeyStore());

		CommandMessage commandMessage = (CommandMessage)message;

		Signature signature = Signature.getInstance(_signingAlgorithm);

		KeyStore.ProtectionParameter protectionParameter =
			new KeyStore.PasswordProtection(_keyStorePassword.toCharArray());

		KeyStore keyStore = getKeyStore();

		KeyStore.PrivateKeyEntry privateKeyEntry =
			(KeyStore.PrivateKeyEntry)keyStore.getEntry(
				keyAlias, protectionParameter);

		signature.initSign(privateKeyEntry.getPrivateKey());

		signature.update(getBytes(commandMessage));

		Map<String, Object> values = commandMessage.getValues();

		values.put(
			Message.KEY_SIGNATURE,
			DatatypeConverter.printBase64Binary(signature.sign()));
	}

	/**
	 * Returns <code>true</code> if the command message's digital signature is
	 * valid.
	 *
	 * @param  commandMessage the command message
	 * @return <code>true</code> if the command message's digital signature is
	 *         valid; <code>false</code> otherwise
	 * @throws Exception if the signature algorithm was unable to process the
	 *         input data provided, if the public key in the certificate did not
	 *         include required parameter information, or if an exception
	 *         occurred
	 * @since  LCS 0.1
	 */
	protected boolean doVerifyMessage(
			int buildNumber, CommandMessage commandMessage)
		throws Exception {

		Map<String, Object> values = commandMessage.getValues();

		if (!values.containsKey(Message.KEY_SIGNATURE)) {
			return false;
		}

		Signature signature = Signature.getInstance(
			_signingAlgorithm, _algorithmProvider);

		KeyStore keyStore = getKeyStore();

		String keyAlias = _keyStoreAdvisor.getKeyAlias(
			buildNumber, _keyAlias, keyStore);

		Certificate certificate = keyStore.getCertificate(keyAlias);

		signature.initVerify(certificate);

		signature.update(getBytes(commandMessage));

		String signatureString = (String)values.get(Message.KEY_SIGNATURE);

		return signature.verify(
			DatatypeConverter.parseBase64Binary(signatureString));
	}

	/**
	 * Returns the command message values of the command type, creation time,
	 * key, payload, and key-value map as bytes used for digital signing.
	 *
	 * @param  commandMessage the command message
	 * @return the command message's values as bytes
	 * @since  LCS 0.1
	 */
	protected byte[] getBytes(CommandMessage commandMessage) {
		StringBuilder sb = new StringBuilder();

		sb.append(commandMessage.getCommandType());
		sb.append(commandMessage.getCreateTime());
		sb.append(commandMessage.getKey());

		if (commandMessage.getPayload() != null) {
			sb.append(commandMessage.getPayload());
		}

		Map<String, Object> values = commandMessage.getValues();

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			String key = entry.getKey();

			if (key.equals(Message.KEY_SIGNATURE)) {
				continue;
			}

			sb.append(entry.getValue());
		}

		return sb.toString().getBytes();
	}

	/**
	 * Returns the key store with the public and private keys.
	 *
	 * @return the key store with the public and private keys
	 * @throws Exception if the key store could not be initialized or if an
	 *         exception occurred
	 * @since  LCS 0.1
	 */
	protected KeyStore getKeyStore() throws Exception {
		if (_keyStore != null) {
			return _keyStore;
		}

		_keyStore = KeyStoreFactory.getInstance(
			_keyStorePath, _keyStoreType, _keyStorePassword);

		return _keyStore;
	}

	private String _algorithmProvider;
	private String _keyAlias;
	private KeyStore _keyStore;
	private final KeyStoreAdvisor _keyStoreAdvisor = new KeyStoreAdvisor();
	private String _keyStorePassword;
	private String _keyStorePath;
	private String _keyStoreType;
	private String _signingAlgorithm;

}