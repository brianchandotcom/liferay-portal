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
CPDefinition cpDefinition = (CPDefinition)request.getAttribute(CPWebKeys.CP_DEFINITION);

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
%>

<liferay-util:html-top
	outputKey="commerce_product_definitions_main_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css", portlet.getTimestamp()) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="portlet-commerce-product-definitions">
	<div class="entry-body">

		<%
		String description = cpDefinition.getDescription(languageId);
		%>

		<c:if test="<%= Validator.isNotNull(description) %>">
			<div class="entry-subtitle">
				<p><%= HtmlUtil.escape(description) %></p>
			</div>
		</c:if>

		<liferay-expando:custom-attributes-available
			className="<%= CPDefinition.class.getName() %>"
		>
			<liferay-expando:custom-attribute-list
				className="<%= CPDefinition.class.getName() %>"
				classPK="<%= (cpDefinition != null) ? cpDefinition.getCPDefinitionId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-expando:custom-attributes-available>
	</div>
</div>