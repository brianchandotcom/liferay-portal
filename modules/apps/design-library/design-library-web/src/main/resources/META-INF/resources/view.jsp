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

<div class="design-library-fds-wrapper">
	<frontend-data-set:headless-display
		apiURL="<%= viewDesignLibraryAdminDisplayContext.getAPIURL() %>"
		emptyState="<%= viewDesignLibraryAdminDisplayContext.getEmptyState() %>"
		formName="fm"
		id="<%= DesignLibraryAdminFDSNames.DESIGN_LIBRARIES %>"
		propsTransformer="{DesignLibraryAdminFDSPropsTransformer} from design-library-web"
		selectedItemsKey="id"
		selectionType="multiple"
	/>
</div>