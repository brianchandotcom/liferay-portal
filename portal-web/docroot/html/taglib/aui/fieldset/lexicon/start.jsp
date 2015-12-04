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

<%@ include file="/html/taglib/aui/fieldset/init.jsp" %>

<%
if (Validator.isNull(label)) {
	collapsible = false;
	collapsed = false;
}
%>

<div aria-labelledby="<%= panelId %>Title" class="<%= collapsible ? "panel panel-default" : StringPool.BLANK %> <%= cssClass %>" <%= Validator.isNotNull(id) ? "id=\"" + namespace + id + "\"" : StringPool.BLANK %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> role="group">
	<c:if test="<%= Validator.isNotNull(label) %>">
		<liferay-util:buffer var="header">
			<liferay-ui:message key="<%= label %>" localizeKey="<%= localizeLabel %>" />

			<c:if test="<%= Validator.isNotNull(helpMessage) %>">
				<liferay-ui:icon-help message="<%= helpMessage %>" />
			</c:if>
		</liferay-util:buffer>

		<div class="panel-heading" id="<%= panelId %>Header" role="presentation">
			<div class="panel-title" id="<%= panelId %>Title">
				<c:choose>
					<c:when test="<%= collapsible %>">
						<a aria-controls="<%= panelId %>Content" aria-expanded="true" class="collapse-icon collapse-icon-middle <%= collapsed ? "collapsed" : StringPool.BLANK %>" data-toggle="collapse" href="#<%= panelId %>Content" role="button">
							<%= header %>
						</a>
					</c:when>
					<c:otherwise>
						<%= header %>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:if>

	<div aria-labelledby="<%= panelId %>Header" class="<%= !collapsed ? "in" : StringPool.BLANK %> <%= collapsible ? "panel-collapse collapse" : StringPool.BLANK %> <%= column ? "row" : StringPool.BLANK %>" id="<%= panelId %>Content" role="presentation">
		<div class="panel-body">