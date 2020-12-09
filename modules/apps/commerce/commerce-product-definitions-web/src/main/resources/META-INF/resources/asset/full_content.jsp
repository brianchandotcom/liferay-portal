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
		<h1><%= HtmlUtil.escape(cpDefinition.getName(languageId)) %></h1>

		<div class="lfr-asset-categories">
			<liferay-asset:asset-categories-summary
				className="<%= cpDefinition.getModelClassName() %>"
				classPK="<%= cpDefinition.getCPDefinitionId() %>"
			/>
		</div>

		<liferay-ui:tabs
			names="details,specs,skus"
			param="<%= String.valueOf(cpDefinition.getCPDefinitionId()) %>"
			refresh="<%= false %>"
			type="nav-tabs tabs"
		>
			<liferay-ui:section>
				<dl>

					<%
					String defaultImageThumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc();
					%>

					<c:if test="<%= Validator.isNotNull(defaultImageThumbnailSrc) %>">
						<div class="default-image">
							<img src="<%= defaultImageThumbnailSrc %>" />
						</div>
					</c:if>

					<%
					String description = cpDefinition.getDescription(languageId);
					%>

					<c:if test="<%= Validator.isNotNull(description) %>">
						<dt class="h5 uppercase">
							<liferay-ui:message key="description" />
						</dt>
						<dd class="h6">
							<%= description %>
						</dd>
					</c:if>

					<dt class="h5 uppercase">
						<liferay-ui:message key="created" />
					</dt>
					<dd class="h6">
						<%= HtmlUtil.escape(cpDefinition.getUserName()) %>
					</dd>
				</dl>

				<div class="lfr-asset-tags">
					<liferay-asset:asset-tags-summary
						className="<%= cpDefinition.getModelClassName() %>"
						classPK="<%= cpDefinition.getCPDefinitionId() %>"
						message="tags"
					/>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<dl>

					<%
					List<CPDefinitionSpecificationOptionValue> cpDefinitionSpecificationOptionValues = cpDefinition.getCPDefinitionSpecificationOptionValues();

					for (CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue : cpDefinitionSpecificationOptionValues) {
						CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();
					%>

						<dt class="h5 uppercase">
							<%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %>
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(cpDefinitionSpecificationOptionValue.getValue(languageId)) %>
						</dd>

					<%
					}
					%>

				</dl>
			</liferay-ui:section>

			<liferay-ui:section>

				<%
				List<CPInstance> cpInstances = cpDefinition.getCPInstances();

				for (CPInstance cpInstance : cpInstances) {
				%>

					<dt class="h5">
						<%= HtmlUtil.escape(cpInstance.getSku()) %>
					</dt>

				<%
				}
				%>

			</liferay-ui:section>
		</liferay-ui:tabs>
	</div>
</div>