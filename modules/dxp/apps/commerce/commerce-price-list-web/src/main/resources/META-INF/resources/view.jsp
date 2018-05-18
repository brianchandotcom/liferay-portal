<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
								cssClass="important table-cell-content"
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