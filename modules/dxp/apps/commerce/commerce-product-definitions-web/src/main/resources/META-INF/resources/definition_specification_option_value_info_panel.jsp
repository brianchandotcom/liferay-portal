<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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