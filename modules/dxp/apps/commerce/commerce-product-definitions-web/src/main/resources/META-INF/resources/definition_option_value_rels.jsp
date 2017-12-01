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
CPDefinitionOptionValueRelDisplayContext cpDefinitionOptionValueRelDisplayContext = (CPDefinitionOptionValueRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionOptionValueRelDisplayContext.getCPDefinition();

long cpDefinitionOptionRelId = cpDefinitionOptionValueRelDisplayContext.getCPDefinitionOptionRelId();

SearchContainer<CPDefinitionOptionValueRel> cpDefinitionOptionValueRelSearchContainer = cpDefinitionOptionValueRelDisplayContext.getSearchContainer();

PortletURL portletURL = cpDefinitionOptionValueRelDisplayContext.getPortletURL();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-commerce-product-definition-option-value-rels");

portletURL.setParameter("toolbarItem", toolbarItem);

request.setAttribute("view.jsp-portletURL", portletURL);

PortletURL productOptionRelsURL = renderResponse.createRenderURL();

productOptionRelsURL.setParameter("mvcRenderCommandName", "editProductDefinition");
productOptionRelsURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));
productOptionRelsURL.setParameter("screenNavigationCategoryKey", cpDefinitionOptionValueRelDisplayContext.getScreenNavigationCategoryKey());
%>

<%@ include file="/definition_option_rel_navbar.jspf" %>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpDefinitionOptionValueRels"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= cpDefinitionOptionValueRelDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= cpDefinitionOptionValueRelDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpDefinitionOptionValueRelDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpDefinitionOptionValueRelDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority", "title", "create-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= cpDefinitionOptionValueRelDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPDefinitionOptionValueRels();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />productDefinitionOptionRelsContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= cpDefinitionOptionValueRelDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="cpDefinitionOptionValueRelInfoPanel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="cpDefinitionOptionValueRels"
			>
				<liferay-util:include page="/definition_option_value_rel_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCPDefinitionOptionValueRelIds" type="hidden" />

				<div class="product-definition-option-rels-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="cpDefinitionOptionValueRels"
						iteratorURL="<%= portletURL %>"
						searchContainer="<%= cpDefinitionOptionValueRelSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.model.CPDefinitionOptionValueRel"
							cssClass="entry-display-style"
							keyProperty="CPDefinitionOptionValueRelId"
							modelVar="cpDefinitionOptionValueRel"
						>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editProductDefinitionOptionValueRel");
							rowURL.setParameter("cpDefinitionOptionValueRelId", String.valueOf(cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId()));
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="title"
								value="<%= HtmlUtil.escape(cpDefinitionOptionValueRel.getTitle(languageId)) %>"
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
								path="/definition_option_value_rel_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= cpDefinitionOptionValueRelSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<liferay-portlet:renderURL var="addProductDefinitionOptionValueRelURL">
	<portlet:param name="mvcRenderCommandName" value="editProductDefinitionOptionValueRel" />
	<portlet:param name="cpDefinitionOptionRelId" value="<%= String.valueOf(cpDefinitionOptionRelId) %>" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-option-value") %>' url="<%= addProductDefinitionOptionValueRelURL.toString() %>" />
</liferay-frontend:add-menu>

<aui:script>
	function <portlet:namespace />deleteCPDefinitionOptionValueRels() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-option-values") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCPDefinitionOptionValueRelIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editProductDefinitionOptionValueRel" />');
		}
	}
</aui:script>