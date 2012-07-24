<%--
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
--%>

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>

<%
String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");
String languageId = ParamUtil.getString(request, "languageId");
long wikiPageResourcePrimKey = ParamUtil.getLong(request, "wikiPageResourcePrimKey");
String attachmentURLPrefix = ParamUtil.getString(request, "attachmentURLPrefix");
boolean resizable = ParamUtil.getBoolean(request, "resizable");

String linkButtonBar = "['Link', 'Unlink']";

if (wikiPageResourcePrimKey > 0) {
	linkButtonBar = "['Link', 'Unlink', 'Image']";
}
%>

CKEDITOR.config.attachmentURLPrefix = '<%= HtmlUtil.escapeJS(attachmentURLPrefix) %>';

CKEDITOR.config.bodyClass = 'html-editor <%= HtmlUtil.escapeJS(cssClasses) %>';

CKEDITOR.config.decodeLinks = true;

CKEDITOR.config.disableObjectResizing = true;

CKEDITOR.config.extraPlugins = 'creole,wikilink';

CKEDITOR.config.format_tags = 'p;h1;h2;h3;h4;h5;h6;pre';

CKEDITOR.config.height = 265;

CKEDITOR.config.language = '<%= HtmlUtil.escapeJS(languageId) %>';

CKEDITOR.config.removePlugins = [
	'elementspath',
	'save',
	'font',
	'bidi',
	'colordialog',
	'colorbutton',
	'div',
	'flash',
	'font',
	'forms',
	'indent',
	'justify',
	'keystrokes',
	'link',
	'menu',
	'maximize',
	'newpage',
	'pagebreak',
	'preview',
	'print',
	'save',
	'scayt',
	'smiley',
	'showblocks',
	'stylescombo',
	'templates',
	'wsc'
].join();

CKEDITOR.config.resize_enabled = '<%= resizable %>';

CKEDITOR.config.toolbar_creole = [
	['Cut','Copy','Paste','PasteText','PasteFromWord'],
	['Undo','Redo'],
	['Bold', 'Italic', '-', 'NumberedList', 'BulletedList' ],
	['Format'],
	<%= linkButtonBar %>,
	['Table', '-', 'HorizontalRule', 'SpecialChar' ],
	['Find','Replace','-','SelectAll','RemoveFormat'],
	['Source']
];