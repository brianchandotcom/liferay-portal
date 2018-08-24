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

package com.liferay.portal.security.ldap.internal.validator;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.internal.validator.parser.LDAPFilterLexer;
import com.liferay.portal.security.ldap.internal.validator.parser.LDAPFilterParser;
import com.liferay.portal.security.ldap.validator.LDAPFilterException;
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
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to parse filter " + filter, e);
			}

			return false;
		}

		return true;
	}

	@Override
	public void validate(String filter) throws LDAPFilterException {
		if (!isValid(filter)) {
			throw new LDAPFilterException("Invalid filter " + filter);
		}
	}

	@Override
	public void validate(String filter, String filterPropertyName)
		throws LDAPFilterException {

		if (!isValid(filter)) {
			throw new LDAPFilterException(
				StringBundler.concat(
					"Invalid filter ", filter, " defined by ",
					filterPropertyName));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPFilterValidatorImpl.class);

}