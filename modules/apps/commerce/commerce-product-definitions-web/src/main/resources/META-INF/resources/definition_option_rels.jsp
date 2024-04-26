<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionOptionRelDisplayContext cpDefinitionOptionRelDisplayContext = (CPDefinitionOptionRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionOptionRelDisplayContext.getCPDefinition();
%>

<c:if test="<%= CommerceCatalogPermission.contains(permissionChecker, cpDefinitionOptionRelDisplayContext.getCPDefinition(), ActionKeys.VIEW) %>">
	<div class="pt-4" id="<portlet:namespace />productOptionRelsContainer">
		<div id="item-finder-root"></div>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"portletId", portletDisplay.getRootPortletId()
				).put(
					"productId", cpDefinition.getCProductId()
				).put(
					"productOptions", CommerceProductFDSNames.PRODUCT_OPTIONS
				).put(
					"spritemap", themeDisplay.getPathThemeSpritemap()
				).build()
			%>'
			module="{definitionOptionRels} from commerce-product-definitions-web"
		/>

		<commerce-ui:panel
			bodyClasses="p-0"
			elementClasses="mt-4"
			title='<%= LanguageUtil.get(request, "options") %>'
		>
			<portlet:actionURL name="/cp_definitions/edit_cp_definition" var="editProductDefinitionOptionRelsActionURL" />

			<aui:form action="<%= editProductDefinitionOptionRelsActionURL %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateDefinitionOptionRels" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionOptionRelDisplayContext.getCPDefinitionId() %>" />
				<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

				<frontend-data-set:classic-display
					contextParams='<%=
						HashMapBuilder.<String, String>put(
							"cpDefinitionId", String.valueOf(cpDefinitionOptionRelDisplayContext.getCPDefinitionId())
						).build()
					%>'
					dataProviderKey="<%= CommerceProductFDSNames.PRODUCT_OPTIONS %>"
					id="<%= CommerceProductFDSNames.PRODUCT_OPTIONS %>"
					itemsPerPage="<%= 10 %>"
					selectedItemsKey="cpdefinitionOptionRelId"
				/>
			</aui:form>
		</commerce-ui:panel>
	</div>
</c:if>