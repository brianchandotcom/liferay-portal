// $ANTLR 3.0.1 LDAPFilter.g 2014-11-20 14:55:12

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

package com.liferay.portal.parsers.ldapfilter.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class LDAPFilterParser extends Parser {
    public static final String[] tokenNames = new String[] {
	"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ASCII_LATIN1", "DOT", "UTF", "COLON", "ASCII_LETTER", "DIGIT", "DASH", "'('", "')'", "'&'", "'|'", "'!'", "'='", "'~='", "'>='", "'<='", "':dn'", "':='", "'*'", "';'"
    };
    public static final int COLON=7;
    public static final int DASH=10;
    public static final int ASCII_LETTER=8;
    public static final int ASCII_LATIN1=4;
    public static final int DIGIT=9;
    public static final int DOT=5;
    public static final int EOF=-1;
    public static final int UTF=6;

	public LDAPFilterParser(TokenStream input) {
	    super(input);
	}
	

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "LDAPFilter.g"; }


	@Override
	public void reportError(RecognitionException e) {
		throw new RuntimeException(e);
	}



    // $ANTLR start parse
    // LDAPFilter.g:56:1: parse : filter EOF ;
    public final void parse() throws RecognitionException {
	try {
	    // LDAPFilter.g:56:6: ( filter EOF )
	    // LDAPFilter.g:56:8: filter EOF
	    {
	    pushFollow(FOLLOW_filter_in_parse55);
	    filter();
	    _fsp--;

	    match(input,EOF,FOLLOW_EOF_in_parse57); 

	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end parse


    // $ANTLR start filter
    // LDAPFilter.g:57:1: filter : '(' filtercomp ')' ;
    public final void filter() throws RecognitionException {
	try {
	    // LDAPFilter.g:57:7: ( '(' filtercomp ')' )
	    // LDAPFilter.g:57:9: '(' filtercomp ')'
	    {
	    match(input,11,FOLLOW_11_in_filter63); 
	    pushFollow(FOLLOW_filtercomp_in_filter65);
	    filtercomp();
	    _fsp--;

	    match(input,12,FOLLOW_12_in_filter67); 

	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end filter


    // $ANTLR start filtercomp
    // LDAPFilter.g:58:1: filtercomp : ( and | or | not | item ) ;
    public final void filtercomp() throws RecognitionException {
	try {
	    // LDAPFilter.g:58:11: ( ( and | or | not | item ) )
	    // LDAPFilter.g:58:13: ( and | or | not | item )
	    {
	    // LDAPFilter.g:58:13: ( and | or | not | item )
	    int alt1=4;
	    switch ( input.LA(1) ) {
	    case 13:
		{
		alt1=1;
		}
		break;
	    case 14:
		{
		alt1=2;
		}
		break;
	    case 15:
		{
		alt1=3;
		}
		break;
	    case ASCII_LATIN1:
	    case COLON:
	    case 20:
		{
		alt1=4;
		}
		break;
	    default:
		NoViableAltException nvae =
		    new NoViableAltException("58:13: ( and | or | not | item )", 1, 0, input);

		throw nvae;
	    }

	    switch (alt1) {
		case 1 :
		    // LDAPFilter.g:58:14: and
		    {
		    pushFollow(FOLLOW_and_in_filtercomp74);
		    and();
		    _fsp--;


		    }
		    break;
		case 2 :
		    // LDAPFilter.g:58:20: or
		    {
		    pushFollow(FOLLOW_or_in_filtercomp78);
		    or();
		    _fsp--;


		    }
		    break;
		case 3 :
		    // LDAPFilter.g:58:25: not
		    {
		    pushFollow(FOLLOW_not_in_filtercomp82);
		    not();
		    _fsp--;


		    }
		    break;
		case 4 :
		    // LDAPFilter.g:58:31: item
		    {
		    pushFollow(FOLLOW_item_in_filtercomp86);
		    item();
		    _fsp--;


		    }
		    break;

	    }


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end filtercomp


    // $ANTLR start and
    // LDAPFilter.g:59:1: and : '&' ( filter )+ ;
    public final void and() throws RecognitionException {
	try {
	    // LDAPFilter.g:59:4: ( '&' ( filter )+ )
	    // LDAPFilter.g:59:6: '&' ( filter )+
	    {
	    match(input,13,FOLLOW_13_in_and93); 
	    // LDAPFilter.g:59:10: ( filter )+
	    int cnt2=0;
	    loop2:
	    do {
		int alt2=2;
		int LA2_0 = input.LA(1);

		if ( (LA2_0==11) ) {
		    alt2=1;
		}


		switch (alt2) {
		case 1 :
		    // LDAPFilter.g:59:10: filter
		    {
		    pushFollow(FOLLOW_filter_in_and95);
		    filter();
		    _fsp--;


		    }
		    break;

		default :
		    if ( cnt2 >= 1 ) break loop2;
			EarlyExitException eee =
			    new EarlyExitException(2, input);
			throw eee;
		}
		cnt2++;
	    } while (true);


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end and


    // $ANTLR start or
    // LDAPFilter.g:60:1: or : '|' ( filter )+ ;
    public final void or() throws RecognitionException {
	try {
	    // LDAPFilter.g:60:3: ( '|' ( filter )+ )
	    // LDAPFilter.g:60:5: '|' ( filter )+
	    {
	    match(input,14,FOLLOW_14_in_or102); 
	    // LDAPFilter.g:60:9: ( filter )+
	    int cnt3=0;
	    loop3:
	    do {
		int alt3=2;
		int LA3_0 = input.LA(1);

		if ( (LA3_0==11) ) {
		    alt3=1;
		}


		switch (alt3) {
		case 1 :
		    // LDAPFilter.g:60:9: filter
		    {
		    pushFollow(FOLLOW_filter_in_or104);
		    filter();
		    _fsp--;


		    }
		    break;

		default :
		    if ( cnt3 >= 1 ) break loop3;
			EarlyExitException eee =
			    new EarlyExitException(3, input);
			throw eee;
		}
		cnt3++;
	    } while (true);


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end or


    // $ANTLR start not
    // LDAPFilter.g:61:1: not : '!' ( filter )+ ;
    public final void not() throws RecognitionException {
	try {
	    // LDAPFilter.g:61:4: ( '!' ( filter )+ )
	    // LDAPFilter.g:61:6: '!' ( filter )+
	    {
	    match(input,15,FOLLOW_15_in_not111); 
	    // LDAPFilter.g:61:10: ( filter )+
	    int cnt4=0;
	    loop4:
	    do {
		int alt4=2;
		int LA4_0 = input.LA(1);

		if ( (LA4_0==11) ) {
		    alt4=1;
		}


		switch (alt4) {
		case 1 :
		    // LDAPFilter.g:61:10: filter
		    {
		    pushFollow(FOLLOW_filter_in_not113);
		    filter();
		    _fsp--;


		    }
		    break;

		default :
		    if ( cnt4 >= 1 ) break loop4;
			EarlyExitException eee =
			    new EarlyExitException(4, input);
			throw eee;
		}
		cnt4++;
	    } while (true);


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end not


    // $ANTLR start item
    // LDAPFilter.g:62:1: item : ( simple | present | substring | extensible ) ;
    public final void item() throws RecognitionException {
	try {
	    // LDAPFilter.g:62:5: ( ( simple | present | substring | extensible ) )
	    // LDAPFilter.g:62:7: ( simple | present | substring | extensible )
	    {
	    // LDAPFilter.g:62:7: ( simple | present | substring | extensible )
	    int alt5=4;
	    alt5 = dfa5.predict(input);
	    switch (alt5) {
		case 1 :
		    // LDAPFilter.g:62:8: simple
		    {
		    pushFollow(FOLLOW_simple_in_item121);
		    simple();
		    _fsp--;


		    }
		    break;
		case 2 :
		    // LDAPFilter.g:62:17: present
		    {
		    pushFollow(FOLLOW_present_in_item125);
		    present();
		    _fsp--;


		    }
		    break;
		case 3 :
		    // LDAPFilter.g:62:27: substring
		    {
		    pushFollow(FOLLOW_substring_in_item129);
		    substring();
		    _fsp--;


		    }
		    break;
		case 4 :
		    // LDAPFilter.g:62:39: extensible
		    {
		    pushFollow(FOLLOW_extensible_in_item133);
		    extensible();
		    _fsp--;


		    }
		    break;

	    }


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end item


    // $ANTLR start simple
    // LDAPFilter.g:63:1: simple : attr filtertype ( options {greedy=true; } : values | item ) ;
    public final void simple() throws RecognitionException {
	try {
	    // LDAPFilter.g:63:7: ( attr filtertype ( options {greedy=true; } : values | item ) )
	    // LDAPFilter.g:63:8: attr filtertype ( options {greedy=true; } : values | item )
	    {
	    pushFollow(FOLLOW_attr_in_simple139);
	    attr();
	    _fsp--;

	    pushFollow(FOLLOW_filtertype_in_simple141);
	    filtertype();
	    _fsp--;

	    // LDAPFilter.g:63:24: ( options {greedy=true; } : values | item )
	    int alt6=2;
	    alt6 = dfa6.predict(input);
	    switch (alt6) {
		case 1 :
		    // LDAPFilter.g:63:48: values
		    {
		    pushFollow(FOLLOW_values_in_simple152);
		    values();
		    _fsp--;


		    }
		    break;
		case 2 :
		    // LDAPFilter.g:63:57: item
		    {
		    pushFollow(FOLLOW_item_in_simple156);
		    item();
		    _fsp--;


		    }
		    break;

	    }


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end simple


    // $ANTLR start filtertype
    // LDAPFilter.g:64:1: filtertype : ( equal | approx | greater | less ) ;
    public final void filtertype() throws RecognitionException {
	try {
	    // LDAPFilter.g:64:11: ( ( equal | approx | greater | less ) )
	    // LDAPFilter.g:64:13: ( equal | approx | greater | less )
	    {
	    // LDAPFilter.g:64:13: ( equal | approx | greater | less )
	    int alt7=4;
	    switch ( input.LA(1) ) {
	    case 16:
		{
		alt7=1;
		}
		break;
	    case 17:
		{
		alt7=2;
		}
		break;
	    case 18:
		{
		alt7=3;
		}
		break;
	    case 19:
		{
		alt7=4;
		}
		break;
	    default:
		NoViableAltException nvae =
		    new NoViableAltException("64:13: ( equal | approx | greater | less )", 7, 0, input);

		throw nvae;
	    }

	    switch (alt7) {
		case 1 :
		    // LDAPFilter.g:64:14: equal
		    {
		    pushFollow(FOLLOW_equal_in_filtertype164);
		    equal();
		    _fsp--;


		    }
		    break;
		case 2 :
		    // LDAPFilter.g:64:22: approx
		    {
		    pushFollow(FOLLOW_approx_in_filtertype168);
		    approx();
		    _fsp--;


		    }
		    break;
		case 3 :
		    // LDAPFilter.g:64:31: greater
		    {
		    pushFollow(FOLLOW_greater_in_filtertype172);
		    greater();
		    _fsp--;


		    }
		    break;
		case 4 :
		    // LDAPFilter.g:64:41: less
		    {
		    pushFollow(FOLLOW_less_in_filtertype176);
		    less();
		    _fsp--;


		    }
		    break;

	    }


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end filtertype


    // $ANTLR start equal
    // LDAPFilter.g:65:1: equal : '=' ;
    public final void equal() throws RecognitionException {
	try {
	    // LDAPFilter.g:65:6: ( '=' )
	    // LDAPFilter.g:65:8: '='
	    {
	    match(input,16,FOLLOW_16_in_equal183); 

	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end equal


    // $ANTLR start approx
    // LDAPFilter.g:66:1: approx : '~=' ;
    public final void approx() throws RecognitionException {
	try {
	    // LDAPFilter.g:66:7: ( '~=' )
	    // LDAPFilter.g:66:9: '~='
	    {
	    match(input,17,FOLLOW_17_in_approx189); 

	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end approx


    // $ANTLR start greater
    // LDAPFilter.g:67:1: greater : '>=' ;
    public final void greater() throws RecognitionException {
	try {
	    // LDAPFilter.g:67:8: ( '>=' )
	    // LDAPFilter.g:67:10: '>='
	    {
	    match(input,18,FOLLOW_18_in_greater195); 

	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end greater


    // $ANTLR start less
    // LDAPFilter.g:68:1: less : '<=' ;
    public final void less() throws RecognitionException {
	try {
	    // LDAPFilter.g:68:5: ( '<=' )
	    // LDAPFilter.g:68:7: '<='
	    {
	    match(input,19,FOLLOW_19_in_less201); 

	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end less


    // $ANTLR start extensible
    // LDAPFilter.g:69:1: extensible : ( ( attr ( ':dn' )* ( ':' matchingrule )* ':=' values ) | ( ( ':dn' )* ':' matchingrule ':=' values ) );
    public final void extensible() throws RecognitionException {
	try {
	    // LDAPFilter.g:69:11: ( ( attr ( ':dn' )* ( ':' matchingrule )* ':=' values ) | ( ( ':dn' )* ':' matchingrule ':=' values ) )
	    int alt11=2;
	    int LA11_0 = input.LA(1);

	    if ( (LA11_0==ASCII_LATIN1) ) {
		alt11=1;
	    }
	    else if ( (LA11_0==COLON||LA11_0==20) ) {
		alt11=2;
	    }
	    else {
		NoViableAltException nvae =
		    new NoViableAltException("69:1: extensible : ( ( attr ( ':dn' )* ( ':' matchingrule )* ':=' values ) | ( ( ':dn' )* ':' matchingrule ':=' values ) );", 11, 0, input);

		throw nvae;
	    }
	    switch (alt11) {
		case 1 :
		    // LDAPFilter.g:69:13: ( attr ( ':dn' )* ( ':' matchingrule )* ':=' values )
		    {
		    // LDAPFilter.g:69:13: ( attr ( ':dn' )* ( ':' matchingrule )* ':=' values )
		    // LDAPFilter.g:69:14: attr ( ':dn' )* ( ':' matchingrule )* ':=' values
		    {
		    pushFollow(FOLLOW_attr_in_extensible208);
		    attr();
		    _fsp--;

		    // LDAPFilter.g:69:19: ( ':dn' )*
		    loop8:
		    do {
			int alt8=2;
			int LA8_0 = input.LA(1);

			if ( (LA8_0==20) ) {
			    alt8=1;
			}


			switch (alt8) {
			case 1 :
			    // LDAPFilter.g:69:20: ':dn'
			    {
			    match(input,20,FOLLOW_20_in_extensible211); 

			    }
			    break;

			default :
			    break loop8;
			}
		    } while (true);

		    // LDAPFilter.g:69:28: ( ':' matchingrule )*
		    loop9:
		    do {
			int alt9=2;
			int LA9_0 = input.LA(1);

			if ( (LA9_0==COLON) ) {
			    alt9=1;
			}


			switch (alt9) {
			case 1 :
			    // LDAPFilter.g:69:29: ':' matchingrule
			    {
			    match(input,COLON,FOLLOW_COLON_in_extensible216); 
			    pushFollow(FOLLOW_matchingrule_in_extensible218);
			    matchingrule();
			    _fsp--;


			    }
			    break;

			default :
			    break loop9;
			}
		    } while (true);

		    match(input,21,FOLLOW_21_in_extensible222); 
		    pushFollow(FOLLOW_values_in_extensible224);
		    values();
		    _fsp--;


		    }


		    }
		    break;
		case 2 :
		    // LDAPFilter.g:69:63: ( ( ':dn' )* ':' matchingrule ':=' values )
		    {
		    // LDAPFilter.g:69:63: ( ( ':dn' )* ':' matchingrule ':=' values )
		    // LDAPFilter.g:69:64: ( ':dn' )* ':' matchingrule ':=' values
		    {
		    // LDAPFilter.g:69:64: ( ':dn' )*
		    loop10:
		    do {
			int alt10=2;
			int LA10_0 = input.LA(1);

			if ( (LA10_0==20) ) {
			    alt10=1;
			}


			switch (alt10) {
			case 1 :
			    // LDAPFilter.g:69:65: ':dn'
			    {
			    match(input,20,FOLLOW_20_in_extensible231); 

			    }
			    break;

			default :
			    break loop10;
			}
		    } while (true);

		    match(input,COLON,FOLLOW_COLON_in_extensible235); 
		    pushFollow(FOLLOW_matchingrule_in_extensible237);
		    matchingrule();
		    _fsp--;

		    match(input,21,FOLLOW_21_in_extensible239); 
		    pushFollow(FOLLOW_values_in_extensible241);
		    values();
		    _fsp--;


		    }


		    }
		    break;

	    }
	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end extensible


    // $ANTLR start present
    // LDAPFilter.g:70:1: present : attr '=' '*' EOF ;
    public final void present() throws RecognitionException {
	try {
	    // LDAPFilter.g:70:8: ( attr '=' '*' EOF )
	    // LDAPFilter.g:70:10: attr '=' '*' EOF
	    {
	    pushFollow(FOLLOW_attr_in_present248);
	    attr();
	    _fsp--;

	    match(input,16,FOLLOW_16_in_present250); 
	    match(input,22,FOLLOW_22_in_present252); 
	    match(input,EOF,FOLLOW_EOF_in_present254); 

	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end present


    // $ANTLR start substring
    // LDAPFilter.g:71:1: substring : attr '=' ( value )? any ( value )? ;
    public final void substring() throws RecognitionException {
	try {
	    // LDAPFilter.g:71:10: ( attr '=' ( value )? any ( value )? )
	    // LDAPFilter.g:71:12: attr '=' ( value )? any ( value )?
	    {
	    pushFollow(FOLLOW_attr_in_substring260);
	    attr();
	    _fsp--;

	    match(input,16,FOLLOW_16_in_substring262); 
	    // LDAPFilter.g:71:21: ( value )?
	    int alt12=2;
	    int LA12_0 = input.LA(1);

	    if ( ((LA12_0>=ASCII_LATIN1 && LA12_0<=COLON)) ) {
		alt12=1;
	    }
	    switch (alt12) {
		case 1 :
		    // LDAPFilter.g:71:21: value
		    {
		    pushFollow(FOLLOW_value_in_substring264);
		    value();
		    _fsp--;


		    }
		    break;

	    }

	    pushFollow(FOLLOW_any_in_substring267);
	    any();
	    _fsp--;

	    // LDAPFilter.g:71:32: ( value )?
	    int alt13=2;
	    int LA13_0 = input.LA(1);

	    if ( ((LA13_0>=ASCII_LATIN1 && LA13_0<=COLON)) ) {
		alt13=1;
	    }
	    switch (alt13) {
		case 1 :
		    // LDAPFilter.g:71:32: value
		    {
		    pushFollow(FOLLOW_value_in_substring269);
		    value();
		    _fsp--;


		    }
		    break;

	    }


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end substring


    // $ANTLR start any
    // LDAPFilter.g:72:1: any : '*' ( value '*' )* ;
    public final void any() throws RecognitionException {
	try {
	    // LDAPFilter.g:72:4: ( '*' ( value '*' )* )
	    // LDAPFilter.g:72:6: '*' ( value '*' )*
	    {
	    match(input,22,FOLLOW_22_in_any276); 
	    // LDAPFilter.g:72:10: ( value '*' )*
	    loop14:
	    do {
		int alt14=2;
		alt14 = dfa14.predict(input);
		switch (alt14) {
		case 1 :
		    // LDAPFilter.g:72:11: value '*'
		    {
		    pushFollow(FOLLOW_value_in_any279);
		    value();
		    _fsp--;

		    match(input,22,FOLLOW_22_in_any281); 

		    }
		    break;

		default :
		    break loop14;
		}
	    } while (true);


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end any


    // $ANTLR start attr
    // LDAPFilter.g:73:1: attr : attributeDescription ;
    public final void attr() throws RecognitionException {
	try {
	    // LDAPFilter.g:73:5: ( attributeDescription )
	    // LDAPFilter.g:73:7: attributeDescription
	    {
	    pushFollow(FOLLOW_attributeDescription_in_attr289);
	    attributeDescription();
	    _fsp--;


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end attr


    // $ANTLR start matchingrule
    // LDAPFilter.g:74:1: matchingrule : ( ASCII_LATIN1 | DOT )+ ;
    public final void matchingrule() throws RecognitionException {
	try {
	    // LDAPFilter.g:74:13: ( ( ASCII_LATIN1 | DOT )+ )
	    // LDAPFilter.g:74:15: ( ASCII_LATIN1 | DOT )+
	    {
	    // LDAPFilter.g:74:15: ( ASCII_LATIN1 | DOT )+
	    int cnt15=0;
	    loop15:
	    do {
		int alt15=2;
		int LA15_0 = input.LA(1);

		if ( ((LA15_0>=ASCII_LATIN1 && LA15_0<=DOT)) ) {
		    alt15=1;
		}


		switch (alt15) {
		case 1 :
		    // LDAPFilter.g:
		    {
		    if ( (input.LA(1)>=ASCII_LATIN1 && input.LA(1)<=DOT) ) {
			input.consume();
			errorRecovery=false;
		    }
		    else {
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_matchingrule295);    throw mse;
		    }


		    }
		    break;

		default :
		    if ( cnt15 >= 1 ) break loop15;
			EarlyExitException eee =
			    new EarlyExitException(15, input);
			throw eee;
		}
		cnt15++;
	    } while (true);


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end matchingrule


    // $ANTLR start value
    // LDAPFilter.g:75:1: value : ( ASCII_LATIN1 | UTF | COLON | DOT )+ ;
    public final void value() throws RecognitionException {
	try {
	    // LDAPFilter.g:75:6: ( ( ASCII_LATIN1 | UTF | COLON | DOT )+ )
	    // LDAPFilter.g:75:8: ( ASCII_LATIN1 | UTF | COLON | DOT )+
	    {
	    // LDAPFilter.g:75:8: ( ASCII_LATIN1 | UTF | COLON | DOT )+
	    int cnt16=0;
	    loop16:
	    do {
		int alt16=2;
		int LA16_0 = input.LA(1);

		if ( ((LA16_0>=ASCII_LATIN1 && LA16_0<=COLON)) ) {
		    alt16=1;
		}


		switch (alt16) {
		case 1 :
		    // LDAPFilter.g:
		    {
		    if ( (input.LA(1)>=ASCII_LATIN1 && input.LA(1)<=COLON) ) {
			input.consume();
			errorRecovery=false;
		    }
		    else {
			MismatchedSetException mse =
			    new MismatchedSetException(null,input);
			recoverFromMismatchedSet(input,mse,FOLLOW_set_in_value308);    throw mse;
		    }


		    }
		    break;

		default :
		    if ( cnt16 >= 1 ) break loop16;
			EarlyExitException eee =
			    new EarlyExitException(16, input);
			throw eee;
		}
		cnt16++;
	    } while (true);


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end value


    // $ANTLR start values
    // LDAPFilter.g:76:1: values : value ( equal value )* ;
    public final void values() throws RecognitionException {
	try {
	    // LDAPFilter.g:76:7: ( value ( equal value )* )
	    // LDAPFilter.g:76:9: value ( equal value )*
	    {
	    pushFollow(FOLLOW_value_in_values329);
	    value();
	    _fsp--;

	    // LDAPFilter.g:76:15: ( equal value )*
	    loop17:
	    do {
		int alt17=2;
		int LA17_0 = input.LA(1);

		if ( (LA17_0==16) ) {
		    alt17=1;
		}


		switch (alt17) {
		case 1 :
		    // LDAPFilter.g:76:16: equal value
		    {
		    pushFollow(FOLLOW_equal_in_values332);
		    equal();
		    _fsp--;

		    pushFollow(FOLLOW_value_in_values334);
		    value();
		    _fsp--;


		    }
		    break;

		default :
		    break loop17;
		}
	    } while (true);


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end values


    // $ANTLR start attributeDescription
    // LDAPFilter.g:78:1: attributeDescription : attributeType ( ';' option )* ;
    public final void attributeDescription() throws RecognitionException {
	try {
	    // LDAPFilter.g:78:21: ( attributeType ( ';' option )* )
	    // LDAPFilter.g:78:23: attributeType ( ';' option )*
	    {
	    pushFollow(FOLLOW_attributeType_in_attributeDescription343);
	    attributeType();
	    _fsp--;

	    // LDAPFilter.g:78:37: ( ';' option )*
	    loop18:
	    do {
		int alt18=2;
		int LA18_0 = input.LA(1);

		if ( (LA18_0==23) ) {
		    alt18=1;
		}


		switch (alt18) {
		case 1 :
		    // LDAPFilter.g:78:38: ';' option
		    {
		    match(input,23,FOLLOW_23_in_attributeDescription346); 
		    pushFollow(FOLLOW_option_in_attributeDescription348);
		    option();
		    _fsp--;


		    }
		    break;

		default :
		    break loop18;
		}
	    } while (true);


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end attributeDescription


    // $ANTLR start attributeType
    // LDAPFilter.g:79:1: attributeType : ( ASCII_LATIN1 )+ ;
    public final void attributeType() throws RecognitionException {
	try {
	    // LDAPFilter.g:79:14: ( ( ASCII_LATIN1 )+ )
	    // LDAPFilter.g:79:16: ( ASCII_LATIN1 )+
	    {
	    // LDAPFilter.g:79:16: ( ASCII_LATIN1 )+
	    int cnt19=0;
	    loop19:
	    do {
		int alt19=2;
		int LA19_0 = input.LA(1);

		if ( (LA19_0==ASCII_LATIN1) ) {
		    alt19=1;
		}


		switch (alt19) {
		case 1 :
		    // LDAPFilter.g:79:16: ASCII_LATIN1
		    {
		    match(input,ASCII_LATIN1,FOLLOW_ASCII_LATIN1_in_attributeType356); 

		    }
		    break;

		default :
		    if ( cnt19 >= 1 ) break loop19;
			EarlyExitException eee =
			    new EarlyExitException(19, input);
			throw eee;
		}
		cnt19++;
	    } while (true);


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end attributeType


    // $ANTLR start option
    // LDAPFilter.g:80:1: option : optchar ;
    public final void option() throws RecognitionException {
	try {
	    // LDAPFilter.g:80:7: ( optchar )
	    // LDAPFilter.g:80:9: optchar
	    {
	    pushFollow(FOLLOW_optchar_in_option363);
	    optchar();
	    _fsp--;


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end option


    // $ANTLR start optchar
    // LDAPFilter.g:81:1: optchar : ( ASCII_LETTER | DIGIT | DASH ) ;
    public final void optchar() throws RecognitionException {
	try {
	    // LDAPFilter.g:81:8: ( ( ASCII_LETTER | DIGIT | DASH ) )
	    // LDAPFilter.g:81:9: ( ASCII_LETTER | DIGIT | DASH )
	    {
	    if ( (input.LA(1)>=ASCII_LETTER && input.LA(1)<=DASH) ) {
		input.consume();
		errorRecovery=false;
	    }
	    else {
		MismatchedSetException mse =
		    new MismatchedSetException(null,input);
		recoverFromMismatchedSet(input,mse,FOLLOW_set_in_optchar368);	 throw mse;
	    }


	    }

	}
	catch (RecognitionException re) {
	    reportError(re);
	    recover(input,re);
	}
	finally {
	}
	return ;
    }
    // $ANTLR end optchar


    protected DFA5 dfa5 = new DFA5(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA5_eotS =
	"\20\uffff";
    static final String DFA5_eofS =
	"\7\uffff\1\13\10\uffff";
    static final String DFA5_minS =
	"\2\4\1\uffff\1\10\1\4\1\uffff\1\7\4\4\2\uffff\3\4";
    static final String DFA5_maxS =
	"\1\24\1\27\1\uffff\1\12\1\26\1\uffff\1\27\1\14\1\27\2\26\2\uffff"+
	"\3\26";
    static final String DFA5_acceptS =
	"\2\uffff\1\4\2\uffff\1\1\5\uffff\1\2\1\3\3\uffff";
    static final String DFA5_specialS =
	"\20\uffff}>";
    static final String[] DFA5_transitionS = {
	    "\1\1\2\uffff\1\2\14\uffff\1\2",
	    "\1\1\2\uffff\1\2\10\uffff\1\4\3\5\2\2\1\uffff\1\3",
	    "",
	    "\3\6",
	    "\1\10\2\12\1\11\14\uffff\1\5\1\uffff\1\7",
	    "",
	    "\1\2\10\uffff\1\4\3\5\2\2\1\uffff\1\3",
	    "\4\14\4\uffff\1\14",
	    "\1\10\2\12\1\15\4\uffff\1\5\3\uffff\6\5\1\14\1\5",
	    "\2\16\2\12\4\uffff\1\5\3\uffff\1\5\5\uffff\1\14",
	    "\4\12\4\uffff\1\5\3\uffff\1\5\5\uffff\1\14",
	    "",
	    "",
	    "\2\17\2\12\4\uffff\1\5\3\uffff\1\5\5\uffff\1\14",
	    "\2\16\2\12\4\uffff\1\5\3\uffff\1\5\4\uffff\1\5\1\14",
	    "\2\17\1\12\1\15\4\uffff\1\5\3\uffff\1\5\4\uffff\1\5\1\14"
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
	int numStates = DFA5_transitionS.length;
	DFA5_transition = new short[numStates][];
	for (int i=0; i<numStates; i++) {
	    DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
	}
    }

    class DFA5 extends DFA {

	public DFA5(BaseRecognizer recognizer) {
	    this.recognizer = recognizer;
	    this.decisionNumber = 5;
	    this.eot = DFA5_eot;
	    this.eof = DFA5_eof;
	    this.min = DFA5_min;
	    this.max = DFA5_max;
	    this.accept = DFA5_accept;
	    this.special = DFA5_special;
	    this.transition = DFA5_transition;
	}
	public String getDescription() {
	    return "62:7: ( simple | present | substring | extensible )";
	}
    }
    static final String DFA6_eotS =
	"\14\uffff";
    static final String DFA6_eofS =
	"\14\uffff";
    static final String DFA6_minS =
	"\3\4\2\uffff\3\4\3\uffff\1\4";
    static final String DFA6_maxS =
	"\1\24\1\27\1\20\2\uffff\1\26\1\20\1\25\3\uffff\1\25";
    static final String DFA6_acceptS =
	"\3\uffff\1\2\1\1\3\uffff\3\1\1\uffff";
    static final String DFA6_specialS =
	"\14\uffff}>";
    static final String[] DFA6_transitionS = {
	    "\1\1\2\4\1\2\14\uffff\1\3",
	    "\1\1\2\4\1\6\4\uffff\1\4\3\uffff\1\5\5\3\1\uffff\1\3",
	    "\2\7\2\4\4\uffff\1\4\3\uffff\1\4",
	    "",
	    "",
	    "\1\10\2\12\1\11\14\uffff\1\3\1\uffff\1\3",
	    "\2\13\2\4\4\uffff\1\12\3\uffff\1\12",
	    "\2\7\2\4\4\uffff\1\12\3\uffff\1\12\4\uffff\1\3",
	    "",
	    "",
	    "",
	    "\2\13\1\4\1\6\4\uffff\1\12\3\uffff\1\12\4\uffff\1\3"
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
	int numStates = DFA6_transitionS.length;
	DFA6_transition = new short[numStates][];
	for (int i=0; i<numStates; i++) {
	    DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
	}
    }

    class DFA6 extends DFA {

	public DFA6(BaseRecognizer recognizer) {
	    this.recognizer = recognizer;
	    this.decisionNumber = 6;
	    this.eot = DFA6_eot;
	    this.eof = DFA6_eof;
	    this.min = DFA6_min;
	    this.max = DFA6_max;
	    this.accept = DFA6_accept;
	    this.special = DFA6_special;
	    this.transition = DFA6_transition;
	}
	public String getDescription() {
	    return "63:24: ( options {greedy=true; } : values | item )";
	}
    }
    static final String DFA14_eotS =
	"\4\uffff";
    static final String DFA14_eofS =
	"\4\uffff";
    static final String DFA14_minS =
	"\2\4\2\uffff";
    static final String DFA14_maxS =
	"\1\14\1\26\2\uffff";
    static final String DFA14_acceptS =
	"\2\uffff\1\2\1\1";
    static final String DFA14_specialS =
	"\4\uffff}>";
    static final String[] DFA14_transitionS = {
	    "\4\1\4\uffff\1\2",
	    "\4\1\4\uffff\1\2\11\uffff\1\3",
	    "",
	    ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
	int numStates = DFA14_transitionS.length;
	DFA14_transition = new short[numStates][];
	for (int i=0; i<numStates; i++) {
	    DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
	}
    }

    class DFA14 extends DFA {

	public DFA14(BaseRecognizer recognizer) {
	    this.recognizer = recognizer;
	    this.decisionNumber = 14;
	    this.eot = DFA14_eot;
	    this.eof = DFA14_eof;
	    this.min = DFA14_min;
	    this.max = DFA14_max;
	    this.accept = DFA14_accept;
	    this.special = DFA14_special;
	    this.transition = DFA14_transition;
	}
	public String getDescription() {
	    return "()* loopback of 72:10: ( value '*' )*";
	}
    }
 

    public static final BitSet FOLLOW_filter_in_parse55 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_parse57 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_filter63 = new BitSet(new long[]{0x000000000010E090L});
    public static final BitSet FOLLOW_filtercomp_in_filter65 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_filter67 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_and_in_filtercomp74 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_or_in_filtercomp78 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_not_in_filtercomp82 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_item_in_filtercomp86 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_and93 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_filter_in_and95 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_14_in_or102 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_filter_in_or104 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_15_in_not111 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_filter_in_not113 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_simple_in_item121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_present_in_item125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substring_in_item129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_extensible_in_item133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attr_in_simple139 = new BitSet(new long[]{0x00000000000F0000L});
    public static final BitSet FOLLOW_filtertype_in_simple141 = new BitSet(new long[]{0x00000000001000F0L});
    public static final BitSet FOLLOW_values_in_simple152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_item_in_simple156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_equal_in_filtertype164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_approx_in_filtertype168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_greater_in_filtertype172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_less_in_filtertype176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_equal183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_approx189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_greater195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_less201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attr_in_extensible208 = new BitSet(new long[]{0x0000000000300080L});
    public static final BitSet FOLLOW_20_in_extensible211 = new BitSet(new long[]{0x0000000000300080L});
    public static final BitSet FOLLOW_COLON_in_extensible216 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_matchingrule_in_extensible218 = new BitSet(new long[]{0x0000000000200080L});
    public static final BitSet FOLLOW_21_in_extensible222 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_values_in_extensible224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_extensible231 = new BitSet(new long[]{0x0000000000100080L});
    public static final BitSet FOLLOW_COLON_in_extensible235 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_matchingrule_in_extensible237 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_extensible239 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_values_in_extensible241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attr_in_present248 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_present250 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_present252 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_present254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attr_in_substring260 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_substring262 = new BitSet(new long[]{0x00000000004000F0L});
    public static final BitSet FOLLOW_value_in_substring264 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_any_in_substring267 = new BitSet(new long[]{0x00000000000000F2L});
    public static final BitSet FOLLOW_value_in_substring269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_any276 = new BitSet(new long[]{0x00000000000000F2L});
    public static final BitSet FOLLOW_value_in_any279 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_any281 = new BitSet(new long[]{0x00000000000000F2L});
    public static final BitSet FOLLOW_attributeDescription_in_attr289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_matchingrule295 = new BitSet(new long[]{0x0000000000000032L});
    public static final BitSet FOLLOW_set_in_value308 = new BitSet(new long[]{0x00000000000000F2L});
    public static final BitSet FOLLOW_value_in_values329 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_equal_in_values332 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_value_in_values334 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_attributeType_in_attributeDescription343 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_23_in_attributeDescription346 = new BitSet(new long[]{0x0000000000000700L});
    public static final BitSet FOLLOW_option_in_attributeDescription348 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_ASCII_LATIN1_in_attributeType356 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_optchar_in_option363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_optchar368 = new BitSet(new long[]{0x0000000000000002L});

}
