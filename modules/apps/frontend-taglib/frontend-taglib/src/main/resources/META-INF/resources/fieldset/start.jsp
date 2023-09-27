<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<fieldset class="<%= collapsible ? "panel" : StringPool.BLANK %> <%= cssClass %>" <%= disabled ? "disabled" : StringPool.BLANK %> <%= Validator.isNotNull(id) ? "id=\"" + id + "\"" : StringPool.BLANK %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
	<c:choose>
		<c:when test="<%= Validator.isNotNull(label) %>">
			<liferay-util:buffer
				var="header"
			>
				<liferay-ui:message key="<%= label %>" localizeKey="<%= localizeLabel %>" />

				<c:if test="<%= Validator.isNotNull(helpMessage) %>">
					<liferay-ui:icon-help message="<%= helpMessage %>" />
				</c:if>
			</liferay-util:buffer>

			<c:choose>
				<c:when test="<%= collapsible %>">
					<a aria-controls="<%= id %>Content" aria-expanded="<%= !collapsed %>" class="collapse-icon <%= collapsed ? "collapsed" : StringPool.BLANK %> sheet-subtitle" data-toggle="liferay-collapse" href="#<%= id %>Content" id="<%= id %>Toggle" role="tab">
						<span>
							<%= header %>
						</span>

						<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

						<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
					</a>
				</c:when>
				<c:otherwise>
					<legend class="fieldset-legend">
						<h3 class="legend sheet-subtitle"><%= header %></h3>
					</legend>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<legend class="sr-only">
				<%= HtmlUtil.escape(portletDisplay.getTitle()) %>
			</legend>
		</c:otherwise>
	</c:choose>

	<div aria-labelledby="<%= id %>Toggle" class="<%= !collapsed ? "show" : StringPool.BLANK %> <%= collapsible ? "panel-collapse collapse" : StringPool.BLANK %> <%= column ? "row" : StringPool.BLANK %>" id="<%= id %>Content" role="tabpanel">
		<div class="<%= collapsible ? "panel-body" : StringPool.BLANK %>">