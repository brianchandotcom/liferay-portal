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
CPDefinitionLinkDisplayContext cpDefinitionLinkDisplayContext = (CPDefinitionLinkDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionLinkDisplayContext.getCPDefinition();
long cpDefinitionId = cpDefinitionLinkDisplayContext.getCPDefinitionId();
%>

<c:if test="<%= CommerceCatalogPermission.contains(permissionChecker, cpDefinition, ActionKeys.VIEW) %>">
	<portlet:actionURL name="/cp_definitions/edit_cp_definition_link" var="addCPDefinitionLinkURL" />

	<aui:form action="<%= addCPDefinitionLinkURL %>" cssClass="hide" name="addCPDefinitionLinkFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />
		<aui:input name="cpDefinitionIds" type="hidden" value="" />
		<aui:input name="type" type="hidden" value="" />
	</aui:form>

	<div class="pt-4" id="<portlet:namespace />productDefinitionLinksContainer">
		<portlet:actionURL name="/cp_definitions/edit_cp_definition" var="editProductDefinitionLinksActionURL" />

		<aui:form action="<%= editProductDefinitionLinksActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />
			<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

			<frontend-data-set:classic-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"cpDefinitionId", String.valueOf(cpDefinitionLinkDisplayContext.getCPDefinitionId())
					).build()
				%>'
				creationMenu="<%= cpDefinitionLinkDisplayContext.getCreationMenu() %>"
				dataProviderKey="<%= CommerceProductFDSNames.PRODUCT_LINKS %>"
				formName="fm"
				id="<%= CommerceProductFDSNames.PRODUCT_LINKS %>"
				itemsPerPage="<%= 10 %>"
				style="stacked"
			/>
		</aui:form>
	</div>

	<aui:script sandbox="<%= true %>">
		const eventHandlers = [];
		let eventHandler;

		<%
		for (String type : cpDefinitionLinkDisplayContext.getCPDefinitionLinkTypes()) {
		%>

			eventHandler = Liferay.on(
				'<portlet:namespace />addCommerceProductDefinitionLink<%= HtmlUtil.escapeJS(type) %>',
				() => {
					Liferay.Util.openSelectionModal({
						multiple: true,
						onSelect: (selectedItems) => {
							if (!selectedItems || !selectedItems.length) {
								return;
							}

							const cpDefinitionIdsInput = document.getElementById(
								'<portlet:namespace />cpDefinitionIds'
							);

							if (cpDefinitionIdsInput) {
								const values = selectedItems.map((item) => item.value);

								cpDefinitionIdsInput.value = values.join(',');
							}

							const typeInput = document.getElementById(
								'<portlet:namespace />type'
							);

							if (typeInput) {
								typeInput.value = '<%= HtmlUtil.escapeJS(type) %>';
							}

							const form = document.getElementById(
								'<portlet:namespace />addCPDefinitionLinkFm'
							);

							if (form) {
								submitForm(form);
							}
						},
						title:
							'<liferay-ui:message arguments="<%= HtmlUtil.escapeJS(cpDefinition.getName(languageId)) %>" key="add-new-product-to-x" />',
						url:
							'<%= cpDefinitionLinkDisplayContext.getItemSelectorUrl(type) %>',
					});
				}
			);

			eventHandlers.push(eventHandler);

		<%
		}
		%>

		Liferay.on('destroyPortlet', () => {
			eventHandlers.forEach((eventHandler) => {
				eventHandler.detach();
			});
		});
	</aui:script>
</c:if>