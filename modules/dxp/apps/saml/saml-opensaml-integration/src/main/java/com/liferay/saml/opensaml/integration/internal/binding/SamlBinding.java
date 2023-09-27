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
public interface SamlBinding {

	public String getCommunicationProfileId();

	public Supplier<HttpServletRequestMessageDecoder>
		getHttpServletRequestMessageDecoderSupplier();

	public Supplier<HttpServletResponseMessageEncoder>
		getHttpServletResponseMessageEncoderSupplier();

}