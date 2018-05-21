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