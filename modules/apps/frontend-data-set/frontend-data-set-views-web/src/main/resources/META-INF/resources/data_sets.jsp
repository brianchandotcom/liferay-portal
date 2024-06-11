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
			module="{DataSets} from frontend-data-set-views-web"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"editDataSetURL", fdsViewsDisplayContext.getEditDataSetURL()
				).put(
					"namespace", liferayPortletResponse.getNamespace()
				).put(
					"permissionsURL", fdsViewsDisplayContext.getDSMDataSetPermissionsURL()
				).put(
					"restApplications", fdsViewsDisplayContext.getRESTApplicationsJSONArray()
				).build()
			%>'
		/>
	</c:when>
	<c:otherwise>
		<react:component
			module="{FDSEntries} from frontend-data-set-views-web"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"fdsViewsURL", fdsViewsDisplayContext.getFDSViewsURL()
				).put(
					"namespace", liferayPortletResponse.getNamespace()
				).put(
					"permissionsURL", fdsViewsDisplayContext.getFDSEntryPermissionsURL()
				).put(
					"restApplications", fdsViewsDisplayContext.getRESTApplicationsJSONArray()
				).build()
			%>'
		/>
	</c:otherwise>
</c:choose>