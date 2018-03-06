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
ManageUserAssociatedDataEntitiesDisplay manageUserAssociatedDataEntitiesDisplay = (ManageUserAssociatedDataEntitiesDisplay)request.getAttribute(UserAssociatedDataWebKeys.MANAGE_USER_ASSOCIATED_DATA_ENTITIES_DISPLAY);

UADEntityDisplay uadEntityDisplay = manageUserAssociatedDataEntitiesDisplay.getUADEntityDisplay();

SearchContainer uadEntitySearchContainer = manageUserAssociatedDataEntitiesDisplay.getSearchContainer();
%>

<clay:navigation-bar
	items="<%= manageUserAssociatedDataEntitiesDisplay.getNavigationItems() %>"
/>

<liferay-frontend:management-bar
	searchContainerId="UADEntities"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="closed container-fluid container-fluid-max-xl sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= true %>" id="/user_associated_data/entity_type_sidebar" var="entityTypeSidebarURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= entityTypeSidebarURL %>"
		searchContainerId="UADEntities"
	>
		<%@ include file="/user_associated_data_entity_type_sidebar.jspf" %>
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<liferay-ui:search-container
			emptyResultsMessage="no-entities-remain-of-this-type"
			id="UADEntities"
			searchContainer="<%= manageUserAssociatedDataEntitiesDisplay.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.user.associated.data.entity.UADEntity"
				escapedModel="<%= true %>"
				keyProperty="name"
				modelVar="uadEntity"
			>
				<liferay-ui:search-container-column-text
					name="entity-id"
					property="UADEntityId"
				/>

				<liferay-ui:search-container-column-text
					href="<%= uadEntityDisplay.getEditURL(uadEntity, liferayPortletRequest, liferayPortletResponse) %>"
					name="edit-url"
					value="<%= uadEntityDisplay.getEditURL(uadEntity, liferayPortletRequest, liferayPortletResponse) %>"
				/>

				<liferay-ui:search-container-column-text
					name="nonanonymizable-fields"
					value="<%= uadEntityDisplay.getUADEntityNonanonymizableFieldValues(uadEntity) %>"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/user_associated_data_entity_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</div>
</div>

<%@ include file="/action/confirm_action_js.jspf" %>