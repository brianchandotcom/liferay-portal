<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewSXPBlueprintsDisplayContext viewSXPBlueprintsDisplayContext = (ViewSXPBlueprintsDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_BLUEPRINTS_DISPLAY_CONTEXT);
%>

<aui:form action="<%= viewSXPBlueprintsDisplayContext.getPortletURL() %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= String.valueOf(viewSXPBlueprintsDisplayContext.getPortletURL()) %>" />

	<frontend-data-set:headless-display
		apiURL="<%= viewSXPBlueprintsDisplayContext.getAPIURL() %>"
		bulkActionDropdownItems="<%= viewSXPBlueprintsDisplayContext.getBulkActionDropdownItems() %>"
		creationMenu="<%= viewSXPBlueprintsDisplayContext.getCreationMenu() %>"
		fdsActionDropdownItems="<%= viewSXPBlueprintsDisplayContext.getFDSActionDropdownItems() %>"
		formName="fm"
		id="<%= SXPBlueprintAdminFDSNames.SXP_BLUEPRINTS %>"
		propsTransformer="sxp_blueprint_admin/js/view_sxp_blueprints/ViewSXPBlueprintsPropsTransformer"
		selectedItemsKey="id"
		selectionType="multiple"
		style="fluid"
	/>
</aui:form>

<div id="<portlet:namespace />addSXPBlueprint">
	<react:component
		module="sxp_blueprint_admin/js/view_sxp_blueprints/AddSXPBlueprintModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"contextPath", application.getContextPath()
			).put(
				"defaultLocale", LocaleUtil.toLanguageId(LocaleUtil.getDefault())
			).put(
				"editSXPBlueprintURL",
				PortletURLBuilder.createRenderURL(
					renderResponse
				).setMVCRenderCommandName(
					"/sxp_blueprint_admin/edit_sxp_blueprint"
				).buildString()
			).put(
				"portletNamespace", liferayPortletResponse.getNamespace()
			).build()
		%>'
	/>
</div>

<liferay-frontend:component
	module="sxp_blueprint_admin/js/utils/openInitialSuccessToastHandler"
/>