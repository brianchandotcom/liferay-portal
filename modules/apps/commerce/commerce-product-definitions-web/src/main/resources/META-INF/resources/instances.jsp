<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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