<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalArticle article = journalDisplayContext.getArticle();
%>

<aui:input inlineLabel="right" label="searchable" labelCssClass="simple-toggle-switch" name="indexable" type="toggle-switch" value="<%= (article != null) ? article.isIndexable() : true %>" />