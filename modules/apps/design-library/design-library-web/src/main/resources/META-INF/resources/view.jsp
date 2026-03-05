<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewDesignLibraryAdminDisplayContext viewDesignLibraryAdminDisplayContext = new ViewDesignLibraryAdminDisplayContext(request);
%>

<div>
	<frontend-data-set:headless-display
		apiURL="<%= viewDesignLibraryAdminDisplayContext.getAPIURL() %>"
		emptyState="<%= viewDesignLibraryAdminDisplayContext.getEmptyState() %>"
		formName="fm"
		id="<%= DesignLibraryAdminFDSNames.ALL_DESIGN_LIBRARIES_SECTION %>"
		propsTransformer="{DesignLibraryAdminFDSPropsTransformer} from design-library-web"
	/>
</div>