lexer grammar Creole10;
options {
  language=Java;

}
@header {
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

 package com.liferay.portal.parsers.creole.parser;
}

T44 : ':' ;
T45 : 'C' ;
T46 : '2' ;
T47 : 'D' ;
T48 : 'o' ;
T49 : 'k' ;
T50 : 'u' ;
T51 : 'W' ;
T52 : 'i' ;
T53 : 'F' ;
T54 : 'l' ;
T55 : 'c' ;
T56 : 'r' ;
T57 : 'G' ;
T58 : 'g' ;
T59 : 'e' ;
T60 : 'J' ;
T61 : 'S' ;
T62 : 'P' ;
T63 : 'M' ;
T64 : 'a' ;
T65 : 't' ;
T66 : 'b' ;
T67 : 'd' ;
T68 : 'n' ;
T69 : 'O' ;
T70 : 'm' ;
T71 : 's' ;
T72 : 'h' ;
T73 : 'p' ;
T74 : 'R' ;
T75 : 'x' ;
T76 : 'T' ;
T77 : 'y' ;
T78 : 'U' ;
T79 : 'X' ;

// $ANTLR src "Creole10.g" 1104
ESCAPE					: '~';
// $ANTLR src "Creole10.g" 1105
NOWIKI_BLOCK_CLOSE		: NEWLINE  '}}}';
// $ANTLR src "Creole10.g" 1106
NEWLINE					: ( CR )?  LF
						| CR;
// $ANTLR src "Creole10.g" 1108
fragment CR				: '\r';
// $ANTLR src "Creole10.g" 1109
fragment LF				: '\n';

// $ANTLR src "Creole10.g" 1111
BLANKS					: ( SPACE | TABULATOR )+;
// $ANTLR src "Creole10.g" 1112
fragment SPACE			: ' ';
// $ANTLR src "Creole10.g" 1113
fragment TABULATOR		: '\t';

// $ANTLR src "Creole10.g" 1115
BRACE_CLOSE				: NEWLINE '}';
// $ANTLR src "Creole10.g" 1116
COLON_SLASH				: ':'  '/';
// $ANTLR src "Creole10.g" 1117
ITAL					: '//';
// $ANTLR src "Creole10.g" 1118
NOWIKI_OPEN				: '{{{';
// $ANTLR src "Creole10.g" 1119
NOWIKI_CLOSE			: '}}}';
// $ANTLR src "Creole10.g" 1120
LINK_OPEN				: '[[';
// $ANTLR src "Creole10.g" 1121
LINK_CLOSE				: ']]';
// $ANTLR src "Creole10.g" 1122
IMAGE_OPEN				: '{{';
// $ANTLR src "Creole10.g" 1123
IMAGE_CLOSE				: '}}';
// $ANTLR src "Creole10.g" 1124
FORCED_LINEBREAK		: '\\\\';
// $ANTLR src "Creole10.g" 1125
EQUAL					: '=';
// $ANTLR src "Creole10.g" 1126
PIPE					: '|';
// $ANTLR src "Creole10.g" 1127
POUND					: '#';
// $ANTLR src "Creole10.g" 1128
DASH					: '-';
// $ANTLR src "Creole10.g" 1129
STAR					: '*';
// $ANTLR src "Creole10.g" 1130
SLASH					: '/';
// $ANTLR src "Creole10.g" 1131
EXTENSION				: '@@';
// $ANTLR src "Creole10.g" 1132
TABLE_OF_CONTENTS_OPEN_MARKUP
	:	'<<'
	;
// $ANTLR src "Creole10.g" 1135
TABLE_OF_CONTENTS_CLOSE_MARKUP
	:	'>>'
	;
// $ANTLR src "Creole10.g" 1138
TABLE_OF_CONTENTS_TEXT
	:	'<<TableOfContents>>'
	;
// $ANTLR src "Creole10.g" 1141
INSIGNIFICANT_CHAR		: .;