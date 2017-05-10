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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-product-options");

CPOptionDisplayContext cpOptionDisplayContext = (CPOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer cpOptionSearchContainer = cpOptionDisplayContext.getSearchContainer();

String displayStyle = cpOptionDisplayContext.getDisplayStyle();

PortletURL portletURL = cpOptionDisplayContext.getPortletURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("searchContainerId", "cpOptions");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-portlet:renderURL varImpl="viewProductOptionsURL">
	<portlet:param name="toolbarItem" value="view-all-product-options" />
	<portlet:param name="jspPage" value="/view.jsp" />
</liferay-portlet:renderURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= viewProductOptionsURL.toString() %>"
			label="options"
			selected='<%= toolbarItem.equals("view-all-product-options") %>'
		/>
	</aui:nav>

	<aui:form action="<%= portletURL.toString() %>" name="searchFm">
		<aui:nav-bar-search>
			<liferay-ui:input-search markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:form>
</aui:nav-bar>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="cpOptions" />
</liferay-util:include>

<div id="<portlet:namespace />productOptionsContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= cpOptionDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="cpOptionInfoPanel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="cpOptions"
			>
				<liferay-util:include page="/option_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
				<aui:input name="deleteCPOptionIds" type="hidden" />

				<div class="product-options-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="cpOptions"
						searchContainer="<%= cpOptionSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.model.CPOption"
							cssClass="entry-display-style"
							keyProperty="CPOptionId"
							modelVar="cpOption"
						>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editProductOption");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("cpOptionId", String.valueOf(cpOption.getCPOptionId()));
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="title"
								value="<%= HtmlUtil.escape(cpOption.getTitle(locale)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="author"
								property="userName"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								property="facetable"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								property="required"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="sku-contributor"
								property="skuContributor"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-content"
								name="create-date"
								property="createDate"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-content"
								name="modified-date"
								property="modifiedDate"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/option_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= cpOptionSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<liferay-portlet:renderURL var="addProductOptionURL">
	<portlet:param name="mvcRenderCommandName" value="editProductOption" />
	<portlet:param name="backURL" value="<%= PortalUtil.getCurrentCompleteURL(request) %>" />
	<portlet:param name="toolbarItem" value="view-product-option-details" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-option") %>' url="<%= addProductOptionURL.toString() %>" />
</liferay-frontend:add-menu>