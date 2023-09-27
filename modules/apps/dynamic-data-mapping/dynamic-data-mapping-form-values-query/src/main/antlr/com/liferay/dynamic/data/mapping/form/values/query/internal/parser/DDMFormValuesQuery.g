grammar DDMFormValuesQuery;

options {
	language = Java;
}

@header {
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.values.query.internal.parser;
}

path
	: selectorExpression*
	;

selectorExpression
	: stepType fieldSelectorExpression 
	;

stepType
	: SLASH
	| DOUBLE_SLASH
	;

fieldSelectorExpression
	: fieldSelector predicateExpression?
	;

fieldSelector
	: STAR 
	| IDENTIFIER
	;

predicateExpression
	: OPEN_BRACKET predicateOrExpression CLOSE_BRACKET
	;

predicateOrExpression
	: predicateAndExpression ('or' predicateAndExpression)*
	;

predicateAndExpression
	: predicateEqualityExpression ('and' predicateEqualityExpression)*
	;

predicateEqualityExpression
	: AT attribute EQUALS STRING_LITERAL
	;

attribute
	: attributeType
	| attributeValue
	;

attributeType
	: 'type'
	;

attributeValue
	: 'value' localeExpression?
	;

localeExpression
	: '(' STRING_LITERAL ')' 
	;

IDENTIFIER
	: ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9')*
    ;

STRING_LITERAL
	:  '\'' ( ESC_SEQ | ~('\\'|'\'') )* '\''
    ;

SLASH 
	: '/';

DOUBLE_SLASH 
	: '//';

STAR 
	: '*';

AT 
	: '@';

OPEN_BRACKET : '[';
CLOSE_BRACKET : ']';
EQUALS : '=';

fragment
ESC_SEQ
	: '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
	| UNICODE_ESC
	| OCTAL_ESC
	;

fragment
OCTAL_ESC
	: '\\' ('0'..'3') ('0'..'7') ('0'..'7')
	| '\\' ('0'..'7') ('0'..'7')
	| '\\' ('0'..'7')
	;


fragment
UNICODE_ESC
	: '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
	;

fragment
HEX_DIGIT 
	: ('0'..'9'|'a'..'f'|'A'..'F') ;

WS :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) -> channel(HIDDEN)
    ;