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

<pre class="m-4"><%= HtmlUtil.escape(article.getContent()) %></pre>

<aui:button-row>
	<clay:button
		displayType="secondary"
		label="close"
		onClick="Liferay.Util.getOpener().Liferay.fire('closeModal')"
	/>
</aui:button-row>