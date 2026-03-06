<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewDesignLibraryAdminDisplayContext viewDesignLibraryAdminDisplayContext = new ViewDesignLibraryAdminDisplayContext(request);

String designLibraryEntryName = "A Design Library";
%>

<div>
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

<div>
	<portlet:renderURL var="designLibraryURL">
		<portlet:param name="mvcRenderCommandName" value="/design_library/view_design_library_dashboard" />
		<portlet:param name="designLibraryEntryId" value="1234567890" />
	</portlet:renderURL>

	<a href="<%= designLibraryURL %>"><%= designLibraryEntryName %></a>
</div>
