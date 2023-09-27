<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info/item/renderer/init.jsp" %>

<liferay-journal:journal-article
	article="<%= (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE) %>"
	ddmTemplateKey="<%= (String)request.getAttribute(WebKeys.JOURNAL_TEMPLATE_ID) %>"
	languageId="<%= themeDisplay.getLanguageId() %>"
/>