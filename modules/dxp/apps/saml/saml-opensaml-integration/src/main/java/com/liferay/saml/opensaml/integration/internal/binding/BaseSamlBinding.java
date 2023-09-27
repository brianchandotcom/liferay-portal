/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.binding;

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