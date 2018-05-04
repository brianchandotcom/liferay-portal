lexer grammar Creole10;
options {
  language=Java;

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

 package com.liferay.wiki.engine.creole.internal.parser.parser;
}

T41 : ':' ;
T42 : 'C' ;
T43 : '2' ;
T44 : 'D' ;
T45 : 'o' ;
T46 : 'k' ;
T47 : 'u' ;
T48 : 'W' ;
T49 : 'i' ;
T50 : 'F' ;
T51 : 'l' ;
T52 : 'c' ;
T53 : 'r' ;
T54 : 'G' ;
T55 : 'g' ;
T56 : 'e' ;
T57 : 'J' ;
T58 : 'S' ;
T59 : 'P' ;
T60 : 'M' ;
T61 : 'a' ;
T62 : 't' ;
T63 : 'b' ;
T64 : 'd' ;
T65 : 'n' ;
T66 : 'O' ;
T67 : 'm' ;
T68 : 's' ;
T69 : 'h' ;
T70 : 'p' ;
T71 : 'R' ;
T72 : 'x' ;
T73 : 'T' ;
T74 : 'y' ;
T75 : 'U' ;
T76 : 'X' ;
T77 : '<<TableOfContents>>' ;
T78 : '<<TableOfContents title=' ;
T79 : '\"' ;
T80 : '>>' ;

// $ANTLR src "Creole10.g" 1148
ESCAPE					: '~';
// $ANTLR src "Creole10.g" 1149
NOWIKI_BLOCK_CLOSE		: NEWLINE  '}}}';
// $ANTLR src "Creole10.g" 1150
NEWLINE					: ( CR )?  LF
						| CR;
// $ANTLR src "Creole10.g" 1152
fragment CR				: '\r';
// $ANTLR src "Creole10.g" 1153
fragment LF				: '\n';

// $ANTLR src "Creole10.g" 1155
BLANKS					: ( SPACE | TABULATOR )+;
// $ANTLR src "Creole10.g" 1156
fragment SPACE			: ' ';
// $ANTLR src "Creole10.g" 1157
fragment TABULATOR		: '\t';

// $ANTLR src "Creole10.g" 1159
BRACE_CLOSE				: NEWLINE '}';
// $ANTLR src "Creole10.g" 1160
COLON_SLASH				: ':'  '/';
// $ANTLR src "Creole10.g" 1161
ITAL					: '//';
// $ANTLR src "Creole10.g" 1162
NOWIKI_OPEN				: '{{{';
// $ANTLR src "Creole10.g" 1163
NOWIKI_CLOSE			: '}}}';
// $ANTLR src "Creole10.g" 1164
LINK_OPEN				: '[[';
// $ANTLR src "Creole10.g" 1165
LINK_CLOSE				: ']]';
// $ANTLR src "Creole10.g" 1166
IMAGE_OPEN				: '{{';
// $ANTLR src "Creole10.g" 1167
IMAGE_CLOSE				: '}}';
// $ANTLR src "Creole10.g" 1168
FORCED_LINEBREAK		: '\\\\';
// $ANTLR src "Creole10.g" 1169
EQUAL					: '=';
// $ANTLR src "Creole10.g" 1170
PIPE					: '|';
// $ANTLR src "Creole10.g" 1171
POUND					: '#';
// $ANTLR src "Creole10.g" 1172
DASH					: '-';
// $ANTLR src "Creole10.g" 1173
STAR					: '*';
// $ANTLR src "Creole10.g" 1174
SLASH					: '/';
// $ANTLR src "Creole10.g" 1175
EXTENSION				: '@@';

// $ANTLR src "Creole10.g" 1177
INSIGNIFICANT_CHAR		: .;