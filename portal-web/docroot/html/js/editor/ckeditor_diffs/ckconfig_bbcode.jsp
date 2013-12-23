<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ContentTypes" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.LocaleUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBThreadConstants" %>

<%@ page import="java.util.Locale" %>

<%
String contentsLanguageId = ParamUtil.getString(request, "contentsLanguageId");
String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");
String emoticonsPath = ParamUtil.getString(request, "emoticonsPath");
String imagesPath = ParamUtil.getString(request, "imagesPath");
String languageId = ParamUtil.getString(request, "languageId");
String name = ParamUtil.getString(request, "name");
boolean resizable = ParamUtil.getBoolean(request, "resizable");

response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
%>

var ckEditor = CKEDITOR.instances['<%= name %>'];

ckEditor.config.height = 265;

ckEditor.config.removePlugins = [
	'elementspath',
	'save',
	'bidi',
	'div',
	'flash',
	'forms',
	'keystrokes',
	'link',
	'maximize',
	'newpage',
	'pagebreak',
	'preview',
	'print',
	'save',
	'showblocks',
	'templates',
	'video'
].join(',');

ckEditor.config.toolbar_bbcode = [
	['Bold', 'Italic', 'Underline', 'Strike', '-', 'Link', 'Unlink'],
	['Image', 'Smiley', '-', 'TextColor', '-', 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'Blockquote', '-', 'Code'],
	'/',
	['Font', 'FontSize', '-', 'Format', '-', 'Undo', 'Redo', '-', 'Source']
];

ckEditor.config.toolbar_phone = [
	['Bold', 'Italic', 'Underline'],
	['NumberedList', 'BulletedList'],
	['Image', 'Link', 'Unlink']
];

ckEditor.config.toolbar_tablet = [
	['Bold', 'Italic', 'Underline', 'Strike'],
	['NumberedList', 'BulletedList'],
	['Image', 'Link', 'Unlink'],
	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
	['Styles', 'FontSize']
];

ckEditor.config.bodyClass = 'html-editor <%= HtmlUtil.escapeJS(cssClasses) %>';

ckEditor.config.contentsCss = '<%= HtmlUtil.escapeJS(cssPath) %>/main.css';

<%
Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

String contentsLanguageDir = LanguageUtil.get(contentsLocale, "lang.dir");
%>

ckEditor.config.contentsLangDirection = '<%= HtmlUtil.escapeJS(contentsLanguageDir) %>';

ckEditor.config.contentsLanguage = '<%= HtmlUtil.escapeJS(contentsLanguageId.replace("iw_", "he_")) %>';

ckEditor.config.enterMode = CKEDITOR.ENTER_BR;

ckEditor.config.extraPlugins = 'bbcode,wikilink';

ckEditor.config.filebrowserBrowseUrl = '';

ckEditor.config.filebrowserImageBrowseLinkUrl = '';

ckEditor.config.filebrowserImageBrowseUrl = '';

ckEditor.config.filebrowserImageUploadUrl = '';

ckEditor.config.filebrowserUploadUrl = '';

ckEditor.config.fontSize_sizes = '10/10px;12/12px;16/16px;18/18px;24/24px;32/32px;48/48px';

ckEditor.config.format_tags = 'p;pre';

ckEditor.config.imagesPath = '<%= HtmlUtil.escapeJS(imagesPath) %>/message_boards/';

ckEditor.config.language = '<%= HtmlUtil.escapeJS(languageId.replace("iw_", "he_")) %>';

ckEditor.config.newThreadURL = '<%= MBThreadConstants.NEW_THREAD_URL %>';

<c:if test="<%= resizable %>">
	ckEditor.config.resize_dir = 'vertical';
</c:if>

ckEditor.config.resize_enabled = <%= resizable %>;

ckEditor.config.smiley_descriptions = ['<%= StringUtil.merge(BBCodeTranslatorUtil.getEmoticonDescriptions(), "','") %>'];

ckEditor.config.smiley_images = ['<%= StringUtil.merge(BBCodeTranslatorUtil.getEmoticonFiles(), "','") %>'];

ckEditor.config.smiley_path = '<%= HtmlUtil.escapeJS(emoticonsPath) %>' + '/';

ckEditor.config.smiley_symbols = ['<%= StringUtil.merge(BBCodeTranslatorUtil.getEmoticonSymbols(), "','") %>'];