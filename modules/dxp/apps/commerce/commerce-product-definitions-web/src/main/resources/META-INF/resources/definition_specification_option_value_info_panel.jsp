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

<%@ include file="/init.jsp" %>

<%
List<CPDefinitionSpecificationOptionValue> cpDefinitionSpecificationOptionValues = (List<CPDefinitionSpecificationOptionValue>)request.getAttribute(CPWebKeys.CP_DEFINITION_SPECIFICATION_OPTION_VALUES);

if (cpDefinitionSpecificationOptionValues == null) {
	cpDefinitionSpecificationOptionValues = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= cpDefinitionSpecificationOptionValues.size() == 1 %>">

		<%
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue = cpDefinitionSpecificationOptionValues.get(0);

		CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();

		request.setAttribute("info_panel.jsp-entry", cpDefinitionSpecificationOptionValue);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/definition_specification_option_value_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %></h4>
		</div>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(cpDefinitionSpecificationOptionValue.getCPDefinitionSpecificationOptionValueId())) %>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= cpDefinitionSpecificationOptionValues.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>