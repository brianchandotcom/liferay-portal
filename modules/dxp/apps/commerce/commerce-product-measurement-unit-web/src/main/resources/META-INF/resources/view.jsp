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
CPMeasurementUnitsDisplayContext cpMeasurementUnitsDisplayContext = (CPMeasurementUnitsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CPMeasurementUnit> cpMeasurementUnitSearchContainer = cpMeasurementUnitsDisplayContext.getSearchContainer();

int type = cpMeasurementUnitsDisplayContext.getType();

boolean hasManageCPMeasurementUnitsPermission = CPMeasurementUnitPermission.contains(permissionChecker, scopeGroupId, CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);
%>

<clay:navigation-bar
	inverted="<%= false %>"
	items="<%= cpMeasurementUnitsDisplayContext.getNavigationItems() %>"
/>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpMeasurementUnits"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= cpMeasurementUnitsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpMeasurementUnitsDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpMeasurementUnitsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= cpMeasurementUnitsDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= cpMeasurementUnitsDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= hasManageCPMeasurementUnitsPermission %>">
			<portlet:renderURL var="addCPMeasurementUnitURL">
				<portlet:param name="mvcRenderCommandName" value="editCPMeasurementUnit" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="type" value="<%= String.valueOf(type) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-measurement-unit") %>'
					url="<%= addCPMeasurementUnitURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCPMeasurementUnitsPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPMeasurementUnits();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCPMeasurementUnit" var="editCPMeasurementUnitActionURL" />

	<aui:form action="<%= editCPMeasurementUnitActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCPMeasurementUnitIds" type="hidden" />

		<liferay-ui:search-container
			id="cpMeasurementUnits"
			searchContainer="<%= cpMeasurementUnitSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.product.model.CPMeasurementUnit"
				keyProperty="CPMeasurementUnitId"
				modelVar="cpMeasurementUnit"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "editCPMeasurementUnit");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("cpMeasurementUnitId", String.valueOf(cpMeasurementUnit.getCPMeasurementUnitId()));
				rowURL.setParameter("type", String.valueOf(type));
				%>

				<liferay-ui:search-container-column-text
					cssClass="important table-cell-content"
					href="<%= rowURL %>"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="key"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="ratio-to-primary"
					property="rate"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="primary"
					value='<%= LanguageUtil.get(request, cpMeasurementUnit.isPrimary() ? "yes" : "no") %>'
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="priority"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/measurement_unit_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCPMeasurementUnits() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-measurement-units" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCPMeasurementUnitIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>