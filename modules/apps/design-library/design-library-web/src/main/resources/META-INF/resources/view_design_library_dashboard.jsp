<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long designLibraryId = (long)request.getAttribute(DesignLibraryConstants.DESIGN_LIBRARY_ENTRY_ID_KEY);
%>

<%
DesignLibraryDashboardDisplayContext designLibraryDashboardDisplayContext = new DesignLibraryDashboardDisplayContext(request);
%>

<div>
	<div>
		<react:component
			module="{DesignLibraryBreadcrumb} from design-library-web"
			props='<%= designLibraryDashboardDisplayContext.getHeaderProps(designLibraryId) %>'
		/>
	</div>

	<div class="design-library-fds-wrapper">
		<frontend-data-set:headless-display
			apiURL="<%= designLibraryDashboardDisplayContext.getAPIURL() %>"
			emptyState="<%= designLibraryDashboardDisplayContext.getEmptyState() %>"
			formName="fm"
			id="<%= DesignLibraryAdminFDSNames.DESIGN_LIBRARY_DASHBOARD %>"
			propsTransformer="{DesignLibraryDashboardFDSPropsTransformer} from design-library-web"
		/>
	</div>
</div>