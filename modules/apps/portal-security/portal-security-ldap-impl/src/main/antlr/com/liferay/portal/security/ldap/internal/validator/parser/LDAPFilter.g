grammar LDAPFilter;

options {
  language = Java;
}

@header {
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

package com.liferay.portal.security.ldap.internal.validator.parser;

/**
* This is a generated file from LDAPFilter.g. DO NOT MODIFY THIS FILE MANUALLY!!
* If needed, modify the grammar and rerun the gradle generation task
* (../../../gradlew generateGrammarSource)
*/
}

@lexer::header {
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

package com.liferay.portal.security.ldap.internal.validator.parser;
}
@lexer::members {
	@Override
	public void reportError(RecognitionException e) {
		throw new RuntimeException(e);
	}
}

@parser::members {
	@Override
	public void reportError(RecognitionException e) {
		throw new RuntimeException(e);
	}
}

parse
	: filter EOF
	;
filter
	: '(' filtercomp ')'
	;
filtercomp
	: and
	| or
	| not
	| item
	;
and
	: '&' filter+
	;
or
	: '|' filter+
	;
not
	: '!' filter+
	;
item
	: simple
	| present
	| substring
	| extensible
	;
simple
	: attr filtertype (options {greedy=true;}: values | item)
	;
filtertype
	: equal
	| approx
	| greater
	| less
	;
equal
	: '='
	;
approx
	: '~='
	;
greater
	: '>='
	;
less
	: '<='
	;
extensible
	: (attr (':dn')* (':' matchingrule)* ':=' values)
	| ((':dn')* ':' matchingrule ':=' values)
	;
present
	: attr '=' '*' EOF
	;
substring
	: attr '=' value? any value?
	;
any
	: '*' (value '*')*
	;
attr
	: attributeDescription
	;
matchingrule
	: (ASCII_LATIN1 | DOT)+
	;
value
	: (ASCII_LATIN1 | UTF | COLON | DOT)+
	;
values
	: value (equal value)*
	|
	;

attributeDescription
	: attributeType (';' option)*
	;
attributeType
	: ASCII_LATIN1+
	;
option
	: optchar
	;
optchar
	: ASCII_LETTER
	| DIGIT
	| DASH
	;

fragment ASCII_LETTER
	: 'a'..'z'
	|'A'..'Z'
	;
fragment DIGIT:  '0'..'9';
fragment DASH: '-';

DOT: '.';
COLON: ':';
UTF: '\u0080'..'\ufffe';
ASCII_LATIN1: '\u0000'..'\u007f';