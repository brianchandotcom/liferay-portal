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
CommerceRegionsDisplayContext commerceRegionsDisplayContext = (CommerceRegionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceRegionsDisplayContext.hasPermission(ActionKeys.MANAGE_COUNTRIES) %>">
	<clay:management-toolbar
		managementToolbarDisplayContext="<%= new CommerceRegionsManagementToolbarDisplayContext(commerceRegionsDisplayContext, request, liferayPortletRequest, liferayPortletResponse) %>"
		propsTransformer="js/CommerceRegionsManagementToolbarPropsTransformer"
	/>

	<div class="container-fluid container-fluid-max-xl">
		<portlet:actionURL name="/commerce_country/edit_commerce_region" var="editCommerceRegionActionURL" />

		<aui:form action="<%= editCommerceRegionActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteRegionIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceRegions"
				searchContainer="<%= commerceRegionsDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Region"
					keyProperty="regionId"
					modelVar="region"
				>
					<liferay-ui:search-container-column-text
						cssClass="font-weight-bold important table-cell-expand"
						href='<%=
							PortletURLBuilder.createRenderURL(
								renderResponse
							).setMVCRenderCommandName(
								"/commerce_country/edit_commerce_region"
							).setRedirect(
								currentURL
							).setParameter(
								"countryId", region.getCountryId()
							).setParameter(
								"regionId", region.getRegionId()
							).buildPortletURL()
						%>'
						property="name"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						property="regionCode"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="active"
					>
						<c:choose>
							<c:when test="<%= region.isActive() %>">
								<liferay-ui:icon
									cssClass="commerce-admin-icon-check"
									icon="check"
									markupView="lexicon"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:icon
									cssClass="commerce-admin-icon-times"
									icon="times"
									markupView="lexicon"
								/>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="priority"
						property="position"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/commerce_region_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</c:if>