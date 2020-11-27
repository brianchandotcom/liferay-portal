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

PortletURL portletURL = commercePriceListDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commercePriceLists");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="commercePriceLists" />
</liferay-util:include>

<div id="<portlet:namespace />priceListsContainer">
	<div class="closed container-fluid container-fluid-max-xl sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
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
						searchContainer="<%= commercePriceListDisplayContext.getSearchContainer() %>"
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

							CommerceCatalog commerceCatalog = commercePriceListDisplayContext.fetchCommerceCatalog(commercePriceList);
							%>

							<liferay-ui:search-container-column-text
								cssClass="important table-cell-expand"
								href="<%= rowURL %>"
								property="name"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand"
								name="author"
								property="userName"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand"
								name="catalog"
								value="<%= (commerceCatalog == null) ? StringPool.BLANK : HtmlUtil.escape(commerceCatalog.getName()) %>"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-expand"
								name="create-date"
								property="createDate"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-expand"
								name="display-date"
								property="displayDate"
							/>

							<liferay-ui:search-container-column-status
								cssClass="table-cell-expand"
								name="status"
								status="<%= commercePriceList.getStatus() %>"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/price_list_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator
							displayStyle="list"
							markupView="lexicon"
						/>
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>