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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.search.insights.display.context.SearchInsightsDisplayContext" %>

<portlet:defineObjects />

<%
SearchInsightsDisplayContext searchInsightsDisplayContext = (SearchInsightsDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));
%>

<style>
	<!--
	.full-query {
		font-size: x-small;
	}
	-->
</style>

<c:choose>
	<c:when test="<%= !Validator.isBlank(searchInsightsDisplayContext.getHelpMessage()) %>">
		<div class="alert alert-info">
			<%= HtmlUtil.escape(searchInsightsDisplayContext.getHelpMessage()) %>
		</div>
	</c:when>
	<c:otherwise>
		<div class="full-query">
			<liferay-ui:panel-container
				extended="<%= true %>"
				id='<%= renderResponse.getNamespace() + "insightsPanelContainer" %>'
				markupView="lexicon"
				persistState="<%= true %>"
			>
				<liferay-ui:panel
					collapsible="<%= true %>"
					id='<%= renderResponse.getNamespace() + "insightsRequestPanel" %>'
					markupView="lexicon"
					persistState="<%= true %>"
					title="request-string"
				>
					<code>
						<%= HtmlUtil.escape(searchInsightsDisplayContext.getRequestString()) %>
					</code>
				</liferay-ui:panel>

				<liferay-ui:panel
					collapsible="<%= true %>"
					id='<%= renderResponse.getNamespace() + "insightsResponsePanel" %>'
					markupView="lexicon"
					persistState="<%= true %>"
					title="response-string"
				>
					<code>
						<%= HtmlUtil.escape(searchInsightsDisplayContext.getResponseString()) %>
					</code>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</div>
	</c:otherwise>
</c:choose>