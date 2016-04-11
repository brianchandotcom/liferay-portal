define("frontend-js-metal-web@1.0.6/html2incdom/src/HTMLParser-min", [], function(){"use strict";/*
  * HTML5 Parser By Sam Blowes
  *
  * Designed for HTML5 documents
  *
  * Original code by John Resig (ejohn.org)
  * http://ejohn.org/blog/pure-javascript-html-parser/
  * Original code by Erik Arvidsson, Mozilla Public License
  * http://erik.eae.net/simplehtmlparser/simplehtmlparser.js
  *
  * ----------------------------------------------------------------------------
  * License
  * ----------------------------------------------------------------------------
  *
  * This code is triple licensed using Apache Software License 2.0,
  * Mozilla Public License or GNU Public License
  *
  * ////////////////////////////////////////////////////////////////////////////
  *
  * Licensed under the Apache License, Version 2.0 (the "License"); you may not
  * use this file except in compliance with the License.  You may obtain a copy
  * of the License at http://www.apache.org/licenses/LICENSE-2.0
  *
  * ////////////////////////////////////////////////////////////////////////////
  *
  * The contents of this file are subject to the Mozilla Public License
  * Version 1.1 (the "License"); you may not use this file except in
  * compliance with the License. You may obtain a copy of the License at
  * http://www.mozilla.org/MPL/
  *
  * Software distributed under the License is distributed on an "AS IS"
  * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
  * License for the specific language governing rights and limitations
  * under the License.
  *
  * The Original Code is Simple HTML Parser.
  *
  * The Initial Developer of the Original Code is Erik Arvidsson.
  * Portions created by Erik Arvidssson are Copyright (C) 2004. All Rights
  * Reserved.
  *
  * ////////////////////////////////////////////////////////////////////////////
  *
  * This program is free software; you can redistribute it and/or
  * modify it under the terms of the GNU General Public License
  * as published by the Free Software Foundation; either version 2
  * of the License, or (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program; if not, write to the Free Software
  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  * @license
  */
(function(){function e(e){for(var t={},a=e.split(","),r=0;r<a.length;r++)t[a[r]]=!0;return t}var t=/^<([-A-Za-z0-9_]+)((?:\s+[a-zA-Z_:][-a-zA-Z0-9_:.]*(?:\s*=\s*(?:(?:"[^"]*")|(?:'[^']*')|[^>\s]+))?)*)\s*(\/?)>/,a=/^<\/([-A-Za-z0-9_]+)[^>]*>/,r=/([a-zA-Z_:][-a-zA-Z0-9_:.]*)(?:\s*=\s*(?:(?:"((?:\\.|[^"])*)")|(?:'((?:\\.|[^'])*)')|([^>\s]+)))?/g,s=e("area,base,basefont,br,col,frame,hr,img,input,link,meta,param,embed,command,keygen,source,track,wbr"),n=e("a,address,article,applet,aside,audio,blockquote,button,canvas,center,dd,del,dir,div,dl,dt,fieldset,figcaption,figure,footer,form,frameset,h1,h2,h3,h4,h5,h6,header,hgroup,hr,iframe,ins,isindex,li,map,menu,noframes,noscript,object,ol,output,p,pre,section,script,table,tbody,td,tfoot,th,thead,tr,ul,video"),i=e("abbr,acronym,applet,b,basefont,bdo,big,br,button,cite,code,del,dfn,em,font,i,iframe,img,input,ins,kbd,label,map,object,q,s,samp,script,select,small,span,strike,strong,sub,sup,textarea,tt,u,var"),o=e("colgroup,dd,dt,li,options,p,td,tfoot,th,thead,tr"),l=e("checked,compact,declare,defer,disabled,ismap,multiple,nohref,noresize,noshade,nowrap,readonly,selected"),c=e("script,style");window.HTMLParser=function(e,d){function f(e,t,a,c){if(t=t.toLowerCase(),n[t])for(;b.last()&&i[b.last()];)u("",b.last());if(o[t]&&b.last()==t&&u("",t),c=s[t]||!!c,c||b.push(t),d.start){var f=[];a.replace(r,function(e,t){var a=arguments[2]?arguments[2]:arguments[3]?arguments[3]:arguments[4]?arguments[4]:l[t]?t:"";f.push({name:t,value:a,escaped:a.replace(/(^|[^\\])"/g,'$1\\"')})}),d.start&&d.start(t,f,c)}}function u(e,t){if(t)for(var a=b.length-1;a>=0&&b[a]!=t;a--);else var a=0;if(a>=0){for(var r=b.length-1;r>=a;r--)d.end&&d.end(b[r]);b.length=a}}var p,h,m,b=[],g=e;for(b.last=function(){return this[this.length-1]};e;){if(h=!0,b.last()&&c[b.last()])e=e.replace(new RegExp("([\\s\\S]*?)</"+b.last()+"[^>]*>"),function(e,t){return (t=t.replace(/<!--([\s\S]*?)-->|<!\[CDATA\[([\s\S]*?)]]>/g,"$1$2"), d.chars&&d.chars(t), "")}),u("",b.last());else if(0==e.indexOf("<!--")?(p=e.indexOf("-->"),p>=0&&(d.comment&&d.comment(e.substring(4,p)),e=e.substring(p+3),h=!1)):0==e.indexOf("</")?(m=e.match(a),m&&(e=e.substring(m[0].length),m[0].replace(a,u),h=!1)):0==e.indexOf("<")&&(m=e.match(t),m&&(e=e.substring(m[0].length),m[0].replace(t,f),h=!1)),h){p=e.indexOf("<");var v=0>p?e:e.substring(0,p);e=0>p?"":e.substring(p),d.chars&&d.chars(v)}if(e==g)throw"Parse Error: "+e;g=e}u()}}).call(void 0)});