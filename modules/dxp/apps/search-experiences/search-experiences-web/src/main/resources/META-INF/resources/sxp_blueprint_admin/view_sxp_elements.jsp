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
ViewSXPElementsDisplayContext viewSXPElementsDisplayContext = (ViewSXPElementsDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_ELEMENTS_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteSXPElementURL",
			PortletURLBuilder.createActionURL(
				renderResponse
			).setActionName(
				"/sxp_blueprint_admin/delete_sxp_element"
			).setRedirect(
				currentURL
			).buildString()
		).put(
			"editSXPElementURL",
			PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCRenderCommandName(
				"/sxp_blueprint_admin/edit_sxp_element"
			).setRedirect(
				currentURL
			).buildString()
		).put(
			"hideSXPElementURL",
			PortletURLBuilder.createActionURL(
				renderResponse
			).setActionName(
				"/sxp_blueprint_admin/edit_sxp_element"
			).setCMD(
				"hide"
			).setRedirect(
				currentURL
			).setParameter(
				"hidden", true
			).buildString()
		).put(
			"redirectURL", currentURL
		).put(
			"showSXPElementURL",
			PortletURLBuilder.createActionURL(
				renderResponse
			).setActionName(
				"/sxp_blueprint_admin/edit_sxp_element"
			).setCMD(
				"hide"
			).setRedirect(
				currentURL
			).setParameter(
				"hidden", false
			).buildString()
		).build()
	%>'
	managementToolbarDisplayContext="<%= (ViewSXPElementsManagementToolbarDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_ELEMENTS_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT) %>"
	propsTransformer="sxp_blueprint_admin/js/view_sxp_elements/SXPElementEntriesManagementToolbarPropsTransformer"
	searchContainerId="sxpElementEntries"
	supportsBulkActions="<%= true %>"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			cssClass="blueprints-search-container"
			id="sxpElementEntries"
			searchContainer="<%= viewSXPElementsDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.search.experiences.model.SXPElement"
				keyProperty="sxpElementId"
				modelVar="sxpElement"
			>
				<%@ include file="/sxp_blueprint_admin/sxp_element_search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= viewSXPElementsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>