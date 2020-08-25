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

<%@ include file="/management_bar_filter/init.jsp" %>

<%
boolean disabled = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:management-bar-filter:disabled"));
String label = (String)request.getAttribute("liferay-frontend:management-bar-filter:label");
List<ManagementBarFilterItem> managementBarFilterItems = (List<ManagementBarFilterItem>)request.getAttribute("liferay-frontend:management-bar-filter:managementBarFilterItems");
String value = (String)request.getAttribute("liferay-frontend:management-bar-filter:value");
%>

<c:if test="<%= managementBarFilterItems.size() > 0 %>">
	<li class="dropdown <%= disabled ? "disabled" : StringPool.BLANK %>">
		<a aria-expanded="true" class="dropdown-toggle" data-qa-id="filter<%= Validator.isNotNull(label) ? label : StringPool.BLANK %>" data-toggle="<%= disabled ? StringPool.BLANK : "dropdown" %>" href="javascript:;">
			<span class="management-bar-item-title">
				<c:if test="<%= Validator.isNotNull(label) %>">
					<liferay-ui:message key="<%= label %>" />:
				</c:if>

				<liferay-ui:message key="<%= HtmlUtil.escape(value) %>" />
			</span>

			<aui:icon image="caret-double-l" markupView="lexicon" />
		</a>

		<ul class="dropdown-menu" data-qa-id="filter<%= Validator.isNotNull(label) ? label : StringPool.BLANK %>Values">

			<%
			for (ManagementBarFilterItem managementBarFilterItem : managementBarFilterItems) {
			%>

				<li class="<%= managementBarFilterItem.isActive() ? "active" : StringPool.BLANK %>">
					<aui:a href="<%= managementBarFilterItem.getUrl() %>" id="<%= Validator.isNotNull(managementBarFilterItem.getId()) ? managementBarFilterItem.getId() : StringPool.BLANK %>" label="<%= managementBarFilterItem.getLabel() %>" />
				</li>

			<%
			}
			%>

		</ul>
	</li>
</c:if>