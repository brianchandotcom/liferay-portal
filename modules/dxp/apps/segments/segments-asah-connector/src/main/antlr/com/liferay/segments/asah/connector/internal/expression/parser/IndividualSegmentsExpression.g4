grammar IndividualSegmentsExpression;

options {
	language = Java;
}

@header {
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

package com.liferay.segments.asah.connector.internal.expression.parser;
}

booleanOperandExpression
   : logicalTerm # ToLogicalTerm
   | LPAREN logicalOrExpression RPAREN # BooleanParenthesis
   ;

booleanUnaryExpression
   : NOT booleanUnaryExpression # NotExpression
   | booleanOperandExpression # ToBooleanOperandExpression
   ;

comparisonExpression
    : VARIABLE_IDENTIFIER GT booleanOperandExpression # GreaterThanExpression
    | VARIABLE_IDENTIFIER GE booleanOperandExpression # GreaterThanOrEqualsExpression
    | VARIABLE_IDENTIFIER LT booleanOperandExpression # LessThanExpression
   | VARIABLE_IDENTIFIER LE booleanOperandExpression # LessThanOrEqualsExpression
   | booleanUnaryExpression #ToBooleanUnaryExpression
   ;

equalityExpression
    : VARIABLE_IDENTIFIER EQ comparisonExpression # EqualsExpression
    | VARIABLE_IDENTIFIER NEQ comparisonExpression # NotEqualsExpression
    | comparisonExpression #ToComparisonExpression
   ;

expression
   : logicalOrExpression EOF
   ;

filterExpression
   : filterType=VARIABLE_IDENTIFIER '.filter(filter=' filter=STRING_LITERAL ')'
   ;

filterByCountExpression
   : filterType=VARIABLE_IDENTIFIER '.filterByCount(filter=' filter=STRING_LITERAL COMMA 'operator=' operator=STRING_LITERAL COMMA 'value=' value=INTEGER_LITERAL ')'
   ;

functionCallExpression
   : functionName=VARIABLE_IDENTIFIER LPAREN functionParameters RPAREN
   ;

functionParameters
   : VARIABLE_IDENTIFIER COMMA literal
   ;

literal
   : FLOATING_POINT_LITERAL # FloatingPointLiteral
   | INTEGER_LITERAL # IntegerLiteral
   | ('true' | 'false') # BooleanLiteral
   | 'null' # NullLiteral
   | STRING_LITERAL # StringLiteral
   ;

logicalAndExpression
   : logicalAndExpression AND equalityExpression # AndExpression
   | equalityExpression # ToEqualityExpression
   ;

logicalOrExpression
   : logicalOrExpression OR logicalAndExpression # OrExpression
   | logicalAndExpression # ToLogicalAndExpression
   ;

logicalTerm
   : literal # ToLiteral
   | functionCallExpression # ToFunctionCallExpression
   | filterExpression # ToFilterExpression
   | filterByCountExpression # ToFilterByCountExpression
   ;

AND
   : '&&'
   | '&'
   | 'and'
   | 'AND'
   ;

COMMA
   : ','
   ;

EQ
   : 'eq'
   | '='
   ;

FLOATING_POINT_LITERAL
    : MINUS? DIGITS '.' DIGITS?
    | MINUS? '.' DIGITS
    ;

NEQ
   : 'ne'
   ;

GE
   : 'ge'
   ;

GT
   : 'gt'
   ;

INTEGER_LITERAL
   : MINUS? DIGITS
   ;

LE
   : 'le'
   ;

LPAREN
   : '('
   ;

RPAREN
   : ')'
   ;

LT
   : 'lt'
   ;

NOT
   : 'not'
   | 'NOT'
   ;

OR
   : '||'
   | '|'
   | 'or'
   | 'OR'
   ;

STRING_LITERAL
   : '"' ( '""' | ~["] )* '"'
   | '\'' ( '\'\'' | ~['] )* '\''
   ;

VARIABLE_IDENTIFIER
   : NAME_START_CHAR NAME_CHAR*
    | NAME_START_CHAR NAME_CHAR* '/' NAME_START_CHAR NAME_CHAR*
   | NAME_START_CHAR NAME_CHAR* '/' NAME_START_CHAR NAME_CHAR* '/value'
   ;

fragment
DIGITS
    : [0-9]+
    ;

fragment
MINUS
   : '-'
   ;

fragment
NAME_CHAR
   : NAME_START_CHAR
   | '0'..'9'
   ;

fragment
NAME_START_CHAR
   : '_'
   | 'A'..'Z' | 'a'..'z'
   ;

WS
   : [ \r\t\u000C\n]+ -> skip
   ;