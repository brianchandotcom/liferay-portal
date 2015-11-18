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

Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");
String emoticonsPath = ParamUtil.getString(request, "emoticonsPath");
String imagesPath = ParamUtil.getString(request, "imagesPath");

String languageId = ParamUtil.getString(request, "languageId");

Locale locale = LocaleUtil.fromLanguageId(languageId);

languageId = LocaleUtil.toLanguageId(locale);

String name = ParamUtil.getString(request, "name");
boolean resizable = ParamUtil.getBoolean(request, "resizable");

response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
%>

;(function() {
	var config = CKEDITOR.instances['<%= HtmlUtil.escapeJS(name) %>'].config;

	config.allowedContent = true;

	config.height = 265;

	config.removePlugins = [
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

	config.toolbar_bbcode = [
		['Bold', 'Italic', 'Underline', 'Strike', '-', 'Link', 'Unlink'],
		['Image', 'Smiley', '-', 'TextColor', '-', 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'Blockquote', '-', 'Code'],
		'/',
		['Font', 'FontSize', '-', 'Format', '-', 'Undo', 'Redo', '-', 'Source'],
		['A11YBtn']
	];

	config.toolbar_phone = [
		['Bold', 'Italic', 'Underline'],
		['NumberedList', 'BulletedList'],
		['Image', 'Link', 'Unlink']
	];

	config.toolbar_tablet = [
		['Bold', 'Italic', 'Underline', 'Strike'],
		['NumberedList', 'BulletedList'],
		['Image', 'Link', 'Unlink'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
		['Styles', 'FontSize']
	];

	config.bodyClass = 'html-editor <%= HtmlUtil.escapeJS(cssClasses) %>';

	config.contentsCss = ['<%= HtmlUtil.escapeJS(cssPath) %>/aui.css', '<%= HtmlUtil.escapeJS(cssPath) %>/main.css'];

	<%
	String contentsLanguageDir = LanguageUtil.get(contentsLocale, "lang.dir");
	%>

	config.contentsLangDirection = '<%= HtmlUtil.escapeJS(contentsLanguageDir) %>';

	config.contentsLanguage = '<%= contentsLanguageId.replace("iw_", "he_") %>';

	config.enterMode = CKEDITOR.ENTER_BR;

	config.extraPlugins = 'a11yhelpbtn,bbcode,wikilink';

	config.filebrowserBrowseUrl = '';

	config.filebrowserImageBrowseLinkUrl = '';

	config.filebrowserImageBrowseUrl = '';

	config.filebrowserImageUploadUrl = '';

	config.filebrowserUploadUrl = '';

	config.fontSize_sizes = '10/10px;12/12px;16/16px;18/18px;24/24px;32/32px;48/48px';

	config.format_tags = 'p;pre';

	config.imagesPath = '<%= HtmlUtil.escapeJS(imagesPath) %>/message_boards/';

	config.language = '<%= languageId.replace("iw_", "he_") %>';

	config.newThreadURL = '<%= MBThreadConstants.NEW_THREAD_URL %>';

	<c:if test="<%= resizable %>">
		config.resize_dir = 'vertical';
	</c:if>

	config.resize_enabled = <%= resizable %>;

	config.smiley_descriptions = ['<%= StringUtil.merge(BBCodeTranslatorUtil.getEmoticonDescriptions(), "','") %>'];

	config.smiley_images = ['<%= StringUtil.merge(BBCodeTranslatorUtil.getEmoticonFiles(), "','") %>'];

	config.smiley_path = '<%= HtmlUtil.escapeJS(emoticonsPath) %>' + '/';

	config.smiley_symbols = ['<%= StringUtil.merge(BBCodeTranslatorUtil.getEmoticonSymbols(), "','") %>'];

	config.title = false;
})();