<%--
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
--%>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ContentTypes" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.LocaleUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>

<%@ page import="java.util.Locale" %>

<%
String contentsLanguageId = ParamUtil.getString(request, "contentsLanguageId");

Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

String languageId = ParamUtil.getString(request, "languageId");

Locale locale = LocaleUtil.fromLanguageId(languageId);

languageId = LocaleUtil.toLanguageId(locale);

String name = ParamUtil.getString(request, "name");
String toolbarSet = ParamUtil.getString(request, "toolbarSet");

response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
%>

;window['<%= HtmlUtil.escapeJS(name) %>Config'] = function() {
	var alloyEditor = CKEDITOR.instances['<%= HtmlUtil.escapeJS(name) %>'];

	var config = alloyEditor.config;

	config.allowedContent = true;

	<%
	String contentsLanguageDir = LanguageUtil.get(contentsLocale, "lang.dir");
	%>

	config.contentsLangDirection = '<%= HtmlUtil.escapeJS(contentsLanguageDir) %>';

	config.contentsLanguage = '<%= contentsLanguageId.replace("iw_", "he_") %>';

	alloyEditor.config.removePlugins = 'contextmenu,elementspath,link,liststyle,magicline,resize,tabletools,toolbar';
	alloyEditor.config.extraPlugins = 'dropimages,linktooltip,placeholder,selectionregion,uicore,uiloader';

	alloyEditor.config.title = false;

	alloyEditor.config.placeholderClass = 'alloy-editor-placeholder';

	alloyEditor.config.bodyClass = 'alloy-editor';

	config.language = '<%= languageId.replace("iw_", "he_") %>';

	var toolbars = {
		default: {
			add: ['imageselector'],
			image: ['left', 'right'],
			styles: ['strong', 'em', 'u', 'h1', 'h2', 'a', 'twitter']
		},
		none: {}
	}

	alloyEditor.config.toolbars = toolbars['<%= HtmlUtil.escapeJS(toolbarSet) %>'] || toolbars.default;
};

window['<%= HtmlUtil.escapeJS(name) %>Config']();