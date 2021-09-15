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

String displayStyle = cpOptionCategoryDisplayContext.getDisplayStyle();

PortletURL portletURL = PortletURLBuilder.create(
	cpOptionCategoryDisplayContext.getPortletURL()
).setParameter(
	"searchContainerId", "cpOptionCategories"
).buildPortletURL();

request.setAttribute("view.jsp-portletURL", portletURL);

renderResponse.setTitle(LanguageUtil.get(request, "specifications"));
%>

<%@ include file="/navbar_specifications.jspf" %>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpOptionCategories"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= cpOptionCategoryDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= cpOptionCategoryDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>

		<c:if test="<%= cpOptionCategoryDisplayContext.hasPermission(CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION_CATEGORY) %>">
			<liferay-portlet:renderURL var="addProductOptionCategoryURL">
				<portlet:param name="mvcRenderCommandName" value="/cp_specification_options/edit_cp_option_category" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</liferay-portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-specification-group") %>'
					url="<%= addProductOptionCategoryURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= cpOptionCategoryDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpOptionCategoryDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpOptionCategoryDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"modified-date", "group", "priority"} %>'
			portletURL="<%= cpOptionCategoryDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= cpOptionCategoryDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCPOptionCategories();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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
				<aui:form action="<%= portletURL %>" method="post" name="fm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" />
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
									cssClass="font-weight-bold table-cell-expand"
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
	function <portlet:namespace />deleteCPOptionCategories() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-specification-groups" />'
			)
		) {
			var form = window.document['<portlet:namespace />fm'];

			form.setAttribute('method', 'post');
			form['<portlet:namespace /><%= Constants.CMD %>'].value =
				'<%= Constants.DELETE %>';
			form[
				'<portlet:namespace />deleteCPOptionCategoryIds'
			].value = Liferay.Util.listCheckedExcept(
				form,
				'<portlet:namespace />allRowIds'
			);

			submitForm(
				form,
				'<portlet:actionURL name="/cp_specification_options/edit_cp_option_category" />'
			);
		}
	}
</aui:script>