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