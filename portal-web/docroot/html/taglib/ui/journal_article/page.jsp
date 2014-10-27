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

<%@ include file="/html/taglib/ui/journal_article/init.jsp" %>

<%
long articleResourcePrimKey = GetterUtil.getLong((String)request.getAttribute("liferay-ui:journal-article:articleResourcePrimKey"));
long groupId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:journal-article:groupId"));
String articleId = GetterUtil.getString((String)request.getAttribute("liferay-ui:journal-article:articleId"));
String ddmTemplateKey = GetterUtil.getString((String)request.getAttribute("liferay-ui:journal-article:ddmTemplateKey"));
String languageId = GetterUtil.getString((String)request.getAttribute("liferay-ui:journal-article:languageId"), LanguageUtil.getLanguageId(request));
int articlePage = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:journal-article:articlePage"));

if (articleResourcePrimKey > 0) {
	JournalArticleResource articleResource = JournalArticleResourceLocalServiceUtil.getArticleResource(articleResourcePrimKey);

	groupId = articleResource.getGroupId();
	articleId = articleResource.getArticleId();
}

PortletRequestModel portletRequestModel = (PortletRequestModel)request.getAttribute("liferay-ui:journal-article:portletRequestModel");

if ((portletRequestModel == null) && (portletRequest != null) && (portletResponse != null)) {
	portletRequestModel = new PortletRequestModel(portletRequest, portletResponse);
}

JournalArticleDisplay articleDisplay = JournalContentUtil.getDisplay(groupId, articleId, ddmTemplateKey, null, languageId, articlePage, portletRequestModel, themeDisplay);
%>

<c:if test="<%= articleDisplay != null %>">

	<%
	boolean showTitle = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:journal-article:showTitle"));
	%>

	<c:if test="<%= showTitle %>">
		<h3 class="journal-content-title"><%= articleDisplay.getTitle() %></h3>
	</c:if>

	<%
	boolean showAvailableLocales = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:journal-article:showAvailableLocales"));
	%>

	<c:if test="<%= showAvailableLocales %>">

		<%
		String[] availableLocales = articleDisplay.getAvailableLocales();
		%>

		<c:if test="<%= availableLocales.length > 1 %>">
			<div>
				<br />

				<liferay-ui:language displayStyle="<%= 0 %>" languageIds="<%= availableLocales %>" />
			</div>
		</c:if>
	</c:if>

	<%= RuntimePageUtil.processXML(request, response, articleDisplay.getContent()) %>
</c:if>