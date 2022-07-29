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
String specificationNavbarItemKey = ParamUtil.getString(request, "specificationNavbarItemKey", "specification-groups");

CPOptionCategoryDisplayContext cpOptionCategoryDisplayContext = (CPOptionCategoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = PortletURLBuilder.create(
	cpOptionCategoryDisplayContext.getPortletURL()
).setParameter(
	"searchContainerId", "cpOptionCategories"
).buildPortletURL();

request.setAttribute("view.jsp-portletURL", portletURL);

renderResponse.setTitle(LanguageUtil.get(request, "specifications"));
%>

<%@ include file="/navbar_specifications.jspf" %>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new CPOptionCategoryManagementToolbarDisplayContext(cpOptionCategoryDisplayContext, request, liferayPortletRequest, liferayPortletResponse) %>"
	propsTransformer="js/CPOptionCategoryManagementToolbarPropsTransformer"
/>

<div id="<portlet:namespace />productOptionCategoriesContainer">
	<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= cpOptionCategoryDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/cp_specification_options/cp_option_category_info_panel" var="sidebarPanelURL" />

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="cpOptionCategories"
			>
				<liferay-util:include page="/cp_option_category_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<clay:container-fluid>
				<portlet:actionURL name="/cp_specification_options/edit_cp_option_category" var="editCPOptionCategoryURL" />

				<aui:form action="<%= editCPOptionCategoryURL %>" method="post" name="fm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
					<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
					<aui:input name="deleteCPOptionCategoryIds" type="hidden" />

					<div class="product-option-categories-container" id="<portlet:namespace />entriesContainer">
						<liferay-ui:search-container
							id="cpOptionCategories"
							iteratorURL="<%= portletURL %>"
							searchContainer="<%= cpOptionCategoryDisplayContext.getSearchContainer() %>"
						>
							<liferay-ui:search-container-row
								className="com.liferay.commerce.product.model.CPOptionCategory"
								keyProperty="CPOptionCategoryId"
								modelVar="cpOptionCategory"
							>

								<%
								PortletURL rowURL = PortletURLBuilder.createRenderURL(
									renderResponse
								).setMVCRenderCommandName(
									"/cp_specification_options/edit_cp_option_category"
								).setRedirect(
									currentURL
								).setParameter(
									"cpOptionCategoryId", cpOptionCategory.getCPOptionCategoryId()
								).buildPortletURL();
								%>

								<liferay-ui:search-container-column-text
									cssClass="font-weight-bold important table-cell-expand"
									href="<%= rowURL %>"
									name="group"
									value="<%= HtmlUtil.escape(cpOptionCategory.getTitle(locale)) %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand"
									property="priority"
								/>

								<liferay-ui:search-container-column-date
									cssClass="table-cell-expand"
									name="modified-date"
									property="modifiedDate"
								/>

								<liferay-ui:search-container-column-jsp
									cssClass="entry-action-column"
									path="/option_category_action.jsp"
								/>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator
								displayStyle="list"
								markupView="lexicon"
							/>
						</liferay-ui:search-container>
					</div>
				</aui:form>
			</clay:container-fluid>
		</div>
	</div>
</div>