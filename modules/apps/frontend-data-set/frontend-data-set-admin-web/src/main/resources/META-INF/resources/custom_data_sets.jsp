<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
portletDisplay.setBeta(true);
%>

<div>
	<react:component
		module="{CustomDataSets} from frontend-data-set-admin-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"editDataSetURL", fdsAdminDisplayContext.getEditDataSetURL()
			).put(
				"hasAddDataSetObjectEntryPermission", fdsAdminDisplayContext.hasAddDataSetObjectEntryPermission()
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"permissionsURL", fdsAdminDisplayContext.getDataSetPermissionsURL()
			).put(
				"resolvedRESTSchemas", fdsAdminDisplayContext.getRESTApplicationResolvedSchemasJSONArray()
			).put(
				"restApplications", fdsAdminDisplayContext.getRESTApplicationsJSONArray()
			).build()
		%>'
	/>
</div>