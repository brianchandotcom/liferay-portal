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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-media-types");

CPMediaTypeDisplayContext cpMediaTypeDisplayContext = (CPMediaTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer cpMediaTypeSearchContainer = cpMediaTypeDisplayContext.getSearchContainer();

String displayStyle = cpMediaTypeDisplayContext.getDisplayStyle();

PortletURL portletURL = cpMediaTypeDisplayContext.getPortletURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("searchContainerId", "cpMediaTypes");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-portlet:renderURL varImpl="viewMediaTypesURL">
	<portlet:param name="toolbarItem" value="view-all-media-types" />
	<portlet:param name="jspPage" value="/view.jsp" />
</liferay-portlet:renderURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= viewMediaTypesURL.toString() %>"
			label="media-types"
			selected='<%= toolbarItem.equals("view-all-media-types") %>'
		/>
	</aui:nav>

	<aui:form action="<%= portletURL.toString() %>" name="searchFm">
		<aui:nav-bar-search>
			<liferay-ui:input-search markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:form>
</aui:nav-bar>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="cpMediaTypes" />
</liferay-util:include>

<div id="<portlet:namespace />mediaTypesContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= cpMediaTypeDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="cpMediaTypeInfoPanel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="cpMediaTypes"
			>
				<liferay-util:include page="/media_type_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
				<aui:input name="deleteCPMediaTypeIds" type="hidden" />

				<div class="media-types-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="cpMediaTypes"
						searchContainer="<%= cpMediaTypeSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.model.CPMediaType"
							cssClass="entry-display-style"
							keyProperty="CPMediaTypeId"
							modelVar="cpMediaType"
						>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editMediaType");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("cpMediaTypeId", String.valueOf(cpMediaType.getCPMediaTypeId()));
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="id"
								value="<%= String.valueOf(cpMediaType.getCPMediaTypeId()) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="title"
								value="<%= HtmlUtil.escape(cpMediaType.getTitle(locale)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="priority"
								property="priority"
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
								path="/media_type_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= cpMediaTypeSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<liferay-portlet:renderURL var="addMediaTypeURL">
	<portlet:param name="mvcRenderCommandName" value="editMediaType" />
	<portlet:param name="backURL" value="<%= PortalUtil.getCurrentCompleteURL(request) %>" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-media-type") %>' url="<%= addMediaTypeURL.toString() %>" />
</liferay-frontend:add-menu>