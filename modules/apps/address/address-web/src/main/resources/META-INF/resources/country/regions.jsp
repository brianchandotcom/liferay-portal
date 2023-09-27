<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SearchContainer<Region> regionSearchContainer = RegionSearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new RegionsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, regionSearchContainer) %>"
	propsTransformer="js/RegionsManagementToolbarPropsTransformer"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="regionIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= regionSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Region"
				keyProperty="regionId"
				modelVar="region"
			>

				<%
				List<String> availableActions = new ArrayList<>();

				availableActions.add("deleteRegions");

				if (region.getActive()) {
					availableActions.add("deactivateRegions");
				}
				else {
					availableActions.add("activateRegions");
				}

				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(availableActions)
					).build());
				%>

				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/address/edit_region" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="countryId" value="<%= String.valueOf(region.getCountryId()) %>" />
					<portlet:param name="regionId" value="<%= String.valueOf(region.getRegionId()) %>" />
				</portlet:renderURL>

				<%
				if (!PortalPermissionUtil.contains(permissionChecker, ActionKeys.MANAGE_COUNTRIES)) {
					rowURL = null;
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="font-weight-bold important table-cell-expand-smallest"
					href="<%= rowURL %>"
					name="name"
					value="<%= HtmlUtil.escape(region.getTitle(LocaleUtil.toLanguageId(locale))) %>"
				/>

				<liferay-ui:search-container-column-text
					name="region-code"
					property="regionCode"
				/>

				<liferay-ui:search-container-column-text
					name="active"
				>
					<liferay-ui:icon
						cssClass='<%= region.isActive() ? "text-success" : "text-danger" %>'
						icon='<%= region.isActive() ? "check" : "times" %>'
						markupView="lexicon"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="priority"
					property="position"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="table-column-text-end"
					path="/region_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>