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

<c:choose>
	<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPD-15729") %>'>
		<react:component
			module="{DataSets} from frontend-data-set-admin-web"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"editDataSetURL", fdsAdminDisplayContext.getEditDataSetURL()
				).put(
					"namespace", liferayPortletResponse.getNamespace()
				).put(
					"permissionsURL", fdsAdminDisplayContext.getFDSViewPermissionsURL()
				).put(
					"resolvedRESTSchemas", fdsAdminDisplayContext.getRESTApplicationResolvedSchemasJSONArray()
				).put(
					"restApplications", fdsAdminDisplayContext.getRESTApplicationsJSONArray()
				).build()
			%>'
		/>
	</c:when>
	<c:otherwise>
		<react:component
			module="{FDSEntries} from frontend-data-set-admin-web"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"fdsViewsURL", fdsAdminDisplayContext.getFDSViewsURL()
				).put(
					"namespace", liferayPortletResponse.getNamespace()
				).put(
					"permissionsURL", fdsAdminDisplayContext.getFDSEntryPermissionsURL()
				).put(
					"resolvedRESTSchemas", fdsAdminDisplayContext.getRESTApplicationResolvedSchemasJSONArray()
				).put(
					"restApplications", fdsAdminDisplayContext.getRESTApplicationsJSONArray()
				).build()
			%>'
		/>
	</c:otherwise>
</c:choose>