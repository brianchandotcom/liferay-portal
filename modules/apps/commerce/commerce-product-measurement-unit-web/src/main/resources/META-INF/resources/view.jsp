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
CPMeasurementUnitsDisplayContext cpMeasurementUnitsDisplayContext = (CPMeasurementUnitsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= cpMeasurementUnitsDisplayContext.hasManageCPMeasurementUnitsPermission() %>">
	<clay:navigation-bar
		inverted="<%= false %>"
		navigationItems="<%= cpMeasurementUnitsDisplayContext.getNavigationItems() %>"
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

			<portlet:renderURL var="addCPMeasurementUnitURL">
				<portlet:param name="mvcRenderCommandName" value="/cp_measurement_unit/edit_cp_measurement_unit" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="type" value="<%= String.valueOf(cpMeasurementUnitsDisplayContext.getType()) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-measurement-unit") %>'
					url="<%= addCPMeasurementUnitURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCPMeasurementUnits();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<div class="container-fluid container-fluid-max-xl">
		<portlet:actionURL name="/cp_measurement_unit/edit_cp_measurement_unit" var="editCPMeasurementUnitActionURL" />

		<aui:form action="<%= editCPMeasurementUnitActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteCPMeasurementUnitIds" type="hidden" />

			<liferay-ui:search-container
				id="cpMeasurementUnits"
				searchContainer="<%= cpMeasurementUnitsDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.product.model.CPMeasurementUnit"
					keyProperty="CPMeasurementUnitId"
					modelVar="cpMeasurementUnit"
				>
					<liferay-ui:search-container-column-text
						cssClass="font-weight-bold table-cell-expand"
						href='<%=
							PortletURLBuilder.createRenderURL(
								renderResponse
							).setMVCRenderCommandName(
								"/cp_measurement_unit/edit_cp_measurement_unit"
							).setRedirect(
								currentURL
							).setParameter(
								"cpMeasurementUnitId", cpMeasurementUnit.getCPMeasurementUnitId()
							).setParameter(
								"type", cpMeasurementUnitsDisplayContext.getType()
							).buildPortletURL()
						%>'
						name="name"
						value="<%= HtmlUtil.escape(cpMeasurementUnit.getName(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						name="key"
						value="<%= HtmlUtil.escape(cpMeasurementUnit.getKey()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="ratio-to-primary"
						property="rate"
					/>

					<liferay-ui:search-container-column-text
						name="primary"
					>
						<c:choose>
							<c:when test="<%= cpMeasurementUnit.isPrimary() %>">
								<liferay-ui:icon
									cssClass="commerce-admin-icon-check"
									icon="check"
									markupView="lexicon"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:icon
									cssClass="commerce-admin-icon-times"
									icon="times"
									markupView="lexicon"
								/>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
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
			if (
				confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-measurement-units" />'
				)
			) {
				var form = window.document['<portlet:namespace />fm'];

				form[
					'<portlet:namespace />deleteCPMeasurementUnitIds'
				].value = Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				);

				submitForm(form);
			}
		}
	</aui:script>
</c:if>