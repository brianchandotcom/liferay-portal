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

<%@ include file="/html/taglib/ui/journal_content_search/init.jsp" %>

<%
boolean showListed = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:journal-content-search:showListed"));
String targetPortletId = (String)request.getAttribute("liferay-ui:journal-content-search:targetPortletId");
String type = (String)request.getAttribute("liferay-ui:journal-content-search:type");

String defaultKeywords = LanguageUtil.get(pageContext, "search") + StringPool.TRIPLE_PERIOD;
String unicodeDefaultKeywords = UnicodeFormatter.toString(defaultKeywords);

String keywords = ParamUtil.getString(request, namespace + "keywords", defaultKeywords);

PortletURL portletURL = null;

if (portletResponse != null) {
	LiferayPortletResponse liferayPortletResponse = (LiferayPortletResponse)portletResponse;

	portletURL = liferayPortletResponse.createLiferayPortletURL(PortletKeys.JOURNAL_CONTENT_SEARCH, PortletRequest.RENDER_PHASE);
}
else {
	portletURL = new PortletURLImpl(request, PortletKeys.JOURNAL_CONTENT_SEARCH, plid, PortletRequest.RENDER_PHASE);
}

portletURL.setParameter("struts_action", "/journal_content_search/search");
portletURL.setParameter("showListed", String.valueOf(showListed));

if (Validator.isNotNull(targetPortletId)) {
	portletURL.setParameter("targetPortletId", targetPortletId);
}

if (Validator.isNotNull(type)) {
	portletURL.setParameter("type", type);
}

portletURL.setPortletMode(PortletMode.VIEW);
portletURL.setWindowState(WindowState.MAXIMIZED);
%>

<form action="<%= HtmlUtil.escape(portletURL.toString()) %>" class="form" method="post" name="<%= namespace %>fm" onSubmit="submitForm(this); return false;">

<%
String taglibOnBlur = "if (this.value == '') { this.value = '" + unicodeDefaultKeywords + "'; }";
String taglibOnFocus = "if (this.value == '" + unicodeDefaultKeywords + "') { this.value = ''; }";
%>

<aui:input cssClass="lfr-search-keywords" inlineField="<%= true %>" label="" name="keywords" onBlur="<%= taglibOnBlur %>" onFocus="<%= taglibOnFocus %>" size="30" title="search-web-content" type="text" value="<%= HtmlUtil.escape(keywords) %>" />

<aui:input alt="search" cssClass="lfr-search-button" inlineField="<%= true %>" label="" name="search" src='<%= themeDisplay.getPathThemeImages() + "/common/search.png" %>' type="image" />