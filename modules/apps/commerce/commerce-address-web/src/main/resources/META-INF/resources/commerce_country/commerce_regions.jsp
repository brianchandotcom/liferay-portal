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
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="commerceRegions"
	>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all", "active", "inactive"} %>'
				portletURL="<%= commerceRegionsDisplayContext.getPortletURL() %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= commerceRegionsDisplayContext.getOrderByCol() %>"
				orderByType="<%= commerceRegionsDisplayContext.getOrderByType() %>"
				orderColumns='<%= new String[] {"name", "priority"} %>'
				portletURL="<%= commerceRegionsDisplayContext.getPortletURL() %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= commerceRegionsDisplayContext.getPortletURL() %>"
				selectedDisplayStyle="list"
			/>

			<portlet:renderURL var="addCommerceRegionURL">
				<portlet:param name="mvcRenderCommandName" value="/commerce_country/edit_commerce_region" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="countryId" value="<%= String.valueOf(commerceRegionsDisplayContext.getCountryId()) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-region") %>'
					url="<%= addCommerceRegionURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteRegions();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

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
						cssClass="font-weight-bold table-cell-expand"
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

	<aui:script>
		function <portlet:namespace />deleteCommerceRegions() {
			if (
				confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-regions" />'
				)
			) {
				var form = window.document['<portlet:namespace />fm'];

				form[
					'<portlet:namespace />deleteRegionIds'
				].value = Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				);

				submitForm(form);
			}
		}
	</aui:script>
</c:if>