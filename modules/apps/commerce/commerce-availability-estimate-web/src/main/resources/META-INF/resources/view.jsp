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
CommerceAvailabilityEstimateDisplayContext commerceAvailabilityEstimateDisplayContext = (CommerceAvailabilityEstimateDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceAvailabilityEstimateDisplayContext.hasManageCommerceAvailabilityEstimatesPermission() %>">
	<clay:management-toolbar
		managementToolbarDisplayContext="<%= new CommerceAvailabilityEstimateManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, commerceAvailabilityEstimateDisplayContext.getSearchContainer()) %>"
		propsTransformer="js/CommerceAvailabilityEstimateManagementToolbarPropsTransformer"
	/>

	<div class="container-fluid container-fluid-max-xl">
		<portlet:actionURL name="/commerce_availability_estimate/edit_commerce_availability_estimate" var="editCommerceAvailabilityEstimateActionURL" />

		<aui:form action="<%= editCommerceAvailabilityEstimateActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteCommerceAvailabilityEstimateIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceAvailabilityEstimates"
				searchContainer="<%= commerceAvailabilityEstimateDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceAvailabilityEstimate"
					keyProperty="commerceAvailabilityEstimateId"
					modelVar="commerceAvailabilityEstimate"
				>
					<liferay-ui:search-container-column-text
						cssClass="font-weight-bold important table-cell-expand"
						href='<%=
							PortletURLBuilder.createRenderURL(
								renderResponse
							).setMVCRenderCommandName(
								"/commerce_availability_estimate/edit_commerce_availability_estimate"
							).setRedirect(
								currentURL
							).setParameter(
								"commerceAvailabilityEstimateId", commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId()
							).buildPortletURL()
						%>'
						name="title"
						value="<%= HtmlUtil.escape(commerceAvailabilityEstimate.getTitle(languageId)) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						property="priority"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-expand"
						name="modified-date"
						property="modifiedDate"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/availability_estimate_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</c:if>