<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:cssClass"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:icon-menu:data");
String direction = (String)request.getAttribute("liferay-ui:icon-menu:direction");
String dropdownCssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:dropdownCssClass"));
String icon = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:icon"));
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:id"));
String message = (String)request.getAttribute("liferay-ui:icon-menu:message");
String triggerAriaLabel = (String)request.getAttribute("liferay-ui:icon-menu:triggerAriaLabel");
boolean scroll = GetterUtil.getBoolean(request.getAttribute("liferay-ui:icon-menu:scroll"));
String triggerCssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:triggerCssClass"));
String triggerLabel = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:triggerLabel"));
String triggerType = GetterUtil.getString(request.getAttribute("liferay-ui:icon-menu:triggerType"));

if (Validator.isNull(icon)) {
	icon = "ellipsis-v";
}
%>

<div class="dropdown lfr-icon-menu <%= cssClass %>" <%= AUIUtil.buildData(data) %>>
	<c:choose>
		<c:when test='<%= triggerType.equals("button") %>'>
			<button aria-expanded="false" aria-haspopup="true" class="btn btn-monospaced btn-secondary direction-<%= direction %> dropdown-toggle <%= triggerCssClass %>" id="<%= id %>" <%= ((message != null) && !message.isEmpty()) ? "title=\"" + message + "\"" : StringPool.BLANK %> type="button">
				<aui:icon cssClass="inline-item" image="<%= icon %>" markupView="lexicon" />

				<c:if test="<%= Validator.isNotNull(triggerLabel) %>">
					<c:choose>
						<c:when test="<%= Validator.isNotNull(triggerAriaLabel) %>">
							<span aria-label="<%= triggerAriaLabel %>" class="btn-section"><%= triggerLabel %></span>
						</c:when>
						<c:otherwise>
							<span class="btn-section"><%= triggerLabel %></span>
						</c:otherwise>
					</c:choose>
				</c:if>
			</button>
		</c:when>
		<c:otherwise>
			<a aria-expanded="false" aria-haspopup="true" class="component-action direction-<%= direction %> dropdown-toggle <%= triggerCssClass %>" href="javascript:void(0);" id="<%= id %>" role="button" <%= ((message != null) && !message.isEmpty()) ? "title=\"" + message + "\"" : StringPool.BLANK %>>
				<aui:icon image="<%= icon %>" markupView="lexicon" />
			</a>
		</c:otherwise>
	</c:choose>

	<aui:script position="inline" use="liferay-menu">
		Liferay.Menu.register('<%= id %>');
	</aui:script>

	<c:choose>
		<c:when test="<%= scroll %>">
			<div class="dropdown-menu dropdown-menu-<%= direction %> <%= dropdownCssClass %>">
				<ul class="inline-scroller">
		</c:when>
		<c:otherwise>
			<ul class="dropdown-menu dropdown-menu-<%= direction %> <%= dropdownCssClass %>">
		</c:otherwise>
	</c:choose>