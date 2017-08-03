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

package com.liferay.saml.opensaml.integration.internal.identifier;

import com.liferay.portal.kernel.security.SecureRandom;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeFormatter;

import org.opensaml.common.IdentifierGenerator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = IdentifierGenerator.class)
public class SamlIdentifierGenerator implements IdentifierGenerator {

	@Override
	public String generateIdentifier() {
		return generateIdentifier(16);
	}

	@Override
	public String generateIdentifier(int size) {
		byte[] bytes = new byte[size];

		_secureRandom.nextBytes(bytes);

		return StringPool.UNDERLINE.concat(UnicodeFormatter.bytesToHex(bytes));
	}

	private final SecureRandom _secureRandom = new SecureRandom();

}