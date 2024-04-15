<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathProxy() + application.getContextPath() + "/css/configuration.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<clay:container-fluid
	cssClass="mt-4"
>
	<clay:management-toolbar
		managementToolbarDisplayContext="<%= new PortalDefaultPermissionsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, portalDefaultPermissionsCompanyConfigurationDisplayContext.getSearchContainer()) %>"
	/>

	<div data-qa-id="portal-default-permissions-search-container">
		<liferay-ui:search-container
			id="portalDefaultPermissionsSearchContainer"
			searchContainer="<%= portalDefaultPermissionsCompanyConfigurationDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.defaultpermissions.web.internal.search.PortalDefaultPermissionsSearchEntry"
				modelVar="portalDefaultPermissionsSearchEntry"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-cell-minw-200 table-title"
					name="asset-type"
					value="<%= portalDefaultPermissionsSearchEntry.getLabel() %>"
				/>

				<liferay-ui:search-container-column-jsp
					path="/configuration/portal_default_permissions_entry_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="list"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</div>
</clay:container-fluid>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"namespace", liferayPortletResponse.getNamespace()
		).build()
	%>'
	module="{portalDefaultPermissionsCompanyConfiguration} from portal-defaultpermissions-web"
/>