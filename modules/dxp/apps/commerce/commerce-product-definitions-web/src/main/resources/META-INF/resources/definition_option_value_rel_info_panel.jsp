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
List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels = (List<CPDefinitionOptionValueRel>)request.getAttribute(CPWebKeys.CP_DEFINITION_OPTION_VALUE_RELS);

if (cpDefinitionOptionValueRels == null) {
	cpDefinitionOptionValueRels = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= cpDefinitionOptionValueRels.size() == 1 %>">

		<%
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = cpDefinitionOptionValueRels.get(0);

		request.setAttribute("info_panel.jsp-entry", cpDefinitionOptionValueRel);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/definition_option_value_rel_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(cpDefinitionOptionValueRel.getName(languageId)) %></h4>
		</div>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId())) %>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= cpDefinitionOptionValueRels.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>