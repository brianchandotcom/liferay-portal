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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.search.insights.display.context.SearchInsightsDisplayContext" %>

<portlet:defineObjects />

<%
SearchInsightsDisplayContext searchInsightsDisplayContext = (SearchInsightsDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

String insightsRequestId = liferayPortletResponse.getNamespace() + "insightsRequest";
String insightsResponseId = liferayPortletResponse.getNamespace() + "insightsResponse";
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
				id='<%= liferayPortletResponse.getNamespace() + "insightsPanelContainer" %>'
				markupView="lexicon"
				persistState="<%= true %>"
			>
				<liferay-ui:panel
					collapsible="<%= true %>"
					id='<%= liferayPortletResponse.getNamespace() + "insightsRequestPanel" %>'
					markupView="lexicon"
					persistState="<%= true %>"
					title="request-string"
				>
					<clay:button
						displayType="secondary"
						icon="copy"
						label="copy-to-clipboard"
						onClick='<%= liferayPortletResponse.getNamespace() + "copyToClipboard('" + insightsRequestId + "');" %>'
						small="<%= true %>"
					/>

					<div class="codemirror-editor-wrapper">
						<textarea readonly id="<%= insightsRequestId %>"><%= HtmlUtil.escape(searchInsightsDisplayContext.getRequestString()) %></textarea>
					</div>

					<liferay-frontend:component
						context='<%=
							HashMapBuilder.<String, Object>put(
								"id", insightsRequestId
							).build()
						%>'
						module="js/CodeMirrorTextArea"
					/>
				</liferay-ui:panel>

				<liferay-ui:panel
					collapsible="<%= true %>"
					id='<%= liferayPortletResponse.getNamespace() + "insightsResponsePanel" %>'
					markupView="lexicon"
					persistState="<%= true %>"
					title="response-string"
				>
					<clay:button
						displayType="secondary"
						icon="copy"
						label="copy-to-clipboard"
						onClick='<%= liferayPortletResponse.getNamespace() + "copyToClipboard('" + insightsResponseId + "');" %>'
						small="<%= true %>"
					/>

					<div class="codemirror-editor-wrapper">
						<textarea readonly id="<%= insightsResponseId %>"><%= HtmlUtil.escape(searchInsightsDisplayContext.getResponseString()) %></textarea>
					</div>

					<liferay-frontend:component
						context='<%=
							HashMapBuilder.<String, Object>put(
								"id", insightsResponseId
							).build()
						%>'
						module="js/CodeMirrorTextArea"
					/>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</div>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />copyToClipboard(id) {
		const text = document.getElementById(id).value;

		navigator.clipboard.writeText(text).then(
			() => {
				Liferay.Util.openToast({
					message: '<liferay-ui:message key="copied-to-clipboard" />',
					type: 'success',
				});
			},
			() => {
				Liferay.Util.openToast({
					message:
						'<liferay-ui:message key="an-unexpected-error-occurred" />',
					type: 'danger',
				});
			}
		);
	}
</aui:script>