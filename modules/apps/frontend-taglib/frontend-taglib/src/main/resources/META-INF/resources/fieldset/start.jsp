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

<%@ include file="/fieldset/init.jsp" %>

<%
if (Validator.isNull(label)) {
	collapsible = false;
	collapsed = false;
}
else if (collapsible) {
	boolean defaultState = collapsed;

	collapsed = GetterUtil.getBoolean(SessionClicks.get(request, id, null), defaultState);
}
%>

<fieldset aria-labelledby="<%= id %>Title" class="<%= collapsible ? "panel" : StringPool.BLANK %> <%= cssClass %>" <%= Validator.isNotNull(id) ? "id=\"" + id + "\"" : StringPool.BLANK %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> role="group">
	<c:if test="<%= Validator.isNotNull(label) %>">
		<liferay-util:buffer
			var="header"
		>
			<liferay-ui:message key="<%= label %>" localizeKey="<%= localizeLabel %>" />

			<c:if test="<%= Validator.isNotNull(helpMessage) %>">
				<liferay-ui:icon-help message="<%= helpMessage %>" />
			</c:if>

			<c:if test="<%= collapsible %>">
				<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

				<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
			</c:if>
		</liferay-util:buffer>

		<div class="panel-heading" id="<%= id %>Header" role="presentation">
			<div id="<%= id %>Title">
				<c:choose>
					<c:when test="<%= collapsible %>">
						<a aria-controls="<%= id %>Content" aria-expanded="<%= !collapsed %>" class="collapse-icon <%= collapsed ? "collapsed" : StringPool.BLANK %> sheet-subtitle" data-toggle="collapse" href="#<%= id %>Content" role="button">
							<%= header %>
						</a>
					</c:when>
					<c:otherwise>
						<h3 class="sheet-subtitle"><%= header %></h3>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:if>

	<div aria-labelledby="<%= id %>Header" class="<%= !collapsed ? "in" : StringPool.BLANK %> <%= collapsible ? "panel-collapse collapse" : StringPool.BLANK %> <%= column ? "row" : StringPool.BLANK %>" id="<%= id %>Content" role="presentation">
		<div class="<%= collapsible ? "panel-body" : StringPool.BLANK %>">