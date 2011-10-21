<%--
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
--%>

<%@ page import="com.liferay.portal.kernel.util.ContentTypes" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>
<%@ page import="com.liferay.portal.kernel.util.LocaleUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.PropsKeys" %>
<%@ page import="com.liferay.portal.util.PropsUtil" %>

<%@ page import="java.util.Locale" %>

<%
long plid = ParamUtil.getLong(request, "p_l_id");
String portletId = ParamUtil.getString(request, "p_p_id");
String mainPath = ParamUtil.getString(request, "p_main_path");
String doAsUserId = ParamUtil.getString(request, "doAsUserId");
String doAsGroupId = ParamUtil.getString(request, "doAsGroupId");
String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");

String connectorURL = HttpUtil.encodeURL(mainPath + "/portal/fckeditor?p_l_id=" + plid + "&p_p_id=" + HttpUtil.encodeURL(portletId) + "&doAsUserId=" + HttpUtil.encodeURL(doAsUserId) + "&doAsGroupId=" + HttpUtil.encodeURL(doAsGroupId));

String languageId = ParamUtil.getString(request, "languageId");

Locale locale = LocaleUtil.fromLanguageId(languageId);

languageId = locale.getLanguage();

response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
%>

FCKConfig.AutoDetectLanguage = false;

FCKConfig.DefaultLanguage = '<%= HtmlUtil.escape(languageId) %>' ;

FCKConfig.IncludeLatinEntities = false ;

FCKConfig.ToolbarSets["liferay"] = [
	['Style', 'FontSize', '-', 'TextColor', 'BGColor'],
	['Bold', 'Italic', 'Underline', 'StrikeThrough'],
	['Subscript', 'Superscript'],
	'/',
	['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteWord', '-', 'SelectAll', 'RemoveFormat'],
	['Find', 'Replace', 'SpellCheck'],
	['OrderedList', 'UnorderedList', '-', 'Outdent', 'Indent'],
	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull'],
	'/',
	['Source'],
	['Link', 'Unlink', 'Anchor'],
	['Image', 'Flash', 'Table', '-', 'Smiley', 'SpecialChar']
];

FCKConfig.ToolbarSets["liferay-article"] = [
	['Style', 'FontSize', '-', 'TextColor', 'BGColor'],
	['Bold', 'Italic', 'Underline', 'StrikeThrough'],
	['Subscript', 'Superscript'],
	'/',
	['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteWord', '-', 'SelectAll', 'RemoveFormat'],
	['Find', 'Replace', 'SpellCheck'],
	['OrderedList', 'UnorderedList', '-', 'Outdent', 'Indent'],
	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull'],
	'/',
	['Source'],
	['Link', 'Unlink', 'Anchor'],
	['Image', 'Flash', 'Table', '-', 'Smiley', 'SpecialChar', 'LiferayPageBreak']
];

FCKConfig.ToolbarSets["edit-in-place"] = [
	['Style'],
	['Bold', 'Italic', 'Underline', 'StrikeThrough'],
	['Subscript', 'Superscript', 'SpecialChar'],
	['Undo', 'Redo'],
	['SpellCheck'],
	['OrderedList', 'UnorderedList', '-', 'Outdent', 'Indent'], ['Source', 'RemoveFormat'],
];

FCKConfig.ToolbarSets["email"] = [
	['FontSize', 'TextColor', 'BGColor', '-', 'Bold', 'Italic', 'Underline', 'StrikeThrough'],
	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull'],
	['SpellCheck'],
	'/',
	['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteWord', '-', 'SelectAll', 'RemoveFormat'],
	['Source'],
	['Link', 'Unlink'],
	['Image']
];

FCKConfig.BackgroundBlockerColor = '#000' ;
FCKConfig.BackgroundBlockerOpacity = 0.70 ;

FCKConfig.BodyClass = 'html-editor <%= cssClasses %>' ;
FCKConfig.CustomStyles = {};
FCKConfig.StylesXmlPath = FCKConfig.EditorPath + 'fckstyles.xml' ;
FCKConfig.EditorAreaCSS = '<%= HtmlUtil.escape(cssPath) %>/main.css' ;

FCKConfig.LinkBrowserURL = FCKConfig.BasePath + "filemanager/browser/liferay/browser.html?Connector=<%= connectorURL %>";
FCKConfig.ImageBrowserURL = FCKConfig.BasePath + "filemanager/browser/liferay/browser.html?Type=Image&Connector=<%= connectorURL %>";
FCKConfig.FlashBrowser = false ;
FCKConfig.LinkUpload = false ;
FCKConfig.ImageUpload = false ;
FCKConfig.FlashUpload = false ;

var sOtherPluginPath = FCKConfig.BasePath.substr(0, FCKConfig.BasePath.length - 7) + 'editor/plugins/' ;

var _TOKEN_PAGE_BREAK = '<%= _TOKEN_PAGE_BREAK %>';

FCKConfig.Plugins.Add('liferaypagebreak', null, sOtherPluginPath ) ;

FCKConfig.ProtectedSource.Add(/<[\/]{0,1}(article|aside|audio|canvas|command|datalist|details|dialog|embed|figure|footer|header|hgroup|keygen|mark|meter|nav|output|progress|rp|rt|ruby|script|section|time|video).*?>/gi);

<%!
private static final String _TOKEN_PAGE_BREAK = PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_TOKEN_PAGE_BREAK);
%>