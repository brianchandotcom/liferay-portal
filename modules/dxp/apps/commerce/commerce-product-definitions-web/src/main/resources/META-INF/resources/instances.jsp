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
CPInstanceDisplayContext cpInstanceDisplayContext = (CPInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpInstanceDisplayContext.getCPDefinition();

long cpDefinitionId = cpInstanceDisplayContext.getCPDefinitionId();

List<CPDefinitionOptionRel> cpDefinitionOptionRels = cpInstanceDisplayContext.getCPDefinitionOptionRels();

SearchContainer<CPInstance> cpInstanceSearchContainer = cpInstanceDisplayContext.getSearchContainer();

PortletURL portletURL = cpInstanceDisplayContext.getPortletURL();

String displayStyle = cpInstanceDisplayContext.getDisplayStyle();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpInstances"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= cpInstanceDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>

		<liferay-portlet:renderURL var="addProductInstanceURL">
			<portlet:param name="mvcRenderCommandName" value="editProductInstance" />
			<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
		</liferay-portlet:renderURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				id="addSkuButton"
				title='<%= LanguageUtil.get(request, "add-sku") %>'
				url="<%= addProductInstanceURL.toString() %>"
			/>

			<c:if test="<%= !cpDefinition.isIgnoreSKUCombinations() %>">
				<liferay-portlet:actionURL name="editProductInstance" var="addProductInstancesURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_MULTIPLE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				</liferay-portlet:actionURL>

				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "generate-all-sku-combinations") %>'
					url="<%= addProductInstancesURL.toString() %>"
				/>
			</c:if>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-filter
			label="status"
			managementBarFilterItems="<%= cpInstanceDisplayContext.getManagementBarStatusFilterItems() %>"
			value="<%= cpInstanceDisplayContext.getManagementBarStatusFilterValue() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpInstanceDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpInstanceDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"sku", "create-date", "display-date"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<aui:form action="<%= portletURL.toString() %>" name="searchFm">
				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= cpInstanceDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPInstances();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />productInstancesContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= cpInstanceDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="cpInstanceInfoPanel" var="sidebarPanelURL" />

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="cpInstances"
			>
				<liferay-util:include page="/instance_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCPInstanceIds" type="hidden" />

				<liferay-ui:error exception="<%= NoSuchSkuContributorCPDefinitionOptionRelException.class %>" message="there-are-no-options-set-as-sku-contributor" />

				<div class="product-skus-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="cpInstances"
						iteratorURL="<%= portletURL %>"
						searchContainer="<%= cpInstanceSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.model.CPInstance"
							cssClass="entry-display-style"
							keyProperty="CPInstanceId"
							modelVar="cpInstance"
						>

							<%
							Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>> cpDefinitionOptionRelListMap = cpInstanceDisplayContext.parseCPInstanceDDMContent(cpInstance.getCPInstanceId());

							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editProductInstance");
							rowURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionId));
							rowURL.setParameter("cpInstanceId", String.valueOf(cpInstance.getCPInstanceId()));
							%>

							<c:choose>
								<c:when test='<%= displayStyle.equals("descriptive") %>'>
									<%@ include file="/instance_descriptive.jspf" %>
								</c:when>
								<c:when test='<%= displayStyle.equals("icon") %>'>

									<%
									row.setCssClass("entry-card lfr-asset-folder " + row.getCssClass());
									%>

									<liferay-ui:search-container-column-text>
										<liferay-frontend:icon-vertical-card
											actionJsp="/instance_action.jsp"
											actionJspServletContext="<%= application %>"
											icon="web-content"
											resultRow="<%= row %>"
											rowChecker="<%= cpInstanceDisplayContext.getRowChecker() %>"
											title="<%= HtmlUtil.escape(cpInstance.getSku()) %>"
											url="<%= rowURL.toString() %>"
										>
											<%@ include file="/instance_vertical_card.jspf" %>
										</liferay-frontend:icon-vertical-card>
									</liferay-ui:search-container-column-text>
								</c:when>
								<c:otherwise>
									<%@ include file="/instance_columns.jspf" %>
								</c:otherwise>
							</c:choose>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator
							displayStyle="<%= displayStyle %>"
							markupView="lexicon"
							searchContainer="<%= cpInstanceSearchContainer %>"
						/>
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<aui:script>
	function <portlet:namespace />deleteCPInstances() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-skus" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCPInstanceIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editProductInstance" />');
		}
	}

	function <portlet:namespace />updateCPDefinitionIgnoreSKUCombinations() {
		var form = AUI.$(document.<portlet:namespace />fm1);

		submitForm(form, '<portlet:actionURL name="editProductInstance" />');
	}
</aui:script>