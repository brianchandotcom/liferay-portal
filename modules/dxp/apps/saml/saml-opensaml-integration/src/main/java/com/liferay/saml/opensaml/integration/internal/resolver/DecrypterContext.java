/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import org.opensaml.messaging.context.BaseContext;
import org.opensaml.saml.saml2.encryption.Decrypter;

/**
 * @author Stian Sigvartsen
 */
public class DecrypterContext extends BaseContext {

	public DecrypterContext(Decrypter decrypter) {
		_decrypter = decrypter;
	}

	public Decrypter getDecrypter() {
		return _decrypter;
	}

	private final Decrypter _decrypter;

}