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

import java.util.function.Supplier;

import org.opensaml.messaging.decoder.servlet.HttpServletRequestMessageDecoder;
import org.opensaml.messaging.encoder.servlet.HttpServletResponseMessageEncoder;

/**
 * @author Mika Koivisto
 */
public abstract class BaseSamlBinding implements SamlBinding {

	public BaseSamlBinding(
		Supplier<HttpServletRequestMessageDecoder>
			httpServletRequestMessageDecoderSupplier,
		Supplier<HttpServletResponseMessageEncoder>
			httpServletResponseMessageEncoderSupplier) {

		_httpServletRequestMessageDecoderSupplier =
			httpServletRequestMessageDecoderSupplier;
		_httpServletResponseMessageEncoderSupplier =
			httpServletResponseMessageEncoderSupplier;
	}

	@Override
	public Supplier<HttpServletRequestMessageDecoder>
		getHttpServletRequestMessageDecoderSupplier() {

		return _httpServletRequestMessageDecoderSupplier;
	}

	@Override
	public Supplier<HttpServletResponseMessageEncoder>
		getHttpServletResponseMessageEncoderSupplier() {

		return _httpServletResponseMessageEncoderSupplier;
	}

	private final Supplier<HttpServletRequestMessageDecoder>
		_httpServletRequestMessageDecoderSupplier;
	private final Supplier<HttpServletResponseMessageEncoder>
		_httpServletResponseMessageEncoderSupplier;

}