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

<%@ include file="/html/taglib/ui/journal_content_search/init.jsp" %>

<%
boolean showListed = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:journal-content-search:showListed"));
String targetPortletId = (String)request.getAttribute("liferay-ui:journal-content-search:targetPortletId");
String type = (String)request.getAttribute("liferay-ui:journal-content-search:type");

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

<div class="form-search">
	<liferay-ui:input-search name="keywords" placeholder='<%= LanguageUtil.get(locale, "keywords") %>' />
</div>