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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if (article != null) {
	if ((article.getExpirationDate() != null) && !article.isExpired()) {
		neverExpire = false;
	}
}

boolean neverReview = ParamUtil.getBoolean(request, "neverReview", true);

if (article != null) {
	if (article.getReviewDate() != null) {
		neverReview = false;
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="schedule" />

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<h3><liferay-ui:message key="schedule" /></h3>

<liferay-ui:error exception="<%= ArticleDisplayDateException.class %>" message="please-enter-a-valid-display-date" />
<liferay-ui:error exception="<%= ArticleExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />

<aui:fieldset>
	<aui:input formName="fm1" name="displayDate" />

	<aui:input checkBoxLabel="neverExpire" disabled="<%= neverExpire %>" formName="fm1" name="expirationDate" />

	<aui:input checkBoxLabel="neverReview" disabled="<%= neverReview %>" formName="fm1" name="reviewDate" />
</aui:fieldset>