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

// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 879
ESCAPE					: '~';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 880
NOWIKI_BLOCK_CLOSE		: NEWLINE  '}}}';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 881
NEWLINE					: ( CR )?  LF
						| CR;
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 883
fragment CR				: '\r';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 884
fragment LF				: '\n';

// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 886
BLANKS					: ( SPACE | TABULATOR )+;
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 887
fragment SPACE			: ' ';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 888
fragment TABULATOR		: '\t';

// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 890
BRACE_CLOSE				: NEWLINE '}';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 891
COLON_SLASH				: ':'  '/';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 892
ITAL					: '//';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 893
NOWIKI_OPEN				: '{{{';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 894
NOWIKI_CLOSE			: '}}}';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 895
LINK_OPEN				: '[[';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 896
LINK_CLOSE				: ']]';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 897
IMAGE_OPEN				: '{{';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 898
IMAGE_CLOSE				: '}}';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 899
FORCED_LINEBREAK		: '\\\\';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 900
EQUAL					: '=';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 901
PIPE					: '|';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 902
POUND					: '#';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 903
DASH					: '-';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 904
STAR					: '*';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 905
SLASH					: '/';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 906
EXTENSION				: '@@';
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 907
TABLE_OF_CONTENTS_OPEN_MARKUP
	:	'<<'
	;
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 910
TABLE_OF_CONTENTS_CLOSE_MARKUP
	:	'>>'
	;
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 913
TABLE_OF_CONTENTS_TEXT
	:	'<<TableOfContents>>'
	;
// $ANTLR src "/home/dougwong/liferay/github/tr-portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 916
INSIGNIFICANT_CHAR		: .;


