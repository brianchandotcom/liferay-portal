/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.binding;

import com.liferay.saml.opensaml.integration.SamlBinding;

import org.opensaml.ws.message.decoder.MessageDecoder;
import org.opensaml.ws.message.encoder.MessageEncoder;

/**
 * @author Mika Koivisto
 */
public abstract class BaseSamlBinding implements SamlBinding {

	public BaseSamlBinding(
		MessageDecoder messageDecoder, MessageEncoder messageEncoder) {

		_messageDecoder = messageDecoder;
		_messageEncoder = messageEncoder;
	}

	@Override
	public MessageDecoder getMessageDecoder() {
		return _messageDecoder;
	}

	@Override
	public MessageEncoder getMessageEncoder() {
		return _messageEncoder;
	}

	private final MessageDecoder _messageDecoder;
	private final MessageEncoder _messageEncoder;

}