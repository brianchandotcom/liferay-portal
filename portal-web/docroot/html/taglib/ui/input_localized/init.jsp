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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.editor.Editor" %><%@
page import="com.liferay.taglib.ui.InputEditorTag" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_input_localized") + StringPool.UNDERLINE;

boolean autoFocus = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:autoFocus"));
boolean autoSize = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:autoSize"));
Set<Locale> availableLocales = (Set<Locale>)request.getAttribute("liferay-ui:input-localized:availableLocales");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:cssClass"));
String defaultLanguageId = (String)request.getAttribute("liferay-ui:input-localized:defaultLanguageId");
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:disabled"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ui:input-localized:dynamicAttributes");
String editorName = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:editorName"));
String fieldPrefix = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:fieldPrefix"));
String fieldPrefixSeparator = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:fieldPrefixSeparator"));
String id = HtmlUtil.getAUICompatibleId((String)request.getAttribute("liferay-ui:input-localized:id"));
boolean ignoreRequestValue = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:ignoreRequestValue"));
String languageId = (String)request.getAttribute("liferay-ui:input-localized:languageId");
String maxLength = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:maxLength"));
String name = (String)request.getAttribute("liferay-ui:input-localized:name");
String placeholder = (String)request.getAttribute("liferay-ui:input-localized:placeholder");
String toolbarSet = (String)request.getAttribute("liferay-ui:input-localized:toolbarSet");
String type = (String)request.getAttribute("liferay-ui:input-localized:type");
String xml = (String)request.getAttribute("liferay-ui:input-localized:xml");

Locale defaultLocale = null;

if (Validator.isNotNull(defaultLanguageId)) {
	defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);
}
else {
	defaultLocale = LocaleUtil.getSiteDefault();
	defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
}

String mainLanguageId = defaultLanguageId;

if (Validator.isNotNull(languageId)) {
	mainLanguageId = languageId;
}

Locale mainLocale = LocaleUtil.fromLanguageId(mainLanguageId);

String mainLanguageDir = LanguageUtil.get(mainLocale, "lang.dir");

String mainLanguageValue = LocalizationUtil.getLocalization(xml, mainLanguageId, false);

if (!ignoreRequestValue) {
	mainLanguageValue = ParamUtil.getString(request, name + StringPool.UNDERLINE + mainLanguageId, mainLanguageValue);
}

if (Validator.isNull(mainLanguageValue)) {
	mainLanguageValue = LocalizationUtil.getLocalization(xml, defaultLanguageId, true);
}

String fieldNamePrefix = StringPool.BLANK;
String fieldNameSuffix = StringPool.BLANK;

if (Validator.isNotNull(fieldPrefix)) {
	fieldNamePrefix = fieldPrefix + fieldPrefixSeparator;
	fieldNameSuffix = fieldPrefixSeparator;
}

String fieldSuffix = StringPool.BLANK;

if (!Validator.isNull(languageId)) {
	fieldSuffix = StringPool.UNDERLINE + mainLanguageId;
}

List<String> languageIds = new ArrayList<String>();

String fieldName = HtmlUtil.escapeAttribute(name + fieldSuffix);

Exception exception = (Exception)request.getAttribute("liferay-ui:error:exception");
String focusField = (String)request.getAttribute("liferay-ui:error:focusField");

Set<Locale> errorLocales = new HashSet<Locale>();

if ((exception != null) && fieldName.equals(focusField)) {
	if (exception instanceof LocalizedException) {
		LocalizedException le = (LocalizedException)exception;

		Map<Locale, Exception> localizedExceptionsMap = le.getLocalizedExceptionsMap();

		errorLocales = localizedExceptionsMap.keySet();
	}
}
%>