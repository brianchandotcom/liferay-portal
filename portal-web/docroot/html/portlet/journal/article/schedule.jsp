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

	<aui:input disabled="<%= neverExpire %>" formName="fm1" name="expirationDate" />

	<%
	String taglibNeverExpireOnClick = renderResponse.getNamespace() + "disableInputDate('expirationDate', this.checked);";
	%>

	<aui:input label="never-auto-expire" name="neverExpire" onClick="<%= taglibNeverExpireOnClick %>" type="checkbox" value="<%= neverExpire %>" />

	<aui:input disabled="<%= neverReview %>" formName="fm1" name="reviewDate" />

	<%
	String taglibNeverReviewOnClick = renderResponse.getNamespace() + "disableInputDate('reviewDate', this.checked);";
	%>

	<aui:input name="neverReview" onClick="<%= taglibNeverReviewOnClick %>" type="checkbox" value="<%= neverReview %>" />
</aui:fieldset>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />disableInputDate',
		function(date, checked) {
			var A = AUI();

			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Month"].disabled = checked;
			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Day"].disabled = checked;
			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Year"].disabled = checked;
			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Hour"].disabled = checked;
			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Minute"].disabled = checked;
			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "AmPm"].disabled = checked;

			var calendarWidget = A.Widget.getByNode(document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Month"]);

			if (calendarWidget) {
				calendarWidget.set('disabled', checked);
			}
		},
		['aui-base']
	);
</aui:script>