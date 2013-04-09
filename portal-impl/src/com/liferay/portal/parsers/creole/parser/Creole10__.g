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

T43 : ':' ;
T44 : 'C' ;
T45 : '2' ;
T46 : 'D' ;
T47 : 'o' ;
T48 : 'k' ;
T49 : 'u' ;
T50 : 'W' ;
T51 : 'i' ;
T52 : 'F' ;
T53 : 'l' ;
T54 : 'c' ;
T55 : 'r' ;
T56 : 'G' ;
T57 : 'g' ;
T58 : 'e' ;
T59 : 'J' ;
T60 : 'S' ;
T61 : 'P' ;
T62 : 'M' ;
T63 : 'a' ;
T64 : 't' ;
T65 : 'b' ;
T66 : 'd' ;
T67 : 'n' ;
T68 : 'O' ;
T69 : 'm' ;
T70 : 's' ;
T71 : 'h' ;
T72 : 'p' ;
T73 : 'R' ;
T74 : 'x' ;
T75 : 'T' ;
T76 : 'y' ;
T77 : 'U' ;
T78 : 'X' ;
T79 : 'TableOfContents' ;
T80 : 'TableOfContents title=' ;
T81 : '\"' ;

// $ANTLR src "Creole10.g" 1120
ESCAPE					: '~';
// $ANTLR src "Creole10.g" 1121
NOWIKI_BLOCK_CLOSE		: NEWLINE  '}}}';
// $ANTLR src "Creole10.g" 1122
NEWLINE					: ( CR )?  LF
						| CR;
// $ANTLR src "Creole10.g" 1124
fragment CR				: '\r';
// $ANTLR src "Creole10.g" 1125
fragment LF				: '\n';

// $ANTLR src "Creole10.g" 1127
BLANKS					: ( SPACE | TABULATOR )+;
// $ANTLR src "Creole10.g" 1128
fragment SPACE			: ' ';
// $ANTLR src "Creole10.g" 1129
fragment TABULATOR		: '\t';

// $ANTLR src "Creole10.g" 1131
BRACE_CLOSE				: NEWLINE '}';
// $ANTLR src "Creole10.g" 1132
COLON_SLASH				: ':'  '/';
// $ANTLR src "Creole10.g" 1133
ITAL					: '//';
// $ANTLR src "Creole10.g" 1134
NOWIKI_OPEN				: '{{{';
// $ANTLR src "Creole10.g" 1135
NOWIKI_CLOSE			: '}}}';
// $ANTLR src "Creole10.g" 1136
LINK_OPEN				: '[[';
// $ANTLR src "Creole10.g" 1137
LINK_CLOSE				: ']]';
// $ANTLR src "Creole10.g" 1138
IMAGE_OPEN				: '{{';
// $ANTLR src "Creole10.g" 1139
IMAGE_CLOSE				: '}}';
// $ANTLR src "Creole10.g" 1140
FORCED_LINEBREAK		: '\\\\';
// $ANTLR src "Creole10.g" 1141
EQUAL					: '=';
// $ANTLR src "Creole10.g" 1142
PIPE					: '|';
// $ANTLR src "Creole10.g" 1143
POUND					: '#';
// $ANTLR src "Creole10.g" 1144
DASH					: '-';
// $ANTLR src "Creole10.g" 1145
STAR					: '*';
// $ANTLR src "Creole10.g" 1146
SLASH					: '/';
// $ANTLR src "Creole10.g" 1147
EXTENSION				: '@@';

// $ANTLR src "Creole10.g" 1149
TABLE_OF_CONTENTS_OPEN	: '<<';
// $ANTLR src "Creole10.g" 1150
TABLE_OF_CONTENTS_CLOSE	: '>>';

// $ANTLR src "Creole10.g" 1152
INSIGNIFICANT_CHAR		: .;