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

// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 863
ESCAPE					: '~';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 864
NOWIKI_BLOCK_CLOSE		: 	NEWLINE  '}}}';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 865
NEWLINE					: ( CR )?  LF
						| CR;
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 867
fragment CR				: '\r';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 868
fragment LF				: '\n';

// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 870
BLANKS					: ( SPACE | TABULATOR )+;
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 871
fragment SPACE			: ' ';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 872
fragment TABULATOR		: '\t';

// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 874
COLON_SLASH				: ':'  '/';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 875
ITAL					: '//';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 876
NOWIKI_OPEN				: '{{{';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 877
NOWIKI_CLOSE			: '}}}';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 878
LINK_OPEN				: '[[';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 879
LINK_CLOSE				: ']]';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 880
IMAGE_OPEN				: '{{';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 881
IMAGE_CLOSE				: '}}';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 882
FORCED_LINEBREAK		: '\\\\';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 883
EQUAL					: '=';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 884
PIPE					: '|';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 885
POUND					: '#';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 886
DASH					: '-';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 887
STAR					: '*';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 888
SLASH					: '/';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 889
EXTENSION				: '@@';
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 890
TABLE_OF_CONTENTS_OPEN_MARKUP
	:	'<<'
	;
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 893
TABLE_OF_CONTENTS_CLOSE_MARKUP
	:	'>>'
	;
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 896
TABLE_OF_CONTENTS_TEXT
	:	'<<TableOfContents>>'
	;	
// $ANTLR src "/home/migue/development/workspaces/workspace-liferayportal/Archive/portal/portal-impl/src/com/liferay/portal/parsers/creole/grammar/Creole10.g" 899
INSIGNIFICANT_CHAR		: .;


