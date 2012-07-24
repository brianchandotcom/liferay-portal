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

<%@ page import="com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>

<%
String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");
String imagesPath = ParamUtil.getString(request, "imagesPath");
String languageId = ParamUtil.getString(request, "languageId");
String emoticonsPath = ParamUtil.getString(request, "emoticonsPath");
boolean resizable = ParamUtil.getBoolean(request, "resizable");
%>

CKEDITOR.config.height = 265;

CKEDITOR.config.removePlugins = [
	'elementspath',
	'save',
	'bidi',
	'div',
	'flash',
	'forms',
	'indent',
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
	'showblocks',
	'templates',
	'wsc'
].join(',');

CKEDITOR.config.toolbar_bbcode = [
	['Bold', 'Italic', 'Underline', 'Strike', '-', 'Link', 'Unlink'],
	['Image', 'Smiley', '-', 'TextColor', '-', 'NumberedList', 'BulletedList'],
	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'Blockquote', '-', 'Code'],
	'/',
	['Font', 'FontSize', '-', 'Format', '-', 'Table', '-', 'Undo', 'Redo', '-', 'Source']
];

CKEDITOR.config.bodyClass = 'html-editor <%= HtmlUtil.escapeJS(cssClasses) %>';

CKEDITOR.config.contentsCss = '<%= HtmlUtil.escapeJS(cssPath) %>/main.css';

CKEDITOR.config.enterMode = CKEDITOR.ENTER_BR;

CKEDITOR.config.extraPlugins = 'bbcode,wikilink';

CKEDITOR.config.filebrowserBrowseUrl = '';

CKEDITOR.config.filebrowserImageBrowseLinkUrl = '';

CKEDITOR.config.filebrowserImageBrowseUrl = '';

CKEDITOR.config.filebrowserImageUploadUrl = '';

CKEDITOR.config.filebrowserUploadUrl = '';

CKEDITOR.config.fontSize_sizes = '10/10px;12/12px;16/16px;18/18px;24/24px;32/32px;48/48px';

CKEDITOR.config.format_tags = 'p;pre';

CKEDITOR.config.imagesPath = '<%= HtmlUtil.escapeJS(imagesPath) %>/message_boards/';

CKEDITOR.config.language = '<%= HtmlUtil.escapeJS(languageId) %>';

CKEDITOR.config.newThreadURL = '<%= BBCodeTranslatorUtil.NEW_THREAD_URL %>';

CKEDITOR.config.resize_enabled = '<%= resizable %>';

CKEDITOR.config.smiley_descriptions = ['<%= StringUtil.merge(BBCodeTranslatorUtil.getEmoticonDescriptions(), "','") %>'];

CKEDITOR.config.smiley_images = ['<%= StringUtil.merge(BBCodeTranslatorUtil.getEmoticonFiles(), "','") %>'];

CKEDITOR.config.smiley_path = '<%= HtmlUtil.escapeJS(emoticonsPath) %>' + '/';

CKEDITOR.config.smiley_symbols = ['<%= StringUtil.merge(BBCodeTranslatorUtil.getEmoticonSymbols(), "','") %>'];