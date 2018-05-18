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
List<CPDefinitionLink> cpDefinitionLinks = (List<CPDefinitionLink>)request.getAttribute(CPWebKeys.CP_DEFINITION_LINKS);

if (cpDefinitionLinks == null) {
	cpDefinitionLinks = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= cpDefinitionLinks.size() == 1 %>">

		<%
		CPDefinitionLink cpDefinitionLink = cpDefinitionLinks.get(0);

		request.setAttribute("info_panel.jsp-entry", cpDefinitionLink);

		CPDefinition cpDefinition2 = cpDefinitionLink.getCPDefinition2();
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/definition_link_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(cpDefinition2.getName(languageId)) %></h4>
		</div>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="product-id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(cpDefinition2.getCPDefinitionId())) %>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= cpDefinitionLinks.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>