<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePricingClassCPDefinitionDisplayContext commercePricingClassCPDefinitionDisplayContext = (CommercePricingClassCPDefinitionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePricingClass commercePricingClass = commercePricingClassCPDefinitionDisplayContext.getCommercePricingClass();

long commercePricingClassId = commercePricingClass.getCommercePricingClassId();

boolean hasPermission = commercePricingClassCPDefinitionDisplayContext.hasPermission();
%>

<portlet:actionURL name="/commerce_pricing_classes/edit_commerce_pricing_class" var="editCommercePricingClassActionURL" />

<aui:form action="<%= editCommercePricingClassActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<c:if test="<%= hasPermission %>">
		<div class="row">
			<div class="col-12 pt-4">
				<div id="item-finder-root"></div>

				<liferay-frontend:component
					context='<%=
						HashMapBuilder.<String, Object>put(
							"commercePricingClassId", commercePricingClass.getCommercePricingClassId()
						).put(
							"portletId", portletDisplay.getRootPortletId()
						).put(
							"pricingClassExternalReferenceCode", commercePricingClass.getExternalReferenceCode()
						).put(
							"pricingFDSName", CommercePricingFDSNames.PRICING_CLASSES_PRODUCT_DEFINITIONS
						).put(
							"spritemap", themeDisplay.getPathThemeSpritemap()
						).build()
					%>'
					module="{pricingClassesProducts} from commerce-pricing-web"
				/>
			</div>

			<div class="col-12">
				<commerce-ui:panel
					bodyClasses="p-0"
					title='<%= LanguageUtil.get(request, "products") %>'
				>
					<frontend-data-set:classic-display
						contextParams='<%=
							HashMapBuilder.<String, String>put(
								"commercePricingClassId", String.valueOf(commercePricingClassId)
							).build()
						%>'
						dataProviderKey="<%= CommercePricingFDSNames.PRICING_CLASSES_PRODUCT_DEFINITIONS %>"
						formName="fm"
						id="<%= CommercePricingFDSNames.PRICING_CLASSES_PRODUCT_DEFINITIONS %>"
						itemsPerPage="<%= 10 %>"
					/>
				</commerce-ui:panel>
			</div>
		</div>
	</c:if>
</aui:form>