<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionPricingClassDisplayContext cpDefinitionPricingClassDisplayContext = (CPDefinitionPricingClassDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionPricingClassDisplayContext.getCPDefinition();

CProduct cProduct = cpDefinition.getCProduct();
%>

<c:if test="<%= cpDefinitionPricingClassDisplayContext.hasPermission(permissionChecker, cpDefinition, ActionKeys.VIEW) %>">
	<div class="pt-4" id="<portlet:namespace />productPricingClassRelsContainer"><portlet:actionURL name="/cp_definitions/edit_cp_definition" var="editProductDefinitionProductGroupsActionURL" />

		<aui:form action="<%= editProductDefinitionProductGroupsActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateDefinitionPricingClasses" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionPricingClassDisplayContext.getCPDefinitionId() %>" />
			<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />
			<div id="item-finder-root"></div>

			<liferay-frontend:component
				context='<%=
					HashMapBuilder.<String, Object>put(
						"portletId", portletDisplay.getRootPortletId()
					).put(
						"pricingFDSName", CommercePricingFDSNames.PRODUCT_PRICING_CLASSES
					).put(
						"productExternalReferenceCode", cProduct.getExternalReferenceCode()
					).put(
						"productId", cpDefinition.getCProductId()
					).put(
						"spritemap", themeDisplay.getPathThemeSpritemap()
					).build()
				%>'
				module="{cpDefinitionPricingClasses} from commerce-pricing-web"
			/>

			<commerce-ui:panel
				bodyClasses="p-0"
				elementClasses="mt-4"
				title='<%= LanguageUtil.get(request, "product-groups") %>'
			>
				<frontend-data-set:classic-display
					contextParams='<%=
						HashMapBuilder.<String, String>put(
							"cpDefinitionId", String.valueOf(cpDefinitionPricingClassDisplayContext.getCPDefinitionId())
						).build()
					%>'
					dataProviderKey="<%= CommercePricingFDSNames.PRODUCT_PRICING_CLASSES %>"
					id="<%= CommercePricingFDSNames.PRODUCT_PRICING_CLASSES %>"
					itemsPerPage="<%= 10 %>"
					selectedItemsKey="pricingClassId"
				/>
			</commerce-ui:panel>
		</aui:form>
	</div>
</c:if>