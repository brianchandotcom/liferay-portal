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
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommercePriceList> commercePriceListSearchContainer = commercePriceListDisplayContext.getSearchContainer();

PortletURL portletURL = commercePriceListDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commercePriceLists");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<%@ include file="/price_list_navbar.jspf" %>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="commercePriceLists" />
</liferay-util:include>

<div id="<portlet:namespace />priceListsContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= commercePriceListDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="commercePriceListInfoPanel" var="sidebarPanelURL" />

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="commercePriceLists"
			>
				<liferay-util:include page="/price_list_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
				<aui:input name="deleteCommercePriceListIds" type="hidden" />

				<div class="price-lists-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="commercePriceLists"
						searchContainer="<%= commercePriceListSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.price.list.model.CommercePriceList"
							cssClass="entry-display-style"
							keyProperty="commercePriceListId"
							modelVar="commercePriceList"
						>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editCommercePriceList");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("commercePriceListId", String.valueOf(commercePriceList.getCommercePriceListId()));
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								property="name"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="author"
								property="userName"
							/>

							<liferay-ui:search-container-column-status
								cssClass="table-cell-content"
								name="status"
								status="<%= commercePriceList.getStatus() %>"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-content"
								name="create-date"
								property="createDate"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-content"
								name="display-date"
								property="displayDate"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								property="priority"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/price_list_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator
							displayStyle="list"
							markupView="lexicon"
							searchContainer="<%= commercePriceListSearchContainer %>"
						/>
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>