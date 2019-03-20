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