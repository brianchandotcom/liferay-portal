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
CPInstanceDisplayContext cpInstanceDisplayContext = (CPInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= CommerceCatalogPermission.contains(permissionChecker, cpInstanceDisplayContext.getCPDefinition(), ActionKeys.VIEW) %>">
	<div class="pt-4" id="<portlet:namespace />productInstancesContainer">
		<portlet:actionURL name="/cp_definitions/edit_cp_definition" var="editProductDefinitionInstancesActionURL" />

		<aui:form action="<%= editProductDefinitionInstancesActionURL %>" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="cpDefinitionId" type="hidden" value="<%= cpInstanceDisplayContext.getCPDefinitionId() %>" />
			<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

			<frontend-data-set:classic-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"cpDefinitionId", String.valueOf(cpInstanceDisplayContext.getCPDefinitionId())
					).build()
				%>'
				creationMenu="<%= cpInstanceDisplayContext.getCreationMenu() %>"
				dataProviderKey="<%= CommerceProductFDSNames.PRODUCT_INSTANCES %>"
				id="<%= CommerceProductFDSNames.PRODUCT_INSTANCES %>"
				itemsPerPage="<%= 10 %>"
				selectedItemsKey="cpinstanceId"
				style="stacked"
			/>
		</aui:form>
	</div>
</c:if>