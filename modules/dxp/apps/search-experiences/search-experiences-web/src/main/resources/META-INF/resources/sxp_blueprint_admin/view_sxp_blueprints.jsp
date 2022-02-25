<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewSXPBlueprintsDisplayContext viewSXPBlueprintsDisplayContext = (ViewSXPBlueprintsDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_BLUEPRINTS_DISPLAY_CONTEXT);
%>

<frontend-data-set:headless-display
	apiURL="<%= viewSXPBlueprintsDisplayContext.getAPIURL() %>"
	creationMenu="<%= viewSXPBlueprintsDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= viewSXPBlueprintsDisplayContext.getFDSActionDropdownItems() %>"
	formName="fm"
	id="<%= SXPBlueprintAdminFDSNames.SXP_BLUEPRINTS %>"
	itemsPerPage="<%= 20 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= liferayPortletResponse.createRenderURL() %>"
	propsTransformer="sxp_blueprint_admin/js/view_sxp_blueprints/ViewSXPBlueprintsPropsTransformer"
	style="fluid"
/>

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