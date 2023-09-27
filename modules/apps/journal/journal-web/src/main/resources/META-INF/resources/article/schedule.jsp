<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, journalDisplayContext.getArticle());
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="schedule"
/>

<aui:model-context bean="<%= journalDisplayContext.getArticle() %>" model="<%= JournalArticle.class %>" />

<liferay-ui:error exception="<%= ArticleDisplayDateException.class %>" message="please-enter-a-valid-display-date" />
<liferay-ui:error exception="<%= ArticleExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />

<div class="schedule">
	<aui:input formName="fm1" name="displayDate" wrapperCssClass="mb-3" />

	<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= journalEditArticleDisplayContext.isNeverExpire() %>" formName="fm1" name="expirationDate" wrapperCssClass="expiration-date mb-3" />

	<aui:input dateTogglerCheckboxLabel="never-review" disabled="<%= journalEditArticleDisplayContext.isNeverReview() %>" formName="fm1" name="reviewDate" wrapperCssClass="mb-3 review-date" />
</div>