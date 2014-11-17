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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.parsers.ldapfilter.parser.LDAPFilterLexer;
import com.liferay.portal.parsers.ldapfilter.parser.LDAPFilterParser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

/**
 * @author Vilmos Papp
 */
public class LDAPFilterValidatorImpl implements LDAPFilterValidator {

	public boolean isValidFilter(String filter) {
		if (Validator.isNull(filter)) {
			return true;
		}

		CharStream charStream = new ANTLRStringStream(filter);
		LDAPFilterLexer ldapFilterLexer = new LDAPFilterLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(ldapFilterLexer);
		LDAPFilterParser ldapFilterParser = new LDAPFilterParser(tokenStream);

		try {
			ldapFilterParser.parse();

			return true;
		}
		catch (RecognitionException re) {
			return false;
		}
		catch (RuntimeException re) {
			return false;
		}
	}

}