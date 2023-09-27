/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.identifier;

import com.google.common.escape.Escaper;
import com.google.common.xml.XmlEscapers;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.SecureRandom;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.saml.opensaml.integration.internal.servlet.profile.IdentifierGenerationStrategyFactory;

import javax.annotation.Nonnull;

import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(service = IdentifierGenerationStrategyFactory.class)
public class SamlIdentifierGeneratorStrategyFactory
	implements IdentifierGenerationStrategyFactory {

	@Override
	public IdentifierGenerationStrategy create(int length) {
		return new IdentifierGenerationStrategy() {

			@Nonnull
			@Override
			public String generateIdentifier() {
				return generateIdentifier(length, false);
			}

			@Nonnull
			@Override
			public String generateIdentifier(boolean xmlSafe) {
				return generateIdentifier(length, xmlSafe);
			}

			public String generateIdentifier(int size, boolean xmlSafe) {
				byte[] bytes = new byte[size];

				_secureRandom.nextBytes(bytes);

				String identifier = StringPool.UNDERLINE.concat(
					UnicodeFormatter.bytesToHex(bytes));

				if (xmlSafe) {
					Escaper escaper = XmlEscapers.xmlAttributeEscaper();

					return escaper.escape(identifier);
				}

				return identifier;
			}

			private final SecureRandom _secureRandom = new SecureRandom();

		};
	}

}