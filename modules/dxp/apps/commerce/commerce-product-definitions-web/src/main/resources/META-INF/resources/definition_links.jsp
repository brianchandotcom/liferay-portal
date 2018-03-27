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

SearchContainer<CPDefinitionLink> cpDefinitionLinkSearchContainer = cpDefinitionLinkDisplayContext.getSearchContainer();

String type = cpDefinitionLinkDisplayContext.getType();

PortletURL portletURL = cpDefinitionLinkDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpDefinitionLinks"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= cpDefinitionLinkDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= cpDefinitionLinkDisplayContext.getDisplayStyle() %>"
		/>

		<portlet:actionURL name="editCPDefinitionLink" var="addCPDefinitionLinkURL" />

		<aui:form action="<%= addCPDefinitionLinkURL %>" cssClass="hide" name="addCPDefinitionLinkFm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />
			<aui:input name="cpDefinitionIds" type="hidden" value="" />
			<aui:input name="screenNavigationCategoryKey" type="hidden" value="<%= cpDefinitionLinkDisplayContext.getScreenNavigationCategoryKey() %>" />
			<aui:input name="type" type="hidden" value="<%= type %>" />
		</aui:form>

		<liferay-frontend:add-menu inline="<%= true %>">
			<liferay-frontend:add-menu-item id="addCommerceProductDefinition" title="<%= LanguageUtil.format(request, "add-x-product", type, true) %>" url="javascript:;" />
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpDefinitionLinkDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpDefinitionLinkDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= cpDefinitionLinkDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPDefinitionLinks();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />productDefinitionLinksContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= cpDefinitionLinkDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="cpDefinitionLinkInfoPanel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="cpDefinitionLinks"
			>
				<liferay-util:include page="/definition_link_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCPDefinitionLinkIds" type="hidden" />
				<aui:input name="screenNavigationCategoryKey" type="hidden" value="<%= cpDefinitionLinkDisplayContext.getScreenNavigationCategoryKey() %>" />
				<aui:input name="type" type="hidden" value="<%= type %>" />

				<div class="product-definition-links-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="cpDefinitionLinks"
						iteratorURL="<%= portletURL %>"
						searchContainer="<%= cpDefinitionLinkSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.model.CPDefinitionLink"
							cssClass="entry-display-style"
							keyProperty="CPDefinitionLinkId"
							modelVar="cpDefinitionLink"
						>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editCPDefinitionLink");
							rowURL.setParameter("cpDefinitionLinkId", String.valueOf(cpDefinitionLink.getCPDefinitionLinkId()));
							rowURL.setParameter("type", String.valueOf(cpDefinitionLink.getType()));

							CPDefinition cpDefinition2 = cpDefinitionLink.getCPDefinition2();
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="title"
								value="<%= HtmlUtil.escape(cpDefinition2.getTitle(languageId)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								property="priority"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-content"
								name="create-date"
								property="createDate"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/definition_link_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= cpDefinitionLinkSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<aui:script>
	function <portlet:namespace />deleteCPDefinitionLinks() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-products" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCPDefinitionLinkIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCPDefinitionLink" />');
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addCommerceProductDefinition').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'productDefinitionsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								$('#<portlet:namespace />cpDefinitionIds').val(selectedItems);

								var addCPDefinitionLinkFm = $('#<portlet:namespace />addCPDefinitionLinkFm');

								submitForm(addCPDefinitionLinkFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= cpDefinition.getTitle(languageId) %>" key="add-new-product-to-x" />',
					url: '<%= cpDefinitionLinkDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>