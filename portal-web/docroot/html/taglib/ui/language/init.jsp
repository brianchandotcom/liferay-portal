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

<%
if (Validator.isNull(namespace)) {
	namespace = PortalUtil.generateRandomKey(request, "taglib_ui_language_page") + StringPool.UNDERLINE;
}

String formName = (String)request.getAttribute("liferay-ui:language:formName");

String formAction = (String)request.getAttribute("liferay-ui:language:formAction");

if (Validator.isNull(formAction)) {
	formAction = themeDisplay.getPathMain() + "/portal/update_language?p_l_id=" + themeDisplay.getPlid() + "&redirect=" + currentURL;
}

boolean displayCurrentLocale = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:language:displayCurrentLocale"), true);
String displayStyle = GetterUtil.getString((String)request.getAttribute("liferay-ui:language:displayStyle"));
String languageId = GetterUtil.getString((String)request.getAttribute("liferay-ui:language:languageId"), LocaleUtil.toLanguageId(locale));
Locale[] locales = (Locale[])request.getAttribute("liferay-ui:language:locales");
String name = (String)request.getAttribute("liferay-ui:language:name");

Map langCounts = new HashMap();

for (int i = 0; i < locales.length; i++) {
	Integer count = (Integer)langCounts.get(locales[i].getLanguage());

	if (count == null) {
		count = new Integer(1);
	}
	else {
		count = new Integer(count.intValue() + 1);
	}

	langCounts.put(locales[i].getLanguage(), count);
}

Set<String> duplicateLanguages = new HashSet<String>();

for (int i = 0; i < locales.length; i++) {
	Integer count = (Integer)langCounts.get(locales[i].getLanguage());

	if (count.intValue() != 1) {
		duplicateLanguages.add(locales[i].getLanguage());
	}
}
%>