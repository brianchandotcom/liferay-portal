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
else if (collapsible) {
	boolean defaultState = collapsed;

	collapsed = GetterUtil.getBoolean(SessionClicks.get(request, id, null), defaultState);
}

if (Validator.isNull(cssClass)) {
	cssClass = collapsible ? "panel panel-unstyled" : StringPool.BLANK;
}

if (Validator.isNull(panelHeaderLinkCssClass)) {
	panelHeaderLinkCssClass = collapsible ? "sheet-subtitle" : StringPool.BLANK;
}
%>

<fieldset aria-labelledby="<%= id %>Title" class="<%= cssClass %>" <%= Validator.isNotNull(id) ? "id=\"" + id + "\"" : StringPool.BLANK %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> role="group">
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

		<c:choose>
			<c:when test="<%= collapsible %>">
				<a aria-controls="<%= id %>Content" aria-expanded="<%= !collapsed %>" class="collapse-icon collapse-icon-middle <%= panelHeaderLinkCssClass %> <%= collapsed ? "collapsed" : StringPool.BLANK %>" data-toggle="liferay-collapse" href="#<%= id %>Content" role="button">
					<span class="c-inner" tabindex="-1">
						<%= header %>
					</span>
				</a>
			</c:when>
			<c:otherwise>
				<%= header %>
			</c:otherwise>
		</c:choose>
	</c:if>

	<div aria-labelledby="<%= id %>Header" class="<%= !collapsed ? "show" : StringPool.BLANK %> <%= collapsible ? "panel-collapse collapse" : StringPool.BLANK %> <%= column ? "row" : StringPool.BLANK %>" id="<%= id %>Content" role="presentation">
		<div class="panel-body">