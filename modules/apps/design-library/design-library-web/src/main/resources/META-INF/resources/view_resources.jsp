<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long designLibraryEntryId = (long)request.getAttribute(DesignLibraryConstants.DESIGN_LIBRARY_ENTRY_ID_KEY);

DesignLibraryResourcesDisplayContext designLibraryResourcesDisplayContext = new DesignLibraryResourcesDisplayContext(request, liferayPortletResponse);
%>

<div>
	<div>
		<react:component
			module="{DesignLibraryBreadcrumb} from design-library-web"
			props="<%= designLibraryResourcesDisplayContext.getBreadcrumbProps(designLibraryEntryId) %>"
		/>
	</div>

	<div class="design-library-fds-wrapper">
		<frontend-data-set:headless-display
			apiURL="<%= designLibraryResourcesDisplayContext.getAPIURL() %>"
			emptyState="<%= designLibraryResourcesDisplayContext.getEmptyState() %>"
			formName="fm"
			id="<%= DesignLibraryAdminFDSNames.DESIGN_LIBRARY_RESOURCES %>"
			propsTransformer="{DesignLibraryResourcesFDSPropsTransformer} from design-library-web"
		/>
	</div>
</div>