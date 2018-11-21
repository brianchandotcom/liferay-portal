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
@Component(
	immediate = true, service = IdentifierGenerationStrategyFactory.class
)
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