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
String specificationNavbarItemKey = ParamUtil.getString(request, "specificationNavbarItemKey", "specification-labels");

CPSpecificationOptionDisplayContext cpSpecificationOptionDisplayContext = (CPSpecificationOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String displayStyle = cpSpecificationOptionDisplayContext.getDisplayStyle();

PortletURL portletURL = cpSpecificationOptionDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "cpSpecificationOptions");

request.setAttribute("view.jsp-portletURL", portletURL);

renderResponse.setTitle(LanguageUtil.get(request, "specifications"));
%>

<%@ include file="/navbar_specifications.jspf" %>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpSpecificationOptions"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= cpSpecificationOptionDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= cpSpecificationOptionDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>

		<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION_CATEGORY) %>">
			<liferay-portlet:renderURL var="addProductSpecificationOptionURL">
				<portlet:param name="mvcRenderCommandName" value="/cp_specification_options/edit_cp_specification_option" />
			</liferay-portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-specification-label") %>'
					url="<%= addProductSpecificationOptionURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			label="use-in-faceted-navigation"
			navigationKeys='<%= new String[] {"all", "yes", "no"} %>'
			portletURL="<%= cpSpecificationOptionDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpSpecificationOptionDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpSpecificationOptionDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"group", "label", "modified-date"} %>'
			portletURL="<%= cpSpecificationOptionDisplayContext.getPortletURL() %>"
		/>

		<li>
			<liferay-commerce:search-input
				actionURL="<%= cpSpecificationOptionDisplayContext.getPortletURL() %>"
				formName="searchFm"
			/>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= cpSpecificationOptionDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCPSpecificationOptions();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />productSpecificationOptionsContainer">
	<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= cpSpecificationOptionDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/cp_specification_options/cp_specification_option_info_panel" var="sidebarPanelURL" />

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="cpSpecificationOptions"
			>
				<liferay-util:include page="/cp_specification_option_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<clay:container-fluid>
				<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" />
					<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
					<aui:input name="deleteCPSpecificationOptionIds" type="hidden" />

					<div class="product-specification-options-container" id="<portlet:namespace />entriesContainer">
						<liferay-ui:search-container
							id="cpSpecificationOptions"
							iteratorURL="<%= portletURL %>"
							searchContainer="<%= cpSpecificationOptionDisplayContext.getSearchContainer() %>"
						>
							<liferay-ui:search-container-row
								className="com.liferay.commerce.product.model.CPSpecificationOption"
								keyProperty="CPSpecificationOptionId"
								modelVar="cpSpecificationOption"
							>

								<%
								PortletURL rowURL = renderResponse.createRenderURL();

								rowURL.setParameter("mvcRenderCommandName", "/cp_specification_options/edit_cp_specification_option");
								rowURL.setParameter("redirect", currentURL);
								rowURL.setParameter("cpSpecificationOptionId", String.valueOf(cpSpecificationOption.getCPSpecificationOptionId()));
								%>

								<liferay-ui:search-container-column-text
									cssClass="important table-cell-expand"
									href="<%= rowURL %>"
									name="label"
									value="<%= HtmlUtil.escape(cpSpecificationOption.getTitle(locale)) %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand"
									name="default-group"
									value="<%= HtmlUtil.escape(cpSpecificationOptionDisplayContext.getCPOptionCategoryTitle(cpSpecificationOption)) %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand"
									name="use-in-faceted-navigation"
									value='<%= LanguageUtil.get(request, cpSpecificationOption.isFacetable() ? "yes" : "no") %>'
								/>

								<liferay-ui:search-container-column-date
									cssClass="table-cell-expand"
									name="modified-date"
									property="modifiedDate"
								/>

								<liferay-ui:search-container-column-jsp
									cssClass="entry-action-column"
									path="/specification_option_action.jsp"
								/>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator
								displayStyle="<%= displayStyle %>"
								markupView="lexicon"
							/>
						</liferay-ui:search-container>
					</div>
				</aui:form>
			</clay:container-fluid>
		</div>
	</div>
</div>

<aui:script>
	function <portlet:namespace />deleteCPSpecificationOptions() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-specification-labels" />'
			)
		) {
			var form = window.document['<portlet:namespace />fm'];

			form.setAttribute('method', 'post');
			form['<portlet:namespace /><%= Constants.CMD %>'].value =
				'<%= Constants.DELETE %>';
			form[
				'<portlet:namespace />deleteCPSpecificationOptionIds'
			].value = Liferay.Util.listCheckedExcept(
				form,
				'<portlet:namespace />allRowIds'
			);

			submitForm(
				form,
				'<portlet:actionURL name="/cp_specification_options/edit_cp_specification_option" />'
			);
		}
	}
</aui:script>