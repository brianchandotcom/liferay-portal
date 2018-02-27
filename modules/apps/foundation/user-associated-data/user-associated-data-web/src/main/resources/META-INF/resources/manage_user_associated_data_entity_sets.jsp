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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

List<UADEntitySetComposite> uadEntitySetComposites = (List<UADEntitySetComposite>)request.getAttribute(UserAssociatedDataWebKeys.UAD_ENTITY_SET_COMPOSITES);
%>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<liferay-ui:header title="application-data-review" />

	<liferay-ui:search-container
		emptyResultsMessage="no-data-requires-anonymization"
		id="UADEntitySetComposite"
		iteratorURL="<%= currentURLObj %>"
	>
		<liferay-ui:search-container-results
			results="<%= uadEntitySetComposites %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.user.associated.data.web.internal.util.UADEntitySetComposite"
			escapedModel="<%= true %>"
			keyProperty="name"
			modelVar="uadEntitySetComposite"
		>
			<portlet:renderURL var="manageUserAssociatedDataEntitiesURL">
				<portlet:param name="mvcRenderCommandName" value="/user_associated_data/manage_user_associated_data_entities" />
				<portlet:param name="selUserId" value="<%= String.valueOf(selUserId) %>" />
				<portlet:param name="uadEntitySetName" value="<%= uadEntitySetComposite.getUADEntitySetName() %>" />
				<portlet:param name="uadRegistryKey" value="<%= uadEntitySetComposite.getDefaultRegistryKey() %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= manageUserAssociatedDataEntitiesURL %>"
				name="name"
				property="UADEntitySetName"
			/>

			<liferay-ui:search-container-column-text
				href="<%= manageUserAssociatedDataEntitiesURL %>"
				name="count"
				property="count"
			/>

			<liferay-ui:search-container-column-text
				href="<%= manageUserAssociatedDataEntitiesURL %>"
				name="status"
				property="statusLabel"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>