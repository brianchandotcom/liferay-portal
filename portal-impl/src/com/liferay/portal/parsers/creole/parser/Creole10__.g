lexer grammar Creole10;
options {
  language=Java;

}
@header {
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 874
ESCAPE					: '~';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 875
NOWIKI_BLOCK_CLOSE		: 	NEWLINE  '}}}';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 876
NEWLINE					: ( CR )?  LF
						| CR;
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 878
fragment CR				: '\r';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 879
fragment LF				: '\n';

// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 881
BLANKS					: ( SPACE | TABULATOR )+;
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 882
fragment SPACE			: ' ';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 883
fragment TABULATOR		: '\t';

// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 885
COLON_SLASH				: ':'  '/';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 886
ITAL					: '//';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 887
NOWIKI_OPEN				: '{{{';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 888
NOWIKI_CLOSE			: '}}}';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 889
LINK_OPEN				: '[[';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 890
LINK_CLOSE				: ']]';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 891
IMAGE_OPEN				: '{{';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 892
IMAGE_CLOSE				: '}}';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 893
FORCED_LINEBREAK		: '\\\\';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 894
EQUAL					: '=';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 895
PIPE					: '|';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 896
POUND					: '#';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 897
DASH					: '-';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 898
STAR					: '*';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 899
SLASH					: '/';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 900
EXTENSION				: '@@';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 901
TABLE_OF_CONTENTS_OPEN_MARKUP
	:	'<<'
	;
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 904
TABLE_OF_CONTENTS_CLOSE_MARKUP
	:	'>>'
	;
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 907
TABLE_OF_CONTENTS_TEXT
	:	'<<TableOfContents>>'
	;	
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 910
INSIGNIFICANT_CHAR		: .;


