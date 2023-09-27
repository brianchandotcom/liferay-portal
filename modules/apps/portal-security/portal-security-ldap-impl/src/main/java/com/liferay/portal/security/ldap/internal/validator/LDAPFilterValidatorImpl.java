/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.validator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.internal.validator.parser.LDAPFilterLexer;
import com.liferay.portal.security.ldap.internal.validator.parser.LDAPFilterParser;
import com.liferay.portal.security.ldap.validator.LDAPFilterValidator;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vilmos Papp
 */
@Component(service = LDAPFilterValidator.class)
public class LDAPFilterValidatorImpl implements LDAPFilterValidator {

	@Override
	public boolean isValid(String filter) {
		if (Validator.isNull(filter)) {
			return true;
		}

		CharStream charStream = new ANTLRStringStream(filter);

		LDAPFilterLexer ldapFilterLexer = new LDAPFilterLexer(charStream);

		TokenStream tokenStream = new CommonTokenStream(ldapFilterLexer);

		LDAPFilterParser ldapFilterParser = new LDAPFilterParser(tokenStream);

		try {
			ldapFilterParser.parse();
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to parse filter " + filter, exception);
			}

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPFilterValidatorImpl.class);

}