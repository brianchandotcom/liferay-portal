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
List<CommerceProductDefinitionOptionValueRel> commerceProductDefinitionOptionValueRels = (List<CommerceProductDefinitionOptionValueRel>)request.getAttribute(CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION_OPTION_VALUE_RELS);

if (ListUtil.isEmpty(commerceProductDefinitionOptionValueRels)) {
	commerceProductDefinitionOptionValueRels = new ArrayList<CommerceProductDefinitionOptionValueRel>();
}
%>

<c:choose>
	<c:when test="<%= commerceProductDefinitionOptionValueRels.size() == 1 %>">

		<%
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel = commerceProductDefinitionOptionValueRels.get(0);

		request.setAttribute("commerce_product_definition_option_value_rel_info_panel.jsp-entry", commerceProductDefinitionOptionValueRel);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include
						page="/commerce_product_definition_option_value_rel_action.jsp"
						servletContext="<%= application %>"
					/>
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(commerceProductDefinitionOptionValueRel.getTitle(languageId)) %></h4>
		</div>

		<aui:nav-bar markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(commerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionValueRelId())) %>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= commerceProductDefinitionOptionValueRels.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>